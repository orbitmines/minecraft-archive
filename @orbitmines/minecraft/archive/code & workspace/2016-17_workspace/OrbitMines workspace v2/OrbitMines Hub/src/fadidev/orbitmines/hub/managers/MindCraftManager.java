package fadidev.orbitmines.hub.managers;

import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.handlers.npc.NPC;
import fadidev.orbitmines.api.utils.enums.Language;
import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.HubMessages;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import fadidev.orbitmines.hub.handlers.players.MindCraftPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fadi on 8-9-2016.
 */
public class MindCraftManager {

    private OrbitMinesHub hub;

    private Location location;
    private Location winsSign;
    private Location bestGameSign;
    private Map<Integer, List<Block>> blocksForTurn;
    private Map<Integer, List<Block>> blocksForTurnStatus;
    private NPC npc;

    public MindCraftManager(){
        hub = OrbitMinesHub.getHub();

        this.location = new Location(hub.getLobby(), 39.5, 77, 1.5, 0, 0);
        this.winsSign = new Location(hub.getLobby(), 41, 79, 2);
        this.bestGameSign = new Location(hub.getLobby(), 37, 79, 2);
        this.blocksForTurn = new HashMap<>();
        this.blocksForTurnStatus = new HashMap<>();

        registerMindCraft();
        registerKits();
    }

    public Location getLocation() {
        return location;
    }

    public Location getWinsSign() {
        return winsSign;
    }

    public Location getBestGameSign() {
        return bestGameSign;
    }

    public Map<Integer, List<Block>> getBlocksForTurn() {
        return blocksForTurn;
    }

    public Map<Integer, List<Block>> getBlocksForTurnStatus() {
        return blocksForTurnStatus;
    }

    public NPC getNpc() {
        return npc;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }

