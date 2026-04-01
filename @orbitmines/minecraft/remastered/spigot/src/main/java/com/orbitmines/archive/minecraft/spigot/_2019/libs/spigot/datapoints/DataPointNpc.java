package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.datapoints;

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.ServerList;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.PeriodLootItem;
import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.LastVote;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.LootGUI;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;
import com.orbitmines.archive.minecraft._2019.utils.mutable.MutableString;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.PlayerUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.VectorUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.entities.Mob;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.ArmorStandNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.MobNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.PersonalisedMobNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.PassiveRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointLoader;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointSign;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
@Deprecated
public class DataPointNpc<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends DataPointSign {

    private S server;
    private Map<String, List<Location>> npcLocations;

    public DataPointNpc(S server) {
        super("NPC", Type.IRON_PLATE, Material.YELLOW_WOOL);

        this.server = server;

        npcLocations = new HashMap<>();
    }

    @Override
    public boolean buildAt(DataPointLoader loader, Location location, String[] data) {
        String type;
        if (data.length >= 1) {
            type = data[0];
        } else {
            failureMessage = "Npc Type not given.";
            return false;
        }

        float yaw;
        if (data.length >= 2) {
            try {
                yaw = Float.parseFloat(data[1]);
            } catch(NumberFormatException ex) {
                failureMessage = "Invalid Yaw.";
                return false;
            }
        } else {
            failureMessage = "Yaw not given.";
            return false;
        }

        float pitch;
        if (data.length >= 3) {
            try {
                pitch = Float.parseFloat(data[2]);
            } catch(NumberFormatException ex) {
                failureMessage = "Invalid Pitch.";
                return false;
            }
        } else {
            failureMessage = "Pitch not given.";
            return false;
        }

        location.setYaw(yaw);
        location.setPitch(pitch);
        location.add(0.5, 0, 0.5);

        if (!npcLocations.containsKey(type))
            npcLocations.put(type, new ArrayList<>());

        npcLocations.get(type).add(location);

        /* Add delay for LeaderBoard setup */
        new BukkitRunnable() {
            @Override
            public void run() {
                setupNpc(type, location);
            }
        }.runTaskLater(OMServer.getInstance(), 1);

        return true;
    }

    @Override
    public boolean setup() {
        return true;
    }

    public Map<String, List<Location>> getNpcLocations() {
        return npcLocations;
    }

