package fadidev.orbitmines.api.handlers.chat;

import fadidev.orbitmines.api.OrbitMinesAPI;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBar {

    private OrbitMinesAPI api;
	private String message;
    private Player player;
	private int stay;
	
	public ActionBar(Player player, String message, int stay){
        this.api = OrbitMinesAPI.getApi();
        this.player = player;
        this.message = message;
		this.stay = stay;
	}
	
	public String getMessage() {
		return message;
	}

    public Player getPlayer() {
        return player;
    }

    public int getStay() {
        return stay;
    }

    public void setStay(int stay) {
        this.stay = stay;
    }

    public void setMessage(String message) {
		this.message = message;
	}

    public void send(){
        api.getNms().actionbar().send(player, this);
        start();
    }
	
	public ActionBar copy(){
		return new ActionBar(player, message, stay);
	}

    public ActionBar copy(Player player){
        return new ActionBar(player, message, stay);
    }

    private void start(){
        api.getCurrentActionBars().put(player, this);

        new BukkitRunnable(){
            public void run() {
                stop();
            }
        }.runTaskLater(api, getStay());
    }

    public void stop(){
        if(api.getCurrentActionBars().get(player) == this)
            api.getCurrentActionBars().remove(player);
    }
}
