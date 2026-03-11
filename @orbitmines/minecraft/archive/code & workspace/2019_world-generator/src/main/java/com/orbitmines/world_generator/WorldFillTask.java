package com.orbitmines.world_generator;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import org.bukkit.Chunk;
import org.bukkit.Server;
import org.bukkit.World;

import java.util.*;

public class WorldFillTask implements Runnable {
    // general task-related reference data
    private transient Server server = null;
    private transient World world = null;
    private transient WorldFileData worldData;
    private transient boolean readyToGo = false;
    private transient boolean paused = false;
    private transient boolean pausedForMemory = false;
    private transient int taskID = -1;
    private transient int chunksPerRun = 1;

    // values for the spiral pattern check which fills out the map to the border
    private transient int x = 0;
    private transient int z = 0;
    private transient boolean isZLeg = false;
    private transient boolean isNeg = false;
    private transient int length = -1;
    private transient int current = 0;
    private transient boolean insideBorder = true;
    private List<CoordXZ> storedChunks = new LinkedList<>();
    private Set<CoordXZ> originalChunks = new HashSet<>();
    private transient CoordXZ lastChunk = new CoordXZ(0, 0);

    // for reporting progress back to user occasionally
//    private transient int reportTarget = 0;
    private transient int reportTotal = 0;
    private transient int reportNum = 0;

    private static final int BORDER_X = 15000;
    private static final int BORDER_Z = 15000;

    private transient long proccesedPause;

    public WorldFillTask(Server theServer, World world, int chunksPerRun) {
        this.server = theServer;
        this.chunksPerRun = chunksPerRun;

        this.world = world;
        this.worldData = WorldFileData.create(world);

        this.x = CoordXZ.blockToChunk(BORDER_X);
        this.z = CoordXZ.blockToChunk(BORDER_Z);

        // keep track of the chunks which are already loaded when the task starts, to not unload them
        Chunk[] originals = world.getLoadedChunks();
        for (Chunk original : originals) {
            originalChunks.add(new CoordXZ(original.getX(), original.getZ()));
        }

        this.readyToGo = true;
    }

    @Override
    public void run() {
        if (server == null || !readyToGo || paused)
            return;

        // this is set so it only does one iteration at a time, no matter how frequently the timer fires
        readyToGo = false;
        // and this is tracked to keep one iteration from dragging on too long and possibly choking the system if the user specified a really high frequency
        long loopStartTime = System.currentTimeMillis();

        for (int loop = 0; loop < chunksPerRun; loop++) {
            // in case the task has been paused while we're repeating...
            if (paused || pausedForMemory)
                return;

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
            int rLoop = 0;
            while (worldData.isChunkFullyGenerated(x, z)) {
                rLoop++;
                insideBorder = true;
                if (!moveToNext())
                    return;
                if (rLoop > 255) {    // only skim through max 256 chunks (~8 region files) at a time here, to allow process to take a break if needed
                    readyToGo = true;
                    return;
                }
            }

            // load the target chunk and generate it if necessary
            world.loadChunk(x, z, true);
            worldData.chunkExistsNow(x, z);

            // There need to be enough nearby chunks loaded to make the server populate a chunk with trees, snow, etc.
            // So, we keep the last few chunks loaded, and need to also temporarily load an extra inside chunk (neighbor closest to center of map)
            int popX = !isZLeg ? x : (x + (isNeg ? -1 : 1));
            int popZ = isZLeg ? z : (z + (!isNeg ? -1 : 1));
            world.loadChunk(popX, popZ, false);

            // make sure the previous chunk in our spiral is loaded as well (might have already existed and been skipped over)
            if (!storedChunks.contains(lastChunk) && !originalChunks.contains(lastChunk)) {
                world.loadChunk(lastChunk.x, lastChunk.z, false);
                storedChunks.add(new CoordXZ(lastChunk.x, lastChunk.z));
            }

            // Store the coordinates of these latest 2 chunks we just loaded, so we can unload them after a bit...
            storedChunks.add(new CoordXZ(popX, popZ));
            storedChunks.add(new CoordXZ(x, z));

            // If enough stored chunks are buffered in, go ahead and unload the oldest to free up memory
            while (storedChunks.size() > 8) {
                CoordXZ coord = storedChunks.remove(0);
                if (!originalChunks.contains(coord))
                    world.unloadChunkRequest(coord.x, coord.z);
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
        if (paused || pausedForMemory)
            return false;

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
            if (!insideBorder) {    // and finish if so
                finish();
                return false;
            }    // otherwise, reset the "inside border" flag
            else
                insideBorder = false;
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
        this.paused = true;
        world.save();
        this.stop();
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
        return ((double) (reportTotal + reportNum) / (double) ((CoordXZ.blockToChunk(BORDER_X) * 2) + (CoordXZ.blockToChunk(BORDER_Z) * 2))) * 100;
    }

    /**
     * Amount of chunks completed for the fill task.
     *
     * @return Number of chunks processed.
     */
    public int getChunksCompleted() {
        return reportTotal;
    }
}