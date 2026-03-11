package fadidev.orbitmines.api.managers;

import fadidev.orbitmines.api.handlers.OMPlayer;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fadi on 5-9-2016.
 */
public class GadgetManager {

    private Map<Entity, OMPlayer> swapTeleporter;
    private List<Entity> creeperLaunched;
    private List<Entity> paintBalls;
    private List<Entity> soccerMagmaCubes;
    private List<Entity> eggBombs;
    private List<Entity> fireballs;
    private List<Entity> inkBombs;
    private Map<Entity, Integer> inkBombTime;
    private List<Entity> silverFishBombs;
    private List<Entity> snowGolemAttackBalls;

    public GadgetManager(){
        swapTeleporter = new HashMap<>();
        creeperLaunched = new ArrayList<>();
        paintBalls = new ArrayList<>();
        soccerMagmaCubes = new ArrayList<>();
        eggBombs = new ArrayList<>();
        fireballs = new ArrayList<>();
        inkBombs = new ArrayList<>();
        inkBombTime = new HashMap<>();
        silverFishBombs = new ArrayList<>();
        snowGolemAttackBalls = new ArrayList<>();
    }

    public Map<Entity, OMPlayer> getSwapTeleporter() {
        return swapTeleporter;
    }

    public List<Entity> getCreeperLaunched() {
        return creeperLaunched;
    }

    public List<Entity> getPaintBalls() {
        return paintBalls;
    }

    public List<Entity> getSoccerMagmaCubes() {
        return soccerMagmaCubes;
    }

    public List<Entity> getEggBombs() {
        return eggBombs;
    }

    public List<Entity> getFireballs() {
        return fireballs;
    }

    public List<Entity> getInkBombs() {
        return inkBombs;
    }

    public Map<Entity, Integer> getInkBombTime() {
        return inkBombTime;
    }

    public List<Entity> getSilverFishBombs() {
        return silverFishBombs;
    }

    public List<Entity> getSnowGolemAttackBalls() {
        return snowGolemAttackBalls;
    }
}
