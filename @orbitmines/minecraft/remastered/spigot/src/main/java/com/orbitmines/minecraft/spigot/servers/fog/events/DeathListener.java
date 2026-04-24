package com.orbitmines.minecraft.spigot.servers.fog.events;

import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.choice.Choice;
import com.orbitmines.minecraft.spigot.servers.fog.choice.ChoiceState;
import com.orbitmines.minecraft.spigot.servers.fog.level.LevelFormulas;
import com.orbitmines.minecraft.spigot.servers.fog.run.Difficulty;
import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import com.orbitmines.minecraft.spigot.servers.fog.run.RunStore;
import com.orbitmines.minecraft.spigot.servers.fog.structure.Compartment;
import com.orbitmines.minecraft.spigot.servers.fog.structure.CompartmentType;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * On death: lose up to N levels ({@link Difficulty#getMaxLevelsLostOnDeath()}). Choices
 * made at those levels flip to DAMAGED state, tracked XP rolls back to the start of
 * the target level, and Hardcore enters permanent spectator mode.
 */
public class DeathListener implements Listener {

    private final FoG server;

    public DeathListener(FoG server) {
        this.server = server;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player bukkit = event.getEntity();
        FoGPlayer player = server.getPlayer(bukkit);
        if (player == null) return;
        Run run = player.getActiveRun();
        if (run == null) return;

        Difficulty diff = run.getDifficulty();
        int currentLevel = player.getLevel();
        int target = Math.max(0, currentLevel - diff.getMaxLevelsLostOnDeath());
        UUID uuid = player.getUUID();

        /* Sync bits — tweak the death event immediately so vanilla doesn't drop XP. */
        event.setKeepInventory(!diff.isPermadeath());
        event.setKeepLevel(true);
        event.setDroppedExp(0);

        if (diff.isPermadeath()) {
            server.runSync(() -> player.setGameMode(GameMode.SPECTATOR));
        }

        player.sendMessage("FoG", com.orbitmines.archive.minecraft._2019.libs.Color.RED, "fog", "death.lost_levels",
                currentLevel - target);

        /* Async: walk the choice grid, flip DAMAGED, roll back XP. */
        final int fTarget = target;
        server.runAsync(() -> {
            RunStore store = run.getStore();
            List<CompartmentType> toSeal = new ArrayList<>();
            for (int lvl = fTarget + 1; lvl <= currentLevel; lvl++) {
                for (Choice c : Choice.values()) {
                    if (store.getChoiceState(uuid, lvl, c) == ChoiceState.ACTIVE) {
                        store.setChoiceState(uuid, lvl, c, ChoiceState.DAMAGED);
                        if (player.getStats() != null) player.getStats().onChoiceDamaged(lvl, c);
                        if (c.getCategory() == Choice.Category.STRUCTURE) {
                            CompartmentType ct = guessCompartmentType(c);
                            if (ct != null) toSeal.add(ct);
                        }
                    }
                }
            }

            long newTotalXp = 0;
            for (int i = 0; i < fTarget; i++) newTotalXp += LevelFormulas.required(i);
            store.setMemberExperience(uuid, newTotalXp);
            store.setMemberLevel(uuid, fTarget);
            if (diff.isPermadeath()) store.setMemberDead(uuid, true);

            /* Sync-seal each damaged compartment (block ops). */
            for (CompartmentType ct : toSeal) {
                Integer cx = store.getCompartmentX(ct);
                Integer cy = store.getCompartmentY(ct);
                Integer cz = store.getCompartmentZ(ct);
                if (cx == null || cy == null || cz == null) continue;
                int fx = cx, fy = cy, fz = cz;
                Compartment.markDamagedPersist(store, ct);
                server.runSync(() -> Compartment.markDamagedBlocks(run, ct, fx, fy, fz));
            }

            /* Refresh derived stats after the rollback, sync. */
            server.runSync(player::applyDerivedStats);
        });
    }

    private CompartmentType guessCompartmentType(Choice c) {
        return switch (c) {
            case STRUCTURE_FARM -> CompartmentType.FARM;
            case STRUCTURE_ENCHANT -> CompartmentType.ENCHANTING;
            case STRUCTURE_CRATES -> CompartmentType.CRATES;
            case DRONE_FACTORY -> CompartmentType.DRONE_FACTORY;
            default -> null;
        };
    }
}
