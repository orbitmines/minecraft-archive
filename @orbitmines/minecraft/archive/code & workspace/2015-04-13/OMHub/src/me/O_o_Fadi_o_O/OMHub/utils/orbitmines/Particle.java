package me.O_o_Fadi_o_O.OMHub.utils.orbitmines;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import net.minecraft.server.v1_8_R2.EnumParticle;
import net.minecraft.server.v1_8_R2.PacketPlayOutWorldParticles;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Particle {
		
	private EnumParticle enumparticle;
	private double x;
	private double y;
	private double z;
	private int xsize;
	private int ysize;
	private int zsize;
	private int special;
	private int amount;
	
	public Particle(EnumParticle enumparticle){
		this.enumparticle = enumparticle;
		this.xsize = 0;
		this.ysize = 0;
		this.zsize = 0;
		this.special = 0;
		this.amount = 1;
	}
	public Particle(EnumParticle enumparticle, Location location){
		this.enumparticle = enumparticle;
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.xsize = 0;
		this.ysize = 0;
		this.zsize = 0;
		this.special = 0;
		this.amount = 1;
	}

	public EnumParticle getEnumParticle(){
		return enumparticle;
	}
	public void setEnumParticle(EnumParticle enumparticle){
		this.enumparticle = enumparticle;
	}
	
	public void setLocation(Location location){
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
	}

	public double getX(){
		return x;
	}
	public void setX(double x){
		this.x = x;
	}

	public double getY(){
		return y;
	}
	public void setY(double y){
		this.y = y;
	}

	public double getZ(){
		return z;
	}
	public void setZ(double z){
		this.z = z;
	}

	public void setSize(int xsize, int ysize, int zsize){
		this.xsize = xsize;
		this.ysize = ysize;
		this.zsize = zsize;
	}

	public int getXSize(){
		return xsize;
	}

	public int getYSize(){
		return ysize;
	}

	public int getZSize(){
		return zsize;
	}

	public int getSpecial(){
		return special;
	}
	public void setSpecial(int special){
		this.special = special;
	}

	public int getAmount(){
		return amount;
	}
	public void setAmount(int amount){
		this.amount = amount;
	}

	public void send(Player... players){
		try{
			for(Player player : players){
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(PacketPlayOutWorldParticles.class.getConstructor(EnumParticle.class, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class).newInstance(getEnumParticle(), true, (float) getX(), (float) getY(), (float) getZ(), getXSize(), getYSize(), getZSize(), getSpecial(), getAmount(), null));
			}
		}catch(InstantiationException | IllegalAccessException| IllegalArgumentException | InvocationTargetException| NoSuchMethodException | SecurityException e){}
	}
	
	public void send(List<Player> players){
		try{
			for(Player player : players){
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(PacketPlayOutWorldParticles.class.getConstructor(EnumParticle.class, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class).newInstance(getEnumParticle(), true, (float) getX(), (float) getY(), (float) getZ(), getXSize(), getYSize(), getZSize(), getSpecial(), getAmount(), null));
			}
		}catch(InstantiationException | IllegalAccessException| IllegalArgumentException | InvocationTargetException| NoSuchMethodException | SecurityException e){}
	}
	
	public void send(Collection<? extends Player> players){
		try{
			for(Player player : players){
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(PacketPlayOutWorldParticles.class.getConstructor(EnumParticle.class, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class).newInstance(getEnumParticle(), true, (float) getX(), (float) getY(), (float) getZ(), getXSize(), getYSize(), getZSize(), getSpecial(), getAmount(), null));
			}
		}catch(InstantiationException | IllegalAccessException| IllegalArgumentException | InvocationTargetException| NoSuchMethodException | SecurityException e){}
	}
}
