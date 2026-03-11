package om.api.handlers;

import java.util.List;

import om.api.API;
import om.api.utils.ColorUtils;
import om.api.utils.Utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFirework;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FireWork {

	private API api;
	private Firework firework;
	private FireworkMeta fwmeta;
	private FireworkEffect.Builder builder;
	
	public FireWork(Location location){
		this.api = API.getInstance();
    	this.firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        this.fwmeta = this.firework.getFireworkMeta();
        this.builder = FireworkEffect.builder();
	}
	
	public void applySettings(FireworkSettings fwsettings){
		if(fwsettings.getColor1() != null){
			withColor(fwsettings.getColor1());
		}
		if(fwsettings.getColor2() != null){
			withColor(fwsettings.getColor2());
		}
		if(fwsettings.getFade1() != null){
			withColor(fwsettings.getFade1());
		}
		if(fwsettings.getFade2() != null){
			withColor(fwsettings.getFade2());
		}
		with(fwsettings.getType());
		if(fwsettings.hasFlicker()){
			withFlicker();
		}
		if(fwsettings.hasTrail()){
			withTrail();
		}
		build();
	}
	
	public void withColor(Color color){
		builder.withColor(color);
	}
	
	public void withFade(Color color){
		builder.withFade(color);
	}
	
	public void with(Type type){
		builder.with(type);
	}
	
	public void withFlicker(){
		builder.withFlicker();
	}
	
	public void withTrail(){
		builder.withTrail();
	}
	
	public void randomize(){
		List<Color> colors = ColorUtils.values();
		
		withColor(ColorUtils.random(colors));
		withColor(ColorUtils.random(colors));
		withFade(ColorUtils.random(colors));
		withFade(ColorUtils.random(colors));
		
		if(Utils.random()) withFlicker();
		if(Utils.random()) withTrail();
	}
	
	public void build(){
		this.fwmeta.addEffects(this.builder.build());
		this.firework.setFireworkMeta(this.fwmeta);
	}
	
	public void setVelocity(Vector vector){
		firework.setVelocity(vector);
	}
	
	public void explode(){
		new BukkitRunnable(){
			public void run(){
				((CraftFirework) firework).getHandle().expectedLifespan = 0;
			}
		}.runTaskLater(api, 1);
	}
}
