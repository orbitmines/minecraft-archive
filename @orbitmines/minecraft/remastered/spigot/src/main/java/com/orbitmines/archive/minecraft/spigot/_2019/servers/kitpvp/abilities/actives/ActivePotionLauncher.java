package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives.PassiveSelfKnockback;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Map;

public class ActivePotionLauncher implements Active.Handler {

    private final Cooldown cooldown = new Cooldown(5 * 1000);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        Location eyeLoc = player.getEyeLocation();
        Vector direction = eyeLoc.getDirection().multiply(2);

        ThrownPotion potion = player.launchProjectile(ThrownPotion.class);
        ItemStack potionItem = new ItemStack(Material.SPLASH_POTION);
        PotionMeta meta = (PotionMeta) potionItem.getItemMeta();
        meta.addCustomEffect(new PotionEffect(PotionEffectType.INSTANT_DAMAGE, 1, level - 1), true);
        potionItem.setItemMeta(meta);
        potion.setItem(potionItem);
        potion.setVelocity(direction);

        omp.playSound(Sound.ENTITY_SPLASH_POTION_THROW);

        /* Apply recoil if the item has SELF_KNOCKBACK passive */
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        Map<Passive, Integer> passives = Passive.from(kitPvP.getNms().customItem(), heldItem);
        if (passives != null && passives.containsKey(Passive.SELF_KNOCKBACK)) {
            int recoilLevel = passives.get(Passive.SELF_KNOCKBACK);
            PassiveSelfKnockback recoil = (PassiveSelfKnockback) Passive.SELF_KNOCKBACK.getHandler();
            Vector knockback = player.getLocation().getDirection().normalize().multiply(-recoil.getStrength(recoilLevel));
            knockback.setY(recoil.getYBoost(recoilLevel));
            player.setVelocity(player.getVelocity().add(knockback));
        }
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldown;
    }
}
