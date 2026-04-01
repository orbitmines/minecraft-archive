package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.particles.image.builder;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.particles.ParticleImage;

import java.util.HashMap;
import java.util.Map;

/*
* OrbitMines - @author Fadi Shawki - 29-7-2017
*/
public class ParticleImageBuilder {

    protected Map<Character, ParticleImage.Pixel> chars;

    public ParticleImageBuilder() {
        this.chars = new HashMap<>();
    }

    public void apply(char ch, ParticleImage.Pixel variable) {
        this.chars.put(ch, variable);
    }

    public ParticleImage.Pixel parse(char ch) {
        return chars.getOrDefault(ch, null);
    }
}
