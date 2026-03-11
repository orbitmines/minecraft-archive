package fadidev.orbitmines.api.handlers;

import fadidev.orbitmines.api.OrbitMinesAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

/**
 * Created by Fadi on 3-9-2016.
 */
public class Particle {

    private OrbitMinesAPI api;
    private org.bukkit.Particle particle;
    private Location location;
    private boolean positive;
    private double xAdded;
    private double yAdded;
    private double zAdded;
    private int xSize;
    private int ySize;
    private int zSize;
    private int special;
    private int amount;
    private int index;

    public Particle(org.bukkit.Particle particle){
        this.api = OrbitMinesAPI.getApi();
        this.particle = particle;
        this.xAdded = 0;
        this.yAdded = 0;
        this.zAdded = 0;
        this.xSize = 0;
        this.ySize = 0;
        this.zSize = 0;
        this.special = 0;
        this.amount = 1;
    }

    public Particle(org.bukkit.Particle particle, Location location){
        this.api = OrbitMinesAPI.getApi();
        this.particle = particle;
        this.location = location;
        this.xAdded = 0;
        this.yAdded = 0;
        this.zAdded = 0;
        this.xSize = 0;
        this.ySize = 0;
        this.zSize = 0;
        this.special = 0;
        this.amount = 1;
    }

    public org.bukkit.Particle getParticle() {
        return particle;
    }

    public void setParticle(org.bukkit.Particle particle) {
        this.particle = particle;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositiv(boolean positive) {
        this.positive = positive;
    }

    public void add(double x, double y, double z){
        this.location.add(x, y, z);
    }

    public void subtract(double x, double y, double z){
        this.location.subtract(x, y, z);
    }

    public double getX(){
        return this.location.getX();
    }

    public void setX(double x){
        this.location.setX(x);
    }

    public void addX(double xAdded){
        this.xAdded += xAdded;
    }

    public double getY(){
        return this.location.getY();
    }

    public void setY(double y){
        this.location.setY(y);
    }

    public void addY(double yAdded){
        this.yAdded += yAdded;
    }

    public double getZ(){
        return this.location.getZ();
    }

    public void setZ(double z){
        this.location.setX(z);
    }

    public void addZ(double zAdded){
        this.zAdded += zAdded;
    }

    public double getXAdded(){
        return xAdded;
    }

    public void setXAdded(double xAdded){
        this.xAdded = xAdded;
    }

    public double getYAdded(){
        return yAdded;
    }

    public void setYAdded(double yAdded){
        this.yAdded = yAdded;
    }

    public double getZAdded(){
        return zAdded;
    }

    public void setZAdded(double zAdded){
        this.zAdded = zAdded;
    }

    public void setSize(int xSize, int ySize, int zSize){
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;
    }

    public int getXSize(){
        return xSize;
    }

    public int getYSize(){
        return ySize;
    }

    public int getZSize(){
        return zSize;
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

    public int getIndex(){
        return index;
    }

    public void setIndex(int index){
        this.index = index;
    }

    public void send(Player... players){
        for(Player player : players){
            send(player);
        }
    }

    public void sendOpposite(Player... players){
        for(Player player : players){
            sendOpposite(player);
        }
    }

    public void send(List<Player> players){
        for(Player player : players){
            send(player);
        }
    }

    public void sendOpposite(List<Player> players){
        for(Player player : players){
            sendOpposite(player);
        }
    }

    public void send(Collection<? extends Player> players){
        for(Player player : players){
            send(player);
        }
    }

    public void sendOpposite(Collection<? extends Player> players){
        for(Player player : players){
            sendOpposite(player);
        }
    }

    public void send(Player player){
        api.getNms().particles().send(player, getParticle(), (float) (getX() + getXAdded()), (float) (getY() + getYAdded()), (float) (getZ() + getZAdded()), getXSize(), getYSize(), getZSize(), getSpecial(), getAmount());
    }

    public void sendOpposite(Player player){
        api.getNms().particles().send(player, getParticle(), (float) (getX() - getXAdded()), (float) (getY() + getYAdded()), (float) (getZ() - getZAdded()), getXSize(), getYSize(), getZSize(), getSpecial(), getAmount());
    }
}
