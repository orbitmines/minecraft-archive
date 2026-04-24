package com.orbitmines.minecraft.spigot.servers.fog.commands;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.choice.Choice;
import com.orbitmines.minecraft.spigot.servers.fog.choice.ChoicePicker;
import com.orbitmines.minecraft.spigot.servers.fog.run.Run;

/**
 * DEV+ command for triggering level-up prompts / directly applying a specific choice.
 *
 *   /choice                 → open the normal 3-option level-up prompt at current level + 1
 *   /choice DRONE_UNLOCK    → apply that specific choice at current level + 1, skipping the prompt
 *
 * <p>Brigadier command executors run on the async DB thread, so any Bukkit-side
 * work (entity spawns, attribute writes, chat to the player) must be wrapped in
 * {@code server.runSync}.</p>
 */
public class CommandChoice extends Command<FoG, FoGPlayer> {

    public CommandChoice(FoG server) {
        super(server, Server.FOG, "choice");

        requires(StaffRank.DEVELOPER);

        executes((Executor0<FoG, FoGPlayer>) player -> {
            if (!player.isEligible(StaffRank.DEVELOPER)) return;
            Run run = player.getActiveRun();
            if (run == null) {
                player.sendMessage("FoG", Color.RED, "fog", "player.command.choice.no_run");
                return;
            }
            /* LevelUpFlow.start spawns armor stands + floating items — must be sync. */
            server.runSync(() -> server.getLevelUpFlow().start(player, player.getLevel() + 1, run));
        });

        withArg(new ChoiceArgument().executes(
            (Executor1<FoG, FoGPlayer, Choice, ChoiceArgument>) (player, choice) -> {
                if (!player.isEligible(StaffRank.DEVELOPER)) return;
                Run run = player.getActiveRun();
                if (run == null) {
                    player.sendMessage("FoG", Color.RED, "fog", "player.command.choice.no_run");
                    return;
                }
                int level = player.getLevel() + 1;
                server.runSync(() -> {
                    ChoicePicker.applyInMemory(run, player.getStats(), choice);
                    player.applyDerivedStats();
                    player.sendMessage("FoG", Color.LIME, "fog", "player.command.choice.applied",
                            choice.getDisplayName(player), level);
                });
                /* This executor is already on an async thread — persist directly. */
                ChoicePicker.recordChoice(run, player.getUUID(), level, choice);
                run.getStore().setMemberLevel(player.getUUID(), level);
            }
        ));
    }

    @Override
    public String getDescription(FoGPlayer player) {
        return player.translate("fog", "player.command.choice.description");
    }
}
