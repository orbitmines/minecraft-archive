package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalClaim;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalClaimMember;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import lombok.Getter;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Claim {

    public static int MIN_WIDTH = 3;
    public static int MIN_AREA = MIN_WIDTH * MIN_WIDTH;

    @Getter private SurvivalClaim model;
    private OfflinePlayer ownerPlayer;

    public Claim(SurvivalClaim model) {
        this.model = model;

        SpigotServer.getInstance().runAsync(() -> {
            reloadMembers();

            if (hasOwner())
                ownerPlayer = OfflinePlayer.get(getOwner());
        });
    }

    public Claim(UUID owner, Location corner1, Location corner2) {
        this.model = new SurvivalClaim(owner, corner1, corner2);

        SpigotServer.getInstance().runAsync(() -> {
            reloadMembers();

            if (hasOwner())
                ownerPlayer = OfflinePlayer.get(getOwner());
        });
    }

    private Survival survival() {
        return (Survival) Survival.getInstance();
    }

    public int getWidth() {
        return getCorner2().getBlockX() - getCorner1().getBlockX() + 1;
    }

    public int getHeight() {
        return getCorner2().getBlockZ() - getCorner1().getBlockZ() + 1;
    }

    public int getArea() {
        return getWidth() * getHeight();
    }

    public boolean hasOwner() {
        return getOwner() != null;
    }

    public boolean isOwner(UUID uuid) {
        return getOwner().equals(uuid);
    }

    public String getOwnerName() {
        if (!hasOwner())
            return Server.SURVIVAL.getDisplayName();

        return ownerPlayer.getName(Name.RAW_COLORED);
    }

    public boolean hasPermission(UUID member, SurvivalClaim.Permission permission) {
        if (getOwner() == null)
            return true; /* End Spawn */

        SurvivalClaimMember claimMember = getMember(member, false);

        return claimMember != null && claimMember.hasPermission(permission);
    }

    /*
        Edit Permissions
     */

    public boolean canModify(SurvivalPlayer omp) {
        if (omp.isOpMode())
            return true;

        if (isOwner(omp.getUUID()))
            return true;

        String name = getOwnerName();
        new ActionBar(omp.bukkit(), () -> "§c§l" + omp.translate("survival", "player.claim.permission.cant_modify", name + "§c§l"), 60).send();

        return false;
    }

    public boolean canInteract(SurvivalPlayer omp) {
        if (omp.isOpMode())
            return true;

        if (isOwner(omp.getUUID()))
            return true;

        if (hasPermission(omp.getUUID(), SurvivalClaim.Permission.MANAGE))
            return true;

        String name = getOwnerName();
        new ActionBar(omp.bukkit(), () -> "§c§l" + omp.translate("survival", "player.claim.permission.cant_interact", name + "§c§l"), 60).send();

        return false;
    }

    public boolean canBuild(SurvivalPlayer omp, Material material) {
        if (omp.isOpMode())
            return true;

        if (isOwner(omp.getUUID()))
            return true;

        if (hasPermission(omp.getUUID(), SurvivalClaim.Permission.BUILD))
            return true;

        if (ItemUtils.isFarmMaterial(material)) {
            if (hasPermission(omp.getUUID(), SurvivalClaim.Permission.MANAGE))
                return true;

            String name = getOwnerName();
            new ActionBar(omp.bukkit(), () -> "§c§l" + omp.translate("survival", "player.claim.permission.cant_farm", name + "§c§l"), 60).send();

            return false;
        }

        String name = getOwnerName();
        new ActionBar(omp.bukkit(), () -> "§c§l" + omp.translate("survival", "player.claim.permission.cant_build", name + "§c§l"), 60).send();

        return false;
    }

    public boolean canAccess(SurvivalPlayer omp, boolean notify) {
        if (omp.isOpMode())
            return true;

        if (isOwner(omp.getUUID()))
            return true;

        if (hasPermission(omp.getUUID(), SurvivalClaim.Permission.ACCESS))
            return true;

        if (notify) {
            String name = getOwnerName();
            new ActionBar(omp.bukkit(), () -> "§c§l" + omp.translate("survival", "player.claim.permission.cant_access", name + "§c§l"), 60).send();
        }

        return false;
    }
    
    /*
        Claim Management
     */

    public boolean inClaim(Location location, boolean ignoreY) {
        if (!location.getWorld().equals(getCorner1().getWorld()))
            return false;

        Location corner1 = getCorner1();
        Location corner2 = getCorner2();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        return (ignoreY || y >= corner1.getY() && y < corner2.getY() + 1)
            && x >= corner1.getX() && x < corner2.getX() + 1
            && z >= corner1.getZ() && z < corner2.getZ() + 1;
    }

    public boolean overlaps(Claim claim) {
        if (!getCorner1().getWorld().equals(claim.getCorner1().getWorld()))
            return false;

        if (claim.inClaim(getCorner1(), true))
            return true;
        if (claim.inClaim(getCorner2(), true))
            return true;
        if (claim.inClaim(new Location(getCorner1().getWorld(), getCorner1().getBlockX(), 0, getCorner2().getBlockZ()), true))
            return true;
        if (claim.inClaim(new Location(getCorner1().getWorld(), getCorner2().getBlockX(), 0, getCorner1().getBlockZ()), true))
            return true;

        if (inClaim(claim.getCorner1(), true))
            return true;

        if (getCorner1().getBlockZ() <= claim.getCorner2().getBlockZ() &&
            getCorner1().getBlockZ() >= claim.getCorner1().getBlockZ() &&
            getCorner1().getBlockX() < claim.getCorner1().getBlockX() &&
            getCorner2().getBlockX() > claim.getCorner2().getBlockX())
            return true;

        if (getCorner2().getBlockZ() <= claim.getCorner2().getBlockZ() &&
            getCorner2().getBlockZ() >= claim.getCorner1().getBlockZ() &&
            getCorner1().getBlockX() < claim.getCorner1().getBlockX() &&
            getCorner2().getBlockX() > claim.getCorner2().getBlockX())
            return true;

        if (getCorner1().getBlockX() <= claim.getCorner2().getBlockX() &&
            getCorner1().getBlockX() >= claim.getCorner1().getBlockX() &&
            getCorner1().getBlockZ() < claim.getCorner1().getBlockZ() &&
            getCorner2().getBlockZ() > claim.getCorner2().getBlockZ())
            return true;

        if (getCorner2().getBlockX() <= claim.getCorner2().getBlockX() &&
            getCorner2().getBlockX() >= claim.getCorner1().getBlockX() &&
            getCorner1().getBlockZ() < claim.getCorner1().getBlockZ() &&
            getCorner2().getBlockZ() > claim.getCorner2().getBlockZ())
            return true;

        return false;
    }

    public List<Chunk> getChunks() {
        List<Chunk> chunks = new ArrayList<>();

        World world = getCorner1().getWorld();
        Chunk lesserChunk = getCorner1().getChunk();
        Chunk greaterChunk = getCorner2().getChunk();

        for (int x = lesserChunk.getX(); x <= greaterChunk.getX(); x++) {
            for (int z = lesserChunk.getZ(); z <= greaterChunk.getZ(); z++) {
                chunks.add(world.getChunkAt(x, z));
            }
        }

        return chunks;
    }

    public ArrayList<Long> getChunkHashes() {
        ArrayList<Long> hashes = new ArrayList<>();
        int smallX = getCorner1().getBlockX() >> 4;
        int smallZ = getCorner1().getBlockZ() >> 4;
        int largeX = getCorner2().getBlockX() >> 4;
        int largeZ = getCorner2().getBlockZ() >> 4;

        for (int x = smallX; x <= largeX; x++) {
            for (int z = smallZ; z <= largeZ; z++) {
                hashes.add(survival().getClaimHandler().getChunkHash(x, z));
            }
        }

        return hashes;
    }

    public static class CreateResult {

        private Claim claim;
        private boolean succeeded;

        public Claim getClaim() {
            return claim;
        }

        public void setClaim(Claim claim) {
            this.claim = claim;
        }

        public boolean isSucceeded() {
            return succeeded;
        }

        public void setSucceeded(boolean succeeded) {
            this.succeeded = succeeded;
        }
    }

    /*

        SurvivalClaim delegates

     */

    public void setName(String name) {
        model.setName(name);
    }

    public void setCorner2(Location corner2) {
        model.setCorner2(corner2);
    }

    public void setCorner1(Location corner1) {
        model.setCorner1(corner1);
    }

    public Long getId() {
        return model.getId();
    }

    public UUID getOwner() {
        return model.getOwner();
    }

    public String getName() {
        if (getId() == null)
            return "Claim #<Loading...>";

        return model.getName() == null ? "Claim #" + NumberUtils.locale(getId()) : model.getName();
    }

    public Location getCorner1() {
        return model.getCorner1();
    }

    public Location getCorner2() {
        return model.getCorner2();
    }

    public Date getCreatedOn() {
        return model.getCreatedOn();
    }

    public void insert() {
        model.insert();
    }

    public void update(SurvivalClaim.column... columns) {
        model.update(columns);
    }

    public void delete() {
        model.delete();

        for (SurvivalClaimMember member : getMembers(true)) {
            member.delete();
        }
    }

    public boolean isInserted() {
        return model.isInserted();
    }

    public boolean isDestroyed() {
        return model.isDestroyed();
    }

    public void reload() {
        model.reload();
    }

    public void insertOrUpdate(SurvivalClaim.column... columns) {
        model.insertOrUpdate(columns);
    }

    public List<SurvivalClaimMember> getMembers(boolean reload) {
        return model.getMembers(reload);
    }

    public void reloadMembers() {
        model.reloadMembers();
    }

    public SurvivalClaimMember getMember(UUID uuid, boolean reload) {
        return model.getMember(uuid, reload);
    }
}