    private void setupNpc(String string, Location location) {
        switch (string.toUpperCase()) {
            /* Check any global Npcs */
            case "SURVIVAL": {
                MobNpc<P> npc = new MobNpc<>(Mob.DOLPHIN, location, getNpcDisplayName(Server.SURVIVAL));
                npc.setInteractAction((event, omp) -> omp.connect(Server.SURVIVAL, true));

                npc.create();

                startUpdate(npc);
                break;
            }
            case "KITPVP": {
                MobNpc<P> npc = new MobNpc<>(Mob.DROWNED, location, getNpcDisplayName(Server.KITPVP));
                npc.setInteractAction((event, omp) -> omp.connect(Server.KITPVP, true));
                npc.create();

                npc.setItemInMainHand(new ItemBuilder(Material.IRON_SWORD).build());
                npc.setHelmet(new ItemBuilder(Material.CHAINMAIL_HELMET).addEnchantment(Enchantment.PROTECTION, 1).build());
                npc.setChestPlate(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).addEnchantment(Enchantment.PROTECTION, 1).build());
                npc.setLeggings(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).addEnchantment(Enchantment.PROTECTION, 1).build());
                npc.setBoots(new ItemBuilder(Material.CHAINMAIL_BOOTS).addEnchantment(Enchantment.PROTECTION, 1).build());

                startUpdate(npc);
                break;
            }
            case "FOG":
            case "UHSURVIVAL":
            case "SKYBLOCK":
            case "CREATIVE":
            case "PRISON":
            case "EMPTY_SLOT": {
                MobNpc<P> npc = new MobNpc<>(Mob.WITHER_SKELETON, location, () -> "§8§lSpace Guard");
                npc.create();
                break;
            }
            case "MG_SW":
            case "MG_UHC":
            case "MG_SG":
            case "MG_CB":
            case "MG_SC":
            case "MG_GA":
            case "MG_SP": {
                MobNpc<P> npc = new MobNpc<>(Mob.WITHER_SKELETON, location, () -> "§8§lSpace Guard");
                npc.create();
                break;
            }
            case "BACK_TO_HUB": {
                MobNpc<P> npc = new MobNpc<>(Mob.WITHER_SKELETON, location, () -> "§7§lBack to " + Server.HUB.getDisplayName());
                npc.setInteractAction((event, omp) -> omp.connect(Server.HUB, true));

                npc.create();

                npc.setHelmet(new ItemBuilder(Material.WHITE_STAINED_GLASS).build());
                npc.setItemInMainHand(new ItemBuilder(Material.ENDER_PEARL).build());

                break;
            }
            case "LOOT": {
                PersonalisedMobNpc<P> npc = new PersonalisedMobNpc<>(Mob.TURTLE, location.clone().add(0, 1, 0),
                    player -> "§a§lSpace§2§lTurtle",
                    player -> {
                        int lootCount = player.getLootItems(false).size();

                        PeriodLootItem shortestDuration = null;
                        long duration = 0;

                        for (PeriodLootItem loot : new ArrayList<>(player.getPeriodLootItems(false))) {
                            /* Dont display loot specificly for a server */
                            if (loot.getType().getServer() != null && loot.getType().getServer() != server.getType())
                                continue;

                            boolean hasRank = true;
                            switch (loot.getType()) {
                                case MONTHLY_VIP:
                                case SURVIVAL_BACK_CHARGES:
                                    hasRank = !player.getVipRank().isNone();
                                    break;
                                case SURVIVAL_SPAWNER_ITEM:
                                    hasRank = player.getVipRank() == VipRank.EMERALD;
                                    break;
                            }

                            if (hasRank && loot.canCollect()) {
                                lootCount++;
                                continue;
                            }

                            if (!hasRank)
                                continue;

                            long left = loot.getMillisLeft();

                            if (shortestDuration != null && duration < left)
                                continue;

                            shortestDuration = loot;
                            duration = left;
                        }

                        return lootCount != 0 ? (color ? "§a" : "§2") + "§l" + player.translate("spigot", "player.loot.collect", lootCount + "", (lootCount == 1 ? "ITEM" : "ITEMS")) : "§7" + player.translate("spigot", "player.loot.more_loot_in",  "§a§l" + TimeUtils.humanFriendlyTimer(player.getLanguage(), duration));
                    },
                    player -> {
                        int votes = ServerList.ACTIVE.size();

                        for (LastVote lastVote : new ArrayList<>(player.getLastVotes(false))) {
                            if (!lastVote.getServerList().isActive())
                                continue;

                            if (lastVote.canVote())
                                continue;

                            votes--;
                        }

                        if (votes != 0)
                            return "§9§l" + player.translate("spigot", "player.loot.votes_left", votes + "", (votes == 1 ? "VOTE" : "VOTES"));

                        LastVote next = null;
                        for (LastVote lastVote : new ArrayList<>(player.getLastVotes(false))) {
                            if (next == null || next.getLastVoteAt().before(lastVote.getLastVoteAt()))
                                next = lastVote;
                        }

                        if (next == null) /* Shouldn't happen */
                            return "§c§lCannot find votes";

                        return "§7" + player.translate("spigot", "player.loot.vote_again_in", "§9§l" + TimeUtils.humanFriendlyTimer(player.getLanguage(), next.getMillisLeft()) + "§7");
                    }
                );

                npc.setInteractAction((event, omp) -> new LootGUI<>(omp, omp).open());

                npc.create();

                startLootUpdate(npc);

                {
                    LivingEntity entity = (LivingEntity) npc.getEntity();
                    Location inFront = PlayerUtils.getTargetBlock(entity, 2).getLocation().add(0.5, 0, 0.5);

                    Location helmetLoc = npc.getSpawnLocation().clone().add(VectorUtils.point2D(npc.getSpawnLocation().toVector(), inFront.toVector()).multiply(0.45)).subtract(0, 0.675, 0);

                    ArmorStandNpc<P> helmet = new ArmorStandNpc(helmetLoc.clone());
                    helmet.setGravity(false);
                    helmet.setVisible(false);
                    helmet.setSmall(true);
                    helmet.setInteractAction(npc.getInteractAction());
                    helmet.setHelmet(new ItemBuilder(Material.LIME_STAINED_GLASS).build());
//                    helmet.setHeadPose(new EulerAngle(LocationUtils.pitchToDegree(entity.getLocation()), 0, 0));

                    helmet.create();
                }
                break;
            }
            default:
                /* Otherwise pass it on to the ServerHandler */
                server.setupNpc(string, location);
                break;
        }
    }

    private void startUpdate(MobNpc npc) {
        new PassiveRunnable(OMServer.getInstance(), Interval.of(TimeUnit.SECOND, 3)) {
            @Override
            public void onRun() {
                npc.update();
            }
        }.async().start();
    }

    private List<PersonalisedMobNpc<P>> lootNpcs = new ArrayList<>();
    private boolean startedLoot = false;
    private boolean color = false;

    private void startLootUpdate(PersonalisedMobNpc<P> npc) {
        lootNpcs.add(npc);

        if (startedLoot)
            return;

        startedLoot = true;

        new PassiveRunnable(server, Interval.of(TimeUnit.TICK, 5)) {
            @Override
            public void onRun() {
                color = !color;

                for (PersonalisedMobNpc<P> npc : lootNpcs) {
                    npc.update();
                }
            }
        }.async().start();
    }

    private MutableString[] getNpcDisplayName(Server server) {
//        if (server == Server.KITPVP)
//            return new MutableString[] {
//                () -> "§c§lNEW!",
//                () -> "§8§lOrbit§7§lMines " + server.getDisplayName(),
//                () -> {
//                    Server.Status status = server.getStatus();
//                    return status != Server.Status.ONLINE ? status.getColor().getCc() + "§l" + status.getName() : server.getColor().getCc() + "§l" + server.getPlayers() + " §7§l/ " + server.getMaxPlayers();
//                }
//            };

        return new MutableString[]{
                () -> "§8§lOrbit§7§lMines " + server.getDisplayName(),
                () -> {
                    Server.Status status = Bukkit.isPrimaryThread() ? Server.Status.OFFLINE : server.getStatus();
                    return status != Server.Status.ONLINE ? status.getColor().getCc() + "§l" + status.getName() : server.getColor().getCc() + "§l" + server.getPlayerCount() + " §7§l/ " + server.getMaxPlayers();
                }
        };
    }
}
