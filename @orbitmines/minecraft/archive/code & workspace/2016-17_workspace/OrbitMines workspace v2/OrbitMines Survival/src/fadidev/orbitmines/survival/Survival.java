package fadidev.orbitmines.survival;

import fadidev.orbitmines.api.OrbitMinesServer;
import fadidev.orbitmines.api.runnables.orbitmines.NPCRunnable;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerRunnable;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerSecondRunnable;
import fadidev.orbitmines.api.utils.enums.Server;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import fadidev.orbitmines.survival.runnables.SurvivalNPCRunnable;
import fadidev.orbitmines.survival.runnables.SurvivalPlayerRunnable;
import fadidev.orbitmines.survival.runnables.SurvivalPlayerSecondRunnable;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Fadi on 15-9-2016.
 */
public class Survival extends OrbitMinesServer {

    @Override
    public Server getServer() {
        return Server.SURVIVAL;
    }

    @Override
    public int getGadgetSlot() {
        return 0;
    }

    @Override
    public String getSpawnBuilders() {
        return "§b§lMod §bAlderius";
    }

    @Override
    public boolean petsEnabled() {
        return true;
    }

    @Override
    public boolean chatcolorsEnabled() {
        return true;
    }

    @Override
    public boolean disguisesEnabled() {
        return true;
    }

    @Override
    public boolean gadgetsEnabled() {
        return false;
    }

    @Override
    public boolean wardrobeEnabled() {
        return false;
    }

    @Override
    public boolean trailsEnabled() {
        return true;
    }

    @Override
    public boolean hatsEnabled() {
        return false;
    }

    @Override
    public boolean fireworksEnabled() {
        return false;
    }

    @Override
    public NPCRunnable startNpcRunnable() {
        return new SurvivalNPCRunnable();
    }

    @Override
    public PlayerRunnable startPlayerRunnable() {
        return new SurvivalPlayerRunnable();
    }

    @Override
    public PlayerSecondRunnable startPlayerSecondRunnable() {
        return new SurvivalPlayerSecondRunnable();
    }

    @Override
    public void updateFormat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        SurvivalPlayer omp = SurvivalPlayer.getSurvivalPlayer(p);

        e.setFormat(omp.getChatFormat());
    }
}
