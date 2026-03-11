package fadidev.orbitmines.api.handlers;

import fadidev.orbitmines.api.managers.VoteManager;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 7-9-2016.
 */
public class ConsoleCommandExecuter implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String l, String[] a) {
        OMPlayer omp = null;
        if(sender instanceof Player)
            omp = OMPlayer.getOMPlayer((Player) sender);

        if(cmd.getName().equalsIgnoreCase("setvip")){
            if(sender instanceof ConsoleCommandSender || sender instanceof Player && omp.isOpMode()){
                if(a.length == 1){
                    sender.sendMessage("§4§lOP §8| §7Use §6/setvip <player> <vip>");
                }
                else if(a.length == 2){
                    Player p2 = PlayerUtils.getPlayer(a[0]);

                    if(p2 != null){
                        try{
                            VIPRank rank = VIPRank.valueOf(a[1]);

                            OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
                            omp2.setVIP(rank);

                            sender.sendMessage("§4§lOP §8| §7You set §6" + p2.getName() + "'s§7 VIP Rank to §6" + rank.toString() + "§7!");

                        }catch(Exception ex){
                            sender.sendMessage("§4§lOP §8| §7VIP Rank §6" + a[1] + "§7 isn't a valid rank!");
                        }
                    }
                    else{
                        sender.sendMessage("§4§lOP §8| §7Player §6" + a[0] + "§7 isn't §aOnline§7!");
                    }
                }
                else{
                    sender.sendMessage("§4§lOP §8| §7Use §6/setvip <player> <vip>");
                }
            }
            else{
                omp.unknownCommand("/" + cmd.getName().toLowerCase());
            }
        }
        else if(cmd.getName().equalsIgnoreCase("setstaff")){
            if(sender instanceof ConsoleCommandSender || sender instanceof Player && omp.isOpMode()){
                if(a.length == 1){
                    sender.sendMessage("§4§lOP §8| §7Use §6/setstaff <player> <staff>");
                }
                else if(a.length == 2){
                    Player p2 = PlayerUtils.getPlayer(a[0]);

                    if(p2 != null){
                        try{
                            StaffRank rank = StaffRank.valueOf(a[1]);

                            OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
                            omp2.setStaff(rank);

                            sender.sendMessage("§4§lOP §8| §7You set §6" + p2.getName() + "'s§7 Staff Rank to §6" + rank.toString() + "§7!");
                        }catch(Exception ex){
                            sender.sendMessage("§4§lOP §8| §7Staff Rank §6" + a[1] + "§7 isn't a valid rank!");
                        }
                    }
                    else{
                        sender.sendMessage("§4§lOP §8| §7Player §6" + a[0] + "§7 isn't §aOnline§7!");
                    }
                }
                else{
                    sender.sendMessage("§4§lOP §8| §7Use §6/setstaff <player> <staff>");
                }
            }
            else{
                omp.unknownCommand("/" + cmd.getName().toLowerCase());
            }
        }
        else if(cmd.getName().equalsIgnoreCase("votes")){
            if(sender instanceof ConsoleCommandSender || sender instanceof Player && omp.isOpMode()){
                if(a.length == 0 && sender instanceof Player){
                    sender.sendMessage("§6§lOrbitMines§b§lVote §8| §7Your Total Votes this Month: §b§l" + omp.getVotes());
                }
                else if(a.length == 2){
                    if(a[0].equalsIgnoreCase("add")){
                        sender.sendMessage("§6§lOrbitMines§b§lVote §8| §7You gave §b" + a[1] + "§7 a §b§lVote§7!");
                        new VoteManager().registerVote(a[1]);
                    }
                    else{
                        sender.sendMessage("§6§lOrbitMines§b§lVote §8| §7Use §b/votes add <player>");
                    }
                }
                else{
                    sender.sendMessage("§6§lOrbitMines§b§lVote §8| §7Use §b/votes add <player>");
                }
            }
            else{
                sender.sendMessage(Messages.CMD_VOTE_TOTAL.get(omp, "" + omp.getVotes()));
            }
        }
        else if(cmd.getName().equalsIgnoreCase("vippoints")){
            if(sender instanceof ConsoleCommandSender || sender instanceof Player && omp.isOpMode()){
                if(a.length == 0 && sender instanceof Player){
                    sender.sendMessage("§b§lVIP Points §8| §7Your VIP Points: §b§l" + omp.getVipPoints());
                }
                else if(a.length == 3){
                    if(a[0].equalsIgnoreCase("give")){
                        int amount = Integer.parseInt(a[2]);
                        Player player = PlayerUtils.getPlayer(a[1]);

                        if(player != null){
                            OMPlayer omp2 = OMPlayer.getOMPlayer(player);
                            omp2.addVIPPoints(amount);

                            sender.sendMessage("§b§lVIP Points §8| §7You gave §b" + a[1] + " §b§l" + amount + " VIP Points§7!");
                        }
                        else{
                            sender.sendMessage("§b§lVIP Points §8| §7Player §b" + a[1] + " §7isn't §aOnline§7!");
                        }
                    }
                    else if(a[0].equalsIgnoreCase("remove")){
                        int amount = Integer.parseInt(a[2]);
                        Player player = PlayerUtils.getPlayer(a[1]);

                        if(player != null){
                            OMPlayer omplayer = OMPlayer.getOMPlayer(player);
                            omplayer.removeVipPoints(amount);

                            sender.sendMessage("§b§lVIP Points §8| §7You removed §b§l" + amount + " VIP Points§7 from §b" + a[1] + "§7!");
                        }
                        else{
                            sender.sendMessage("§b§lVIP Points §8| §7Player §b" + a[1] + " §7isn't §aOnline§7!");
                        }
                    }
                    else{
                        sender.sendMessage("§b§lVIP Points §8| §7/vippoints give|remove <player> <amount>");
                    }
                }
                else{
                    sender.sendMessage("§b§lVIP Points §8| §7/vippoints give|remove <player> <amount>");
                }
            }
            else{
                sender.sendMessage(Messages.CMD_TOTAL_VIPPOINTS.get(omp, "" + omp.getVipPoints()));
            }
        }
        else if(cmd.getName().equalsIgnoreCase("omt")){
            if(sender instanceof ConsoleCommandSender || sender instanceof Player && omp.isOpMode()){
                if(a.length == 0 && sender instanceof Player){
                    sender.sendMessage("§e§lOMT §8| §7Your OrbitMines Tokens: §e§l" + omp.getOrbitMinesTokens());
                }
                else if(a.length == 3){
                    if(a[0].equalsIgnoreCase("give")){
                        int amount = Integer.parseInt(a[2]);
                        Player p2 = PlayerUtils.getPlayer(a[1]);

                        if(p2 != null){
                            OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
                            omp2.addOrbitMinesTokens(amount);

                            sender.sendMessage("§e§lOMT §8| §7You gave §e" + a[1] + " §e§l" + amount + " OMT§7!");

                        }
                        else{
                            sender.sendMessage("§e§lOMT §8| §7Player §e" + a[1] + " §7isn't §aOnline§7!");
                        }
                    }
                    else if(a[0].equalsIgnoreCase("remove")){
                        int amount = Integer.parseInt(a[2]);
                        Player p2 = PlayerUtils.getPlayer(a[1]);

                        if(p2 != null){
                            OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
                            omp2.removeOrbitMinesTokens(amount);

                            sender.sendMessage("§e§lOMT §8| §7You removed §e§l" + amount + " OMT§7 from §e" + a[1] + "§7!");
                        }
                        else{
                            sender.sendMessage("§e§lOMT §8| §7Player §e" + a[1] + " §7isn't §aOnline§7!");
                        }
                    }
                    else{
                        sender.sendMessage("§e§lOMT §8| §7/omt give|remove <player> <amount>");
                    }
                }
                else{
                    sender.sendMessage("§e§lOMT §8| §7/omt give|remove <player> <amount>");
                }
            }
            else{
                sender.sendMessage(Messages.CMD_TOTAL_OMT.get(omp, "" + omp.getOrbitMinesTokens()));
            }
        }

        return false;
    }
}
