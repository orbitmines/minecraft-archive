package fadidev.spigotspleef.nms.actionbar;

import fadidev.spigotspleef.handlers.ActionBar;
import fadidev.spigotspleef.handlers.SpleefPlayer;
import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.PacketPlayOutChat;
import net.minecraft.server.v1_8_R2.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;

/**
 * Created by Fadi on 30-4-2016.
 */
public class ActionBarNms_1_8_R2 implements ActionBarNms {

    public void send(SpleefPlayer spleefPlayer, ActionBar actionBar){
        IChatBaseComponent a = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + actionBar.getMessage() + "\"}");
        PacketPlayOutChat apacket = new PacketPlayOutChat(a, (byte) 2);

        PlayerConnection c = ((CraftPlayer) spleefPlayer.getPlayer()).getHandle().playerConnection;
        c.sendPacket(apacket);
    }
}
