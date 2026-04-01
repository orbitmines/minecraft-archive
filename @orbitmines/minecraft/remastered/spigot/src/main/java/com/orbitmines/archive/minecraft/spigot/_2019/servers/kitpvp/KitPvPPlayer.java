package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.StoredProgressAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.servers.KitPvPAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp.KitPvPPlayerKitModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp.KitPvPPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitClass;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits.*;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.scoreboard.KitPvPScoreboard;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.Title;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.entity.EntityNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.ItemStackNms;
import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class KitPvPPlayer extends OMPlayer<KitPvP, KitPvPPlayer> {

    @Getter private KitPvPPlayerModel kitPvPModel;
    private List<KitPvPPlayerKitModel> kits;

    @Getter private KitPvPPlayerModel.LevelData levelData;

    @Getter private boolean spectator;
    @Getter private boolean spawnProtection;
    @Getter private KitPvPKit.Level selectedKit;
    @Getter private int killStreak;

    public KitPvPPlayer(Player player, KitPvP server) {
        super(player, server);

        clearFullInventory();
        clearPotionEffects();

        setGameMode(GameMode.SURVIVAL);
    }

    @Override
    protected void register() {
        server.registerPlayer(this);
    }

    @Override
    protected void unregister() {
        server.unregisterPlayer(this);
    }

    @Override
    public KitPvPPlayer getInstance() {
        return this;
    }

    @Override
    public boolean onJoin() {
        super.onJoin();

        this.kitPvPModel = KitPvPPlayerModel.findOrInitializeBy(getUUID());

        if (!this.kitPvPModel.isInserted()) {
            this.kitPvPModel.insert();

            /* Give player Knight, Archer and Soldier on first login */
            new KitPvPPlayerKitModel(getUUID(), KitKnight.ID, 1).insert();
            new KitPvPPlayerKitModel(getUUID(), KitArcher.ID, 1).insert();
            new KitPvPPlayerKitModel(getUUID(), KitSoldier.ID, 1).insert();
        }

        levelData = kitPvPModel.getLevelData();

        getInventory().setHeldItemSlot(4);

        reloadKits();

        server.runSync(() -> {
            levelData.updateExperienceBar(player);
            
            server.getLobbyKit().copyToInventory(this);

            resetScoreboard();
            setScoreboard(new KitPvPScoreboard(server, this));
        });

        return true;
    }

    @Override
    public void beforeQuitSync() {
        super.beforeQuitSync();

        if (selectedKit == null)
            return;

        EntityNms nms = server.getNms().entity();

        if (player.getHealth() != nms.getAttribute(player, EntityNms.Attribute.MAX_HEALTH))
            player.damage(nms.getAttribute(player, EntityNms.Attribute.MAX_HEALTH), player.getLastDamageCause().getEntity());

        /* Update damage dealt for current round */
        KitPvPPlayerKitModel kit = getKit(selectedKit.getHandler(), false);
        kit.insertOrUpdate(KitPvPPlayerKitModel.column.DAMAGE_DEALT);
    }

    @Override
    public void onFirstLogin() {

    }

    public void processKill(PlayerDeathEvent event, KitPvPPlayer killed) {
        if (killed == this)
            /* We don't want to do this, invite coins woohoo! */
            return;

        addKill();

        this.killStreak++;
        new Title<>(p -> "", p -> "                     §5" + killStreak, 20, 60, 20).send(this);

        if (this.killStreak > 25 || this.killStreak % 5 == 0)
            new BukkitRunnable() {
                @Override
                public void run() {
                    server.broadcastRaw("", Color.PURPLE, getName(Name.RAW_COLORED) + " §7has a §5§l" + killStreak + " Kill Streak");
                }
            }.runTaskLater(server, 1);

        KitPvPPlayerKitModel kit = getKit((getSelectedKit() != null ? getSelectedKit() : server.getKit(getLastSelectedId(), getLastSelectedLevel())).getHandler(), false);
        if (this.killStreak > getBestStreak()) {
            if (this.killStreak >= 3) {
                sendRawMessage("", Color.PURPLE, "You have broken your record with a §5§l" + this.killStreak + " Kill Streak§7!");
                playSound(Sound.ENTITY_PLAYER_LEVELUP);
            }

            kit.setBestStreak(this.killStreak);
            kit.setBestStreakAt(DateUtils.now());
            kit.insertOrUpdate(KitPvPPlayerKitModel.column.BEST_STREAK, KitPvPPlayerKitModel.column.BEST_STREAK_AT);
        } else if (selectedKit != null && this.killStreak > kit.getBestStreak()) {
            kit.setBestStreak(this.killStreak);
            kit.setBestStreakAt(DateUtils.now());
            kit.insertOrUpdate(KitPvPPlayerKitModel.column.BEST_STREAK, KitPvPPlayerKitModel.column.BEST_STREAK_AT);
        }

        /* Passives */
        {
            ItemStackNms nms = server.getNms().customItem();

            for (ItemStack item : getInventory().getContents()) {
                if (item == null)
                    continue;

                Map<Passive, Integer> passives = Passive.from(nms, item, Passive.Interaction.KILL_PLAYER);

                /* No Passives on this item */
                if (passives == null)
                    continue;

                for (Passive passive : passives.keySet()) {
                    passive.getHandler().trigger(new KitEvent<>(this, event), event, passives.get(passive));
                }
            }
        }

        /* Coins */
        int coins = KitPvP.COINS_PER_KILL;

        coins += applyMultiplier(coins, getCoinMultiplier());
        if (CoinBooster.ACTIVE != null)
            coins += applyMultiplier(coins, CoinBooster.ACTIVE.getType().getMultiplier());

        /* Experience */
        int experience = KitPvP.XP_PER_KILL;

        experience += applyMultiplier(experience, getXPMultiplier());

        /* Give Rewards */
        addCoinsExperience(coins, experience, true);
        update(KitPvPPlayerModel.column.COINS, KitPvPPlayerModel.column.EXPERIENCE);

        /* Kill Hologram */
        //TODO

        /* Handle Achievements */
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        KitPvPKit.Level selectedKit = getSelectedKit();
        KitPvPKit.Level selectedKitKilled = killed.getSelectedKit();

        server.runAsync(() -> {
            if (!ItemUtils.hasLocalizedName(itemStack, KitSoldier.JARNBJORN)) {
                StoredProgressAchievement handler = (StoredProgressAchievement) KitPvPAchievement.THOR.getHandler();
                handler.progress(this, 1, true);
            }

            if (selectedKit != null && selectedKit.getHandler().getKitClass() == KitClass.SPELLCASTER) {
                StoredProgressAchievement handler = (StoredProgressAchievement) KitPvPAchievement.MERLIN.getHandler();
                if (handler.hasCompleted(this))
                    handler.complete(this, true);
            }

            if (selectedKitKilled != null && selectedKitKilled.getHandler().getId() == KitKing.ID) {
                StoredProgressAchievement handler = (StoredProgressAchievement) KitPvPAchievement.KINGSLAYER.getHandler();
                handler.progress(this, 1, true);
            }

            if (selectedKit != null && selectedKitKilled != null && selectedKit.getHandler().getId() == KitArcher.ID && selectedKitKilled.getHandler().getId() == KitBunny.ID) {
                StoredProgressAchievement handler = (StoredProgressAchievement) KitPvPAchievement.HUNTER.getHandler();
                handler.progress(this, 1, true);
            }

            if (selectedKit != null && selectedKit.getHandler().getId() == KitDrunk.ID) {
                StoredProgressAchievement handler = (StoredProgressAchievement) KitPvPAchievement.DRUNKEN_FOOL.getHandler();
                if (handler.hasCompleted(this))
                    handler.complete(this, true);
            }

            {
                StoredProgressAchievement handler = (StoredProgressAchievement) KitPvPAchievement.MANSLAUGHTER.getHandler();
                if (handler.hasCompleted(this))
                    handler.complete(this, true);
            }

            {
                StoredProgressAchievement handler = (StoredProgressAchievement) KitPvPAchievement.KEEPING_SCORE.getHandler();
                if (handler.hasCompleted(this))
                    handler.complete(this, true);
            }
        });
    }

    private int applyMultiplier(int current, double multiplier) {
        return (int) (current * (multiplier - 1));
    }

    public void processDeath(PlayerDeathEvent event, @Nullable KitPvPPlayer killer) {
        addDeath();

        /* Update damage dealt for current round */
        KitPvPPlayerKitModel kit = getKit(getSelectedKit().getHandler(), false);
        kit.insertOrUpdate(KitPvPPlayerKitModel.column.DAMAGE_DEALT);

        this.selectedKit = null;
        this.killStreak = 0;

        levelData.updateExperienceBar(player);

        if (killer != null) {
            if (player.getLastDamageCause().getEntity() instanceof Arrow) {
                server.broadcastRaw("", Color.MAROON, getName(Name.RAW_COLORED) + "§7 was shot by " + killer.getName(Name.RAW_COLORED) + "§7!");
            } else {
                server.broadcastRaw("", Color.MAROON, getName(Name.RAW_COLORED) + "§7 was killed by " + killer.getName(Name.RAW_COLORED) + "§7!");
            }
        } else {
            server.broadcastRaw("§7" + event.getDeathMessage().replaceAll(getRawName(), getName(Name.RAW_COLORED) + "§7"));
        }

        event.setDeathMessage(null);

        //TODO SPAWN BED
    }

    public void joinMap() {
        joinMap(server.getKit(getLastSelectedId(), getLastSelectedLevel()));
    }

    public void joinMap(KitPvPKit.Level selectedKit) {
        teleportToMap();
        setSelectedKit(selectedKit);
        player.getInventory().setHeldItemSlot(0);
    }

    public void setSelectedKit(KitPvPKit.Level selectedKit) {
        this.selectedKit = selectedKit;

        clearFullInventory();
        clearPotionEffects();

        /* Apply Attributes */
        EntityNms nms = server.getNms().entity();
        nms.setAttribute(player, EntityNms.Attribute.MAX_HEALTH, selectedKit.getMaxHealth());
        player.setHealth(selectedKit.getMaxHealth());
        nms.setAttribute(player, EntityNms.Attribute.KNOCKBACK_RESISTANCE, selectedKit.getKnockbackResistance());
        nms.setAttribute(player, EntityNms.Attribute.ATTACK_DAMAGE, 2.0D);

        /* Remove Attack Delay */
        nms.setAttribute(player, EntityNms.Attribute.ATTACK_SPEED, 16.0D);

        /* Give Kit */
        selectedKit.getKit().copyToInventory(this);

        /* Update Last Selected Kit */
        setLastSelectedId(selectedKit.getHandler().getId());
        setLastSelectedLevel(selectedKit.getLevel());
        update(KitPvPPlayerModel.column.LAST_SELECTED_ID, KitPvPPlayerModel.column.LAST_SELECTED_LEVEL);

        /* Add usage */
        KitPvPPlayerKitModel kit = getKit(getSelectedKit().getHandler(), false);
        kit.addTimesUsed();
        kit.insertOrUpdate(KitPvPPlayerKitModel.column.TIMES_USED);

//        updateNpcs();

        new Title<>(p -> selectedKit.getHandler().getDisplayName(), p -> "§a§lLevel " + selectedKit.getLevel(), 20, 40, 20).send(this);

        /* Passives on select */
        ItemStackNms itemStackNms = server.getNms().customItem();
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null || item.getType() == Material.AIR)
                continue;

            Map<Passive, Integer> passives = Passive.from(itemStackNms, item, Passive.Interaction.ON_SELECT);

            /* No Passives on this item */
            if (passives == null)
                continue;

            for (Passive passive : passives.keySet()) {
                passive.getHandler().trigger(new KitEvent<>(this, null), null, passives.get(passive));
            }
        }
    }

    public void teleportToMap() {
        server.getMap().teleport(this);

        /* Spawn Protection */
        if (player.getGameMode() != GameMode.SPECTATOR) {
            spawnProtection = true;

            new BukkitRunnable() {
                @Override
                public void run() {
                    spawnProtection = false;
                }
            }.runTaskLater(server, 60);
        }

        /* Teleport sound */
        playSound(Sound.ENTITY_ENDERMAN_TELEPORT);
    }

    public void addDamageDealt(double amount) {
        if (selectedKit == null)
            return;

        getKit(selectedKit.getHandler(), false).addDamageDealt(amount);
    }
    
    public int getBestStreak() {
        return kitPvPModel.getBestStreak(getKits(false));
    }

    public int getKills() {
        return kitPvPModel.getKills(getKits(false));
    }

    public int getDeaths() {
        return kitPvPModel.getDeaths(getKits(false));
    }

    public void addDeath() {
        if (selectedKit == null)
            return;

        KitPvPPlayerKitModel kit = getKit(selectedKit.getHandler(), false);
        kit.addDeath();
        kit.insertOrUpdate(KitPvPPlayerKitModel.column.DEATHS);
    }

    public void addKill() {
        if (selectedKit == null)
            return;

        KitPvPPlayerKitModel kit = getKit(selectedKit.getHandler(), false);
        kit.addKill();
        kit.insertOrUpdate(KitPvPPlayerKitModel.column.KILLS);
    }

    @Override
    public void removePotionEffect(PotionEffectType effectType) {
        super.removePotionEffect(effectType);

        for (PotionBuilder potionBuilder : selectedKit.getKit().getPotionBuilders()) {
            if (potionBuilder.getType() == effectType)
                addPotionEffect(potionBuilder.build());
        }
    }

    @Override
    public boolean addPotionEffect(PotionEffect potionEffect) {
        return super.addPotionEffect(potionEffect, true);
    }

    public void reloadKits() {
        this.kits = this.kitPvPModel.getKits();
    }

    public List<KitPvPPlayerKitModel> getKits(boolean reload) {
        if (this.kits == null || reload)
            reloadKits();

        return kits;
    }

    public KitPvPPlayerKitModel getKit(KitPvPKit kit, boolean reload) {
        for (KitPvPPlayerKitModel kitModel : getKits(reload)) {
            if (kitModel.getKitId() == kit.getId())
                return kitModel;
        }

        KitPvPPlayerKitModel model = new KitPvPPlayerKitModel(getUUID(), kit.getId(), 0);
        model.insert();

        this.kits.add(model);

        return model;
    }

    private void addCoins(int amount, boolean notify) {
        if (notify) {
            double coinMultiplier = getCoinMultiplier();
            new ActionBar(this, () -> "§6+" + amount + " " + (amount == 1 ? "Coin" : "Coins" + (coinMultiplier == 1.0D ? "" : " §7(" + getVipRank().getPrefixColor().getCc() + "x" + String.format("%.2f", coinMultiplier) + "§7" + (CoinBooster.ACTIVE == null ? "" : ", §ex" + String.format("%.2f", CoinBooster.ACTIVE.getType().getMultiplier()) + "§7") + ")")), 100).send();
        }

        addCoins(amount);
    }

    private void addExperience(int amount, boolean notify) {
        if (levelData.getLevel() == KitPvPPlayerModel.LevelData.maxLevel)
            return;

        if (notify) {
            double experienceMultiplier = getXPMultiplier();
            new ActionBar(this, () -> "§e+" + amount + " XP" + (experienceMultiplier == 1.0D ? "" : " §7(" + getVipRank().getPrefixColor().getCc() + "x" + String.format("%.2f", experienceMultiplier) + "§7)"), 100).send();
        }

        kitPvPModel.addExperience(amount);

        levelData.update(this, true);
    }

    public void addCoinsExperience(int coins, int experience, boolean notify) {
        if (notify) {
            double coinMultiplier = getCoinMultiplier();
            double experienceMultiplier = getXPMultiplier();

            String message = "§6§l+" + coins + " " + (coins == 1 ? "Coin" : "Coins") + (coinMultiplier == 1.0D ? (CoinBooster.ACTIVE == null ? "" : " §7(§ex" + String.format("%.2f", CoinBooster.ACTIVE.getType().getMultiplier()) + "§7)") : " §7(" + getVipRank().getPrefixColor().getCc() + "x" + String.format("%.2f", coinMultiplier) + "§7" + (CoinBooster.ACTIVE == null ? "" : ", §ex" + String.format("%.2f", CoinBooster.ACTIVE.getType().getMultiplier()) + "§7") + ")") + "§7, " + "§e§l+" + experience + " XP" + (experienceMultiplier == 1.0D ? "" : " §7(" + getVipRank().getPrefixColor().getCc() + "x" + String.format("%.2f", experienceMultiplier) + "§7)");

            new ActionBar(this, () -> message, 100).send();

            new BukkitRunnable() {
                @Override
                public void run() {
                    sendRawMessage(message);
                }
            }.runTaskLater(server, 1);
        }

        addCoins(coins, false);
        addExperience(experience, false);
    }

    public double getCoinMultiplier() {
        switch (getVipRank()) {

            case NONE:
            case IRON:
            case GOLD:
                return 1.0;
            case DIAMOND:
                return 1.2;
            case EMERALD:
                return 1.5;
        }
        throw new IllegalStateException();
    }

    public double getXPMultiplier() {
        switch (getVipRank()) {

            case NONE:
            case IRON:
                return 1.0;
            case GOLD:
                return 1.5;
            case DIAMOND:
                return 2.0;
            case EMERALD:
                return 2.5;
        }
        throw new IllegalStateException();
    }

    /*

        KitPvPPlayerModel Delegates

     */

    public UUID getUuid() {
        return getKitPvPModel().getUuid();
    }

    public int getCoins() {
        return getKitPvPModel().getCoins();
    }

    public long getExperience() {
        return getKitPvPModel().getExperience();
    }

    public long getLastSelectedId() {
        if (getKitPvPModel() == null)
            return KitKnight.ID;

        return getKitPvPModel().getLastSelectedId();
    }

    public int getLastSelectedLevel() {
        if (getKitPvPModel() == null)
            return 1;

        return getKitPvPModel().getLastSelectedLevel();
    }

    public void setCoins(int coins) {
        getKitPvPModel().setCoins(coins);
    }

    public void setExperience(long experience) {
        getKitPvPModel().setExperience(experience);
    }

    public void setLastSelectedId(long lastSelectedId) {
        getKitPvPModel().setLastSelectedId(lastSelectedId);
    }

    public void setLastSelectedLevel(int lastSelectedLevel) {
        getKitPvPModel().setLastSelectedLevel(lastSelectedLevel);
    }

    public void addCoins(int amount) {
        getKitPvPModel().addCoins(amount);
    }

    public void addExperience(int amount) {
        getKitPvPModel().addExperience(amount);
    }

    public void removeCoins(int amount) {
        getKitPvPModel().removeCoins(amount);
    }

    public void insert() {
        getKitPvPModel().insert();
    }

    public void update(KitPvPPlayerModel.column... columns) {
        getKitPvPModel().update(columns);
    }

    public void delete() {
        getKitPvPModel().delete();
    }

    public boolean isDestroyed() {
        return getKitPvPModel().isDestroyed();
    }

    public boolean isInserted() {
        return getKitPvPModel().isInserted();
    }

    public void reload() {
        getKitPvPModel().reload();
    }

    public void insertOrUpdate(KitPvPPlayerModel.column... columns) {
        getKitPvPModel().insertOrUpdate(columns);
    }
}
