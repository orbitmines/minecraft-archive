package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.SkullTextures;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.ItemStackNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.UUID;

public class PlayerSkullBuilder extends ItemBuilderInstance<PlayerSkullBuilder, SkullMeta> {

    private static ItemStackNms nms;
    
    static {
        nms = SpigotServer.getInstance().getNms().customItem();
    }
    
    @Getter private UUID uuid;
    @Getter private String textureName;
    @Getter private String texture;

    public PlayerSkullBuilder(UUID uuid) {
        super(Material.PLAYER_HEAD);

        this.uuid = uuid;
    }

    public PlayerSkullBuilder(UUID uuid, int amount) {
        super(Material.PLAYER_HEAD, amount);

        this.uuid = uuid;
    }

    public PlayerSkullBuilder(UUID uuid, int amount, String displayName) {
        super(Material.PLAYER_HEAD, amount, displayName);

        this.uuid = uuid;
    }

    public PlayerSkullBuilder(UUID uuid, int amount, String displayName, String... lore) {
        super(Material.PLAYER_HEAD, amount, displayName, lore);

        this.uuid = uuid;
    }

    public PlayerSkullBuilder(UUID uuid, int amount, String displayName, List<String> lore) {
        super(Material.PLAYER_HEAD, amount, displayName, lore);

        this.uuid = uuid;
    }

    public PlayerSkullBuilder(String textureName, String texture) {
        super(Material.PLAYER_HEAD);

        this.textureName = textureName;
        this.texture = texture;
    }

    public PlayerSkullBuilder(String textureName, String texture, int amount) {
        super(Material.PLAYER_HEAD, amount);

        this.textureName = textureName;
        this.texture = texture;
    }

    public PlayerSkullBuilder(String textureName, String texture, int amount, String displayName) {
        super(Material.PLAYER_HEAD, amount, displayName);

        this.textureName = textureName;
        this.texture = texture;
    }

    public PlayerSkullBuilder(String textureName, String texture, int amount, String displayName, String... lore) {
        super(Material.PLAYER_HEAD, amount, displayName, lore);

        this.textureName = textureName;
        this.texture = texture;
    }

    public PlayerSkullBuilder(String textureName, String texture, int amount, String displayName, List<String> lore) {
        super(Material.PLAYER_HEAD, amount, displayName, lore);

        this.textureName = textureName;
        this.texture = texture;
    }

    public PlayerSkullBuilder(PlayerSkullBuilder itemBuilder) {
        super(itemBuilder);

        this.uuid = itemBuilder.uuid;
        this.textureName = itemBuilder.textureName;
        this.texture = itemBuilder.texture;
    }

    public PlayerSkullBuilder setUUID(UUID uuid) { this.uuid = uuid; return this; }
    public PlayerSkullBuilder setTexture(String textureName, String texture) { this.textureName = textureName; this.texture = texture; return this; }
    
    @Override
    public PlayerSkullBuilder clone() {
        return new PlayerSkullBuilder(this);
    }

    @Override
    protected PlayerSkullBuilder getInstance() {
        return this;
    }

    @Override
    public ItemStack build() {
        ItemStack itemStack = super.build();
    
        if (this.texture != null)
            itemStack = nms.setCustomSkullTexture(itemStack, this.textureName, this.texture);

        return itemStack;
    }

    @Override
    public SkullMeta buildMeta(SkullMeta meta) {
        meta = super.buildMeta(meta);

        if (this.uuid != null)
            SkullTextures.applyTo(meta, this.uuid);

        return meta;
    }
}
