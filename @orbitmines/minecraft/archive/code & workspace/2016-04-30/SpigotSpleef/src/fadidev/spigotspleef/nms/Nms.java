package fadidev.spigotspleef.nms;

import fadidev.spigotspleef.SpigotSpleef;
import fadidev.spigotspleef.nms.actionbar.*;
import fadidev.spigotspleef.nms.anvilgui.*;
import fadidev.spigotspleef.nms.npc.*;
import fadidev.spigotspleef.nms.npc.bat.*;
import fadidev.spigotspleef.nms.title.*;
import fadidev.spigotspleef.utils.Utils;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 30-4-2016.
 */
public class Nms {

    private SpigotSpleef ss;
    private String version;

    /* NPCs */
    private NpcNms npcNms;

    private BatNpc batNpc;

    /* Title */
    private TitleNms titleNms;
    /* ActionBar */
    private ActionBarNms actionBarNms;

    public Nms(){
        ss = SpigotSpleef.getInstance();

        String version = checkVersion();
        this.version = version;

        if(version == null){
            Utils.sendConsoleEmpty();
            Utils.warnConsole("Error while registering NMS!");
            Utils.warnConsole("Disabling plugin...");
            Utils.sendConsoleEmpty();
            ss.getServer().getPluginManager().disablePlugin(ss);
        }
    }

    private String checkVersion(){
        String version;

        try{
            version = ss.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
        }catch(ArrayIndexOutOfBoundsException ex) {
            return null;
        }

        switch(version){
            case "v1_8_R1": // 1.8 - 1.8.1
                /* NPCs */
                npcNms = new NpcNms_1_8_R1();

                batNpc = new BatNpc_1_8_R1();

                /* Titles */
                titleNms = new TitleNms_1_8_R1();
                /* ActionBar */
                actionBarNms = new ActionBarNms_1_8_R1();


                break;
            case "v1_8_R2": // 1.8.3
                /* NPCs */
                npcNms = new NpcNms_1_8_R2();

                batNpc = new BatNpc_1_8_R2();

                /* Titles */
                titleNms = new TitleNms_1_8_R2();
                /* ActionBar */
                actionBarNms = new ActionBarNms_1_8_R2();



                break;
            case "v1_8_R3": // 1.8.7 - 1.8.9
                /* NPCs */
                npcNms = new NpcNms_1_8_R3();

                batNpc = new BatNpc_1_8_R3();

                /* Titles */
                titleNms = new TitleNms_1_8_R3();
                /* ActionBar */
                actionBarNms = new ActionBarNms_1_8_R3();


                break;
            case "v1_9_R1": // 1.9 - 1.9.2
                /* NPCs */
                npcNms = new NpcNms_1_9_R1();

                batNpc = new BatNpc_1_9_R1();

                /* Titles */
                titleNms = new TitleNms_1_9_R1();
                /* ActionBar */
                actionBarNms = new ActionBarNms_1_9_R1();


                break;
            default:
                return null;
        }


        return version;
    }

    public NpcNms npc(){
        return npcNms;
    }

    public TitleNms title(){
        return titleNms;
    }

    public ActionBarNms actionbar(){
        return actionBarNms;
    }

    /*ss.getNms().anvilgui(null, new AnvilNms.AnvilClickEventHandler(){
        @Override
        public void onAnvilClick(AnvilNms.AnvilClickEvent e){
            if(e.getSlot() == AnvilNms.AnvilSlot.OUTPUT){

            }
            else{
                e.setWillClose(false);
                e.setWillDestroy(false);
            }
        }
    });*/

    public AnvilNms anvilgui(Player player, AnvilNms.AnvilClickEventHandler handler){
        switch(version){
            case "v1_8_R1": // 1.8 - 1.8.1
                return new AnvilNms_1_8_R1(player, handler);
            case "v1_8_R2": // 1.8.3
                return new AnvilNms_1_8_R2(player, handler);
            case "v1_8_R3": // 1.8.7 - 1.8.9
                return new AnvilNms_1_8_R3(player, handler);
            case "v1_9_R1": // 1.9 - 1.9.2
                return new AnvilNms_1_9_R1(player, handler);
        }
        return null;
    }

    public BatNpc getBatNpc() {
        return batNpc;
    }
}
