package fadidev.orbitmines.api.nms.title;

import fadidev.orbitmines.api.handlers.chat.Title;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 30-4-2016.
 */
public class TitleNms_1_8_R1 implements TitleNms {

    public void send(Player player, Title title){
        IChatBaseComponent time = ChatSerializer.a("{\"text\": \"" + "" + "\"}");
        PacketPlayOutTitle timepacket = new PacketPlayOutTitle(EnumTitleAction.TIMES, time, title.getFadeIn(), title.getStay(), title.getFadeOut());

        IChatBaseComponent t = ChatSerializer.a("{\"text\": \"" + title.getTitle() + "\"}");
        PacketPlayOutTitle tpacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, t);

        IChatBaseComponent s = ChatSerializer.a("{\"text\": \"" + title.getSubTitle() + "\"}");
        PacketPlayOutTitle spacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, s);

        PlayerConnection c = ((CraftPlayer) player).getHandle().playerConnection;

        c.sendPacket(timepacket);
        c.sendPacket(tpacket);
        c.sendPacket(spacket);
    }
}
