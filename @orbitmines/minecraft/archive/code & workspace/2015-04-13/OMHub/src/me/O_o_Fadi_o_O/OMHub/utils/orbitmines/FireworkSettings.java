package me.O_o_Fadi_o_O.OMHub.utils.orbitmines;

import java.util.List;

import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.ServerData.ServerStorage;

import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;

public class FireworkSettings {

	private Player player;
	private Color color1;
	private Color color2;
	private Color fade1;
	private Color fade2;
	private boolean flicker;
	private boolean trail;
	private Type type;
	
	public FireworkSettings(Player player, Color color1, Color color2, Color fade1, Color fade2, boolean flicker, boolean trail, Type type){
		this.player = player;
		this.color1 = color1;
		this.color2 = color2;
		this.fade1 = fade1;
		this.fade2 = fade2;
		this.flicker = flicker;
		this.trail = trail;
		this.type = type;
	}

	public Player getPlayer(){
		return player;
	}
	public void setPlayer(Player player){
		this.player = player;
	}

	public Color getColor1(){
		return color1;
	}
	public void setColor1(Color color1){
		this.color1 = color1;
	}

	public Color getColor2(){
		return color2;
	}
	public void setColor2(Color color2){
		this.color2 = color2;
	}

	public Color getFade1(){
		return fade1;
	}
	public void setFade1(Color fade1){
		this.fade1 = fade1;
	}

	public Color getFade2(){
		return fade2;
	}
	public void setFade2(Color fade2){
		this.fade2 = fade2;
	}

	public boolean hasFlicker(){
		return flicker;
	}
	public void setFlicker(boolean flicker){
		this.flicker = flicker;
	}

	public boolean hasTrail(){
		return trail;
	}
	public void setTrail(boolean trail){
		this.trail = trail;
	}

	public Type getType(){
		return type;
	}
	public void setType(Type type){
		this.type = type;
	}
	
	public String toString(){
		return getColor1() + "|" + getColor2() + "|" + getFade1() + "|" + getFade2() + "|" + hasFlicker() + "|" + hasTrail() + "|" + getType().toString();
	}
	
	public static List<FireworkSettings> getFWSettings(){
		return ServerStorage.fwsettings;
	}
	
	public static FireworkSettings getFWSettings(Player player){
		for(FireworkSettings fwsettings : ServerStorage.fwsettings){
			if(fwsettings.getPlayer() == player){
				return fwsettings;
			}
		}
		return null;
	}
	
	public static FireworkSettings addFWSettings(Player player, Color color1, Color color2, Color fade1, Color fade2, boolean flicker, boolean trail, Type type){
		FireworkSettings fwsettings = new FireworkSettings(player, color1, color2, fade1, fade2, flicker, trail, type);
		ServerStorage.fwsettings.add(fwsettings);
		return fwsettings;
	}
}
