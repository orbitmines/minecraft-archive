package fadidev.spigotspleef.nms.actionbar;

import fadidev.spigotspleef.handlers.ActionBar;
import fadidev.spigotspleef.handlers.SpleefPlayer;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;

/**
 * Created by Fadi on 30-4-2016.
 */
public class ActionBarNms_1_8_R1 implements ActionBarNms {

    public void send(SpleefPlayer spleefPlayer, ActionBar actionBar){
        IChatBaseComponent a = ChatSerializer.a("{\"text\": \"" + actionBar.getMessage() + "\"}");
        PacketPlayOutChat apacket = new PacketPlayOutChat(a, (byte) 2);

        PlayerConnection c = ((CraftPlayer) spleefPlayer.getPlayer()).getHandle().playerConnection;
        c.sendPacket(apacket);
    }
}
