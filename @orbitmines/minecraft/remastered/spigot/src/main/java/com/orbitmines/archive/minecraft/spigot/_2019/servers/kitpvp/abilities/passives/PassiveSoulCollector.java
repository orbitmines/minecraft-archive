package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives.ActiveSpearToss;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.entity.EntityNms;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PassiveSoulCollector implements Passive.Handler<PlayerDeathEvent> {

    public static final int MAX_SKULLS = 10;

    @Override
    public void trigger(KitEvent<PlayerDeathEvent> passiveEvent, PlayerDeathEvent event, int level) {
        KitPvPPlayer killer = passiveEvent.getPlayer();
        KitPvPPlayer killed = passiveEvent.getTarget();
        Player killerBukkit = killer.bukkit();
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();

        int skulls = ActiveSpearToss.getSkulls(killerBukkit);
        if (skulls >= MAX_SKULLS)
            return;

        skulls++;

        /* setSkulls also calls updateBundle — this is the only place heads are added */
        ActiveSpearToss.setSkulls(killerBukkit, skulls, killed.bukkit().getName(), killed.getName(Name.RAW_COLORED));

        /* Increase max health by 4 (2 hearts) per skull and heal the increase */
        double newMaxHealth = killer.getSelectedKit().getMaxHealth() + (skulls * 4);
        kitPvP.getNms().entity().setAttribute(killerBukkit, EntityNms.Attribute.MAX_HEALTH, newMaxHealth);
        killerBukkit.setHealth(Math.min(killerBukkit.getHealth() + 4, newMaxHealth));

        /* Max food */
        killerBukkit.setFoodLevel(20);
        killerBukkit.setSaturation(20f);
    }
}
