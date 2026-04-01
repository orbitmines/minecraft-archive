package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs;

import com.orbitmines.archive.minecraft._2019.utils.mutable.MutableString;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.entities.Mob;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.PassiveRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.SpigotRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
public class MovingMobNpc extends MobNpc {
    //TODO NameTag has to follow npc?
    private ArrayList<WayPoint> wayPoints;
    private double movementSpeed;

    private WayPoint movingTo;
    private int secondsToStay;

    private SpigotRunnable runnable;

    public MovingMobNpc(Mob mob, Location spawnLocation, MutableString... displayName) {
        this(mob, spawnLocation, 1.2, displayName);
    }

    public MovingMobNpc(Mob mob, Location spawnLocation, double movementSpeed, MutableString... displayName) {
        super(mob, spawnLocation, displayName);

        this.wayPoints = new ArrayList<>();
        this.movementSpeed = movementSpeed;
    }

    public ArrayList<WayPoint> getWayPoints() {
        return wayPoints;
    }

    public void addWayPoint(WayPoint wayPoint) {
        this.wayPoints.add(wayPoint);
    }

    public void removeWayPoint(WayPoint wayPoint) {
        this.wayPoints.remove(wayPoint);
    }

    public void setWayPoint(int index, WayPoint wayPoint) throws IndexOutOfBoundsException {
        this.wayPoints.set(index, wayPoint);
    }

    public double getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(double movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public WayPoint getMovingTo() {
        return movingTo;
    }

    public void setMovingTo(WayPoint movingTo) {
        this.movingTo = movingTo;
    }

    public int getMovingToIndex() {
        return wayPoints.indexOf(movingTo);
    }

    public boolean isAtWayPoint(WayPoint wayPoint) {
        return entity != null && entity.getLocation().distance(wayPoint.getLocation()) <= 0.65;
    }

    public void navigateToWayPoint(WayPoint wayPoint) {
        if (wayPoint == null)
            wayPoint = wayPoints.get(0);

        nms.entity().navigate((LivingEntity) entity, wayPoint.getLocation(), movementSpeed);
    }

    public WayPoint next() {
        WayPoint next;
        if (movingTo == null) {
            next = wayPoints.get(0);
        } else {
            int index = getMovingToIndex();
            if (index + 1 >= wayPoints.size())
                next = wayPoints.get(0);
            else
                next = wayPoints.get(index + 1);
        }

        this.movingTo = next;
        this.secondsToStay = next.getStay();

        return next;
    }

    @Override
    protected void spawn() {
        super.spawn();

        runnable = new PassiveRunnable<SpigotServer>(SpigotServer.getInstance(), Interval.of(TimeUnit.SECOND, 1)) {
            @Override
            public void onRun() {

                if (wayPoints.size() > 0) {
                    if (movingTo != null) {
                        if (isAtWayPoint(movingTo)) {
                            secondsToStay--;

                            if (secondsToStay == 0) {
                                next();

//                            if (movingNpc.getSecondsToStay() == 0) {
//                                wayPoint = movingNpc.next();
//
//                                movingNpc.setMovingTo(wayPoint);
//                                movingNpc.setSecondsToStay(wayPoint.getStay());
//                            }TODO, WHY IS THIS HERE?
                            } else {
                                movingTo.run(MovingMobNpc.this, secondsToStay);
                            }
                        }
                    } else {
                        next();//TODO SECONDS_TO_SYAY=0 here?
                    }
                }

                navigateToWayPoint(movingTo);
            }
        }.start();
    }

    @Override
    protected void despawn() {
        super.despawn();

        if (runnable != null)
            runnable.cancel();
    }

    public abstract class WayPoint {

        @Getter @Setter private Location location;
        @Getter @Setter private int stay;

        public WayPoint(Location location, int stay) {
            this.location = location;
            this.stay = stay;
        }

        public abstract void run(MovingMobNpc npc, int seconds);
    }
}
