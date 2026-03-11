package fadidev.spigotspleef.handlers;

import fadidev.spigotspleef.SpigotSpleef;
import org.bukkit.entity.Player;

public class ActionBar {

    private SpigotSpleef ss;
	private String message;
    private SpleefPlayer sp;
    private Player player;
	private int stay;
    private int current;
	
	public ActionBar(SpleefPlayer sp, String message, int stay){
        this.ss = SpigotSpleef.getInstance();
        this.sp = sp;
        this.player = sp.getPlayer();
        this.message = message;
		this.stay = stay;
        this.current = 0;
	}
	
	public String getMessage() {
		return message;
	}

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
        ss.getNms().actionbar().send(sp, this);
    }
	
	public ActionBar copy(){
		return new ActionBar(sp, message, stay);
	}

    public void check(){
        if(current == stay){
            stop();
            return;
        }

        send();
        current++;
    }

    public void start(){
        ss.getCurrentActionbars().put(player, this);
        sp.setActionBar(true);
    }

    public void stop(){
        ss.getCurrentActionbars().remove(player);
        sp.setActionBar(false);
    }
}
