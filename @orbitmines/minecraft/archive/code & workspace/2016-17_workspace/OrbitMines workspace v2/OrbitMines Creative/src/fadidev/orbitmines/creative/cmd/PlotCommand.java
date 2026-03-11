package fadidev.orbitmines.creative.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.chat.ComponentMessage;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.UUIDUtils;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.creative.OrbitMinesCreative;
import fadidev.orbitmines.creative.handlers.CreativeCooldowns;
import fadidev.orbitmines.creative.handlers.CreativeMessages;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import fadidev.orbitmines.creative.handlers.Plot;
import fadidev.orbitmines.creative.inventories.PlotInfoInv;
import fadidev.orbitmines.creative.inventories.PlotSetupInv;
import fadidev.orbitmines.creative.utils.enums.PlotType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * Created by Fadi on 10-9-2016.
 */
public class PlotCommand extends Command {

    private OrbitMinesCreative creative;
    String[] alias = { "/plot", "/p" };

    public PlotCommand(){
        creative = OrbitMinesCreative.getCreative();
    }

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        CreativePlayer omp = (CreativePlayer) omPlayer;
        Player p = omp.getPlayer();

        if(a.length > 1){
            if(a[1].equalsIgnoreCase("home") || a[1].equalsIgnoreCase("h")){
                if(!omp.hasPlot()){
                    p.sendMessage(CreativeMessages.CMD_PLOT_PLOT_PREPARING.get(omp));
                    p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
                    Plot.nextPlot(omp);
                }

                p.teleport(omp.getPlot().getHomeLocation());
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 5, 1);
                Title t = new Title("", CreativeMessages.CMD_PLOT_TP_TO_YOUR_PLOT.get(omp), 20, 40, 20);
                t.send(p);
            }
            else if(a[1].equalsIgnoreCase("sethome") || a[1].equalsIgnoreCase("seth")){
                if(omp.hasPlot()){
                    if(p.getWorld().getName().equals(creative.getPlotWorld().getName())){
                        if(omp.isOnPlot(p.getLocation())){
                            omp.getPlot().setHomeLocation(p.getLocation());
                            p.sendMessage(CreativeMessages.CMD_PLOT_HOME_SET.get(omp));
                        }
                    }
                    else{
                        p.sendMessage(CreativeMessages.CMD_PLOT_CANNOT_SET_HOME.get(omp));
                    }
                }
                else{
                    p.sendMessage(CreativeMessages.CMD_PLOT_NO_PLOT.get(omp));
                }
            }
            else if(a[1].equalsIgnoreCase("pvpbroadcast")){
                if(omp.hasPerms(VIPRank.EMERALD_VIP)){
                    if(omp.hasPlot()){
                        if(omp.getPlot().getPlotType() == PlotType.PVP){
                            if(!omp.onCooldown(CreativeCooldowns.BROADCAST)){
                                for(CreativePlayer omp2 : creative.getCreativePlayers()){
                                    Player player = omp2.getPlayer();
                                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
                                    player.sendMessage("");

                                    ComponentMessage cm = new ComponentMessage();
                                    cm.addPart("§d" + p.getName() + "'s Plot§7 is in §c§lPvP Mode§7! ");
                                    cm.addPart(CreativeMessages.CMD_PLOT_BROADCAST.get(omp2), ClickEvent.Action.RUN_COMMAND, "/p join " + omp.getPlot().getPlotId(), HoverEvent.Action.SHOW_TEXT, CreativeMessages.CMD_PLOT_BROADCAST_CLICK.get(omp2));
                                    cm.send(player);
                                }

                                omp.resetCooldown(CreativeCooldowns.BROADCAST);
                            }
                        }
                        else{
                            p.sendMessage(CreativeMessages.CMD_PLOT_MUST_BE_PVP.get(omp));
                        }
                    }
                    else{
                        p.sendMessage(CreativeMessages.CMD_PLOT_NO_PLOT.get(omp));
                    }
                }
                else{
                    omp.requiredVIPRank(VIPRank.EMERALD_VIP);
                }
            }
            else if(a[1].equalsIgnoreCase("join")){
                if(omp.getPvPPlot() == null){
                    if(a.length == 3){
                        try{
                            Plot plot = Plot.getPlot(Integer.parseInt(a[2]));

                            if(plot != null && plot.getPlotType() == PlotType.PVP){
                                List<CreativePlayer> players = Plot.getPvPPlayers(plot);
                                if(players.size() < plot.getPvPMaxPlayers()){
                                    players.add(omp);
                                    omp.setPvPPlot(plot);

                                    p.teleport(plot.getPvPLobbyLocation());
                                    p.setGameMode(GameMode.SURVIVAL);
                                    Title t = new Title("", CreativeMessages.CMD_PLOT_JOIN_PLOT.get(omp, plot.getPlotId() + ""), 20, 40, 20);
                                    t.send(p);

                                    for(CreativePlayer cplayer : players){
                                        cplayer.getPlayer().playSound(cplayer.getPlayer().getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
                                        cplayer.getPlayer().sendMessage(CreativeMessages.CMD_PLOT_JOINED_PLAYER.get(cplayer, p.getName(), plot.getPlotId() + "", players.size() + "", plot.getPvPMaxPlayers() + ""));
                                    }

                                    if(omp.hasHatsBlockTrail())
                                        omp.setHatsBlockTrail(false);
                                    if(omp.isDisguised())
                                        omp.unDisguise();
                                    if(omp.hasWardrobeEnabled())
                                        omp.disableWardrobe();

                                    p.setLevel(0);
                                    p.setExp(0);
                                    omp.clearInventory();
                                    omp.clearPotionEffects();
                                }
                                else{
                                    p.sendMessage(CreativeMessages.CMD_PLOT_FULL.get(omp, players.size() + "", plot.getPvPMaxPlayers() + ""));
                                }
                            }
                            else{
                                p.sendMessage(CreativeMessages.CMD_PLOT_IN_BUILD_MODE_PLAYER.get(omp));
                            }
                        }catch(NumberFormatException ex){
                            p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " §dPlot ID§7.");
                        }
                    }
                    else{
                        p.sendMessage("§7" + Messages.WORD_INVALID_COMMAND.get(omp) + ". (§d/p join <plot-id>§7)");
                    }
                }
                else{
                    p.sendMessage(CreativeMessages.CMD_PLOT_ALREADY_ON.get(omp));
                }
            }
            else if(a[1].equalsIgnoreCase("clear")){
                if(omp.hasPlot()){
                    if(omp.getPlot().canReset()){
                        omp.getPlot().reset();
                    }
                    else{
                        p.sendMessage(CreativeMessages.CMD_PLOT_RESET_COOLDOWN.get(omp));
                    }
                }
                else{
                    p.sendMessage(CreativeMessages.CMD_PLOT_NO_PLOT.get(omp));
                }
            }
            else if(a[1].equalsIgnoreCase("setuppvp")){
                if(omp.hasPerms(VIPRank.EMERALD_VIP)){
                    if(omp.hasPlot()){
                        if(omp.getPlot().getPlotType() == PlotType.NORMAL){
                            new PlotSetupInv().open(p);
                        }
                        else{
                            p.sendMessage(CreativeMessages.CMD_PLOT_IN_BUILD_MODE.get(omp));
                        }
                    }
                    else{
                        p.sendMessage(CreativeMessages.CMD_PLOT_NO_PLOT.get(omp));
                    }
                }
                else{
                    omp.requiredVIPRank(VIPRank.EMERALD_VIP);
                }
            }
            else if(a[1].equalsIgnoreCase("info")){
                if(omp.hasPlot()){
                    new PlotInfoInv().open(p);
                }
                else{
                    p.sendMessage(CreativeMessages.CMD_PLOT_NO_PLOT.get(omp));
                }
            }
            else if(a[1].equalsIgnoreCase("add")){
                if(omp.hasPlot()){
                    if(a.length == 3){
                        Player p2 = PlayerUtils.getPlayer(a[2]);

                        if(p2 != null){
                            if(p2 != p){
                                CreativePlayer omp2 = CreativePlayer.getCreativePlayer(p2);
                                Plot plot = omp.getPlot();

                                if(!plot.getMemberUUIDs().contains(p2.getUniqueId())){
                                    p.sendMessage(CreativeMessages.CMD_PLOT_CAN_ACCESS.get(omp, p2.getName()));
                                    p2.sendMessage(CreativeMessages.CMD_PLOT_CAN_ACCESS_PLAYER.get(omp2, p.getName()));
                                    p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                                    p2.playSound(p2.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);

                                    plot.addMemberUUID(p2.getUniqueId());
                                }
                                else{
                                    p.sendMessage(CreativeMessages.CMD_PLOT_ALREADY_ADDED.get(omp, a[2]));
                                }
                            }
                            else{
                                p.sendMessage(CreativeMessages.CMD_PLOT_ADD_YOURSELF.get(omp));
                            }
                        }
                        else{
                            p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[2]));
                        }
                    }
                    else{
                        p.sendMessage("§7" + Messages.WORD_INVALID_COMMAND.get(omp) + ". (§d/p add <player>§7)");
                    }
                }
                else{
                    p.sendMessage(CreativeMessages.CMD_PLOT_NO_PLOT.get(omp));
                }
            }
            else if(a[1].equalsIgnoreCase("remove")){
                if(omp.hasPlot()){
                    if(a.length == 3){
                        if(!a[2].equals(p.getName())){
                            Plot plot = omp.getPlot();
                            UUID uuid = null;
                            Player p2 = PlayerUtils.getPlayer(a[2]);
                            if(p2 != null)
                                uuid = p2.getUniqueId();
                            else
                                uuid = UUIDUtils.getUUID(a[2]);

                            if(uuid != null && plot.getMemberUUIDs().contains(uuid)){
                                plot.removeMemberUUID(uuid);

                                if(p2 != null){
                                    CreativePlayer omp2 = CreativePlayer.getCreativePlayer(p2);
                                    p2.sendMessage(CreativeMessages.CMD_PLOT_NO_LONGER_ACCESS_PLAYER.get(omp2, p.getName()));
                                    p2.playSound(p2.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                                }

                                p.sendMessage(CreativeMessages.CMD_PLOT_NO_LONGER_ACCESS.get(omp, a[2]));
                                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                            }
                            else{
                                p.sendMessage(CreativeMessages.CMD_PLOT_NOT_ADDED.get(omp, a[2]));
                            }
                        }
                        else{
                            p.sendMessage(CreativeMessages.CMD_PLOT_REMOVE_YOURSELF.get(omp));
                        }
                    }
                    else{
                        p.sendMessage("§7" + Messages.WORD_INVALID_COMMAND.get(omp) + ". (§d/p remove <player>§7)");
                    }
                }
                else{
                    p.sendMessage(CreativeMessages.CMD_PLOT_NO_PLOT.get(omp));
                }
            }
            else if(a[1].equalsIgnoreCase("tp") || a[1].equalsIgnoreCase("teleport")){
                if(a.length == 3){
                    UUID uuid = null;
                    Player p2 = PlayerUtils.getPlayer(a[2]);
                    if(p2 != null)
                        uuid = p2.getUniqueId();
                    else
                        uuid = UUIDUtils.getUUID(a[2]);

                    if(uuid != null){
                        Plot plot = Plot.getPlot(uuid);
                        if(plot != null){
                            p.teleport(plot.getHomeLocation());
                            Title t = new Title("", CreativeMessages.CMD_PLOT_TP_TO_PLOT.get(omp, a[2]), 20, 40, 20);
                            t.send(p);
                        }
                        else{
                            p.sendMessage(CreativeMessages.CMD_PLOT_HAS_NO_PLOT.get(omp, a[2]));
                        }
                    }
                    else{
                        p.sendMessage(CreativeMessages.CMD_PLOT_HAS_NO_PLOT.get(omp, a[2]));
                    }
                }
                else{
                    p.sendMessage("§7" + Messages.WORD_INVALID_COMMAND.get(omp) + ". (§d/p tp <player>§7)");
                }
            }
            else{
                helpMessage(omp);
            }
        }
        else{
            helpMessage(omp);
        }
    }

    private void helpMessage(CreativePlayer omp){
        Player p = omp.getPlayer();

        switch(omp.getLanguage()){
            case DUTCH:
                p.sendMessage("§lHelp Menu:");
                p.sendMessage("§d/plot home | h §7§o(Teleport naar je Plot)");
                p.sendMessage("§d/plot sethome §7§o(Zet je Plot Home neer)");
                p.sendMessage("§d/plot help §7§o(Laat dit Menu zien)");
                p.sendMessage("§d/plot add <player> §7§o(Voeg een speler toe)");
                p.sendMessage("§d/plot remove <player> §7§o(Verwijder een speler)");
                p.sendMessage("§d/plot tp | teleport <player> §7§o(Teleporteer naar een plot)");
                p.sendMessage("§d/plot clear §7§o(Reset je Plot)");
                p.sendMessage("§d/plot info §7§o(Bekijk Plot Info)");
                p.sendMessage("§d/plot setuppvp §7§o(PvP Setup Mode)");
                p.sendMessage("§d/plot pvpbroadcast §7§o(Broadcast je Plot in PvP Mode)");
                break;
            case ENGLISH:
                p.sendMessage("§lHelp Menu:");
                p.sendMessage("§d/plot home | h §7§o(Teleport to your Plot)");
                p.sendMessage("§d/plot sethome §7§o(Set your Plot Home)");
                p.sendMessage("§d/plot help §7§o(Show this Menu)");
                p.sendMessage("§d/plot add <player> §7§o(Add a Player)");
                p.sendMessage("§d/plot remove <player> §7§o(Remove a Player)");
                p.sendMessage("§d/plot tp | teleport <player> §7§o(Teleport to a Plot)");
                p.sendMessage("§d/plot clear §7§o(Clear your Plot)");
                p.sendMessage("§d/plot info §7§o(Show Plot Info)");
                p.sendMessage("§d/plot setuppvp §7§o(Setup PvP Mode)");
                p.sendMessage("§d/plot pvpbroadcast §7§o(Broadcast Plot in PvP Mode)");
                break;
        }
    }
}
