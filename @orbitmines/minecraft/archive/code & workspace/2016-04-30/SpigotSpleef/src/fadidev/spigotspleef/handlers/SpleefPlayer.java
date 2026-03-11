package fadidev.spigotspleef.handlers;

import org.bukkit.entity.Player;

/**
 * Created by Fadi on 30-4-2016.
 */
public class SpleefPlayer {

    private Player player;

    private boolean hasActionBar;

    public SpleefPlayer(Player player){
        this.player = player;
        this.hasActionBar = false;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean hasActionBar() {
        return hasActionBar;
    }

    public void setActionBar(boolean hasActionBar) {
        this.hasActionBar = hasActionBar;
    }

}