    private void registerMindCraft(){
        World w = hub.getLobby();

        blocksForTurn.put(0, Arrays.asList(w.getBlockAt(new Location(w, 42, 75, 5)), w.getBlockAt(new Location(w, 40, 75, 5)), w.getBlockAt(new Location(w, 38, 75, 5)), w.getBlockAt(new Location(w, 36, 75, 5))));
        blocksForTurn.put(1, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 8)), w.getBlockAt(new Location(w, 40, 74, 8)), w.getBlockAt(new Location(w, 38, 74, 8)), w.getBlockAt(new Location(w, 36, 74, 8))));
        blocksForTurn.put(2, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 10)), w.getBlockAt(new Location(w, 40, 74, 10)), w.getBlockAt(new Location(w, 38, 74, 10)), w.getBlockAt(new Location(w, 36, 74, 10))));
        blocksForTurn.put(3, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 12)), w.getBlockAt(new Location(w, 40, 74, 12)), w.getBlockAt(new Location(w, 38, 74, 12)), w.getBlockAt(new Location(w, 36, 74, 12))));
        blocksForTurn.put(4, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 14)), w.getBlockAt(new Location(w, 40, 74, 14)), w.getBlockAt(new Location(w, 38, 74, 14)), w.getBlockAt(new Location(w, 36, 74, 14))));
        blocksForTurn.put(5, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 16)), w.getBlockAt(new Location(w, 40, 74, 16)), w.getBlockAt(new Location(w, 38, 74, 16)), w.getBlockAt(new Location(w, 36, 74, 16))));
        blocksForTurn.put(6, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 18)), w.getBlockAt(new Location(w, 40, 74, 18)), w.getBlockAt(new Location(w, 38, 74, 18)), w.getBlockAt(new Location(w, 36, 74, 18))));
        blocksForTurn.put(7, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 20)), w.getBlockAt(new Location(w, 40, 74, 20)), w.getBlockAt(new Location(w, 38, 74, 20)), w.getBlockAt(new Location(w, 36, 74, 20))));
        blocksForTurn.put(8, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 22)), w.getBlockAt(new Location(w, 40, 74, 22)), w.getBlockAt(new Location(w, 38, 74, 22)), w.getBlockAt(new Location(w, 36, 74, 22))));
        blocksForTurn.put(9, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 24)), w.getBlockAt(new Location(w, 40, 74, 24)), w.getBlockAt(new Location(w, 38, 74, 24)), w.getBlockAt(new Location(w, 36, 74, 24))));
        blocksForTurn.put(10, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 26)), w.getBlockAt(new Location(w, 40, 74, 26)), w.getBlockAt(new Location(w, 38, 74, 26)), w.getBlockAt(new Location(w, 36, 74, 26))));
        blocksForTurn.put(11, Arrays.asList(w.getBlockAt(new Location(w, 42, 76, 29)), w.getBlockAt(new Location(w, 40, 76, 29)), w.getBlockAt(new Location(w, 38, 76, 29)), w.getBlockAt(new Location(w, 36, 76, 29))));

        blocksForTurnStatus.put(1, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 8)), w.getBlockAt(new Location(w, 32, 77, 8)), w.getBlockAt(new Location(w, 32, 78, 8)), w.getBlockAt(new Location(w, 32, 79, 8))));
        blocksForTurnStatus.put(2, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 10)), w.getBlockAt(new Location(w, 32, 77, 10)), w.getBlockAt(new Location(w, 32, 78, 10)), w.getBlockAt(new Location(w, 32, 79, 10))));
        blocksForTurnStatus.put(3, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 12)), w.getBlockAt(new Location(w, 32, 77, 12)), w.getBlockAt(new Location(w, 32, 78, 12)), w.getBlockAt(new Location(w, 32, 79, 12))));
        blocksForTurnStatus.put(4, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 14)), w.getBlockAt(new Location(w, 32, 77, 14)), w.getBlockAt(new Location(w, 32, 78, 14)), w.getBlockAt(new Location(w, 32, 79, 14))));
        blocksForTurnStatus.put(5, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 16)), w.getBlockAt(new Location(w, 32, 77, 16)), w.getBlockAt(new Location(w, 32, 78, 16)), w.getBlockAt(new Location(w, 32, 79, 16))));
        blocksForTurnStatus.put(6, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 18)), w.getBlockAt(new Location(w, 32, 77, 18)), w.getBlockAt(new Location(w, 32, 78, 18)), w.getBlockAt(new Location(w, 32, 79, 18))));
        blocksForTurnStatus.put(7, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 20)), w.getBlockAt(new Location(w, 32, 77, 20)), w.getBlockAt(new Location(w, 32, 78, 20)), w.getBlockAt(new Location(w, 32, 79, 20))));
        blocksForTurnStatus.put(8, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 22)), w.getBlockAt(new Location(w, 32, 77, 22)), w.getBlockAt(new Location(w, 32, 78, 22)), w.getBlockAt(new Location(w, 32, 79, 22))));
        blocksForTurnStatus.put(9, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 24)), w.getBlockAt(new Location(w, 32, 77, 24)), w.getBlockAt(new Location(w, 32, 78, 24)), w.getBlockAt(new Location(w, 32, 79, 24))));
        blocksForTurnStatus.put(10, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 26)), w.getBlockAt(new Location(w, 32, 77, 26)), w.getBlockAt(new Location(w, 32, 78, 26)), w.getBlockAt(new Location(w, 32, 79, 26))));
    }
    
    private void registerKits(){
        {
            Kit kit = new Kit("Mindcraft_" + Language.DUTCH.toString());
            {
                ItemStack item = new ItemStack(Material.TNT, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§4§lReset Kleuren");
                item.setItemMeta(meta);
                kit.setItem(0, item);
            }
            {
                ItemStack item = new ItemStack(Material.REDSTONE_TORCH_ON, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§c§lEinde Beurt");
                item.setItemMeta(meta);
                kit.setItem(1, item);
            }
            {
                ItemStack item = new ItemStack(Material.WOOL, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§1§lBlue");
                item.setItemMeta(meta);
                item.setDurability((short) 11);
                kit.setItem(3, item);
            }
            {
                ItemStack item = new ItemStack(Material.WOOL, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§e§lYellow");
                item.setItemMeta(meta);
                item.setDurability((short) 4);
                kit.setItem(4, item);
            }
            {
                ItemStack item = new ItemStack(Material.WOOL, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§a§lGreen");
                item.setItemMeta(meta);
                item.setDurability((short) 5);
                kit.setItem(5, item);
            }
            {
                ItemStack item = new ItemStack(Material.WOOL, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§c§lRed");
                item.setItemMeta(meta);
                item.setDurability((short) 14);
                kit.setItem(6, item);
            }
            {
                ItemStack item = new ItemStack(Material.WOOL, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§b§lLight Blue");
                item.setItemMeta(meta);
                item.setDurability((short) 3);
                kit.setItem(7, item);
            }
            {
                ItemStack item = new ItemStack(Material.WOOL, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§6§lOrange");
                item.setItemMeta(meta);
                item.setDurability((short) 1);
                kit.setItem(8, item);
            }

            hub.getApi().registerKit(kit);
            hub.getMindCraftKit().put(Language.DUTCH, kit);
        }
        {
            Kit kit = new Kit("Mindcraft_" + Language.ENGLISH.toString());
            {
                ItemStack item = new ItemStack(Material.TNT, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§4§lReset Colors");
                item.setItemMeta(meta);
                kit.setItem(0, item);
            }
            {
                ItemStack item = new ItemStack(Material.REDSTONE_TORCH_ON, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§c§lEnd Turn");
                item.setItemMeta(meta);
                kit.setItem(1, item);
            }
            {
                ItemStack item = new ItemStack(Material.WOOL, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§1§lBlue");
                item.setItemMeta(meta);
                item.setDurability((short) 11);
                kit.setItem(3, item);
            }
            {
                ItemStack item = new ItemStack(Material.WOOL, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§e§lYellow");
                item.setItemMeta(meta);
                item.setDurability((short) 4);
                kit.setItem(4, item);
            }
            {
                ItemStack item = new ItemStack(Material.WOOL, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§a§lGreen");
                item.setItemMeta(meta);
                item.setDurability((short) 5);
                kit.setItem(5, item);
            }
            {
                ItemStack item = new ItemStack(Material.WOOL, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§c§lRed");
                item.setItemMeta(meta);
                item.setDurability((short) 14);
                kit.setItem(6, item);
            }
            {
                ItemStack item = new ItemStack(Material.WOOL, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§b§lLight Blue");
                item.setItemMeta(meta);
                item.setDurability((short) 3);
                kit.setItem(7, item);
            }
            {
                ItemStack item = new ItemStack(Material.WOOL, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§6§lOrange");
                item.setItemMeta(meta);
                item.setDurability((short) 1);
                kit.setItem(8, item);
            }

            hub.getApi().registerKit(kit);
            hub.getMindCraftKit().put(Language.ENGLISH, kit);
        }
    }

    public void updateSigns(HubPlayer omp){
        Player p = omp.getPlayer();
        MindCraftPlayer mcp = omp.getMindCraftPlayer();

        if(p.getLocation().distance(getWinsSign()) < 16){
            String[] sign = new String[4];
            sign[0] = "";
            sign[1] = "§l" + HubMessages.WORD_WINS.get(omp);
            if(mcp != null){
                sign[2] = "" + mcp.getWins();
            }
            else{
                sign[2] = HubMessages.WORD_LOADING.get(omp) + "...";
            }
            sign[3] = "";
            p.sendSignChange(getWinsSign(), sign);
        }

        if(p.getLocation().distance(getBestGameSign()) < 16){
            String[] sign = new String[4];
            sign[0] = "";
            sign[1] = "§l" + HubMessages.WORD_BEST.get(omp) + " Game";
            if(mcp != null && mcp.getBestGame() != 0){
                sign[2] = mcp.getBestGame() + " " + (mcp.getBestGame() != 1 ? HubMessages.WORD_TURNS.get(omp) : HubMessages.WORD_TURN.get(omp));
            }
            else{
                sign[2] = HubMessages.WORD_NONE.get(omp);
            }
            sign[3] = "";
            p.sendSignChange(getBestGameSign(), sign);
        }
    }

    public void updateGame(HubPlayer omp){
        Player p = omp.getPlayer();

        if(omp.isInMindcraft()){
            MindCraftPlayer mcp = omp.getMindCraftPlayer();

            for(int i = 0; i <= 11; i++){
                List<Block> blocks = getBlocksForTurn().get(i);

                List<String> blocksFromTurns = mcp.getBlocksFromTurns();
                String turn = blocksFromTurns.get(i);
                String[] turnBlocks = turn.split("\\|");

                for(Block b : blocks){
                    int bint = Integer.parseInt(turnBlocks[blocks.indexOf(b)]);
                    p.sendBlockChange(b.getLocation(), Material.WOOL, (byte) bint);
                }

                if(i != 0 && i != 11){
                    List<Block> blocks2 = getBlocksForTurnStatus().get(i);

                    List<String> blockStatusFromTurns = mcp.getStatusFromTurns();
                    String status = blockStatusFromTurns.get(i);
                    String[] statusBlocks = status.split("\\|");

                    for(Block b : blocks2){
                        int data = Integer.parseInt(statusBlocks[blocks2.indexOf(b)]);

                        p.sendBlockChange(b.getLocation(), Material.STAINED_GLASS, (byte) data);
                    }
                }
            }
        }
        else{
            for(int i = 0; i <= 11; i++){
                List<Block> blocks = getBlocksForTurn().get(i);

                for(Block b : blocks){
                    p.sendBlockChange(b.getLocation(), Material.WOOL, (byte) 0);
                }

                if(i != 0 && i != 11){
                    List<Block> blocks2 = getBlocksForTurnStatus().get(i);
                    for(Block b : blocks2){
                        p.sendBlockChange(b.getLocation(), Material.STAINED_GLASS, (byte) 0);
                    }
                }
            }
        }
    }
}
