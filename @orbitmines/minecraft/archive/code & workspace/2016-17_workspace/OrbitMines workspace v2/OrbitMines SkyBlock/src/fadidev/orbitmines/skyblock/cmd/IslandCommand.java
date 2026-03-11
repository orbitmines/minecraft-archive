package fadidev.orbitmines.skyblock.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.utils.ConfigUtils;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.UUIDUtils;
import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;
import fadidev.orbitmines.skyblock.handlers.Island;
import fadidev.orbitmines.skyblock.handlers.SkyBlockMessages;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
import fadidev.orbitmines.skyblock.inventories.ChallengesInv;
import fadidev.orbitmines.skyblock.inventories.IslandInfoInv;
import fadidev.orbitmines.skyblock.utils.enums.ChallengeType;
import fadidev.orbitmines.skyblock.utils.enums.IslandRank;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Fadi on 10-9-2016.
 */
public class IslandCommand extends Command {

    private OrbitMinesSkyBlock skyBlock;
    String[] alias = { "/island", "/is" };

    public IslandCommand(){
        skyBlock = OrbitMinesSkyBlock.getSkyBlock();
    }
    
    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        SkyBlockPlayer omp = (SkyBlockPlayer) omPlayer;
        Player p = omp.getPlayer();

        Island is = omp.getIsland();

