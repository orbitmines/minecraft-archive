package fadidev.orbitmines.hub.runnables;

import fadidev.orbitmines.api.handlers.npc.NPCArmorStand;
import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.hub.OrbitMinesHub;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Created by Fadi on 10-9-2016.
 */
public class RecentDonatorRunnable extends OMRunnable {

    private OrbitMinesHub hub;

    public RecentDonatorRunnable() {
        super(TimeUnit.SECOND, 5);

        hub = OrbitMinesHub.getHub();
    }

    @Override
    public void run() {
        NPCArmorStand npc = hub.getLastDonatorNpc();

        if(npc == null)
            return;

        ArmorStand as = npc.getArmorStand();
        ItemStack item = as.getHelmet();
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        String lastDonatorString = hub.getLastDonatorString();

        if(lastDonatorString == null){
            meta.setOwner(null);
            item.setDurability((short) 0);
        }
        else{
            item.setDurability((short) 3);
            meta.setOwner(lastDonatorString);
            npc.setCustomName("§7Recente Donateur §6§l" + lastDonatorString);
        }
        item.setItemMeta(meta);
        as.setHelmet(item);
    }
}
