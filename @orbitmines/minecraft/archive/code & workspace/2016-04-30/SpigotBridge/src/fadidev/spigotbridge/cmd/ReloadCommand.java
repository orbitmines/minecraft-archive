package fadidev.spigotbridge.cmd;

import fadidev.spigotbridge.SpigotBridge;
import fadidev.spigotbridge.handlers.PluginHandler;
import fadidev.spigotbridge.utils.enums.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Fadi-Laptop on 7-2-2016.
 */
public class ReloadCommand implements CommandExecutor {

    private SpigotBridge sb;

    public ReloadCommand(){
        sb = SpigotBridge.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String l, String[] a) {
        if(cmd.getName().equalsIgnoreCase("sbreload")){
            sender.sendMessage("§7Cancelling Tasks...");
            sb.cancelTasks();
            sender.sendMessage("§7Clearing data in BungeeMSG...");
            sb.clearData();
            for(Config config : Config.getCorrectOrder()){
                sender.sendMessage("§7Reloading §6" + config.getFileName() + "§7...");
                sb.getConfigManager().reload(config);
            }
            sb.loadData();
            sender.sendMessage("§7Sending new data to BungeeMSG...");
            sb.updateData();

            sender.sendMessage("§7Reload §aCompleted§7!");
        }
        return false;
    }
}
