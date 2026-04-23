package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot;

import com.orbitmines.archive.minecraft._2019.utils.SkinLibrary;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.Bukkit;
import org.bukkit.block.Skull;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.URL;
import java.util.UUID;

/**
 * Applies a player's skin to a skull meta / skull block by resolving the Mojang
 * skin URL (cached by {@link SkinLibrary}) and setting it via {@link PlayerProfile}.
 * Falls back to {@code setOwningPlayer} (online/cached only) on a cache miss, and
 * kicks off an async fetch so subsequent calls can render offline players correctly.
 */
public final class SkullTextures {

    private SkullTextures() {}

    public static void applyTo(SkullMeta meta, UUID uuid) {
        PlayerProfile profile = cachedProfile(uuid);
        if (profile != null) {
            meta.setOwnerProfile(profile);
            return;
        }

        meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        fetchAsync(uuid, null);
    }

    public static void applyTo(Skull skull, UUID uuid, Runnable onRefreshNeeded) {
        PlayerProfile profile = cachedProfile(uuid);
        if (profile != null) {
            skull.setOwnerProfile(profile);
            return;
        }

        skull.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        fetchAsync(uuid, onRefreshNeeded);
    }

    private static PlayerProfile cachedProfile(UUID uuid) {
        SkinLibrary library = library();
        if (library == null)
            return null;

        String url = library.getCachedSkinUrl(uuid);
        if (url == null)
            return null;

        try {
            PlayerProfile profile = Bukkit.createPlayerProfile(uuid);
            PlayerTextures textures = profile.getTextures();
            textures.setSkin(new URL(url));
            profile.setTextures(textures);
            return profile;
        } catch (Exception e) {
            return null;
        }
    }

    private static void fetchAsync(UUID uuid, Runnable onComplete) {
        SkinLibrary library = library();
        if (library == null) {
            if (onComplete != null) onComplete.run();
            return;
        }
        library.updateSkinUrlAsync(uuid, onComplete);
    }

    private static SkinLibrary library() {
        SpigotServer<?> server = SpigotServer.getInstance();
        if (server instanceof OMServer<?, ?> omServer)
            return omServer.getSkinLibrary();
        return null;
    }
}
