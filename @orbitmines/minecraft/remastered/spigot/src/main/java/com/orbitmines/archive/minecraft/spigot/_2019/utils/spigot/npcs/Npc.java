package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.Nms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.*;
import java.util.stream.Collectors;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
public abstract class Npc<E extends Npc, P extends SpigotPlayer> {

    @Getter private static Map<World, List<Npc>> npcs = new HashMap<>();

    protected static Nms nms;

    static {
        nms = SpigotServer.getInstance().getNms();
    }

    @Getter protected Location spawnLocation;
    @Getter @Setter protected InteractAction<P> interactAction;

    protected Set<Player> watchers;

    public Npc(Location spawnLocation) {
        this.spawnLocation = spawnLocation;

        register();
    }

    /* SurvivalSpawn the Npc/Entit(y)(ies) in this method */
    protected abstract void spawn();

    /* Despawn the Npc/Entit(y)(ies) in this method */
    protected abstract void despawn();

    /* Update any change to Npc/Entit(y)(ies) in this method */
    public abstract void update();

    /* Return the collection of Npcs/Entit(y)(ies) defined under the 'Npc' */
    public abstract Collection<Entity> getEntities();

    protected abstract Map<World, List<E>> getMapping();

    protected abstract E getInstance();

    protected void register() {
        World world = getWorld();

        npcs.computeIfAbsent(world, key -> new ArrayList<>()).add(this);
        getMapping().computeIfAbsent(world, key -> new ArrayList<>()).add(getInstance());
    }

    protected void unregister() {
        World world = getWorld();

        npcs.get(world).remove(this);
        getMapping().get(world).remove(getInstance());
    }

    public World getWorld() {
        return spawnLocation.getWorld();
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;

        /* Only create if the npc has been spawned. */
        if (getEntities().stream().filter(Objects::nonNull).collect(Collectors.toList()).size() != 0)
            create();
    }

    public boolean isClickable() {
        return interactAction != null;
    }

    /* Is null, when visible to all */
    public Set<Player> getWatchers() {
        return watchers;
    }

    public void create() {
        create((Collection<? extends Player>) null);
    }

    public void create(Player createFor) {
        create(Collections.singletonList(createFor));
    }

    public void create(Player... createFor) {
        create(Arrays.asList(createFor));
    }

    /* Create Npc, If you want to create an Npc that is visible to no-one; use an empty collection */
    public void create(Collection<? extends Player> createFor) {
        if (createFor != null) {

            /* From now on the Npc will only be shown to the watchers */
            if (watchers == null)
                watchers = new HashSet<>();

            /* Add all the new watchers to the HashSet */
            watchers.addAll(createFor);
        }

        despawn();
        spawn();

        updateWatchers();
    }

    /* Remove for all players who are not watchers */
    protected void updateWatchers() {
        if (watchers == null)
            return;

        List<Player> hideFor = new ArrayList<>(spawnLocation.getWorld().getPlayers());
        hideFor.removeAll(watchers);

        hideFor(hideFor);
    }

    /* Permanently destroy Npc. */
    public void destroy() {
        despawn();
        unregister();
    }

    public void hideFor(Player player) {
        hideFor(Collections.singletonList(player));
    }

    public void hideFor(Player... players) {
        hideFor(Arrays.asList(players));
    }

    public void hideFor(Collection<? extends Player> players) {
        /* Add all other players in the world to the watchlist if it is visible to everyone */
        if (watchers == null)
            watchers = new HashSet<>(spawnLocation.getWorld().getPlayers());

        /* Remove players from the watchers */
        watchers.removeAll(players);

        Collection<? extends Entity> entities = getEntities();
        if (entities.size() != 0)
            nms.entity().destroyEntitiesFor(entities, players);
    }

    public static Npc getNpc(Entity entity) {
        if (!npcs.containsKey(entity.getWorld()))
            return null;

        for (Npc npc : getNpcsIn(entity.getWorld())) {
            if (npc.getEntities().contains(entity))
                return npc;
        }
        return null;
    }

    public static List<Npc> getNpcsIn(World world) {
        return npcs.get(world);
    }

    public interface InteractAction<P extends SpigotPlayer> {

        void onInteract(PlayerInteractEntityEvent event, P player);

    }
}
