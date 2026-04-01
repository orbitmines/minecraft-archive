package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.claim;

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalClaim;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Claim;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import org.bukkit.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public class ClaimHandler {

    private final Survival survival;

    private ArrayList<Claim> claims;
    private ConcurrentHashMap<Long, ArrayList<Claim>> chunkClaims;

    public ClaimHandler(Survival survival) {
        this.survival = survival;

        claims = new ArrayList<>();
        chunkClaims = new ConcurrentHashMap<>();
    }

    public boolean canBuild(SurvivalPlayer omp, Location location) {
        return canBuild(omp, location, location.getBlock().getType());
    }

    public boolean canBuild(SurvivalPlayer omp, Location location, Material material) {
        if (!survival.canClaimIn(location.getWorld()))
            return false;

        Claim claim = getClaimAt(location, false, omp.getLastClaim());

        if (claim == null)
            return true;

        omp.setLastClaim(claim);

        return claim.canBuild(omp, material);
    }

    public void addClaim(Claim claim, boolean save) {
        this.claims.add(claim);
        ArrayList<Long> chunkHashes = claim.getChunkHashes();
        for (Long chunkHash : chunkHashes) {
            ArrayList<Claim> claimsInChunk = chunkClaims.computeIfAbsent(chunkHash, key -> new ArrayList<>());
            claimsInChunk.add(claim);
        }

        if (save)
            saveClaim(claim);
    }

    public Claim getClaimAt(Location location, boolean ignoreY, Claim cached) {
        if (cached != null && cached.isInserted() && !cached.isDestroyed() && cached.inClaim(location, ignoreY))
            return cached;

        Long chunkHash = getChunkHash(location);
        ArrayList<Claim> claimsInChunk = chunkClaims.get(chunkHash);

        if (claimsInChunk == null)
            return null;

        for (Claim claim : claimsInChunk) {
            if (claim.isInserted() && !claim.isDestroyed() && claim.inClaim(location, ignoreY))
                return claim;
        }

        return null;
    }

    public Claim getClaim(long id) {
        for (Claim claim : claims) {
            if (claim.isInserted() && !claim.isDestroyed() && claim.getId() == id)
                return claim;
        }
        return null;
    }

    public List<Claim> getClaims(UUID owner) {
        List<Claim> claims = new ArrayList<>();
        for (Claim claim : this.claims) {
            if (claim.isInserted() && !claim.isDestroyed() && (claim.getOwner() != null && claim.getOwner().equals(owner)))
                claims.add(claim);
        }
        return claims;
    }

    public Collection<Claim> getClaims() {
        return Collections.unmodifiableCollection(this.claims);
    }

    public Collection<Claim> getClaims(long x, long z) {
        ArrayList<Claim> chunkClaims = this.chunkClaims.get(getChunkHash(x, z));

        if (chunkClaims != null)
            return Collections.unmodifiableCollection(chunkClaims);

        return Collections.unmodifiableCollection(new ArrayList<>());
    }
    
    public java.util.Set<Claim> getNearbyClaims(Location location) {
        java.util.Set<Claim> claims = new HashSet<>();

        Chunk chunk1 = location.getWorld().getChunkAt(location.subtract(150, 0, 150));
        Chunk chunk2 = location.getWorld().getChunkAt(location.add(300, 0, 300));

        for (int chunk_x = chunk1.getX(); chunk_x <= chunk2.getX(); chunk_x++) {
            for (int chunk_z = chunk1.getZ(); chunk_z <= chunk2.getZ(); chunk_z++) {
                
                Chunk chunk = location.getWorld().getChunkAt(chunk_x, chunk_z);
                Long chunkID = getChunkHash(chunk.getBlock(0, 0, 0).getLocation());
                ArrayList<Claim> claimsInChunk = chunkClaims.get(chunkID);
                
                if (claimsInChunk != null) {
                    for (Claim claim : claimsInChunk) {
                        if (claim.isInserted() && !claim.isDestroyed() && claim.getCorner1().getWorld().equals(location.getWorld()))
                            claims.add(claim);

                    }
                }
            }
        }

        return claims;
    }

    public Long getChunkHash(long x, long z) {
        return (z ^ (x << 32));
    }

    public Long getChunkHash(Location location) {
        return getChunkHash(location.getBlockX() >> 4, location.getBlockZ() >> 4);
    }

    public int getRemaining(UUID owner, int claimBlocks) {
        int remaining = claimBlocks;

        for (Claim claim : survival.getClaimHandler().getClaims()) {
            if (claim.isInserted() && !claim.isDestroyed() && owner.equals(claim.getOwner()))
                remaining -= claim.getArea();
        }

        return remaining;
    }

    public Claim.CreateResult createClaim(World world, SurvivalPlayer omp, UUID owner, int x1, int x2, int y1, int y2, int z1, int z2, Claim resizingClaim) {
        y1 = 0;
        y2 = world.getMaxHeight();

        int smallX, bigX, smallY, bigY, smallZ, bigZ;

        /* Small/Big */
        if (x1 < x2) {
            smallX = x1;
            bigX = x2;
        } else {
            smallX = x2;
            bigX = x1;
        }

        if (y1 < y2) {
            smallY = y1;
            bigY = y2;
        } else {
            smallY = y2;
            bigY = y1;
        }

        if (z1 < z2) {
            smallZ = z1;
            bigZ = z2;
        } else {
            smallZ = z2;
            bigZ = z1;
        }

        Claim claim = new Claim(owner, new Location(world, smallX, smallY, smallZ), new Location(world, bigX, bigY, bigZ));

        Claim.CreateResult result = new Claim.CreateResult();

        List<Claim> toCheck = this.claims;

        for (Claim check : toCheck) {
            if (resizingClaim != null && resizingClaim.getId().equals(check.getId()))
                continue;

            if (check.getId() != claim.getId() && check.isInserted() && !check.isDestroyed() && check.overlaps(claim)) {
                result.setSucceeded(false);
                result.setClaim(claim);
                return result;
            }
        }

        if (resizingClaim == null)
            addClaim(claim, true);

        result.setSucceeded(true);
        result.setClaim(claim);
        return result;
    }

    public Claim.CreateResult resizeClaim(Claim claim, SurvivalPlayer omp, int newX1, int newX2, int newY1, int newY2, int newZ1, int newZ2) {
        Claim.CreateResult result = createClaim(claim.getCorner1().getWorld(), omp, claim.getOwner(), newX1, newX2, newY1, newY2, newZ1, newZ2, claim);

        if (!result.isSucceeded())
            return result;

        claim.setCorner1(result.getClaim().getCorner1());
        claim.setCorner2(result.getClaim().getCorner2());

        saveClaim(claim);
        
        return result;
    }

    public void resizeClaimWithChecks(SurvivalPlayer omp, int newX1, int newX2, int newY1, int newY2, int newZ1, int newZ2) {
        Claim claim = omp.getResizingClaim();

        int newWidth = (Math.abs(newX1 - newX2) + 1);
        int newHeight = (Math.abs(newZ1 - newZ2) + 1);
        int newArea = newWidth * newHeight;

        if (newWidth < Claim.MIN_WIDTH || newHeight < Claim.MIN_WIDTH) {
            new ActionBar(omp.bukkit(), () -> "§c§l" + omp.translate("survival", "player.claim.min_width"), 60).send();
            return;
        }

        if (newArea < Claim.MIN_AREA) {
            new ActionBar(omp.bukkit(), () -> omp.translate("survival", "player.claim.min_width"), 60).send();
            return;
        }

        if (claim.hasOwner() && omp.getUUID().equals(claim.getOwner())) {
            int remaining = omp.getRemainingClaimBlocks() + claim.getArea() - newArea;

            if (remaining < 0) {
                new ActionBar(omp.bukkit(), () -> "§c§l" + omp.translate("survival", "player.claim.need_more_claimsblocks", "§6§l" + Math.abs(remaining) + " Claimblocks§c§l"), 60).send();
                return;
            }
        }

        Claim.CreateResult result = resizeClaim(claim, omp, newX1, newX2, newY1, newY2, newZ1, newZ2);
        claim = result.getClaim();

        if (result.isSucceeded()) {
            int remaining = 0;
            if (claim.hasOwner()) {
                UUID owner = claim.getOwner();

                if (omp.getUUID().equals(owner)) {
                    remaining = omp.getRemainingClaimBlocks();
                } else {
                    SurvivalPlayer ownerPlayer = survival.getPlayer(owner);

                    if (ownerPlayer != null)
                        remaining = ownerPlayer.getRemainingClaimBlocks();
                    else
                        remaining = getRemaining(owner, SurvivalPlayerModel.findBy(SurvivalPlayerModel.class, SurvivalPlayerModel.column.UUID.is(owner)).getClaimBlocks());
                }
            }

            int fRemaining = remaining;
            new ActionBar(omp.bukkit(), () -> "§a§l" + omp.translate("survival", "player.claim.resized", "§6§l" + fRemaining + " Claimblocks§a§l"), 100).send();

            Visualization.show(omp, result.getClaim(), omp.getEyeLocation().getBlockY(), Visualization.Type.CLAIM, omp.getLocation());

            omp.setResizingClaim(null);
            omp.setLastClaimToolLocation(null);
            omp.playSound(Sound.ENTITY_ARROW_HIT_PLAYER);
            return;
        }

        new ActionBar(omp.bukkit(), () -> "§c§l" + omp.translate("survival", "player.claim.overlaps"), 60).send();

        if (result.getClaim() == null)
            return;

        Visualization.show(omp, result.getClaim(), omp.getEyeLocation().getBlockY(), Visualization.Type.INVALID, omp.getLocation());
    }

    public void saveClaim(Claim claim) {
        if (claim.isInserted())
            claim.update(SurvivalClaim.column.CORNER_1, SurvivalClaim.column.CORNER_2);
        else
            claim.insert();
    }

    public void deleteClaim(Claim claim) {
        claim.delete();

        this.claims.remove(claim);

        for (Long chunkHash : claim.getChunkHashes()) {
            ArrayList<Claim> claimsInChunk = chunkClaims.get(chunkHash);

            if (claimsInChunk != null)
                claimsInChunk.remove(claim);
        }
    }

    public void deleteClaims(UUID owner) {
        for (Claim claim : new ArrayList<>(this.claims)) {
            if (owner.equals(claim.getOwner()))
                deleteClaim(claim);
        }
    }

    public void deleteClaims(World world) {
        for (Claim claim : new ArrayList<>(this.claims)) {
            if (claim.getCorner1().getWorld().equals(world))
                deleteClaim(claim);
        }
    }

//    public void switchOwner(Claim claim, UUID newOwner) {
//        claim.setOwner(newOwner);
//        saveClaim(claim);
//    }

    public void abandonClaim(Claim claim, SurvivalPlayer omp) {
        if (!claim.canModify(omp)) {
            new ActionBar(omp.bukkit(), () -> "§c§l" + omp.translate("survival", "player.claim.abandon.cant_modify"), 60).send();
        } else {
            deleteClaim(claim);

            new ActionBar(omp.bukkit(), () -> "§a§l" + omp.translate("survival", "player.claim.abandoned", "§6§l" + omp.getRemainingClaimBlocks() + "§a§l"), 60).send();

            Visualization.revert(omp);
        }
    }
}
