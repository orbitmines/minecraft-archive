package om.api.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Utils {
	
	public List<String> asNewStringList(List<String> list){
		List<String> newlist = new ArrayList<String>();
		for(String o : list){
			newlist.add(o);
		}
		return newlist;
	}
	
	public Location asNewLocation(Location l, int xAdded, int yAdded, int zAdded){
		return new Location(l.getWorld(), l.getX() +xAdded, l.getY() +yAdded, l.getZ() +zAdded);
	}
	
	public String statusString(boolean bl){
		if(bl == true){
			return "§a§lENABLED";
		}
		return "§c§lDISABLED";
	}
	public short statusDurability(boolean bl){
		if(bl == true){
			return 5;
		}
		return 14;
	}
	
	public boolean random(){
		return new Random().nextBoolean();
	}
	
    public int random(int lower, int upper){
        return new Random().nextInt((upper - lower) + 1) + lower;
    }
	
	public Vector randomVelocity(){
        float x = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
        float y = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
        float z = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
        int iInt = new Random().nextInt(4);
        	
    	if(iInt == 0){
    		return new Vector(x -0.2, y, z -0.2);
    	}
    	else if(iInt == 1){
    		return new Vector(x, y, z);
    	}
    	else if(iInt == 2){
    		return new Vector(x -0.2, y, z);
    	}
      	else if(iInt == 3){
      		return new Vector(x, y, z -0.2);
    	}
      	else{
      		return null;
      	}
	}
	
	public List<Location> getPaintballLocations(Location l){
		return Arrays.asList(asNewLocation(l, 0, -1, 0), asNewLocation(l, 0, 0, 0), asNewLocation(l, 1, -1, 0), asNewLocation(l, 0, -1, 1), asNewLocation(l, -1, -1, 0), asNewLocation(l, 0, -2, 0), asNewLocation(l, 0, -1, -1), asNewLocation(l, 0, +1, 0), asNewLocation(l, 1, 0, 0), asNewLocation(l, -1, 0, 0), asNewLocation(l, 0, 0, 1), asNewLocation(l, 0, 0, -1), asNewLocation(l, 2, -1, 0), asNewLocation(l, 0, -1, 2), asNewLocation(l, -2, -1, 0), asNewLocation(l, 0, -1, -2), asNewLocation(l, 1, -1, 1), asNewLocation(l, 1, -1, -1), asNewLocation(l, -1, -1, 1), asNewLocation(l, -1, -1, -1), asNewLocation(l, 0, -3, 0), asNewLocation(l, 1, -2, 0), asNewLocation(l, -1, -2, 0), asNewLocation(l, 0, -2, 1), asNewLocation(l, 0, -2, -1));
	}
	
	public String getName(PotionEffectType type){
		if(type == PotionEffectType.ABSORPTION){return "Absorption";}
		else if(type == PotionEffectType.BLINDNESS){return "Blindness";}
		else if(type == PotionEffectType.CONFUSION){return "Nausea";}
		else if(type == PotionEffectType.DAMAGE_RESISTANCE){return "Resistance";}
		else if(type == PotionEffectType.FAST_DIGGING){return "Haste";}
		else if(type == PotionEffectType.FIRE_RESISTANCE){return "Fire Resistance";}
		else if(type == PotionEffectType.HARM){return "Harming";}
		else if(type == PotionEffectType.HEAL){return "Health";}
		else if(type == PotionEffectType.HEALTH_BOOST){return "Health Boost";}
		else if(type == PotionEffectType.HUNGER){return "Hunger";}
		else if(type == PotionEffectType.INCREASE_DAMAGE){return "Strength";}
		else if(type == PotionEffectType.INVISIBILITY){return "Invisibility";}
		else if(type == PotionEffectType.INVISIBILITY){return "Invisibility";}
		else if(type == PotionEffectType.JUMP){return "Jump Boost";}
		else if(type == PotionEffectType.NIGHT_VISION){return "Night Vision";}
		else if(type == PotionEffectType.POISON){return "Poison";}
		else if(type == PotionEffectType.REGENERATION){return "Regeneration";}
		else if(type == PotionEffectType.SATURATION){return "Saturation";}
		else if(type == PotionEffectType.SLOW){return "Slowness";}
		else if(type == PotionEffectType.SLOW_DIGGING){return "Mining Fatigue";}
		else if(type == PotionEffectType.SPEED){return "Speed";}
		else if(type == PotionEffectType.WATER_BREATHING){return "Water Breathing";}
		else if(type == PotionEffectType.WEAKNESS){return "Weakness";}
		else if(type == PotionEffectType.WITHER){return "Wither";}
		else{return null;}
	}
}
