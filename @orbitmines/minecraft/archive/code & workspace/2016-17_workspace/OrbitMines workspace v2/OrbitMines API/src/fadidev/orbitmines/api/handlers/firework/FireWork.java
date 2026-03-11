package fadidev.orbitmines.api.handlers.firework;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.utils.ColorUtils;
import fadidev.orbitmines.api.utils.Utils;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

/**
 * Created by Fadi on 3-9-2016.
 */
public class FireWork {

    private OrbitMinesAPI api;
    private Firework firework;
    private FireworkMeta fireworkMeta;
    private FireworkEffect.Builder builder;

    public FireWork(Location location){
        this.api = OrbitMinesAPI.getApi();
        this.firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        this.fireworkMeta = firework.getFireworkMeta();
        this.builder = FireworkEffect.builder();
    }

    public void applySettings(FireworkSettings fireworkSettings){
        if(fireworkSettings.getColor1() != null)
            getBuilder().withColor(fireworkSettings.getColor1());
        if(fireworkSettings.getColor2() != null)
            getBuilder().withColor(fireworkSettings.getColor2());
        if(fireworkSettings.getFade1() != null)
            getBuilder().withFade(fireworkSettings.getFade1());
        if(fireworkSettings.getFade2() != null)
            getBuilder().withFade(fireworkSettings.getFade2());

        getBuilder().with(fireworkSettings.getType());

        if(fireworkSettings.hasFlicker())
            getBuilder().withFlicker();
        if(fireworkSettings.hasTrail())
            getBuilder().withTrail();

        build();
    }

    public Firework getFirework() {
        return firework;
    }

    public FireworkMeta getFireworkMeta() {
        return fireworkMeta;
    }

    public FireworkEffect.Builder getBuilder() {
        return builder;
    }

    public void randomize(){
        getBuilder().withColor(ColorUtils.random(ColorUtils.VALUES).getBukkitColor());
        getBuilder().withColor(ColorUtils.random(ColorUtils.VALUES).getBukkitColor());
        getBuilder().withFade(ColorUtils.random(ColorUtils.VALUES).getBukkitColor());
        getBuilder().withFade(ColorUtils.random(ColorUtils.VALUES).getBukkitColor());

        if(Utils.RANDOM.nextBoolean())
            getBuilder().withFlicker();
        if(Utils.RANDOM.nextBoolean())
            getBuilder().withTrail();
    }

    public void build(){
        getFireworkMeta().addEffects(getBuilder().build());
        getFirework().setFireworkMeta(getFireworkMeta());
    }

    public void setVelocity(Vector vector){
        getFirework().setVelocity(vector);
    }

    public void explode(){
        api.getNms().firework().explode(getFirework());
    }
}
