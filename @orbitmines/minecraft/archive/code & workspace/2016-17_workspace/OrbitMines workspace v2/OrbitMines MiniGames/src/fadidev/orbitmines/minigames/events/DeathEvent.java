package fadidev.orbitmines.minigames.events;

import fadidev.orbitmines.api.handlers.chat.ActionBar;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.MiniGamesMessages;
import fadidev.orbitmines.minigames.handlers.data.*;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.handlers.players.minigames.*;
import fadidev.orbitmines.minigames.utils.enums.State;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Fadi on 1-10-2016.
 */
public class DeathEvent implements Listener {

    private OrbitMinesMiniGames miniGames;
    
    public DeathEvent(){
        miniGames = OrbitMinesMiniGames.getMiniGames();
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        if(e.getEntity() instanceof Zombie && e.getEntity().getCustomName() != null && e.getEntity().getCustomName().startsWith("§c"))
            e.getDrops().clear();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        final Player p = e.getEntity();
        final MiniGamesPlayer omp = MiniGamesPlayer.getMiniGamesPlayer(p);
        final Arena arena = omp.getArena();

        if(arena == null)
            return;
        
        p.setHealth(20D);
        p.setFoodLevel(20);
        e.setDeathMessage(null);

        if(arena.getState() == State.IN_GAME){
            switch(arena.getType()){
                case CHICKEN_FIGHT:
                    e.getDrops().clear();

                    arena.getPlayers().remove(omp);
                    arena.getDeadPlayers().add(omp);
                    arena.getSpectators().add(omp);

                    omp.getChickenFightPlayer().setSecondsSurvived(900 - (arena.getMinutes() * 60 + arena.getSeconds()));
                    omp.clearPotionEffects();

                    p.setAllowFlight(true);
                    p.setFlying(true);
                    
                    miniGames.getApi().getNms().entity().setInvisible(p, true);

                    if(p.getKiller() != null){
                        Player pK = p.getKiller();
                        MiniGamesPlayer ompK = MiniGamesPlayer.getMiniGamesPlayer(pK);
                        ChickenFightPlayer cfpK = ompK.getChickenFightPlayer();

                        arena.sendMessage(MiniGamesMessages.KILLED_BY, omp.getColorName(), ompK.getColorName());

                        pK.sendMessage("§2§l+1 Kill");

                        int kills = cfpK.getRoundKills();
                        cfpK.addRoundKill();

                        if(cfpK.getRoundKills() % 2 == 0){
                            ActionBar ab = new ActionBar(pK, "§2+1 Kill§7, §f+1 Ticket", 40);
                            ab.send();

                            pK.sendMessage("§f§l+1 Ticket");
                            omp.addMiniGameTickets(1);
                        }
                        else{
                            ActionBar ab = new ActionBar(pK, "§2+1 Kill", 40);
                            ab.send();
                        }
                        cfpK.addKill();

                        pK.sendMessage("§f§l" + MiniGamesMessages.WORD_CURRENT.get(ompK) + " Streak: §c§l" + cfpK.getRoundKills() + " §f§l" + MiniGamesMessages.WORD_BEST.get(ompK) + " Streak: §c§l" + cfpK.getRoundKills());
                        if(cfpK.getBestStreak() < kills +1){
                            cfpK.setBestStreak(kills +1);

                            pK.sendMessage(MiniGamesMessages.NEW_BEST_STREAK.get(ompK, (kills +1) + ""));
                            pK.playSound(pK.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
                        }
                    }
                    else{
                        if(p.getLocation().getY() < 0){
                            arena.sendMessage(MiniGamesMessages.DIED_VOID, omp.getColorName());
                        }
                        else{
                            arena.sendMessage(MiniGamesMessages.DIED, omp.getColorName());
                        }
                    }

                    ChickenFightData cfData = (ChickenFightData) arena.getData();
                    if(arena.getPlayers().size() == 1){
                        cfData.setSecondPlace(omp);
                        cfData.ending();
                    }
                    else if(arena.getPlayers().size() == 2){
                        cfData.setThirdPlace(omp);
                    }

                    new BukkitRunnable(){
                        public void run(){
                            miniGames.getSpectatorKit().get(omp.getLanguage()).setItems(p);

                            if(p.getLocation().getY() < 0){
                                p.teleport(arena.getMap().getSpectatorLocation());
                            }
                        }
                    }.runTaskLater(miniGames, 1);
                    break;
                case GHOST_ATTACK:
                    GhostAttackData gaData = (GhostAttackData) arena.getData();

                    arena.getPlayers().remove(omp);
                    arena.getDeadPlayers().add(omp);
                    arena.getSpectators().add(omp);

                    p.setAllowFlight(true);
                    p.setFlying(true);
                    miniGames.getApi().getNms().entity().setInvisible(p, true);

                    if(gaData.isGhost(omp)) {
                        gaData.setGhostDead(true);
                    }
                    else{
                        Location l = p.getLocation();

                        Zombie zombie = (Zombie) l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
                        zombie.setBaby(false);
                        zombie.getEquipment().setHelmet(ItemUtils.getSkull(p.getName()));
                        zombie.setMaxHealth(1D);
                        zombie.setCustomName("§c" + p.getName());
                        zombie.setCustomNameVisible(true);
                    }

                    if(p.getKiller() != null){
                        Player pK = p.getKiller();
                        MiniGamesPlayer ompK = MiniGamesPlayer.getMiniGamesPlayer(pK);
                        GhostAttackPlayer gapK = ompK.getGhostAttackPlayer();

                        arena.sendMessage(MiniGamesMessages.KILLED_BY, omp.getColorName(), ompK.getColorName());

                        int kills = gapK.getRoundKills();
                        gapK.addRoundKill();
                        
                        if(gaData.isGhost(ompK)){
                            pK.sendMessage("§2§l+1 Kill");
                            pK.sendMessage("§f§l+1 Ticket");

                            ActionBar ab = new ActionBar(pK, "§2+1 Kill§7, §f+1 Ticket", 40);
                            ab.send();

                            gapK.addGhostKill();
                            omp.addMiniGameTickets(1);

                            pK.sendMessage("§f§l" + MiniGamesMessages.WORD_CURRENT.get(ompK) + " Streak: §c§l" + gapK.getRoundKills() + " §f§l" + MiniGamesMessages.WORD_BEST.get(ompK) + " Streak: §c§l" + gapK.getRoundKills());
                            if(gapK.getBestStreak() < kills +1){
                                gapK.setBestStreak(kills +1);

                                pK.sendMessage(MiniGamesMessages.NEW_BEST_STREAK.get(ompK, (kills +1) + ""));
                                pK.playSound(pK.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
                            }
                        }
                        else{
                            pK.sendMessage("§2§l+1 Kill");
                            pK.sendMessage("§f§l+2 Tickets");

                            ActionBar ab = new ActionBar(pK, "§2+1 Kill§7, §f+2 Tickets", 40);
                            ab.send();

                            gapK.addKill();
                            omp.addMiniGameTickets(2);

                            gaData.setGhostKiller(ompK);
                        }
                    }
                    else{
                        if(p.getLocation().getY() < 0){
                            arena.sendMessage(MiniGamesMessages.DIED_VOID, omp.getColorName());
                        }
                        else{
                            arena.sendMessage(MiniGamesMessages.DIED, omp.getColorName());
                        }
                    }

                    if(arena.getPlayers().size() == 1 || gaData.isGhostDead()){
                        gaData.ending();
                    }

                    new BukkitRunnable(){
                        public void run(){
                            miniGames.getSpectatorKit().get(omp.getLanguage()).setItems(p);

                            if(p.getLocation().getY() < 0){
                                p.teleport(arena.getMap().getSpectatorLocation());
                            }
                        }
                    }.runTaskLater(miniGames, 1);
                    break;
                case SKYWARS:
                    arena.getPlayers().remove(omp);
                    arena.getDeadPlayers().add(omp);
                    arena.getSpectators().add(omp);

                    p.setAllowFlight(true);
                    p.setFlying(true);
                    miniGames.getApi().getNms().entity().setInvisible(p, true);

                    if(p.getKiller() != null || omp.getSkywarsPlayer().getLastProjectile() != null && omp != omp.getSkywarsPlayer().getLastProjectile()){
                        Player pK = null;
                        MiniGamesPlayer ompK = null;
                        if(p.getKiller() != null){
                            pK = p.getKiller();
                            ompK = MiniGamesPlayer.getMiniGamesPlayer(pK);
                        }
                        else{
                            ompK = omp.getSkywarsPlayer().getLastProjectile();
                            pK = ompK.getPlayer();
                        }
                        SkywarsPlayer swpK = ompK.getSkywarsPlayer();

                        if(p.getKiller() != null){
                            arena.sendMessage(MiniGamesMessages.KILLED_BY, omp.getColorName(), ompK.getColorName());
                        }
                        else{
                            arena.sendMessage(MiniGamesMessages.THROWN_IN_VOID, omp.getColorName(), ompK.getColorName());
                        }

                        pK.sendMessage("§2§l+1 Kill");
                        pK.sendMessage("§f§l+1 Ticket");

                        ActionBar ab = new ActionBar(pK, "§2+1 Kill§7, §f+1 Ticket", 40);
                        ab.send();

                        int kills = swpK.getRoundKills();
                        swpK.addRoundKill();
                        swpK.addKill();
                        omp.addMiniGameTickets(1);

                        pK.sendMessage("§f§l" + MiniGamesMessages.WORD_CURRENT.get(ompK) + " Streak: §c§l" + swpK.getRoundKills() + " §f§l" + MiniGamesMessages.WORD_BEST.get(ompK) + " Streak: §c§l" + swpK.getRoundKills());
                        if(swpK.getBestStreak() < kills +1){
                            swpK.setBestStreak(kills +1);

                            pK.sendMessage(MiniGamesMessages.NEW_BEST_STREAK.get(ompK, (kills +1) + ""));
                            pK.playSound(pK.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
                        }
                    }
                    else{
                        if(p.getLocation().getY() < 0){
                            arena.sendMessage(MiniGamesMessages.DIED_VOID, omp.getColorName());
                        }
                        else{
                            arena.sendMessage(MiniGamesMessages.DIED, omp.getColorName());
                        }
                    }

                    SkywarsData swData = (SkywarsData) arena.getData();

                    if(arena.getPlayers().size() == 1){
                        swData.setSecondPlace(omp);
                        swData.ending();
                    }
                    else if(arena.getPlayers().size() == 2){
                        swData.setThirdPlace(omp);
                    }

                    new BukkitRunnable(){
                        public void run(){
                            miniGames.getSpectatorKit().get(omp.getLanguage()).setItems(p);

                            if(p.getLocation().getY() < 0){
                                p.teleport(arena.getMap().getSpectatorLocation());
                            }
                        }
                    }.runTaskLater(miniGames, 1);
                    break;
                case SPLATCRAFT:
                    break;
                case SPLEEF:
                    break;
                case SURVIVAL_GAMES:
                    arena.getPlayers().remove(omp);
                    arena.getDeadPlayers().add(omp);
                    arena.getSpectators().add(omp);

                    omp.clearPotionEffects();

                    p.setAllowFlight(true);
                    p.setFlying(true);
                    miniGames.getApi().getNms().entity().setInvisible(p, true);

                    if(p.getKiller() != null){
                        Player pK = p.getKiller();
                        MiniGamesPlayer ompK = MiniGamesPlayer.getMiniGamesPlayer(pK);
                        SurvivalGamesPlayer sgpK = ompK.getSurvivalGamesPlayer();

                        arena.sendMessage(MiniGamesMessages.KILLED_BY, omp.getColorName(), ompK.getColorName());

                        pK.sendMessage("§2§l+1 Kill");
                        pK.sendMessage("§f§l+1 Ticket");

                        ActionBar ab = new ActionBar(pK, "§2+1 Kill§7, §f+1 Ticket", 40);
                        ab.send();

                        int kills = sgpK.getRoundKills();
                        sgpK.addRoundKill();
                        sgpK.addKill();
                        omp.addMiniGameTickets(1);

                        pK.sendMessage("§f§l" + MiniGamesMessages.WORD_CURRENT.get(ompK) + " Streak: §c§l" + sgpK.getRoundKills() + " §f§l" + MiniGamesMessages.WORD_BEST.get(ompK) + " Streak: §c§l" + sgpK.getRoundKills());
                        if(sgpK.getBestStreak() < kills +1){
                            sgpK.setBestStreak(kills +1);

                            pK.sendMessage(MiniGamesMessages.NEW_BEST_STREAK.get(ompK, (kills +1) + ""));
                            pK.playSound(pK.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
                        }
                    }
                    else{
                        arena.sendMessage(MiniGamesMessages.DIED, omp.getColorName());
                    }

                    SurvivalGamesData sgData = (SurvivalGamesData) arena.getData();

                    if(arena.getPlayers().size() == 1){
                        sgData.setSecondPlace(omp);
                        sgData.ending();
                    }
                    else if(arena.getPlayers().size() == 2){
                        sgData.setThirdPlace(omp);
                    }

                    if(!sgData.isInDeathMatch() && arena.getMinutes() != 0 && arena.getPlayers().size() != 1 && arena.getPlayers().size() <= 3){
                        arena.setMinutes(1);
                        arena.setSeconds(0);

                        arena.playSound(Sound.ENTITY_WITHER_DEATH, 5, 1);
                        arena.sendMessage(MiniGamesMessages.SG_DEATHMATCH_STARTING_IN.get(omp, "1", "0"));
                    }

                    new BukkitRunnable(){
                        public void run(){
                            miniGames.getSpectatorKit().get(omp.getLanguage()).setItems(p);
                        }
                    }.runTaskLater(miniGames, 1);
                    break;
                case ULTRA_HARD_CORE:
                    arena.getPlayers().remove(omp);
                    arena.getDeadPlayers().add(omp);
                    arena.getSpectators().add(omp);

                    p.setAllowFlight(true);
                    p.setFlying(true);
                    miniGames.getApi().getNms().entity().setInvisible(p, true);

                    if(p.getKiller() != null){
                        Player pK = p.getKiller();
                        MiniGamesPlayer ompK = MiniGamesPlayer.getMiniGamesPlayer(pK);
                        UHCPlayer uhcK = ompK.getUhcPlayer();

                        arena.sendMessage(MiniGamesMessages.KILLED_BY, omp.getColorName(), ompK.getColorName());

                        pK.sendMessage("§2§l+1 Kill");
                        pK.sendMessage("§f§l+2 Tickets");

                        ActionBar ab = new ActionBar(pK, "§2+1 Kill§7, §f+2 Tickets", 40);
                        ab.send();

                        int kills = uhcK.getRoundKills();
                        uhcK.addRoundKill();
                        uhcK.addKill();
                        omp.addMiniGameTickets(2);

                        pK.sendMessage("§f§l" + MiniGamesMessages.WORD_CURRENT.get(ompK) + " Streak: §c§l" + uhcK.getRoundKills() + " §f§l" + MiniGamesMessages.WORD_BEST.get(ompK) + " Streak: §c§l" + uhcK.getRoundKills());
                        if(uhcK.getBestStreak() < kills +1){
                            uhcK.setBestStreak(kills +1);

                            pK.sendMessage(MiniGamesMessages.NEW_BEST_STREAK.get(ompK, (kills +1) + ""));
                            pK.playSound(pK.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
                        }
                    }
                    else{
                        arena.sendMessage(MiniGamesMessages.DIED, omp.getColorName());
                    }

                    UHCData uhcData = (UHCData) arena.getData();

                    if(arena.getPlayers().size() == 1){
                        uhcData.setSecondPlace(omp);
                        uhcData.ending();
                    }
                    else if(arena.getPlayers().size() == 2){
                        uhcData.setThirdPlace(omp);
                    }

                    new BukkitRunnable(){
                        public void run(){
                            miniGames.getSpectatorKit().get(omp.getLanguage()).setItems(p);
                        }
                    }.runTaskLater(miniGames, 1);
                    break;
            }
        }
    }
}
