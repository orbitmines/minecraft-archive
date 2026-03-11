package fadidev.spigotspleef.nms.actionbar;

import fadidev.spigotspleef.handlers.ActionBar;
import fadidev.spigotspleef.handlers.SpleefPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

/**
 * Created by Fadi on 30-4-2016.
 */
public class ActionBarNms_1_8_R3 implements ActionBarNms {

    public void send(SpleefPlayer spleefPlayer, ActionBar actionBar){
        IChatBaseComponent a = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + actionBar.getMessage() + "\"}");
        PacketPlayOutChat apacket = new PacketPlayOutChat(a, (byte) 2);

        PlayerConnection c = ((CraftPlayer) spleefPlayer.getPlayer()).getHandle().playerConnection;
        c.sendPacket(apacket);
    }
}
