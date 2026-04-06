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
                    "  §3§oa random enchantment§7§o, §3§opassive",
                    "  §7§oor §d§oactive ability §7§oon kill."
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
    SPIDER_CLIMB("Wall Climb", Color.GRAY, Interaction.MOVEMENT, new PassiveSpiderClimb()){
        @Override
        public String[] getDescription(int level) {
            return new String[] {
                    "  §7§oAllows §8§owall climbing",
                    "  §7§owhen jumping near walls."
            };
        }
    },

    /* 2015 Kits */
    KNOCKUP("Knockup", Color.RED, Interaction.HIT_OTHER, new PassiveKnockup()) {
        @Override
        public String[] getDescription(int level) {
            PassiveKnockup passive = (PassiveKnockup) getHandler();

            return new String[] {
                    "  §3§o" + String.format("%.1f", passive.getChance(level) * 100) + "% §7§ochance to launch",
                    "  §7§oyour opponent into the air."
            };
        }
    },
    LIFESTEAL("Lifesteal", Color.PURPLE, Interaction.HIT_OTHER, new PassiveLifesteal()) {
        @Override
        public String[] getDescription(int level) {
            PassiveLifesteal passive = (PassiveLifesteal) getHandler();

            return new String[] {
                    "  §7§oHeal §c§o" + String.format("%.1f", passive.getHealAmount(level)) + " health §7§oper hit."
            };
        }
    },
    WITHER_HIT("Magic", Color.PURPLE, Interaction.HIT_OTHER, new PassiveWitherHit()) {
        @Override
        public String[] getDescription(int level) {
            PassiveWitherHit passive = (PassiveWitherHit) getHandler();

            return new String[] {
                    "  §7§oApply §8§oWither " + NumberUtils.toRoman(passive.getAmplifier(level) + 1),
                    "  §7§ofor §9§o" + (passive.getDuration(level) / 20) + " seconds§7§o."
            };
        }
    },
    WITHER_ARMOR("Wither Armor", Color.GRAY, Interaction.ON_HIT, new PassiveWitherArmor()) {
        @Override
        public String[] getDescription(int level) {
            PassiveWitherArmor passive = (PassiveWitherArmor) getHandler();

            return new String[] {
                    "  §7§oApply §8§oWither " + NumberUtils.toRoman(passive.getAmplifier(level) + 1),
                    "  §7§oto attackers for §9§o" + (passive.getDuration(level) / 20) + " seconds§7§o."
            };
        }
    },
    ARTHROPODS("Arthropods", Color.GREEN, Interaction.HIT_OTHER, new PassiveArthropods()) {
        @Override
        public String[] getDescription(int level) {
            PassiveArthropods passive = (PassiveArthropods) getHandler();

            return new String[] {
                    "  §3§o" + String.format("%.1f", passive.getChance(level) * 100) + "% §7§ochance to summon",
                    "  §7§ospiders and apply §a§oPoison§7§o."
            };
        }
    },
    UNDEATH("Undeath", Color.MAROON, Interaction.APPLY_TO_ARROW, new PassiveUndeath()) {
        @Override
        public String[] getDescription(int level) {
            return new String[] {
                    "  §7§oSummon undead minions",
                    "  §7§owhere your arrow lands."
            };
        }
    },
    ARROW_SPLIT("Arrow Split", Color.SILVER, Interaction.APPLY_TO_ARROW, new PassiveArrowSplit()) {
        @Override
        public String[] getDescription(int level) {
            PassiveArrowSplit passive = (PassiveArrowSplit) getHandler();

            return new String[] {
                    "  §7§oFire §3§o" + passive.getExtraArrows(level) + " additional arrows",
                    "  §7§oin a spread pattern."
            };
        }
    },
    EXPLODE("Explosive Arrow", Color.RED, Interaction.APPLY_TO_ARROW, new PassiveExplode()) {
        @Override
        public String[] getDescription(int level) {
            return new String[] {
                    "  §7§oSpawn §c§oTNT §7§owhere",
                    "  §7§oyour arrow lands."
            };
        }
    },
    TRADE("Trade", Color.GREEN, Interaction.HIT_OTHER, new PassiveTrade()) {
        @Override
        public String[] getDescription(int level) {
            return new String[] {
                    "  §7§oHit an opponent to steal",
                    "  §7§otheir held item. Consumes §6§o1 bread§7§o."
            };
        }
    },
    BACKSTAB("Shadow Strike", Color.AQUA, Interaction.HIT_OTHER, new PassiveBackstab()) {
        @Override
        public String[] getDescription(int level) {
            return new String[] {
                    "  §7§oDeal §c§odouble damage §7§owhen",
                    "  §7§ostriking from §3§obehind§7§o."
            };
        }
    },
    SELF_KNOCKBACK("Recoil", Color.PURPLE, Interaction.HIT_OTHER, new PassiveSelfKnockback()) {
        @Override
        public String[] getDescription(int level) {
            return new String[] {
                    "  §7§oKnock yourself §5§obackwards",
                    "  §7§oon hit, allowing small jumps."
            };
        }
    },
    PULL("Pull", Color.YELLOW, Interaction.HIT_OTHER, new PassivePull()) {
        @Override
        public String[] getDescription(int level) {
            PassivePull passive = (PassivePull) getHandler();

            return new String[] {
                    "  §7§oPull opponents §e§otowards you",
                    "  §7§oon each hit."
            };
        }
    },
    FIRE_TRAIL("Scorched Earth", Color.ORANGE, Interaction.MOVEMENT, new PassiveFireTrail()) {
        @Override
        public String[] getDescription(int level) {
            return new String[] {
                    "  §7§oLeave a §6§otrail of fire",
                    "  §7§obehind you as you walk."
            };
        }
    },
    HEAL_ON_KILL("Soul Harvest", Color.GRAY, Interaction.KILL_PLAYER, new PassiveHealOnKill()) {
        @Override
        public String[] getDescription(int level) {
            PassiveHealOnKill passive = (PassiveHealOnKill) getHandler();

            return new String[] {
                    "  §7§oHeal §c§o" + String.format("%.0f", passive.getHealAmount(level) / 2) + " hearts §7§oon kill."
            };
        }
    },
    IRON_GOLEM_SUMMON("Iron Guardian", Color.WHITE, Interaction.ON_SELECT, false, true, new PassiveIronGolemSummon()) {
        @Override
        public String getDisplayName(int level) {
            return getColor().getCc() + "§oSummon an Iron Golem.";
        }
    },
    SOUL_REAP("Soul Reap", Color.PURPLE, Interaction.KILL_PLAYER, new PassiveSoulReap()) {
        @Override
        public String[] getDescription(int level) {
            return new String[] {
                    "  §7§oGain §d§o" + level + " Soul§7§o per kill",
                    "  §7§ofor the §8§oWither Staff§7§o."
            };
        }
    },
    HOOKED("Hooked", Color.TEAL, null, false, true, new PassiveHooked()) {
        @Override
        public String getDisplayName(int level) {
            return getColor().getCc() + "§oHooked";
        }

        @Override
        public String[] getDescription(int level) {
            return new String[] {
                    "  §7§oCast your line to §b§osnare",
                    "  §7§oopponents and §b§oreel §7§othem in."
            };
        }
    },
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
