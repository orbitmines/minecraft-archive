package com.orbitmines.world_generator;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import org.bukkit.Chunk;
import org.bukkit.Server;
import org.bukkit.World;

import java.text.NumberFormat;
import java.util.*;

public class WorldChunkCounterTask implements Runnable {
    // general task-related reference data
    private transient Server server = null;
    private transient World world = null;
    private transient WorldFileData worldData;
    private transient boolean readyToGo = false;
    private transient int taskID = -1;
    private transient int chunksPerRun = 1;

    private transient long lastRep = System.currentTimeMillis();

    // values for the spiral pattern check which fills out the map to the border
    private transient int x = 0;
    private transient int z = 0;
    private transient boolean isZLeg = false;
    private transient boolean isNeg = false;
    private transient int length = -1;
    private transient int current = 0;
    private List<CoordXZ> storedChunks = new LinkedList<>();
    private Set<CoordXZ> originalChunks = new HashSet<>();
    private transient CoordXZ lastChunk = new CoordXZ(0, 0);

    private transient int reportTotal = 0;
    private transient int reportNum = 0;

    private transient int count = 0;

    private static final int BORDER_X = 15000;
    private static final int BORDER_Z = 15000;

    private transient List<CoordXZ> unloaded = new ArrayList<>();
    private transient long proccesedPause = -1;

    public WorldChunkCounterTask(Server theServer, World world, int chunksPerRun) {
        this.server = theServer;
        this.chunksPerRun = chunksPerRun;

        this.world = world;
        this.worldData = WorldFileData.create(world);

        this.x = 0;
        this.z = 0;

        // keep track of the chunks which are already loaded when the task starts, to not unload them
        Chunk[] originals = world.getLoadedChunks();
        for (Chunk original : originals) {
            originalChunks.add(new CoordXZ(original.getX(), original.getZ()));
        }

        this.readyToGo = true;

        lastRep = System.currentTimeMillis();
        unloaded.add(new CoordXZ(0, 0));
    }

    @Override
    public void run() {
        if (server == null || !readyToGo)
            return;

        if (reportNum != 0 && reportNum % 10000 == 0) {
            proccesedPause = System.currentTimeMillis();
            return;
        }

        if (proccesedPause != -1 && (System.currentTimeMillis() - proccesedPause) <= 5000) {
            System.out.println("Paused");
            return;
        }

        // this is set so it only does one iteration at a time, no matter how frequently the timer fires
        readyToGo = false;
        // and this is tracked to keep one iteration from dragging on too long and possibly choking the system if the user specified a really high frequency
        long loopStartTime = System.currentTimeMillis();

        for (int loop = 0; loop < chunksPerRun; loop++) {
            long now = System.currentTimeMillis();

            // every 5 seconds or so, give basic progress report to let user know how it's going
//            if (now > lastReport + 5000)
//                reportProgress();

            // if this iteration has been running for 45ms (almost 1 tick) or more, stop to take a breather
            if (now > loopStartTime + 45) {
                readyToGo = true;
                return;
            }

            // skip past any chunks which are confirmed as fully generated using our super-special isChunkFullyGenerated routine
//            int rLoop = 0;
//            while (worldData.isChunkFullyGenerated(x, z)) {
//                rLoop++;
//
//                if (!moveToNext())
//                    return;
//                if (rLoop > 255) {    // only skim through max 256 chunks (~8 region files) at a time here, to allow process to take a break if needed
//                    readyToGo = true;
//                    return;
//                }
//            }

            // load the target chunk and generate it if necessary
//            world.loadChunk(x, z, true);
//            worldData.chunkExistsNow(x, z);
            if (!worldData.isChunkFullyGenerated(x, z)) {
                count++;
                System.out.println(" ");
                System.out.println("x: " + x + ", z: " + z);
                System.out.println("Count: " + NumberFormat.getNumberInstance(Locale.US).format(count));
                unloaded.add(new CoordXZ(x, z));
                show();
            }

            if (System.currentTimeMillis() -lastRep >= 10000) {
                System.out.println("Currently at chunk x: " + x + ", z: " + z + " (" + reportNum + "/" + getTotal() + ") (" + getPercentageCompleted() + ")");
                lastRep = System.currentTimeMillis();
                show();
            }

            // move on to next chunk
            if (!moveToNext())
                return;
        }

        // ready for the next iteration to run
        readyToGo = true;
    }

    // step through chunks in spiral pattern from center; returns false if we're done, otherwise returns true
    public boolean moveToNext() {
        reportNum++;

        // make sure of the direction we're moving (X or Z? negative or positive?)
        if (current < length)
            current++;
        else {    // one leg/side of the spiral down...
            current = 0;
            isZLeg ^= true;
            if (isZLeg) {    // every second leg (between X and Z legs, negative or positive), length increases
                isNeg ^= true;
                length++;
            }
        }

        // keep track of the last chunk we were at
        lastChunk.x = x;
        lastChunk.z = z;

        // move one chunk further in the appropriate direction
        if (isZLeg)
            z += (isNeg) ? -1 : 1;
        else
            x += (isNeg) ? -1 : 1;

        // if we've been around one full loop (4 legs)...
        if (isZLeg && isNeg && current == 0) {    // see if we've been outside the border for the whole loop
            if (Math.abs(CoordXZ.chunkToBlock(lastChunk.x)) > BORDER_X && Math.abs(CoordXZ.chunkToBlock(lastChunk.z)) > BORDER_Z) {    // and finish if so
                finish();
                return false;
            }
        }
        return true;

        /* reference diagram used, should move in this pattern:
         *  8 [>][>][>][>][>] etc.
         * [^][6][>][>][>][>][>][6]
         * [^][^][4][>][>][>][4][v]
         * [^][^][^][2][>][2][v][v]
         * [^][^][^][^][0][v][v][v]
         * [^][^][^][1][1][v][v][v]
         * [^][^][3][<][<][3][v][v]
         * [^][5][<][<][<][<][5][v]
         * [7][<][<][<][<][<][<][7]
         */
    }

    // for successful completion
    public void finish() {
        world.save();
        System.out.println("Finished");
        this.stop();

        show();
    }

    private void show() {
        StringBuilder builder = new StringBuilder();

        for (CoordXZ coordXZ : unloaded) {
            builder.append("new int[] { " + coordXZ.x + ", " + coordXZ.z + " }, ");
        }

        System.out.println(builder.toString());
    }

    // for cancelling prematurely
    public void cancel() {
        this.stop();
    }

    // we're done, whether finished or cancelled
    private void stop() {
        if (server == null)
            return;

        readyToGo = false;
        if (taskID != -1)
            server.getScheduler().cancelTask(taskID);
        server = null;

        // go ahead and unload any chunks we still have loaded
        while (!storedChunks.isEmpty()) {
            CoordXZ coord = storedChunks.remove(0);
            if (!originalChunks.contains(coord))
                world.unloadChunkRequest(coord.x, coord.z);
        }
    }

    /**
     * Get the percentage completed for the fill task.
     *
     * @return Percentage
     */
    public double getPercentageCompleted() {
        return ((double) (reportTotal + reportNum) / (double) (getTotal())) * 100;
    }

    /**
     * Amount of chunks completed for the fill task.
     *
     * @return Number of chunks processed.
     */
    public int getChunksCompleted() {
        return reportTotal;
    }

    public int getTotal() {
        return (CoordXZ.blockToChunk(BORDER_X * 2)) * (CoordXZ.blockToChunk(BORDER_Z * 2));
    }
}