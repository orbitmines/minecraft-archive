package com.orbitmines.minecraft.spigot.servers.fog.level;

import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.choice.Choice;
import com.orbitmines.minecraft.spigot.servers.fog.choice.ChoicePicker;
import com.orbitmines.minecraft.spigot.servers.fog.gui.HologramSelector;
import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import com.orbitmines.minecraft.spigot.servers.fog.stats.Stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Level-up prompt. Builds a {@link HologramSelector} with the three {@link Choice}s
 * rolled by {@link ChoicePicker}. All state reads come from in-memory caches
 * (Stats + Run); store writes happen async in the resolve callback.
 */
public class LevelUpFlow {

    private final FoG server;
    private final Map<UUID, HologramSelector<Choice>> active = new HashMap<>();

    public LevelUpFlow(FoG server) {
        this.server = server;
    }

    public boolean hasPrompt(UUID uuid) {
        return active.containsKey(uuid) || HologramSelector.hasOpen(uuid);
    }

    public void cleanup(UUID uuid) {
        HologramSelector<Choice> selector = active.remove(uuid);
        if (selector != null) selector.cancel();
    }

    /** Begin a level-up prompt. Main-thread only. */
    public void start(FoGPlayer player, int level, Run run) {
        start(player, level, run, null);
    }

    /**
     * Begin a level-up prompt with a post-resolve hook. The hook fires after the
     * choice has been applied in-memory and scheduled for persistence.
     * Main-thread only.
     */
    public void start(FoGPlayer player, int level, Run run, Runnable afterResolve) {
        if (active.containsKey(player.getUUID())) return;
        Stats stats = player.getStats();
        if (stats == null) return;

        List<Choice> rolled = ChoicePicker.pickThree(run, stats, level);
        Choice damagedAtThisLevel = stats.getDamagedChoiceAt(level);

        List<HologramSelector.Option<Choice>> options = new ArrayList<>();
        for (Choice choice : rolled) {
            String[] description = choice.getDescriptionLines(player);
            String[] finalDescription = description;
            if (choice == damagedAtThisLevel) {
                finalDescription = new String[description.length + 1];
                System.arraycopy(description, 0, finalDescription, 0, description.length);
                finalDescription[description.length] = "§c" + player.translate("fog", "level.previously");
            }
            options.add(new HologramSelector.Option<>(
                    choice.getIcon(),
                    choice.getRarity().getColor().getCc() + "§l" + choice.getDisplayName(player),
                    finalDescription,
                    choice
            ));
        }

        HologramSelector<Choice> selector = new HologramSelector<>(
                server, player, options,
                (sel, choice) -> {
                    /* In-memory caches first, so applyDerivedStats / further pickers
                       observe the new count immediately. */
                    ChoicePicker.applyInMemory(run, player.getStats(), choice);
                    player.applyDerivedStats();
                    player.sendMessage("FoG",
                            com.orbitmines.archive.minecraft._2019.libs.Color.YELLOW,
                            "fog", "level.chosen", choice.getDisplayName(player));
                    server.runAsync(() -> {
                        ChoicePicker.recordChoice(run, player.getUUID(), level, choice);
                        run.getStore().setPendingChoiceLevel(player.getUUID(), null);
                    });
                    active.remove(player.getUUID());
                    if (afterResolve != null) afterResolve.run();
                }
        );
        active.put(player.getUUID(), selector);
        /* Persist that a prompt is pending — rejoin re-opens the same prompt. */
        server.runAsync(() -> run.getStore().setPendingChoiceLevel(player.getUUID(), level));
        selector.open();
    }
}
