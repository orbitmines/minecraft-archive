package fadidev.orbitmines.hub.runnables;

import fadidev.orbitmines.api.handlers.npc.NPC;
import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.hub.OrbitMinesHub;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MindcraftNpcRunnable extends OMRunnable {

    private OrbitMinesHub hub;

    public MindcraftNpcRunnable() {
        super(TimeUnit.TICK, 10);

        hub = OrbitMinesHub.getHub();
    }

    @Override
    public void run() {
        NPC npc = hub.getMindCraft().getNpc();

        if(npc == null)
            return;

        LivingEntity en = (LivingEntity) npc.getEntity();

        ItemStack i = en.getEquipment().getItemInHand();
        i.setDurability((short) (int) Arrays.asList(1, 3, 4, 5, 11, 14).get(Utils.RANDOM.nextInt(6)));
        en.getEquipment().setItemInHand(i);
    }
}
