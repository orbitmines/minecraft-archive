package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives.*;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.ItemStackNms;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public enum Passive {

    /* Bows */
    BOW_LIGHTNING("Lightning", Color.YELLOW, Interaction.APPLY_TO_ARROW, new PassiveBowLightning()) {
        @Override
        public String[] getDescription(int level) {
            PassiveBowLightning passive = (PassiveBowLightning) getHandler();

            return new String[] {
                    "  §3§o" + String.format("%.1f", passive.getChance(level) * 100) + "% §7§ochance for lightning to",
                    "  §7§ostrike on the opponent's",
                    "  §7§olocation dealing §c§o" + String.format("%.1f", passive.getDamage(level)) + " damage",
                    "  §7§oto nearby players."
            };
        }
    },

    /* Swords */
    WRECKER_OF_WORLDS("Wrecker of Worlds", Color.ORANGE, Interaction.HIT_OTHER, new PassiveWreckerOfWorlds()) {
        @Override
        public String[] getDescription(int level) {
            PassiveWreckerOfWorlds passive = (PassiveWreckerOfWorlds) getHandler();

            return new String[] {
                    "  §3§o" + String.format("%.1f", passive.getChance(level) * 100) + "% §7§ochance for lightning to",
                    "  §7§ostrike on the opponent's",
                    "  §7§olocation dealing §c§o" + String.format("%.1f", passive.getDamage(level)) + " damage",
                    "  §7§oto nearby players."
            };
        }
    },
    SUCKER_PUNCH("Sucker Punch", Color.RED, Interaction.HIT_OTHER, new PassiveSuckerPunch()) {
        @Override
        public String[] getDescription(int level) {
            PassiveSuckerPunch passive = (PassiveSuckerPunch) getHandler();

            return new String[] {
                    "  §3§o" + String.format("%.1f", passive.getChance(level) * 100) + "% §7§ochance for the knockback",
                    "  §7§oenchantment to apply."
            };
        }
    },
    POTION_BREWER("Potion Brewer", Color.RED, Interaction.KILL_PLAYER, new PassivePotionBrewer()) {
        @Override
        public String[] getDescription(int level) {
            PassivePotionBrewer passive = (PassivePotionBrewer) getHandler();

            return new String[] {
                    "  §3§o" + String.format("%.1f", passive.getChance(level) * 100) + "% §7§ochance to receive a",
                    "  §7§orandom potion from your",
                    "  §7§okit when killing an opponent."
            };
        }
    },
    BLEED("Bleed", Color.MAROON, Interaction.HIT_OTHER, new PassiveBleed()) {
        @Override
        public String[] getDescription(int level) {
            PassiveBleed passive = (PassiveBleed) getHandler();

            return new String[] {
                    "  §3§o" + String.format("%.1f", passive.getChance(level) * 100) + "% §7§ochance to cause",
                    "  §7§obleeding to your opponent",
                    "  §7§owhich will result in dealing",
                    "  §c§o" + passive.getDamage(level) + " damage §7§oover §9§o" + passive.getSeconds(level) + " seconds§7§o."
            };
        }
    },
    ENCHANTING_TABLE("Enchanting", Color.BLUE, Interaction.KILL_PLAYER, new PassiveEnchantingTable()){
        @Override
        public String[] getDescription(int level) {
            PassiveEnchantingTable passive = (PassiveEnchantingTable) getHandler();

            return new String[] {
                    "  §3§o" + String.format("%.1f", passive.getChance(level) * 100) + "% §7§ochance to receive",
                    "  §3§oa random enchantment §7§oon",
                    "  §7§oyour weapon or armor",
                    "  §7§owhen killing an opponent."
            };
        }
    },
//    POISONOUS("Poisonous", Color.GREEN, Interaction.HIT_OTHER, new PassivePoisonous()) {
//        @Override
//        public String[] getDescription(int level) {
//            //TODO: PROVIDE DESCRIPTION!
//            return super.getDescription(level);
//        }
//    },
//    SUMMONER("Summoner", Color.PURPLE, Interaction.HIT_OTHER, new PassiveSummoner()){
//        @Override
//        public String[] getDescription(int level) {
//            //TODO: PROVIDE DESCRIPTION!
//            return super.getDescription(level);
//        }
//    },

    /* Armor */
    LIGHTNING_PROTECTION("Lightning Protection", Color.YELLOW, Interaction.ON_HIT, true, false, new PassiveLightningProtection()) {
        @Override
        public String[] getDescription(int level) {
            PassiveLightningProtection passive = (PassiveLightningProtection) getHandler();

            return new String[] {
                    "  §3§o" + String.format("%.1f", passive.getChance(level) * 100) + "% §7§ochance of evading",
                    "  §7§oan incoming lightning strike."
            };
        }
    },
    LAST_BREATH("Last Breath", Color.BLUE, Interaction.LOW_HEALTH, new PassiveLastBreath()) {
        @Override
        public String[] getDescription(int level) {
            PassiveLastBreath passive = (PassiveLastBreath) getHandler();
            PotionBuilder builder = passive.getBuilder(level);

            return new String[] {
                    "  §7§oReceive §e§o" + ItemUtils.getName(builder.getType()) + " " + NumberUtils.toRoman(builder.getAmplifier() + 1),
                    "  §7§owhen below §c§o" + String.format("%.1f", passive.getPercentage(level) * 100) + "% health§7§o.",
            };
        }
    },



    /* Special */
    ATTACK_DAMAGE("Attack Damage", Color.GREEN, Interaction.ON_SELECT, false, true, new PassiveAttackDamage()) {
        @Override
        public String getDisplayName(int level) {
            return getColor().getCc() + "§o+" + level + ".0 Attack Damage";
        }

        @Override
        public ItemStack apply(ItemStackNms nms, ItemStack itemStack, int level) {
            return super.apply(nms, nms.setAttackDamage(itemStack, level), level);
        }
    },
    ARROW_REGEN("Arrow Regen", Color.SILVER, null, false, true, new PassiveArrowRegen()) {
        @Override
        public String getDisplayName(int level) {
            return getColor().getCc() + "§o+1 Arrow every " + level + "s";
        }
    },
    PLAYER_TRACKING("Player Tracking", Color.SILVER, null, false, true, new PassivePlayerTracking()) {
        @Override
        public String getDisplayName(int level) {
            return getColor().getCc() + "§oTrack nearby players.";
        }
    },
//    SPIDER_CLIMB("Spider Climb", Color.PURPLE, Interaction.MOVEMENT, false, true, new PassiveSpiderClimb()){
//        @Override
//        public String[] getDescription(int level) {
//            //TODO: PROVIDE DESCRIPTION!
//            return super.getDescription(level);
//        }
//    }
    ;

    @Getter private final String name;
    @Getter private final Color color;
    @Getter private final Interaction interaction;
    @Getter private final boolean stackable;
    @Getter private final boolean breakLine;
    @Getter private final Handler handler;

    Passive(String name, Color color, Interaction interaction, Handler handler) {
        this(name, color, interaction, false, false, handler);
    }

    Passive(String name, Color color, Interaction interaction, boolean stackable, boolean breakLine, Handler handler) {
        this.name = name;
        this.color = color;
        this.interaction = interaction;
        this.stackable = stackable;
        this.breakLine = breakLine;
        this.handler = handler;
    }

    public String getDisplayName(int level) {
        return color.getCc() + name + " " + NumberUtils.toRoman(level);
    }

    public String[] getDescription(int level) {
        return new String[] {};
    }

    public static Map<Passive, Integer> from(ItemStackNms nms, ItemStack itemStack) {
        return from(nms, itemStack, null);
    }

    public static Map<Passive, Integer> from(ItemStackNms nms, ItemStack itemStack, Interaction interaction) {
        Map<String, String> metaData = nms.getMetaData(itemStack).getKeys("passive");

        if (metaData == null)
            return null;

        Map<Passive, Integer> passives = new HashMap<>();

        for (String string : metaData.keySet()) {
            Passive passive = Passive.valueOf(string);

            if (interaction == null || passive.getInteraction() == interaction)
                passives.put(passive, Integer.parseInt(metaData.get(string)));
        }

        if (passives.size() == 0)
            return null;

        return passives;
    }

    public ItemStack apply(ItemStackNms nms, ItemStack itemStack, int level) {
        return nms.setMetaData(itemStack, "passive", toString(), level + "");
    }

    public int getLevel(ItemStackNms nms, ItemStack itemStack) {
        Map<Passive, Integer> all = from(nms, itemStack);
        if (all == null)
            return 0;

        return all.getOrDefault(this, 0);
    }

    public interface Handler<E extends Event> {

        void trigger(KitEvent<E> passiveEvent, E event, int level);

    }

    public interface LowHealthHandler extends Handler<Event> {

        void triggerOff(KitEvent<Event> passiveEvent, int level);

        double getPercentage(int level);

    }

    public enum Interaction {

        APPLY_TO_ARROW,
        HIT_OTHER,
        KILL_PLAYER,
        ON_HIT,
        ON_SELECT,
        LOW_HEALTH,
        MOVEMENT;

    }
}
