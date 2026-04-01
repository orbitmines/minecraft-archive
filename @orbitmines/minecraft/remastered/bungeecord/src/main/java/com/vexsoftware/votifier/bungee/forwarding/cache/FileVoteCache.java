package com.vexsoftware.votifier.bungee.forwarding.cache;

import com.google.common.io.Files;
import com.google.gson.*;
import com.vexsoftware.votifier.model.Vote;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class FileVoteCache extends MemoryVoteCache {

    private final File cacheFile;
    private final ScheduledTask saveTask;

    public FileVoteCache(int initialMemorySize, final Plugin plugin, File cacheFile) throws IOException {
        super(initialMemorySize);
        this.cacheFile = cacheFile;
        load();

        saveTask = ProxyServer.getInstance().getScheduler().schedule(plugin, () -> {
            try {
                save();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Unable to save cached votes, votes will be lost if you restart.", e);
            }
        }, 3L, 3L, TimeUnit.MINUTES);
    }

    private void load() throws IOException {
        JsonObject object;

        try {
            BufferedReader reader = Files.newReader(cacheFile, StandardCharsets.UTF_8);
            Throwable localThrowable3 = null;
            try {
                object = new JsonParser().parse(reader).getAsJsonObject();
            } catch (Throwable localThrowable1) {
                localThrowable3 = localThrowable1;
                throw localThrowable1;
            } finally {
                if (reader != null) {
                    if (localThrowable3 != null) {
                        try {
                            reader.close();
                        } catch (Throwable localThrowable2) {
                            localThrowable3.addSuppressed(localThrowable2);
                        }
                    } else {
                        reader.close();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            object = new JsonObject();
        }

        for (Map.Entry<String, JsonElement> server : object.entrySet()) {
            JsonArray voteArray = object.get(server.getKey()).getAsJsonArray();

            Object votes = new ArrayList(voteArray.size());
            for (int i = 0; i < voteArray.size(); i++) {
                JsonObject voteObject = voteArray.get(i).getAsJsonObject();
                ((List)votes).add(new Vote(voteObject));
            }
            voteCache.put(server.getKey(), (List) votes);
        }
    }

    public void save() throws IOException {
        cacheLock.lock();
        JsonObject votesObject = new JsonObject();
        Iterator localIterator1;
        Map.Entry<String, Collection<Vote>> entry = null;

        try {
            for (localIterator1 = voteCache.entrySet().iterator(); localIterator1.hasNext();) {
                entry = (Map.Entry) localIterator1.next();
                JsonArray array = new JsonArray();
                for (Vote vote : entry.getValue()) {
                    array.add(vote.serialize());
                }
                votesObject.add(entry.getKey(), array);
            }
        } finally {
            cacheLock.unlock();
        }

        BufferedWriter writer = Files.newWriter(cacheFile, StandardCharsets.UTF_8);
        Throwable throwable = null;

        try {
            new GsonBuilder().create().toJson(votesObject, writer);
        } catch (Throwable localThrowable1) {
            throwable = localThrowable1;
            throw localThrowable1;
        } finally {
            if (writer != null) {
                if (entry != null) {
                    try {
                        writer.close();
                    } catch (Throwable localThrowable2) {
                        localThrowable2.addSuppressed(throwable);
                    }
                } else {
                    writer.close();
                }
            }
        }
    }

    public void halt() throws IOException {
        saveTask.cancel();
        save();
    }
}
