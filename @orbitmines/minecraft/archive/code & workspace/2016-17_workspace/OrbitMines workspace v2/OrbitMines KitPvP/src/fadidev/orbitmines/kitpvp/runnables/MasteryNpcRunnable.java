package fadidev.orbitmines.kitpvp.runnables;

import fadidev.orbitmines.api.handlers.npc.NPCArmorStand;
import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Fadi on 13-9-2016.
 */
public class MasteryNpcRunnable extends OMRunnable {

    private OrbitMinesKitPvP kitPvP;

    public MasteryNpcRunnable() {
        super(TimeUnit.SECOND, 5);

        kitPvP = OrbitMinesKitPvP.getKitPvP();
    }

    @Override
    public void run() {
        NPCArmorStand npc = kitPvP.getMasteryNpc();

        if(npc == null || npc.getItem() == null)
            return;

        ItemStack item = npc.getItem().getItemStack();
        if(item.getType() == Material.DIAMOND_SWORD)
            item.setType(Material.IRON_CHESTPLATE);
        else if(item.getType() == Material.IRON_CHESTPLATE)
            item.setType(Material.BOW);
        else if(item.getType() == Material.BOW)
            item.setType(Material.ARROW);
        else if(item.getType() == Material.ARROW)
            item.setType(Material.DIAMOND_SWORD);
        else
            item.setType(Material.DIAMOND_SWORD);

        npc.getItem().setItemStack(item);
    }
}
