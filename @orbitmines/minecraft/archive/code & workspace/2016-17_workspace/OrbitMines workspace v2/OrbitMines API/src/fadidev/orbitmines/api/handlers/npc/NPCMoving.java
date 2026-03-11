package fadidev.orbitmines.api.handlers.npc;

import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.api.utils.enums.Mob;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fadi on 3-9-2016.
 */
public class NPCMoving extends NPC {

    private List<Location> moveLocations;
    private Map<Location, Integer> locationStay;
    private Location movingTo;
    private int secondsToStay;
    private ArriveAction arriveAction;

    public NPCMoving(Mob mob, Location location, String displayName, ArriveAction arriveAction) {
        super(mob, location, displayName);

        this.moveLocations = new ArrayList<>();
        this.locationStay = new HashMap<>();
        this.arriveAction = arriveAction;
    }

    public NPCMoving(Mob mob, Location location, String displayName, InteractAction interactAction, ArriveAction arriveAction) {
        super(mob, location, displayName, interactAction);

        this.moveLocations = new ArrayList<>();
        this.locationStay = new HashMap<>();
        this.arriveAction = arriveAction;
    }

    public List<Location> getMoveLocations() {
        return moveLocations;
    }

    public void setMoveLocations(List<Location> moveLocations) {
        this.moveLocations = moveLocations;
    }

    public void addMoveLocation(Location moveLocation, int secondsStay){
        if(this.moveLocations.size() == 0)
            this.secondsToStay = secondsStay;

        this.moveLocations.add(moveLocation);
        this.locationStay.put(moveLocation, secondsStay);
    }

    public Map<Location, Integer> getLocationStay() {
        return locationStay;
    }

    public void setLocationStay(Map<Location, Integer> locationStay) {
        this.locationStay = locationStay;
    }

    public int getSecondsToStay(Location location){
        return this.locationStay.get(location);
    }

    public Location getMovingTo() {
        return movingTo;
    }

    public void setMovingTo(Location movingTo) {
        this.movingTo = movingTo;
    }

    public int getMovingToIndex(){
        return this.moveLocations.indexOf(getMovingTo());
    }

    public int getSecondsToStay(){
        return this.secondsToStay;
    }

    public void setSecondsToStay(int secondsToStay){
        this.secondsToStay = secondsToStay;
    }

    public ArriveAction getArriveAction() {
        return arriveAction;
    }

    public boolean isAtLocation(Location location){
        if(getEntity() == null)
            return false;

        return getEntity().getLocation().distance(location) <= 0.65;
    }

    public boolean isAtLocation(int index){
        if(getEntity() == null)
            return false;

        return getEntity().getLocation().distance(this.moveLocations.get(index)) <= 0.65;
    }

    public void moveToLocation(Location location){
        if(location == null)
            location = getNextLocation();

        WorldUtils.navigate((LivingEntity) getEntity(), location, 1.2);
    }

    public Location getNextLocation(){
        if(getMovingTo() == null)
            return getMoveLocations().get(0);

        int index = getMovingToIndex();
        if(index +1 >= getMoveLocations().size())
            return getMoveLocations().get(0);

        return getMoveLocations().get(index + 1);
    }

    public void arrive(int index, int seconds){
        if(arriveAction != null)
            arriveAction.arrive(this, index, seconds);
    }

    public static abstract class ArriveAction {

        public abstract void arrive(NPCMoving npc, int index, int seconds);

    }
}
