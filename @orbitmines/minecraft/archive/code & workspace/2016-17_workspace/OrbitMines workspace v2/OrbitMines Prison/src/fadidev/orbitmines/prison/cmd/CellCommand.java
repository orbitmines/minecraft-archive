package fadidev.orbitmines.prison.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.UUIDUtils;
import fadidev.orbitmines.prison.handlers.Cell;
import fadidev.orbitmines.prison.handlers.PrisonMessages;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import fadidev.orbitmines.prison.inventory.CellInfoInv;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Fadi on 10-9-2016.
 */
public class CellCommand extends Command {

    String[] alias = { "/cell", "/c" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        PrisonPlayer omp = (PrisonPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(a.length > 1){
            if(a[1].equalsIgnoreCase("home") || a[1].equalsIgnoreCase("h")){
                if(!omp.hasCell()){
                    if(!omp.hasGold(25000)){
                        p.sendMessage(PrisonMessages.CMD_PLOT_NO_MONEY.get(omp));
                        return;
                    }

                    omp.removeGold(25000);

                    p.sendMessage(PrisonMessages.CMD_PLOT_PLOT_PREPARING.get(omp));
                    p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
                    Cell.nextCell(omp);
                }

                omp.setTeleportingTo(omp.getCell());
                omp.resetCooldown(Cooldowns.TELEPORTING);
                p.sendMessage("§7" + PrisonMessages.WORD_TELEPORTING_TO.get(omp) + " " + PrisonMessages.WORD_YOUR_SMALL.get(omp) + " §cCell§7...");
            }
            else if(a[1].equalsIgnoreCase("clear")){
                if(omp.hasCell()){
                    if(omp.getCell().canReset()){
                        omp.getCell().reset();
                    }
                    else{
                        p.sendMessage(PrisonMessages.CMD_PLOT_RESET_COOLDOWN.get(omp));
                    }
                }
                else{
                    p.sendMessage(PrisonMessages.CMD_PLOT_NO_PLOT.get(omp));
                }
            }
            else if(a[1].equalsIgnoreCase("info")){
                if(omp.hasCell()){
                    new CellInfoInv().open(p);
                }
                else{
                    p.sendMessage(PrisonMessages.CMD_PLOT_NO_PLOT.get(omp));
                }
            }
            else if(a[1].equalsIgnoreCase("add")){
                if(omp.hasCell()){
                    if(a.length == 3){
                        Player p2 = PlayerUtils.getPlayer(a[2]);

                        if(p2 != null){
                            if(p2 != p){
                                PrisonPlayer omp2 = PrisonPlayer.getPrisonPlayer(p2);
                                Cell cell = omp.getCell();

                                if(!cell.getMemberUUIDs().contains(p2.getUniqueId())){
                                    p.sendMessage(PrisonMessages.CMD_PLOT_CAN_ACCESS.get(omp, p2.getName()));
                                    p2.sendMessage(PrisonMessages.CMD_PLOT_CAN_ACCESS_PLAYER.get(omp2, p.getName()));
                                    p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                                    p2.playSound(p2.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);

                                    cell.addMemberUUID(p2.getUniqueId());
                                }
                                else{
                                    p.sendMessage(PrisonMessages.CMD_PLOT_ALREADY_ADDED.get(omp, a[2]));
                                }
                            }
                            else{
                                p.sendMessage(PrisonMessages.CMD_PLOT_ADD_YOURSELF.get(omp));
                            }
                        }
                        else{
                            p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[2]));
                        }
                    }
                    else{
                        p.sendMessage("§7" + Messages.WORD_INVALID_COMMAND.get(omp) + ". (§c/cell add <player>§7)");
                    }
                }
                else{
                    p.sendMessage(PrisonMessages.CMD_PLOT_NO_PLOT.get(omp));
                }
            }
            else if(a[1].equalsIgnoreCase("remove")){
                if(omp.hasCell()){
                    if(a.length == 3){
                        if(!a[2].equals(p.getName())){
                            Cell cell = omp.getCell();
                            UUID uuid = null;
                            Player p2 = PlayerUtils.getPlayer(a[2]);
                            if(p2 != null)
                                uuid = p2.getUniqueId();
                            else
                                uuid = UUIDUtils.getUUID(a[2]);

                            if(uuid != null && cell.getMemberUUIDs().contains(uuid)){
                                cell.removeMemberUUID(uuid);

                                if(p2 != null){
                                    PrisonPlayer omp2 = PrisonPlayer.getPrisonPlayer(p2);
                                    p2.sendMessage(PrisonMessages.CMD_PLOT_NO_LONGER_ACCESS_PLAYER.get(omp2, p.getName()));
                                    p2.playSound(p2.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                                }

                                p.sendMessage(PrisonMessages.CMD_PLOT_NO_LONGER_ACCESS.get(omp, a[2]));
                                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                            }
                            else{
                                p.sendMessage(PrisonMessages.CMD_PLOT_NOT_ADDED.get(omp, a[2]));
                            }
                        }
                        else{
                            p.sendMessage(PrisonMessages.CMD_PLOT_REMOVE_YOURSELF.get(omp));
                        }
                    }
                    else{
                        p.sendMessage("§7" + Messages.WORD_INVALID_COMMAND.get(omp) + ". (§c/cell remove <player>§7)");
                    }
                }
                else{
                    p.sendMessage(PrisonMessages.CMD_PLOT_NO_PLOT.get(omp));
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
                        Cell cell = Cell.getCell(uuid);
                        if(cell != null){
                            omp.setTeleportingTo(cell);
                            omp.resetCooldown(Cooldowns.TELEPORTING);
                            p.sendMessage("§7" + PrisonMessages.WORD_TELEPORTING_TO.get(omp) + " §c" + a[2] + "'s Cell§7...");
                        }
                        else{
                            p.sendMessage(PrisonMessages.CMD_PLOT_HAS_NO_PLOT.get(omp, a[2]));
                        }
                    }
                    else{
                        p.sendMessage(PrisonMessages.CMD_PLOT_HAS_NO_PLOT.get(omp, a[2]));
                    }
                }
                else{
                    p.sendMessage("§7" + Messages.WORD_INVALID_COMMAND.get(omp) + ". (§c/cell tp <player>§7)");
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

    private void helpMessage(PrisonPlayer omp){
        Player p = omp.getPlayer();

        switch(omp.getLanguage()){
            case DUTCH:
                p.sendMessage("§lHelp Menu:");
                if(!omp.hasCell())
                    p.sendMessage("§7Maak een §ccell§7 met §c/cell home§7. (Prijs: §6§l25.000 Gold§7)");

                p.sendMessage("§c/cell home | h §7§o(Teleport naar je Cell)");
                p.sendMessage("§c/cell help §7§o(Laat dit Menu zien)");
                p.sendMessage("§c/cell add <player> §7§o(Voeg een speler toe)");
                p.sendMessage("§c/cell remove <player> §7§o(Verwijder een speler)");
                p.sendMessage("§c/cell tp | teleport <player> §7§o(Teleporteer naar een cell)");
                p.sendMessage("§c/cell clear §7§o(Reset je Cell)");
                p.sendMessage("§c/cell info §7§o(Bekijk Cell Info)");
                break;
            case ENGLISH:
                p.sendMessage("§lHelp Menu:");
                if(!omp.hasCell())
                    p.sendMessage("§7Create a §ccell§7 with §c/cell home§7. (Price: §6§l25.000 Gold§7)");

                p.sendMessage("§c/cell home | h §7§o(Teleport to your Cell)");
                p.sendMessage("§c/cell help §7§o(Show this Menu)");
                p.sendMessage("§c/cell add <player> §7§o(Add a Player)");
                p.sendMessage("§c/cell remove <player> §7§o(Remove a Player)");
                p.sendMessage("§c/cell tp | teleport <player> §7§o(Teleport to a Cell)");
                p.sendMessage("§c/cell clear §7§o(Clear your Cell)");
                p.sendMessage("§c/cell info §7§o(Show Cell Info)");
                break;
        }
    }
}
