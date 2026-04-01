package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.freezer.ArmorStandPlayerFreezer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.freezer.Freezer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.item_handlers.ItemHover;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.*;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.net.InetSocketAddress;
import java.util.*;

public abstract class SpigotPlayer<S extends SpigotServer> {

    protected final Player player;
    protected final S server;

    @Getter @Setter protected GUI lastGUI;
    @Getter protected Freezer freezer;
    protected Map<Cooldown, Long> cooldowns;
    @Getter @Setter protected ItemHover currentHover;

    public SpigotPlayer(Player player, S server) {
        this.player = player;
        this.server = server;

        this.cooldowns = new HashMap<>();
    }

    /* Return true if join successful */
    public abstract boolean onJoin();

    public abstract void beforeQuitSync();

    public abstract void afterQuitAsync();

    protected abstract void register();

    protected abstract void unregister();

    public abstract boolean hasScoreboard();

    public boolean hasOpened(GUI gui) {
        return hasOpened(gui.getInventory());
    }

    public boolean hasOpened(Inventory inventory) {
        return inventory.getViewers().contains(bukkit());
    }

    public void openInventory(Inventory inventory) {
        bukkit().openInventory(inventory);
    }

    public Player bukkit() {
        return player;
    }

    public S server() {
        return server;
    }

    public void processJoinEventAsync() {
        onJoin();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline())
                    return;

