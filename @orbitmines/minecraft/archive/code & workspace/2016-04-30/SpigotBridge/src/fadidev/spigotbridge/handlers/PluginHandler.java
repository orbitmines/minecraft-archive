package fadidev.spigotbridge.handlers;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fadidev.spigotbridge.SpigotBridge;
import fadidev.spigotbridge.utils.Utils;
import fadidev.spigotbridge.utils.enums.VariableType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi-Laptop on 7-2-2016.
 */
public abstract class PluginHandler {

    private SpigotBridge sb;
    private BukkitTask task;
    private long interval;

    public PluginHandler(){
        sb = SpigotBridge.getInstance();
        sb.getPluginHandlers().add(this);
    }

    public abstract String getVariableName();
    public abstract Variable[] getVariables();

    public void updateAll(){
        update(getVariables());
    }

    public void updateAll(Player p){
        List<Variable> variables = new ArrayList<>();
        for(Variable variable : getVariables()){
            if(variable.getType() == VariableType.PLAYERDATA){
                variables.add(variable);
            }
        }

        update(p, variables.toArray(new Variable[variables.size()]));
    }

    public Variable getVariable(String variable){
        for(Variable v : getVariables()){
            if(v.getVariable().equalsIgnoreCase(variable)){
                return v;
            }
        }
        return null;
    }

    public BukkitTask getTask() {
        return task;
    }

    public void update(Variable... variables){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(sb.getVersion());
        out.writeUTF(sb.getServerName());

        for(Variable variable : variables){
            out.writeUTF(variable.getType().toString());
            out.writeUTF("%" + getVariableName().toLowerCase() + "-" + variable.getVariable() + "%");

            switch (variable.getType()) {
                case STANDERD:
                    out.writeUTF(variable.getReplacement());
                    break;
                case PLAYERDATA:
                    for (Player p : variable.getPlayers()) {
                        out.writeUTF(p.getName() + "|" + variable.getReplacement(p));
                    }

                    out.writeUTF("next");
                    break;
            }
        }
        out.writeUTF("done");

        Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        if(p != null){
            p.sendPluginMessage(sb, "SpigotBridge", out.toByteArray());
        }
    }

    public void update(Player p, Variable... variables){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(sb.getVersion());
        out.writeUTF(sb.getServerName());

        for(Variable variable : variables){
            if(variable.getType() == VariableType.PLAYERDATA) {
                out.writeUTF(variable.getType().toString());
                out.writeUTF("%" + getVariableName().toLowerCase() + "-" + variable.getVariable() + "%");
                out.writeUTF(p.getName() + "|" + variable.getReplacement(p));

                out.writeUTF("next");
            }
            else{
                Utils.warnConsole("Variable %" + getVariableName() + "-" + variable.getVariable() + "% isn't Type PLAYERDATA.");
            }
        }
        out.writeUTF("done");

        p.sendPluginMessage(sb, "SpigotBridge", out.toByteArray());
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval){
        this.interval = interval;
    }

    public void startTask() {
        BukkitTask task = new BukkitRunnable(){

            @Override
            public void run(){
                updateAll();
            }
        }.runTaskTimer(sb, 0, getInterval());

        this.task = task;
    }

    public void stopTask(){
        if(this.task != null) this.task.cancel();
    }
}