        if(a.length > 1){
            if(a[1].equalsIgnoreCase("restart")){
                if(omp.hasIsland()){
                    p.chat("/island leave");
                    p.chat("/island home");
                }
                else{
                    p.sendMessage(SkyBlockMessages.NO_ISLAND.get(omp));
                }
            }
            else if(a[1].equalsIgnoreCase("home") || a[1].equalsIgnoreCase("h")){
                if(omp.hasIsland()){
                    if(!omp.inVoid()){
                        p.sendMessage("§7" + SkyBlockMessages.WORD_TELEPORTING_TO.get(omp) + " " + SkyBlockMessages.WORD_YOUR.get(omp) + " §dIsland§7...");

                        omp.resetCooldown(Cooldowns.TELEPORTING);
                        omp.setTeleportingTo(is);
                    }
                    else{
                        p.sendMessage(SkyBlockMessages.CMD_ISLAND_IN_VOID.get(omp));
                    }
                }
                else{
                    Island.generate(omp);
                }
            }
            else if(a[1].equalsIgnoreCase("sethome") || a[1].equalsIgnoreCase("seth")){
                if(omp.hasIsland()){
                    if(p.getWorld().getName().equals(skyBlock.getSkyBlockWorld().getName())){
                        if(!omp.inVoid()){
                            if(omp.onOwnIsland(p.getLocation(), true)){
                                omp.setHomeLocation(p.getLocation());
                                p.sendMessage(SkyBlockMessages.CMD_ISLAND_HOME_SET.get(omp));
                            }
                        }
                        else{
                            p.sendMessage(SkyBlockMessages.CMD_ISLAND_IN_VOID.get(omp));
                        }
                    }
                    else{
                        p.sendMessage(SkyBlockMessages.CMD_ISLAND_CANT_DO_THAT.get(omp));
                    }
                }
                else{
                    p.sendMessage(SkyBlockMessages.NO_ISLAND.get(omp));
                }
            }
            else if(a[1].equalsIgnoreCase("invite")){
                if(omp.hasIsland()){
                    if(omp.isOwner()){
                        if(a.length == 3){
                            if(is.getMembers().size() +1 != is.getMaxMembers()){
                                Player p2 = PlayerUtils.getPlayer(a[2]);

                                if(p2 != null){
                                    if(p != p2){
                                        SkyBlockPlayer omp2 = SkyBlockPlayer.getSkyBlockPlayer(p2);

                                        if(!omp2.hasIsland()){
                                            p.sendMessage(SkyBlockMessages.CMD_ISLAND_INVITE.get(omp, p2.getName()));
                                            p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);

                                            omp2.setInvited(is);
                                            p2.sendMessage(SkyBlockMessages.CMD_ISLAND_INVITE_PLAYER.get(omp2, p.getName()));
                                            p2.sendMessage(SkyBlockMessages.CMD_ISLAND_INVITE_ACCEPT.get(omp2));
                                            p2.playSound(p2.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);

                                            is.sendMessageToMembers(SkyBlockMessages.CMD_ISLAND_INVITE_MEMBERS, p.getName(), a[1]);
                                        }
                                        else{
                                            p.sendMessage(SkyBlockMessages.CMD_ISLAND_ALREADY_HAS.get(omp, p2.getName()));
                                        }
                                    }
                                    else{
                                        p.sendMessage(SkyBlockMessages.CMD_ISLAND_INVITE_YOURSELF.get(omp));
                                    }
                                }
                                else{
                                    p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[2]));
                                }
                            }
                            else{
                                p.sendMessage(SkyBlockMessages.CMD_ISLAND_LIMIT_REACHED.get(omp, is.getMaxMembers() + ""));
                            }
                        }
                        else{
                            p.sendMessage("§7" + Messages.WORD_USE.get(omp) + " §d" + a[0].toLowerCase() + " invite <player>§7.");
                        }
                    }
                    else{
                        p.sendMessage(SkyBlockMessages.NOT_OWNER.get(omp));
                    }
                }
                else{
                    p.sendMessage(SkyBlockMessages.NO_ISLAND.get(omp));
                }
            }
            else if(a[1].equalsIgnoreCase("remove")){
                if(omp.hasIsland()){
                    if(omp.isOwner()){
                        if(a.length == 3){
                            if(!a[2].equalsIgnoreCase(p.getName())){
                                Player p2 = PlayerUtils.getPlayer(a[2]);
                                UUID uuid = null;

                                if(p2 != null)
                                    uuid = p2.getUniqueId();
                                else
                                    uuid = UUIDUtils.getUUID(a[2]);


                                if(uuid != null && ConfigUtils.parseStringList(is.getMembers()).contains(uuid.toString())){
                                    is.removeMember(uuid);

                                    p.sendMessage(SkyBlockMessages.CMD_ISLAND_REMOVE.get(omp, a[2]));
                                    p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);

                                    if(p2 != null){
                                        SkyBlockPlayer omp2 = SkyBlockPlayer.getSkyBlockPlayer(p2);

                                        omp2.setIsland(null, null);
                                        omp2.setHomeLocation(null);

                                        p2.sendMessage(SkyBlockMessages.CMD_ISLAND_REMOVE_PLAYER.get(omp2, p.getName()));
                                        p2.playSound(p2.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);

                                        p2.teleport(skyBlock.getSpawn());
                                    }
                                    skyBlock.getConfigManager().get("playerdata").set("players." + uuid.toString() + ".IslandInfo", null);
                                    skyBlock.getConfigManager().save("playerdata");

                                    is.sendMessageToMembers(SkyBlockMessages.CMD_ISLAND_REMOVE_MEMBER, p.getName(), a[2]);
                                }
                                else{
                                    p.sendMessage(SkyBlockMessages.CMD_ISLAND_NOT_ON_ISLAND.get(omp, a[2]));
                                }
                            }
                            else{
                                p.sendMessage(SkyBlockMessages.CMD_ISLAND_REMOVE_YOURSELF.get(omp));
                            }
                        }
                        else{
                            p.sendMessage("§7" + Messages.WORD_USE.get(omp) + " §d" + a[0].toLowerCase() + " remove <player>§7.");
                        }
                    }
                    else{
                        p.sendMessage(SkyBlockMessages.NOT_OWNER.get(omp));
                    }
                }
                else{
                    p.sendMessage(SkyBlockMessages.NO_ISLAND.get(omp));
                }
            }
            else if(a[1].equalsIgnoreCase("accept")){
                if(omp.isInvited()){
                    if(!omp.hasIsland()){
                        Island is2 = omp.getInvited();

                        if(is2.getMembers().size() + 1 != is2.getMaxMembers()){
                            is2.sendMessageToMembers(SkyBlockMessages.CMD_ISLAND_JOIN_MEMBER, p.getName());

                            Player owner = PlayerUtils.getPlayer(is2.getOwner());
                            if(owner != null){
                                owner.sendMessage(SkyBlockMessages.CMD_ISLAND_JOIN_MEMBER.get(SkyBlockPlayer.getSkyBlockPlayer(owner), p.getName()));
                            }

                            p.sendMessage(SkyBlockMessages.CMD_ISLAND_JOIN.get(omp, is2.getIslandId() + ""));
                            p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);

                            is2.addMember(p.getUniqueId());
                            omp.setIsland(is2, IslandRank.MEMBER);
                            omp.setInvited(null);

                            p.teleport(omp.getHomeLocation());
                            p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);

                            Title t = new Title("", "§7" + SkyBlockMessages.WORD_TELEPORTED_TO + " " + SkyBlockMessages.WORD_YOUR.get(omp) + " §dIsland§7.", 20, 40, 20);
                            t.send(p);
                        }
                        else{
                            omp.setInvited(null);
                            p.sendMessage(SkyBlockMessages.CMD_ISLAND_HAS_LIMIT_REACHED.get(omp));
                        }
                    }
                    else{
                        omp.setInvited(null);
                        p.sendMessage(SkyBlockMessages.CMD_ISLAND_ALREADY_HAS_PLAYER.get(omp));
                    }
                }
                else{
                    p.sendMessage(SkyBlockMessages.CMD_ISLAND_NOT_INVITED.get(omp));
                }
            }
            else if(a[1].equalsIgnoreCase("deny")){
                if(omp.isInvited()){
                    Island is2 = omp.getInvited();
                    Player owner = PlayerUtils.getPlayer(is2.getOwner());

                    if(owner != null){
                        owner.sendMessage(SkyBlockMessages.CMD_ISLAND_DENY.get(SkyBlockPlayer.getSkyBlockPlayer(owner), p.getName()));
                        owner.playSound(owner.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                    }

                    omp.setInvited(null);
                    p.sendMessage(SkyBlockMessages.CMD_ISLAND_DENY_PLAYER.get(omp));
                    p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                }
                else{
                    p.sendMessage(SkyBlockMessages.CMD_ISLAND_NOT_INVITED.get(omp));
                }
            }
            else if(a[1].equalsIgnoreCase("leave")){
                if(omp.hasIsland()){
                    skyBlock.getConfigManager().get("playerdata").set("players." + p.getUniqueId().toString() + ".IslandInfo", null);
                    skyBlock.getConfigManager().save("playerdata");

                    omp.clearInventory();
                    p.teleport(skyBlock.getSpawn());
                    p.sendMessage(SkyBlockMessages.CMD_ISLAND_LEAVE.get(omp));

                    if(omp.isOwner()){
                        if(is.getMembers().size() != 0){
                            UUID newOwner = is.getMembers().get(0);
                            Player p2 = PlayerUtils.getPlayer(newOwner);

                            is.sendMessageToMembers(SkyBlockMessages.CMD_ISLAND_LEAVE_MEMBER, p.getName());
                            is.removeMember(newOwner);
                            is.setOwner(newOwner);

                            if(p2 != null){
                                SkyBlockPlayer omp2 = SkyBlockPlayer.getSkyBlockPlayer(p2);

                                omp2.setIslandRank(IslandRank.OWNER);
                                is.sendMessageToMembers(SkyBlockMessages.CMD_ISLAND_NOW_OWNER_MEMBER, p2.getName());

                                p2.sendMessage(SkyBlockMessages.CMD_ISLAND_NOW_OWNER.get(omp2));
                            }
                            else{
                                skyBlock.getConfigManager().get("playerdata").set("players." + newOwner.toString() + ".IslandInfo.IslandRank", IslandRank.OWNER.toString());
                                skyBlock.getConfigManager().save("playerdata");

                                is.sendMessageToMembers(SkyBlockMessages.CMD_ISLAND_NOW_OWNER_MEMBER, UUIDUtils.getName(newOwner));
                            }
                        }
                        else{
                            is.delete();
                        }
                    }
                    else{
                        is.removeMember(p.getUniqueId());
                        is.sendMessageToMembers(SkyBlockMessages.CMD_ISLAND_LEAVE_MEMBER, p.getName());

                        Player owner = PlayerUtils.getPlayer(is.getOwner());
                        if(owner != null){
                            owner.sendMessage(SkyBlockMessages.CMD_ISLAND_LEAVE_MEMBER.get(SkyBlockPlayer.getSkyBlockPlayer(owner), p.getName()));
                        }
                    }

                    omp.setIsland(null, null);
                }
                else{
                    p.sendMessage(SkyBlockMessages.NO_ISLAND.get(omp));
                }
            }
            else if(a[1].equalsIgnoreCase("teleport") || a[1].equalsIgnoreCase("tp")){
                if(a.length == 3){
                    Player p2 = PlayerUtils.getPlayer(a[2]);

                    if(p2 != null){
                        SkyBlockPlayer omp2 = SkyBlockPlayer.getSkyBlockPlayer(p2);

                        if(omp2.hasIsland()){
                            Island is2 = omp2.getIsland();

                            if(is2.isTeleportEnabled()){
                                if(!omp.inVoid()){
                                    omp.setTeleportingTo(is2);
                                    omp.resetCooldown(Cooldowns.TELEPORTING);
                                }
                                else{
                                    p.sendMessage(SkyBlockMessages.CMD_ISLAND_IN_VOID.get(omp));
                                }
                            }
                            else{
                                p.sendMessage(SkyBlockMessages.CMD_ISLAND_TP_DISABLED.get(omp, p2.getName()));
                            }
                        }
                        else{
                            p.sendMessage(SkyBlockMessages.CMD_ISLAND_NO_ISLAND_PLAYER.get(omp, p2.getName()));
                        }
                    }
                    else{
                        p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[2]));
                    }
                }
                else{
                    p.sendMessage("§7" + Messages.WORD_USE.get(omp) + " §d" + a[0].toLowerCase() + " " + a[1].toLowerCase() + " <player>§7.");
                }
            }
            else if(a[1].equalsIgnoreCase("challenge") || a[1].equalsIgnoreCase("c")){
                if(omp.hasIsland()){
                    new ChallengesInv(ChallengeType.GATHER).open(p);
                    p.playSound(p.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 5, 1);
                }
                else{
                    p.sendMessage(SkyBlockMessages.NO_ISLAND.get(omp));
                }
            }
            else if(a[1].equalsIgnoreCase("info")){
                if(omp.hasIsland()){
                    new IslandInfoInv().open(p);
                    p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 5, 1);
                }
                else{
                    p.sendMessage(SkyBlockMessages.NO_ISLAND.get(omp));
                }
            }
            else{
                sendHelpMessage(omp);
            }
        }
        else{
           sendHelpMessage(omp);
        }
    }

    private void sendHelpMessage(SkyBlockPlayer omp){
        Player p = omp.getPlayer();

        switch(omp.getLanguage()){
            case DUTCH:
                p.sendMessage("§lHelp Menu:");
                p.sendMessage("§d/is home | h §7§o(Teleport naar jouw Island)");
                p.sendMessage("§d/is sethome §7§o(Zet je Island Home neer)");
                p.sendMessage("§d/is help §7§o(Laat dit Menu zien)");
                p.sendMessage("§d/is invite <player> §7§o(Nodig een speler uit)");
                p.sendMessage("§d/is remove <player> §7§o(Verwijder een speler)");
                p.sendMessage("§d/is accept §7§o(Accepteer een uitnodiging)");
                p.sendMessage("§d/is deny §7§o(Weiger een uitnodiging)");
                p.sendMessage("§d/is tp | teleport <player> §7§o(Teleport naar een Island)");
                p.sendMessage("§d/is c | challenge §7§o(Open het Challenges Menu)");
                p.sendMessage("§d/is leave §7§o(Verlaat je Island)");
                p.sendMessage("§d/is restart §7§o(Restart je Island)");
                p.sendMessage("§d/is info §7§o(Open het Island Info Menu)");
                break;
            case ENGLISH:
                p.sendMessage("§lHelp Menu:");
                p.sendMessage("§d/is home | h §7§o(Teleport to your Island)");
                p.sendMessage("§d/is sethome §7§o(Set your Island Home)");
                p.sendMessage("§d/is help §7§o(Show this Menu)");
                p.sendMessage("§d/is invite <player> §7§o(Invite a Player)");
                p.sendMessage("§d/is remove <player> §7§o(Remove a Player)");
                p.sendMessage("§d/is accept §7§o(Accept an Island invite)");
                p.sendMessage("§d/is deny §7§o(Deny an Island invite)");
                p.sendMessage("§d/is tp | teleport <player> §7§o(Teleport to an Island)");
                p.sendMessage("§d/is c | challenge §7§o(Open the Challenges Menu)");
                p.sendMessage("§d/is leave §7§o(Leave your Island)");
                p.sendMessage("§d/is restart §7§o(Restart your Island)");
                p.sendMessage("§d/is info §7§o(Open the Island Info Menu)");
                break;
        }
    }
}