                /* Hide other npcs that shouldn't be visible */
                for (List<Npc> npcs : Npc.getNpcs().values()) {
                    for (Npc npc : npcs) {
                        if (npc.getWatchers() != null && !npc.getWatchers().contains(player))
                            npc.hideFor(player);
                    }
                }
            }
        }.runTaskLaterAsynchronously(server.getPlugin(), 10);
    }

    protected void spawnPersonalizedNpcs() {
        server.runSync(() -> {
            /* Spawn Personalised MobNpcs */
            for (List<MobNpc> npcs : MobNpc.getMobNpcs().values()) {
                for (MobNpc npc : npcs) {
                    if (npc instanceof PersonalisedMobNpc)
                        ((PersonalisedMobNpc) npc).afterLogin(this);
                }
            }
            for (List<FloatingItem> floatingItems : FloatingItem.getFloatingItems().values()) {
                for (FloatingItem floatingItem : floatingItems) {
                    if (floatingItem instanceof PersonalisedFloatingItem)
                        ((PersonalisedFloatingItem) floatingItem).afterLogin(this);
                }
            }
        });
    }
    
    public void processQuitEventSync() {
        beforeQuitSync();

        unregister();

        /* Destroy Personalised MobNpcs */
        for (List<MobNpc> npcs : MobNpc.getMobNpcs().values()) {
            for (MobNpc npc : npcs) {
                if (npc instanceof PersonalisedMobNpc)
                    ((PersonalisedMobNpc) npc).afterLogout(this);
            }
        }

        for (List<FloatingItem> floatingItems : FloatingItem.getFloatingItems().values()) {
            for (FloatingItem floatingItem : floatingItems) {
                if (floatingItem instanceof PersonalisedFloatingItem)
                    ((PersonalisedFloatingItem) floatingItem).afterLogout(this);
            }
        }

        /* Remove PlayerFreezer */
        ArmorStandPlayerFreezer freezer = ArmorStandPlayerFreezer.getFreezer(player);
        if (freezer != null)
            freezer.destroy();

        /* Leave Hover */
        if (currentHover != null)
            currentHover.leave(this);

        server.runAsync(() -> {
            afterQuitAsync();
        });
    }

    /*

        Helper Methods

     */
    public boolean isMoving(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();

        return Math.abs(from.getX() - to.getX()) > 0.05 || Math.abs(from.getY() - to.getY()) > 0.05 || Math.abs(from.getZ() - to.getZ()) > 0.05;
    }

    /*


        Cooldowns


     */

    public boolean onCooldown(Cooldown cooldown) {
        return cooldown.onCooldown(getCooldown(cooldown));
    }

    public boolean canUse(Cooldown cooldown) {
        return !onCooldown(cooldown);
    }

    public long getCooldown(Cooldown cooldown) {
        return cooldowns.getOrDefault(cooldown, -1L);
    }

    public void resetCooldown(Cooldown cooldown) {
        cooldowns.put(cooldown, System.currentTimeMillis());
    }

    public void removeCooldown(Cooldown cooldown) {
        cooldowns.remove(cooldown);
    }

    /*

        Freezer
          -> Register NpcEvents in order to use Freezer.

     */

    public void freeze(Freezer freezer) {
        freeze(freezer, null);
    }

    public void freeze(Freezer freezer, Location location) {
        this.freezer = freezer;
        this.freezer.freeze(bukkit(), location);
    }

    public void clearFreeze() {
        if (!isFrozen())
            return;

        this.freezer.clearFreeze(bukkit());
        this.freezer = null;
    }

    public boolean isFrozen() {
        return freezer != null;
    }

    /*

        Delegated shortcuts

     */

    public void playSound(Sound sound) {
        playSound(sound, 1f);
    }

    public void playSound(Sound sound, float volume) {
        playSound(sound, volume, 1f);
    }

    public void playSound(Sound sound, float volume, float pitch) {
        playSound(getLocation(), sound, volume, pitch);
    }
    
    /*

        Player Delegated methods

     */

    public String getDisplayName() {
        return bukkit().getDisplayName();
    }

    public void setDisplayName(String s) {
        bukkit().setDisplayName(s);
    }

    public String getPlayerListName() {
        return bukkit().getPlayerListName();
    }

    public void setPlayerListName(String s) {
        bukkit().setPlayerListName(s);
    }

    public String getPlayerListHeader() {
        return bukkit().getPlayerListHeader();
    }

    public String getPlayerListFooter() {
        return bukkit().getPlayerListFooter();
    }

    public void setPlayerListHeader(String s) {
        bukkit().setPlayerListHeader(s);
    }

    public void setPlayerListFooter(String s) {
        bukkit().setPlayerListFooter(s);
    }

    public void setPlayerListHeaderFooter(String s, String s1) {
        bukkit().setPlayerListHeaderFooter(s, s1);
    }

    public void setCompassTarget(Location location) {
        bukkit().setCompassTarget(location);
    }

    public Location getCompassTarget() {
        return bukkit().getCompassTarget();
    }

    public InetSocketAddress getAddress() {
        return bukkit().getAddress();
    }

    public void sendRawMessage(String s) {
        bukkit().sendRawMessage(s);
    }

    public void chat(String s) {
        bukkit().chat(s);
    }

    public boolean performCommand(String s) {
        return bukkit().performCommand(s);
    }

    public boolean isSneaking() {
        return bukkit().isSneaking();
    }

    public void setSneaking(boolean b) {
        bukkit().setSneaking(b);
    }

    public boolean isSprinting() {
        return bukkit().isSprinting();
    }

    public void setSprinting(boolean b) {
        bukkit().setSprinting(b);
    }

    public void saveData() {
        bukkit().saveData();
    }

    public void loadData() {
        bukkit().loadData();
    }

    public void setSleepingIgnored(boolean b) {
        bukkit().setSleepingIgnored(b);
    }

    public boolean isSleepingIgnored() {
        return bukkit().isSleepingIgnored();
    }

    public void playNote(Location location, Instrument instrument, Note note) {
        bukkit().playNote(location, instrument, note);
    }

    public void playSound(Location location, Sound sound, float v, float v1) {
        bukkit().playSound(location, sound, v, v1);
    }

    public void playSound(Location location, String s, float v, float v1) {
        bukkit().playSound(location, s, v, v1);
    }

    public void playSound(Location location, Sound sound, SoundCategory soundCategory, float v, float v1) {
        bukkit().playSound(location, sound, soundCategory, v, v1);
    }

    public void playSound(Location location, String s, SoundCategory soundCategory, float v, float v1) {
        bukkit().playSound(location, s, soundCategory, v, v1);
    }

    public void stopSound(Sound sound) {
        bukkit().stopSound(sound);
    }

    public void stopSound(String s) {
        bukkit().stopSound(s);
    }

    public void stopSound(Sound sound, SoundCategory soundCategory) {
        bukkit().stopSound(sound, soundCategory);
    }

    public void stopSound(String s, SoundCategory soundCategory) {
        bukkit().stopSound(s, soundCategory);
    }

    public <T> void playEffect(Location location, Effect effect, T t) {
        bukkit().playEffect(location, effect, t);
    }

    public void sendBlockChange(Location location, BlockData blockData) {
        bukkit().sendBlockChange(location, blockData);
    }

    public void sendSignChange(Location location, String[] strings) throws IllegalArgumentException {
        bukkit().sendSignChange(location, strings);
    }

    public void sendMap(MapView mapView) {
        bukkit().sendMap(mapView);
    }

    public void updateInventory() {
        bukkit().updateInventory();
    }

    public void incrementStatistic(Statistic statistic) throws IllegalArgumentException {
        bukkit().incrementStatistic(statistic);
    }

    public void decrementStatistic(Statistic statistic) throws IllegalArgumentException {
        bukkit().decrementStatistic(statistic);
    }

    public void incrementStatistic(Statistic statistic, int i) throws IllegalArgumentException {
        bukkit().incrementStatistic(statistic, i);
    }

    public void decrementStatistic(Statistic statistic, int i) throws IllegalArgumentException {
        bukkit().decrementStatistic(statistic, i);
    }

    public void setStatistic(Statistic statistic, int i) throws IllegalArgumentException {
        bukkit().setStatistic(statistic, i);
    }

    public int getStatistic(Statistic statistic) throws IllegalArgumentException {
        return bukkit().getStatistic(statistic);
    }

    public void incrementStatistic(Statistic statistic, Material material) throws IllegalArgumentException {
        bukkit().incrementStatistic(statistic, material);
    }

    public void decrementStatistic(Statistic statistic, Material material) throws IllegalArgumentException {
        bukkit().decrementStatistic(statistic, material);
    }

    public int getStatistic(Statistic statistic, Material material) throws IllegalArgumentException {
        return bukkit().getStatistic(statistic, material);
    }

    public void incrementStatistic(Statistic statistic, Material material, int i) throws IllegalArgumentException {
        bukkit().incrementStatistic(statistic, material, i);
    }

    public void decrementStatistic(Statistic statistic, Material material, int i) throws IllegalArgumentException {
        bukkit().decrementStatistic(statistic, material, i);
    }

    public void setStatistic(Statistic statistic, Material material, int i) throws IllegalArgumentException {
        bukkit().setStatistic(statistic, material, i);
    }

    public void incrementStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException {
        bukkit().incrementStatistic(statistic, entityType);
    }

    public void decrementStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException {
        bukkit().decrementStatistic(statistic, entityType);
    }

    public int getStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException {
        return bukkit().getStatistic(statistic, entityType);
    }

    public void incrementStatistic(Statistic statistic, EntityType entityType, int i) throws IllegalArgumentException {
        bukkit().incrementStatistic(statistic, entityType, i);
    }

    public void decrementStatistic(Statistic statistic, EntityType entityType, int i) {
        bukkit().decrementStatistic(statistic, entityType, i);
    }

    public void setStatistic(Statistic statistic, EntityType entityType, int i) {
        bukkit().setStatistic(statistic, entityType, i);
    }

    public void setPlayerTime(long l, boolean b) {
        bukkit().setPlayerTime(l, b);
    }

    public long getPlayerTime() {
        return bukkit().getPlayerTime();
    }

    public long getPlayerTimeOffset() {
        return bukkit().getPlayerTimeOffset();
    }

    public boolean isPlayerTimeRelative() {
        return bukkit().isPlayerTimeRelative();
    }

    public void resetPlayerTime() {
        bukkit().resetPlayerTime();
    }

    public void setPlayerWeather(WeatherType weatherType) {
        bukkit().setPlayerWeather(weatherType);
    }

    public WeatherType getPlayerWeather() {
        return bukkit().getPlayerWeather();
    }

    public void resetPlayerWeather() {
        bukkit().resetPlayerWeather();
    }

    public void giveExp(int i) {
        bukkit().giveExp(i);
    }

    public void giveExpLevels(int i) {
        bukkit().giveExpLevels(i);
    }

    public float getExp() {
        return bukkit().getExp();
    }

    public void setExp(float v) {
        bukkit().setExp(v);
    }

    public int getLevel() {
        return bukkit().getLevel();
    }

    public void setLevel(int i) {
        bukkit().setLevel(i);
    }

    public int getTotalExperience() {
        return bukkit().getTotalExperience();
    }

    public void setTotalExperience(int i) {
        bukkit().setTotalExperience(i);
    }

    public float getExhaustion() {
        return bukkit().getExhaustion();
    }

    public void setExhaustion(float v) {
        bukkit().setExhaustion(v);
    }

    public float getSaturation() {
        return bukkit().getSaturation();
    }

    public void setSaturation(float v) {
        bukkit().setSaturation(v);
    }

    public int getFoodLevel() {
        return bukkit().getFoodLevel();
    }

    public void setFoodLevel(int i) {
        bukkit().setFoodLevel(i);
    }

    public Location getBedSpawnLocation() {
        return bukkit().getBedSpawnLocation();
    }

    public void setBedSpawnLocation(Location location) {
        bukkit().setBedSpawnLocation(location);
    }

    public void setBedSpawnLocation(Location location, boolean b) {
        bukkit().setBedSpawnLocation(location, b);
    }

    public boolean getAllowFlight() {
        return bukkit().getAllowFlight();
    }

    public void setAllowFlight(boolean b) {
        bukkit().setAllowFlight(b);
    }

    public void hidePlayer(Plugin plugin, Player player) {
        bukkit().hidePlayer(plugin, player);
    }

    public void showPlayer(Plugin plugin, Player player) {
        bukkit().showPlayer(plugin, player);
    }

    public boolean canSee(Player player) {
        return bukkit().canSee(player);
    }

    public boolean isFlying() {
        return bukkit().isFlying();
    }

    public void setFlying(boolean b) {
        bukkit().setFlying(b);
    }

    public void setFlySpeed(float v) throws IllegalArgumentException {
        bukkit().setFlySpeed(v);
    }

    public void setWalkSpeed(float v) throws IllegalArgumentException {
        bukkit().setWalkSpeed(v);
    }

    public float getFlySpeed() {
        return bukkit().getFlySpeed();
    }

    public float getWalkSpeed() {
        return bukkit().getWalkSpeed();
    }

    public void setResourcePack(String s) {
        bukkit().setResourcePack(s);
    }

    public void setResourcePack(String s, byte[] bytes) {
        bukkit().setResourcePack(s, bytes);
    }

    public Scoreboard getScoreboard() {
        return bukkit().getScoreboard();
    }

    public void setScoreboard(Scoreboard scoreboard) throws IllegalArgumentException, IllegalStateException {
        bukkit().setScoreboard(scoreboard);
    }

    public boolean isHealthScaled() {
        return bukkit().isHealthScaled();
    }

    public void setHealthScaled(boolean b) {
        bukkit().setHealthScaled(b);
    }

    public void setHealthScale(double v) throws IllegalArgumentException {
        bukkit().setHealthScale(v);
    }

    public double getHealthScale() {
        return bukkit().getHealthScale();
    }

    @Deprecated
    public double getMaxHealth() {
        return bukkit().getMaxHealth();
    }

    public Entity getSpectatorTarget() {
        return bukkit().getSpectatorTarget();
    }

    public void setSpectatorTarget(Entity entity) {
        bukkit().setSpectatorTarget(entity);
    }

    public void sendTitle(String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        bukkit().sendTitle(title, subTitle, fadeIn, stay, fadeOut);
    }

    public void resetTitle() {
        bukkit().resetTitle();
    }

    public void spawnParticle(Particle particle, Location location, int i) {
        bukkit().spawnParticle(particle, location, i);
    }

    public void spawnParticle(Particle particle, double v, double v1, double v2, int i) {
        bukkit().spawnParticle(particle, v, v1, v2, i);
    }

    public <T> void spawnParticle(Particle particle, Location location, int i, T t) {
        bukkit().spawnParticle(particle, location, i, t);
    }

    public <T> void spawnParticle(Particle particle, double v, double v1, double v2, int i, T t) {
        bukkit().spawnParticle(particle, v, v1, v2, i, t);
    }

    public void spawnParticle(Particle particle, Location location, int i, double v, double v1, double v2) {
        bukkit().spawnParticle(particle, location, i, v, v1, v2);
    }

    public void spawnParticle(Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5) {
        bukkit().spawnParticle(particle, v, v1, v2, i, v3, v4, v5);
    }

    public <T> void spawnParticle(Particle particle, Location location, int i, double v, double v1, double v2, T t) {
        bukkit().spawnParticle(particle, location, i, v, v1, v2, t);
    }

    public <T> void spawnParticle(Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, T t) {
        bukkit().spawnParticle(particle, v, v1, v2, i, v3, v4, v5, t);
    }

    public void spawnParticle(Particle particle, Location location, int i, double v, double v1, double v2, double v3) {
        bukkit().spawnParticle(particle, location, i, v, v1, v2, v3);
    }

    public void spawnParticle(Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, double v6) {
        bukkit().spawnParticle(particle, v, v1, v2, i, v3, v4, v5, v6);
    }

    public <T> void spawnParticle(Particle particle, Location location, int i, double v, double v1, double v2, double v3, T t) {
        bukkit().spawnParticle(particle, location, i, v, v1, v2, v3, t);
    }

    public <T> void spawnParticle(Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, double v6, T t) {
        bukkit().spawnParticle(particle, v, v1, v2, i, v3, v4, v5, v6, t);
    }

    public AdvancementProgress getAdvancementProgress(Advancement advancement) {
        return bukkit().getAdvancementProgress(advancement);
    }

    public int getClientViewDistance() {
        return bukkit().getClientViewDistance();
    }

    public String getLocale() {
        return bukkit().getLocale();
    }

    public void updateCommands() {
        bukkit().updateCommands();
    }

    public Player.Spigot spigot() {
        return bukkit().spigot();
    }

    public String getName() {
        return bukkit().getName();
    }

    public PlayerInventory getInventory() {
        return bukkit().getInventory();
    }

    public Inventory getEnderChest() {
        return bukkit().getEnderChest();
    }

    public MainHand getMainHand() {
        return bukkit().getMainHand();
    }

    public boolean setWindowProperty(InventoryView.Property property, int i) {
        return bukkit().setWindowProperty(property, i);
    }

    public InventoryView getOpenInventory() {
        return bukkit().getOpenInventory();
    }

    public InventoryView openWorkbench(Location location, boolean b) {
        return bukkit().openWorkbench(location, b);
    }

    public InventoryView openEnchanting(Location location, boolean b) {
        return bukkit().openEnchanting(location, b);
    }

    public void openInventory(InventoryView inventoryView) {
        bukkit().openInventory(inventoryView);
    }

    public InventoryView openMerchant(Villager villager, boolean b) {
        return bukkit().openMerchant(villager, b);
    }

    public InventoryView openMerchant(Merchant merchant, boolean b) {
        return bukkit().openMerchant(merchant, b);
    }

    public void closeInventory() {
        bukkit().closeInventory();
    }

    public ItemStack getItemOnCursor() {
        return bukkit().getItemOnCursor();
    }

    public void setItemOnCursor(ItemStack itemStack) {
        bukkit().setItemOnCursor(itemStack);
    }

    public boolean hasCooldown(Material material) {
        return bukkit().hasCooldown(material);
    }

    public int getCooldown(Material material) {
        return bukkit().getCooldown(material);
    }

    public void setCooldown(Material material, int i) {
        bukkit().setCooldown(material, i);
    }

    public boolean isSleeping() {
        return bukkit().isSleeping();
    }

    public int getSleepTicks() {
        return bukkit().getSleepTicks();
    }

    public GameMode getGameMode() {
        return bukkit().getGameMode();
    }

    public void setGameMode(GameMode gameMode) {
        bukkit().setGameMode(gameMode);
    }

    public boolean isBlocking() {
        return bukkit().isBlocking();
    }

    public boolean isHandRaised() {
        return bukkit().isHandRaised();
    }

    public int getExpToLevel() {
        return bukkit().getExpToLevel();
    }

    public boolean discoverRecipe(NamespacedKey namespacedKey) {
        return bukkit().discoverRecipe(namespacedKey);
    }

    public int discoverRecipes(Collection<NamespacedKey> collection) {
        return bukkit().discoverRecipes(collection);
    }

    public boolean undiscoverRecipe(NamespacedKey namespacedKey) {
        return bukkit().undiscoverRecipe(namespacedKey);
    }

    public int undiscoverRecipes(Collection<NamespacedKey> collection) {
        return bukkit().undiscoverRecipes(collection);
    }

    public double getEyeHeight() {
        return bukkit().getEyeHeight();
    }

    public double getEyeHeight(boolean b) {
        return bukkit().getEyeHeight(b);
    }

    public Location getEyeLocation() {
        return bukkit().getEyeLocation();
    }

    public List<Block> getLineOfSight(Set<Material> set, int i) {
        return bukkit().getLineOfSight(set, i);
    }

    public Block getTargetBlock(Set<Material> set, int i) {
        return bukkit().getTargetBlock(set, i);
    }

    public List<Block> getLastTwoTargetBlocks(Set<Material> set, int i) {
        return bukkit().getLastTwoTargetBlocks(set, i);
    }

    public Block getTargetBlockExact(int i) {
        return bukkit().getTargetBlockExact(i);
    }

    public Block getTargetBlockExact(int i, FluidCollisionMode fluidCollisionMode) {
        return bukkit().getTargetBlockExact(i, fluidCollisionMode);
    }

    public RayTraceResult rayTraceBlocks(double v) {
        return bukkit().rayTraceBlocks(v);
    }

    public RayTraceResult rayTraceBlocks(double v, FluidCollisionMode fluidCollisionMode) {
        return bukkit().rayTraceBlocks(v, fluidCollisionMode);
    }

    public int getRemainingAir() {
        return bukkit().getRemainingAir();
    }

    public void setRemainingAir(int i) {
        bukkit().setRemainingAir(i);
    }

    public int getMaximumAir() {
        return bukkit().getMaximumAir();
    }

    public void setMaximumAir(int i) {
        bukkit().setMaximumAir(i);
    }

    public int getMaximumNoDamageTicks() {
        return bukkit().getMaximumNoDamageTicks();
    }

    public void setMaximumNoDamageTicks(int i) {
        bukkit().setMaximumNoDamageTicks(i);
    }

    public double getLastDamage() {
        return bukkit().getLastDamage();
    }

    public void setLastDamage(double v) {
        bukkit().setLastDamage(v);
    }

    public int getNoDamageTicks() {
        return bukkit().getNoDamageTicks();
    }

    public void setNoDamageTicks(int i) {
        bukkit().setNoDamageTicks(i);
    }

    public Player getKiller() {
        return bukkit().getKiller();
    }

    public boolean addPotionEffect(PotionEffect potionEffect) {
        return bukkit().addPotionEffect(potionEffect);
    }

    public boolean addPotionEffect(PotionEffect potionEffect, boolean b) {
        return bukkit().addPotionEffect(potionEffect, b);
    }

    public boolean addPotionEffects(Collection<PotionEffect> collection) {
        return bukkit().addPotionEffects(collection);
    }

    public boolean hasPotionEffect(PotionEffectType potionEffectType) {
        return bukkit().hasPotionEffect(potionEffectType);
    }

    public PotionEffect getPotionEffect(PotionEffectType potionEffectType) {
        return bukkit().getPotionEffect(potionEffectType);
    }

    public void removePotionEffect(PotionEffectType potionEffectType) {
        bukkit().removePotionEffect(potionEffectType);
    }

    public Collection<PotionEffect> getActivePotionEffects() {
        return bukkit().getActivePotionEffects();
    }

    public boolean hasLineOfSight(Entity entity) {
        return bukkit().hasLineOfSight(entity);
    }

    public boolean getRemoveWhenFarAway() {
        return bukkit().getRemoveWhenFarAway();
    }

    public void setRemoveWhenFarAway(boolean b) {
        bukkit().setRemoveWhenFarAway(b);
    }

    public EntityEquipment getEquipment() {
        return bukkit().getEquipment();
    }

    public void setCanPickupItems(boolean b) {
        bukkit().setCanPickupItems(b);
    }

    public boolean getCanPickupItems() {
        return bukkit().getCanPickupItems();
    }

    public boolean isLeashed() {
        return bukkit().isLeashed();
    }

    public Entity getLeashHolder() throws IllegalStateException {
        return bukkit().getLeashHolder();
    }

    public boolean setLeashHolder(Entity entity) {
        return bukkit().setLeashHolder(entity);
    }

    public boolean isGliding() {
        return bukkit().isGliding();
    }

    public void setGliding(boolean b) {
        bukkit().setGliding(b);
    }

    public boolean isSwimming() {
        return bukkit().isSwimming();
    }

    public void setSwimming(boolean b) {
        bukkit().setSwimming(b);
    }

    public boolean isRiptiding() {
        return bukkit().isRiptiding();
    }

    public void setAI(boolean b) {
        bukkit().setAI(b);
    }

    public boolean hasAI() {
        return bukkit().hasAI();
    }

    public void setCollidable(boolean b) {
        bukkit().setCollidable(b);
    }

    public boolean isCollidable() {
        return bukkit().isCollidable();
    }

    public AttributeInstance getAttribute(Attribute attribute) {
        return bukkit().getAttribute(attribute);
    }

    public void damage(double v) {
        bukkit().damage(v);
    }

    public void damage(double v, Entity entity) {
        bukkit().damage(v, entity);
    }

    public double getHealth() {
        return bukkit().getHealth();
    }

    public void setHealth(double v) {
        bukkit().setHealth(v);
    }

    public Location getLocation() {
        return bukkit().getLocation();
    }

    public Location getLocation(Location location) {
        return bukkit().getLocation(location);
    }

    public void setVelocity(Vector vector) {
        bukkit().setVelocity(vector);
    }

    public Vector getVelocity() {
        return bukkit().getVelocity();
    }

    public double getHeight() {
        return bukkit().getHeight();
    }

    public double getWidth() {
        return bukkit().getWidth();
    }

    public BoundingBox getBoundingBox() {
        return bukkit().getBoundingBox();
    }

    public boolean isOnGround() {
        return bukkit().isOnGround();
    }

    public World getWorld() {
        return bukkit().getWorld();
    }

    public boolean teleport(Location location) {
        return bukkit().teleport(location);
    }

    public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause teleportCause) {
        return bukkit().teleport(location, teleportCause);
    }

    public boolean teleport(Entity entity) {
        return bukkit().teleport(entity);
    }

    public boolean teleport(Entity entity, PlayerTeleportEvent.TeleportCause teleportCause) {
        return bukkit().teleport(entity, teleportCause);
    }

    public List<Entity> getNearbyEntities(double v, double v1, double v2) {
        return bukkit().getNearbyEntities(v, v1, v2);
    }

    public int getEntityId() {
        return bukkit().getEntityId();
    }

    public int getFireTicks() {
        return bukkit().getFireTicks();
    }

    public int getMaxFireTicks() {
        return bukkit().getMaxFireTicks();
    }

    public void setFireTicks(int i) {
        bukkit().setFireTicks(i);
    }

    public void remove() {
        bukkit().remove();
    }

    public boolean isDead() {
        return bukkit().isDead();
    }

    public boolean isValid() {
        return bukkit().isValid();
    }

    public Server getServer() {
        return bukkit().getServer();
    }

    public List<Entity> getPassengers() {
        return bukkit().getPassengers();
    }

    public boolean addPassenger(Entity entity) {
        return bukkit().addPassenger(entity);
    }

    public boolean removePassenger(Entity entity) {
        return bukkit().removePassenger(entity);
    }

    public boolean isEmpty() {
        return bukkit().isEmpty();
    }

    public boolean eject() {
        return bukkit().eject();
    }

    public float getFallDistance() {
        return bukkit().getFallDistance();
    }

    public void setFallDistance(float v) {
        bukkit().setFallDistance(v);
    }

    public void setLastDamageCause(EntityDamageEvent entityDamageEvent) {
        bukkit().setLastDamageCause(entityDamageEvent);
    }

    public EntityDamageEvent getLastDamageCause() {
        return bukkit().getLastDamageCause();
    }

    public UUID getUniqueId() {
        return bukkit().getUniqueId();
    }

    public int getTicksLived() {
        return bukkit().getTicksLived();
    }

    public void setTicksLived(int i) {
        bukkit().setTicksLived(i);
    }

    public void playEffect(EntityEffect entityEffect) {
        bukkit().playEffect(entityEffect);
    }

    public EntityType getType() {
        return bukkit().getType();
    }

    public boolean isInsideVehicle() {
        return bukkit().isInsideVehicle();
    }

    public boolean leaveVehicle() {
        return bukkit().leaveVehicle();
    }

    public Entity getVehicle() {
        return bukkit().getVehicle();
    }

    public void setCustomNameVisible(boolean b) {
        bukkit().setCustomNameVisible(b);
    }

    public boolean isCustomNameVisible() {
        return bukkit().isCustomNameVisible();
    }

    public void setGlowing(boolean b) {
        bukkit().setGlowing(b);
    }

    public boolean isGlowing() {
        return bukkit().isGlowing();
    }

    public void setInvulnerable(boolean b) {
        bukkit().setInvulnerable(b);
    }

    public boolean isInvulnerable() {
        return bukkit().isInvulnerable();
    }

    public boolean isSilent() {
        return bukkit().isSilent();
    }

    public void setSilent(boolean b) {
        bukkit().setSilent(b);
    }

    public boolean hasGravity() {
        return bukkit().hasGravity();
    }

    public void setGravity(boolean b) {
        bukkit().setGravity(b);
    }

    public int getPortalCooldown() {
        return bukkit().getPortalCooldown();
    }

    public void setPortalCooldown(int i) {
        bukkit().setPortalCooldown(i);
    }

    public Set<String> getScoreboardTags() {
        return bukkit().getScoreboardTags();
    }

    public boolean addScoreboardTag(String s) {
        return bukkit().addScoreboardTag(s);
    }

    public boolean removeScoreboardTag(String s) {
        return bukkit().removeScoreboardTag(s);
    }

    public PistonMoveReaction getPistonMoveReaction() {
        return bukkit().getPistonMoveReaction();
    }

    public BlockFace getFacing() {
        return bukkit().getFacing();
    }

    public void setMetadata(String s, MetadataValue metadataValue) {
        bukkit().setMetadata(s, metadataValue);
    }

    public List<MetadataValue> getMetadata(String s) {
        return bukkit().getMetadata(s);
    }

    public boolean hasMetadata(String s) {
        return bukkit().hasMetadata(s);
    }

    public void removeMetadata(String s, Plugin plugin) {
        bukkit().removeMetadata(s, plugin);
    }

    public void sendMessage(String s) {
        bukkit().sendMessage(s);
    }

    public void sendMessage(String[] strings) {
        bukkit().sendMessage(strings);
    }

    public <T extends Projectile> T launchProjectile(Class<? extends T> aClass) {
        return bukkit().launchProjectile(aClass);
    }

    public <T extends Projectile> T launchProjectile(Class<? extends T> aClass, Vector vector) {
        return bukkit().launchProjectile(aClass, vector);
    }

    public void sendPluginMessage(Plugin plugin, String s, byte[] bytes) {
        bukkit().sendPluginMessage(plugin, s, bytes);
    }

    public Set<String> getListeningPluginChannels() {
        return bukkit().getListeningPluginChannels();
    }

    /*

        Inventory delegated methods

     */

    public ItemStack getHelmet() {
        return getInventory().getHelmet();
    }

    public ItemStack getChestplate() {
        return getInventory().getChestplate();
    }

    public ItemStack getLeggings() {
        return getInventory().getLeggings();
    }

    public ItemStack getBoots() {
        return getInventory().getBoots();
    }

    public void setHelmet(ItemStack itemStack) {
        getInventory().setHelmet(itemStack);
    }

    public void setChestplate(ItemStack itemStack) {
        getInventory().setChestplate(itemStack);
    }

    public void setLeggings(ItemStack itemStack) {
        getInventory().setLeggings(itemStack);
    }

    public void setBoots(ItemStack itemStack) {
        getInventory().setBoots(itemStack);
    }

    public ItemStack getItemInMainHand() {
        return getInventory().getItemInMainHand();
    }

    public void setItemInMainHand(ItemStack itemStack) {
        getInventory().setItemInMainHand(itemStack);
    }

    public ItemStack getItemInOffHand() {
        return getInventory().getItemInOffHand();
    }

    public void setItemInOffHand(ItemStack itemStack) {
        getInventory().setItemInOffHand(itemStack);
    }

    public int getHeldItemSlot() {
        return getInventory().getHeldItemSlot();
    }
}
