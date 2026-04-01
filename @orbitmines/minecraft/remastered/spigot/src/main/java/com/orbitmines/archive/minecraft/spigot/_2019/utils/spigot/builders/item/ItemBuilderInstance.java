package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.enchantments.GlowEnchantment;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.*;

public abstract class ItemBuilderInstance<B extends ItemBuilderInstance, M extends ItemMeta> {

    public static final GlowEnchantment GLOW_ENCHANTMENT = new GlowEnchantment(new NamespacedKey("orbitmines", "glow"));

    static {
        try {
            Field field = Enchantment.class.getDeclaredField("acceptingNew");
            field.setAccessible(true);
            field.set(null, true);
            Enchantment.registerEnchantment(GLOW_ENCHANTMENT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Getter protected Material material;
    @Getter protected int amount;
    @Getter protected short damage;
    @Getter protected String localizedName;
    @Getter protected List<String> lore;

    @Getter protected Map<Enchantment, Integer> enchantments;
    @Getter protected Map<Attribute, AttributeModifier> attributeModifiers;

    @Getter protected boolean unbreakable;

    @Getter protected Set<ItemFlag> flags;

    public ItemBuilderInstance(Material material) {
        this(material, 1);
    }

    public ItemBuilderInstance(Material material, int amount) {
        this(material, amount, null);
    }

    public ItemBuilderInstance(Material material, int amount, String displayName) {
        this(material, amount, displayName, (List<String>) null);
    }

    public ItemBuilderInstance(Material material, int amount, String displayName, String... lore) {
        this(material, amount, displayName, new ArrayList<>(Arrays.asList(lore)));
    }

    public ItemBuilderInstance(Material material, int amount, String displayName, List<String> lore) {
        this.material = material;
        this.amount = amount;
        this.damage = (short) 0;
        this.localizedName = displayName;
        this.lore = lore == null ? new ArrayList<>() : lore;
        this.enchantments = new HashMap<>();
        this.attributeModifiers = new HashMap<>();
        this.unbreakable = false;
        this.flags = new HashSet<>();
    }

    public ItemBuilderInstance(B itemBuilder) {
        this.material = itemBuilder.material;
        this.amount = itemBuilder.amount;
        this.damage = itemBuilder.damage;
        this.localizedName = itemBuilder.localizedName;
        this.lore = itemBuilder.lore == null ? new ArrayList<>() : new ArrayList<>(itemBuilder.lore);
        this.enchantments = new HashMap<>(itemBuilder.enchantments);
        this.attributeModifiers = new HashMap<>(itemBuilder.attributeModifiers);
        this.unbreakable = itemBuilder.unbreakable;
        this.flags = new HashSet<>(itemBuilder.flags);
    }

    /*

        Helper Methods

     */

    /* This is a really generic equals method, change it in order to be more specific */
    public boolean equals(ItemStack itemStack) {
        if (itemStack == null)
            return material == null;

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null)
            return this.localizedName == null && this.lore.isEmpty();

        return itemStack.getType() == this.material &&
                (this.localizedName != null && this.localizedName.equals(meta.getLocalizedName()) || meta.getLocalizedName() == null && this.localizedName == null);//) &&
//                (meta.getLore() != null ? this.lore.equals(meta.getLore()) : this.lore.isEmpty());
    }

    public abstract B clone();

    protected abstract B getInstance();

    public String getDisplayName() {
        return this.localizedName;
    }

    /*

        Actual Item Builder

     */

    public B setMaterial(Material material) { this.material = material; return getInstance(); }
    public B setAmount(int amount) { this.amount = amount; return getInstance(); }
    public B setDamage(short damage) { this.damage = damage; return getInstance(); }
    public B setDisplayName(String displayName) { this.localizedName = displayName; return getInstance(); }
    public B setLocalizedName(String localizedName) { this.localizedName = localizedName; return getInstance(); }
    public B setLore(List<String> lore) { this.lore = lore; return getInstance(); }
    public B setEnchantments(Map<Enchantment, Integer> enchantments) { this.enchantments = enchantments; return getInstance(); }
    public B setAttributeModifiers(Map<Attribute, AttributeModifier> attributeModifiers) { this.attributeModifiers = attributeModifiers; return getInstance(); }
    public B glow() { return addEnchantment(GLOW_ENCHANTMENT, 1); }
    public B unbreakable() { return unbreakable(true); }

    public B addLore(String lore) { this.lore.add(lore); return getInstance(); }
    public B addEnchantment(Enchantment enchantment, int level) { this.enchantments.put(enchantment, level); return getInstance(); }
    public B addAttributeModifier(Attribute attribute, AttributeModifier attributeModifier) { this.attributeModifiers.put(attribute, attributeModifier); return getInstance(); }
    public B addFlag(ItemFlag itemFlag) { this.flags.add(itemFlag); return getInstance(); }

    public B unbreakable(boolean hide) {
        this.unbreakable = true;

        if (hide)
            addFlag(ItemFlag.HIDE_UNBREAKABLE);

        return getInstance();
    }

    /*

        Build Methods

     */

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(getMaterial(), getAmount());
        itemStack.setItemMeta(buildMeta((M) itemStack.getItemMeta()));

        itemStack.addUnsafeEnchantments(new HashMap<>(this.enchantments));

        return itemStack;
    }

    public M buildMeta(M meta) {
        /* Set both display and localized name to localizedName, as DisplayName is client side, LocalizedName server side */
        meta.setDisplayName(this.localizedName);
        meta.setLocalizedName(this.localizedName);
        meta.setLore(this.lore == null ? new ArrayList<>() : new ArrayList<>(this.lore));

        if (meta instanceof Damageable)
            ((Damageable) meta).setDamage(this.damage);

        for (ItemFlag flag : this.flags) {
            meta.addItemFlags(flag);
        }

        if (this.enchantments.size() == 1 && this.enchantments.containsKey(GLOW_ENCHANTMENT))
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        for (Attribute attribute : this.attributeModifiers.keySet()) {
            meta.getAttributeModifiers().put(attribute, this.attributeModifiers.get(attribute));
        }

        meta.setUnbreakable(this.unbreakable);

        return meta;
    }
}
