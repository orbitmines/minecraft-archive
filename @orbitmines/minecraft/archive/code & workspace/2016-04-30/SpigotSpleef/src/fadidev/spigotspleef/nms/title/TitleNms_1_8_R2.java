package fadidev.spigotspleef.nms.title;

import fadidev.spigotspleef.handlers.SpleefPlayer;
import fadidev.spigotspleef.handlers.Title;
import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R2.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;

/**
 * Created by Fadi on 30-4-2016.
 */
public class TitleNms_1_8_R2 implements TitleNms {

    public void send(SpleefPlayer spleefPlayer, Title title){
        IChatBaseComponent time = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + "" + "\"}");
        PacketPlayOutTitle timepacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, time, title.getFadeIn(), title.getStay(), title.getFadeOut());

        IChatBaseComponent t = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title.getTitle() + "\"}");
        PacketPlayOutTitle tpacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, t);

        IChatBaseComponent s = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title.getSubTitle() + "\"}");
        PacketPlayOutTitle spacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, s);

        PlayerConnection c = ((CraftPlayer) spleefPlayer.getPlayer()).getHandle().playerConnection;

        c.sendPacket(timepacket);
        c.sendPacket(tpacket);
        c.sendPacket(spacket);
    }
}
