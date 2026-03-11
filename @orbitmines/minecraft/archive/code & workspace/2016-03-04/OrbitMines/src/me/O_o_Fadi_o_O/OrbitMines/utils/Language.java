package me.O_o_Fadi_o_O.OrbitMines.utils;

import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.MiniGamesUtils;
import org.apache.commons.codec.language.bm.Lang;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

/**
 * Created by Fadi on 8-2-2016.
 */
public enum Language {

    DUTCH,
    ENGLISH;

    public enum Message {

        /* BreakEvent */
        CHEST_SHOP_DELETED,
        CANNOT_DO_THAT_HERE,
        BLOCK_GLITCH_DENY,

        /* CommandPreprocessEvent */
        WHILE_JOINING_PVP,
        WHILE_IN_PVP,
        NO_ACCESS_COMMAND,

        /* DamageEvent */
        STACKED,
        BEEN_STACKED,
        PLAYERS_OFF,
        YOU_STACKING_OFF,
        STACKING_OFF,
        YOU_RUNNER_KIT,
        RUNNER_KIT,

        /* EntityDeath */
        CURRENT_BEST_STREAK,
        BROKE_BEST_STREAK,
        KILLED_BY,
        SHOT_BY,
        REACHED_LEVEL,
        KILL_STREAK,
        DIED,
        DIED_VOID,
        __,
        __,
        __,
        __,
        __,
        ;

        public void broadcast(List<OMPlayer> omplayers, String... s){
            String DUTCH = send(Language.DUTCH, null, s);
            String ENGLISH = send(Language.ENGLISH, null, s);

            for(OMPlayer omp : omplayers){
                if(omp.getLanguage() == Language.DUTCH){
                    omp.getPlayer().sendMessage(DUTCH);
                }
                else{
                    omp.getPlayer().sendMessage(ENGLISH);
                }
            }
        }

        public void broadcast(String... s){
            String DUTCH = send(Language.DUTCH, null, s);
            String ENGLISH = send(Language.ENGLISH, null, s);

            for(OMPlayer omp : OMPlayer.getOMPlayers()){
                if(omp.getLanguage() == Language.DUTCH){
                    omp.getPlayer().sendMessage(DUTCH);
                }
                else{
                    omp.getPlayer().sendMessage(ENGLISH);
                }
            }
        }

