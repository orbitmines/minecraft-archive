package fadidev.orbitmines.hub.handlers;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Fadi on 7-9-2016.
 */
public class HubConsoleCommandExecuter implements CommandExecutor {

    private OrbitMinesHub hub;

    public HubConsoleCommandExecuter(){
        this.hub = OrbitMinesHub.getHub();
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String l, String[] a) {
        HubPlayer omp = null;
        if(sender instanceof Player)
            omp = HubPlayer.getHubPlayer((Player) sender);

        if(cmd.getName().equalsIgnoreCase("setlastdonator")){
            if(sender instanceof ConsoleCommandSender || sender instanceof Player && OMPlayer.getOMPlayer((Player) sender).isOpMode()){
                if(a.length == 0){
                    sender.sendMessage("§4§lOP §8| §7Use §6/setlastdonator <player>");
                }
                else if(a.length == 1){
                    hub.setLastDonatorString(a[0]);
                    sender.sendMessage("§4§lOP §8| §7You set §6" + a[0] + "§7 as the last Donator§7!");
                }
                else{
                    sender.sendMessage("§4§lOP §8| §7Use §6/setlastdonator <player>");
                }
            }
            else{
                omp.unknownCommand("/" + cmd.getName().toLowerCase());
            }
        }
        else if(cmd.getName().equalsIgnoreCase("resetMonthlyVIPPoints")){
            if(sender instanceof ConsoleCommandSender || sender instanceof Player && OMPlayer.getOMPlayer((Player) sender).isOpMode()){

                if(a.length == 1){
                    Player player = PlayerUtils.getPlayer(a[0]);

                    if(player != null){
                        final OMPlayer omp2 = OMPlayer.getOMPlayer(player);
                        omp2.setReceivedMonthlyBonus(false);

                        final Title t = new Title("", HubMessages.CLAIM_MONTHLY_VIPPOINTS.get(omp2), 20, 80, 20);
                        new BukkitRunnable(){
                            public void run(){
                                t.send(omp2.getPlayer());
                            }
                        }.runTaskLater(hub, 100);

                        sender.sendMessage("§b§lVIP Points §8| §b" + player.getName() + "§7 can claim their §b§lMonthly VIP Points§7!");
                    }
                    else{
                        sender.sendMessage("§b§lVIP Points §8| §7Player §b" + a[0] + "§7 isn't §aOnline§7!");
                    }
                }
                else{
                    sender.sendMessage("§b§lVIP Points §8| §7Use §b/resetMonthlyVIPPoints <player>");
                }

            }
        }

        return false;
    }
}
