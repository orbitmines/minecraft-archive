package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.utils.RandomUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ColorUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.firework.FireworkNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import lombok.Getter;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

/*
* OrbitMines - @author Fadi Shawki - 29-7-2017
*/
public class FireworkBuilder {

    private static FireworkNms nms;

    static {
        nms = SpigotServer.getInstance().getNms().firework();
    }

    @Getter private Firework firework;
    @Getter private FireworkMeta fireworkMeta;
    @Getter private FireworkEffect.Builder builder;

    public FireworkBuilder(Location location) {
        this.firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK_ROCKET);
        this.fireworkMeta = firework.getFireworkMeta();
        this.builder = FireworkEffect.builder();
    }

    public FireworkBuilder with(FireworkEffect.Type type) {
        builder.with(type);

        return this;
    }

    public FireworkBuilder withColor(Color color) {
        builder.withColor(ColorUtils.toBukkitColor(color));

        return this;
    }

    public FireworkBuilder withFade(Color color) {
        builder.withFade(ColorUtils.toBukkitColor(color));

        return this;
    }

    public FireworkBuilder withFlicker() {
        builder.withFlicker();

        return this;
    }

    public FireworkBuilder withTrail() {
        builder.withTrail();

        return this;
    }

    public FireworkBuilder randomize() {
        builder.withColor(ColorUtils.random());
        builder.withColor(ColorUtils.random());
        builder.withFade(ColorUtils.random());
        builder.withFade(ColorUtils.random());

        if (RandomUtils.RANDOM.nextBoolean())
            builder.withFlicker();
        if (RandomUtils.RANDOM.nextBoolean())
            builder.withTrail();

        return this;
    }

    public FireworkBuilder build() {
        fireworkMeta.addEffects(builder.build());
        firework.setFireworkMeta(fireworkMeta);

        return this;
    }

    public FireworkBuilder setVelocity(Vector vector) {
        firework.setVelocity(vector);

        return this;
    }

    public FireworkBuilder explode() {
        nms.explode(firework);

        return this;
    }
}