        public String send(Language la, Player p, String... s){
            String sent = null;
            
            switch(this){
                case CHEST_SHOP_DELETED:
                    switch(la){
                        case DUTCH:
                            sent = "§7Jouw Chest Shop is verwijderd.";
                            break;
                        case ENGLISH:
                            sent = "§7Your Chest Shop has been removed.";
                            break;
                    }
                    break;
                case CANNOT_DO_THAT_HERE:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan dat hier niet doen!";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot do that here!";
                            break;
                    }
                    break;
                case BLOCK_GLITCH_DENY:
                    switch(la){
                        case DUTCH:
                            sent = "§7§lBlock glitchen is niet toegestaan!";
                            break;
                        case ENGLISH:
                            sent = "§7§lDo not try to block glitch!";
                            break;
                    }
                    break;
                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;
                case WHILE_IN_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je in de §2PvP Area§7 bent. (Verlaat de arena met §6/spawn§7)";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands in the §2PvP Area§7. (Leave with §6/spawn§7)";
                            break;
                    }
                    break;
                case NO_ACCESS_COMMAND:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je hebt geen toegang om §6" + s[0] + "§7 te gebruiken!";
                            break;
                        case ENGLISH:
                            sent = "§7You don't have permission to use §6" + s[0] + "§7!";
                            break;
                    }
                    break;
                case STACKED:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je hebt " + s[0] + "§7 op je hoofd gezet!";
                            break;
                        case ENGLISH:
                            sent = "§7You've §6§lstacked§f " + s[0] + "§7 on your Head!";
                            break;
                    }
                    break;
                case BEEN_STACKED:
                    switch(la){
                        case DUTCH:
                            sent = s[0] + " §7heeft je op zijn/haar hoofd gezet!";
                            break;
                        case ENGLISH:
                            sent =  s[0] + " §6§lstacked§7 you on their Head!";
                            break;
                    }
                    break;
                case PLAYERS_OFF:
                    switch(la){
                        case DUTCH:
                            sent = s[0] + "§7 heeft §3§lSpelers§7 §c§lUIT§7 staan.";
                            break;
                        case ENGLISH:
                            sent = s[0] + "§7 has §c§lDISABLED §3§lPlayers§7!";
                            break;
                    }
                    break;
                case YOU_STACKING_OFF:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je hebt de §6§lStacker§7 §c§lUIT§7 staan! Zet het aan in je §c§nInstellingen§7.";
                            break;
                        case ENGLISH:
                            sent = "§7You §c§lDISABLED§6§l stacking§7! Enable it in your §c§nSettings§7!";
                            break;
                    }
                    break;
                case STACKING_OFF:
                    switch(la){
                        case DUTCH:
                            sent = s[0] + "§7heeft de §6§lStacker§7 §c§lUIT§7 staan.";
                            break;
                        case ENGLISH:
                            sent = s[0] + "§7 has §c§lDISABLED §6§lstacking§7!";
                            break;
                    }
                    break;
                case YOU_RUNNER_KIT:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen schade oplopen in de eerste 30s! (§f§l" + s[0] + "§7)";
                            break;
                        case ENGLISH:
                            sent = "§7You can't deal damage the first 30s! (§f§l" + s[0] + "§7)";
                            break;
                    }
                    break;
                case RUNNER_KIT:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan " + s[0] + "§7 niet aanvallen in de eerste 30s! (§f§l" + s[1] + "§7)";
                            break;
                        case ENGLISH:
                            sent = "§7You can't attack " + s[0] + "§7 the first 30s! (§f§l" + s[1] + "§7)";
                            break;
                    }
                    break;
                case CURRENT_BEST_STREAK:
                    switch(la){
                        case DUTCH:
                            sent = "§f§lHuidige Streak: §c§l" + s[0] + " §f§lBeste Streak: §c§l" + s[1];
                            break;
                        case ENGLISH:
                            sent = "§f§lCurrent Streak: §c§l" + s[0] + " §f§lBest Streak: §c§l" + s[1];
                            break;
                    }
                    break;
                case BROKE_BEST_STREAK:
                    switch(la){
                        case DUTCH:
                            sent = "§f§lBeste Streak verbroken: §c§l" + s[0];
                            break;
                        case ENGLISH:
                            sent = "§f§lNew Best Streak: §c§l" + s[0];
                            break;
                    }
                    break;
                case KILLED_BY:
                    switch(la){
                        case DUTCH:
                            sent = "§6" + s[0] + "§7 was gedood door §6" + s[1] + "§7!";
                            break;
                        case ENGLISH:
                            sent = "§6" + s[0] + "§7 was killed by §6" + s[1] + "§7!";
                            break;
                    }
                    break;
                case SHOT_BY:
                    switch(la){
                        case DUTCH:
                            sent = "§6" + s[0] + "§7 was doodgeschoten door §6" + s[1] + "§7!";
                            break;
                        case ENGLISH:
                            sent = "§6" + s[0] + "§7 was shot by §6" + s[1] + "§7!";
                            break;
                    }
                    break;
                case REACHED_LEVEL:
                    switch(la){
                        case DUTCH:
                            sent = "§6" + s[0] + " §7heeft het level §6" + s[1] + "§7 bereikt!";
                            break;
                        case ENGLISH:
                            sent = "§6" + s[0] + " §7reached level §6" + s[1] + "§7!";
                            break;
                    }
                    break;
                case KILL_STREAK:
                    switch(la){
                        case DUTCH:
                            sent = "§c§l" + s[0] + "§7 heeft een §c§l" + s[1] + " Kill Streak§7!";
                            break;
                        case ENGLISH:
                            sent = "§c§l" + s[0] + "§7 has a §c§l" + s[1] + " Kill Streak§7!";
                            break;
                    }
                    break;
                case DIED:
                    switch(la){
                        case DUTCH:
                            sent = "§6" + s[0] + "§7 is uitgeschakeld.";
                            break;
                        case ENGLISH:
                            sent = "§6" + s[0] + "§7 died.";
                            break;
                    }
                    break;
                case DIED_VOID:
                    switch(la){
                        case DUTCH:
                            sent = s[0] + "§7 is in de afgrond gevallen.";
                            break;
                        case ENGLISH:
                            sent = s[0] + "§7 fell into the void.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

                case WHILE_JOINING_PVP:
                    switch(la){
                        case DUTCH:
                            sent = "§7Je kan geen commands uitvoeren terwijl je de §2PvP Area§7 aan het joinen bent.";
                            break;
                        case ENGLISH:
                            sent = "§7You cannot perform commands while joining the §2PvP Area§7.";
                            break;
                    }
                    break;

            }

            if(p != null){ p.sendMessage(sent); }
            return sent;
        }
    }
}
