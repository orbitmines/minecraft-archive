package fadidev.orbitmines.hub.managers;

import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.handlers.firework.FireWork;
import fadidev.orbitmines.api.inventory.perks.CosmeticPerksInv;
import fadidev.orbitmines.api.managers.InteractManager;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.CageBuilder;
import fadidev.orbitmines.hub.handlers.HubMessages;
import fadidev.orbitmines.hub.handlers.MiniGameArena;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import fadidev.orbitmines.hub.handlers.players.MindCraftPlayer;
import fadidev.orbitmines.hub.inventories.SettingsInv;
import fadidev.orbitmines.hub.utils.enums.MindCraftColor;
import fadidev.orbitmines.hub.utils.enums.State;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Fadi on 10-9-2016.
 */
public class HubInteractManager extends InteractManager {

    /*
     * TODO
     * Not the best code: (Old Code)
     * Should edit this shortly.
     */

    private OrbitMinesHub hub;
    private HubPlayer omp;

    public HubInteractManager(PlayerInteractEvent e) {
        super(e);

        hub = OrbitMinesHub.getHub();
        omp = HubPlayer.getHubPlayer(e.getPlayer());
    }

    public boolean handleCageBuilder(){
        if(omp.hasPerms(StaffRank.OWNER) && item.getType() == CageBuilder.getWand().getType() && item.getItemMeta().getDisplayName().equals(CageBuilder.getWand().getItemMeta().getDisplayName())){
            e.setCancelled(true);

            if(a == Action.LEFT_CLICK_BLOCK){
                if(omp.getCageBuilder() != null){
                    omp.getCageBuilder().setL1(b.getLocation());
                    p.sendMessage("§7Set §eLocation 1§7 to §e" + b.getLocation().getBlockX() + "§7, §e" + b.getLocation().getBlockY() + "§7, §e" + b.getLocation().getBlockZ() + "§7.");
                }
                else{
                    p.sendMessage(HubMessages.CMD_CAGE_FIRST_NEW.get(omp));
                }
            }
            else if(a == Action.RIGHT_CLICK_BLOCK){
                if(omp.getCageBuilder() != null){
                    omp.getCageBuilder().setL2(b.getLocation());
                    p.sendMessage("§7Set §eLocation 2§7 to §e" + b.getLocation().getBlockX() + "§7, §e" + b.getLocation().getBlockY() + "§7, §e" + b.getLocation().getBlockZ() + "§7.");
                }
                else{
                    p.sendMessage(HubMessages.CMD_CAGE_FIRST_NEW.get(omp));
                }
            }

            return true;
        }
        return false;
    }

    public void handleSpawnInteract(){
        if(!omp.isOpMode() && p.getWorld().getName().equals(hub.getLobby().getName()) && e.getClickedBlock() != null){
            Material m = e.getClickedBlock().getType();

            if(item != null && (item.getType() == Material.WATER_BUCKET || item.getType() == Material.LAVA_BUCKET) || (m == Material.TRAP_DOOR || m == Material.CHEST || m == Material.TRAPPED_CHEST || m == Material.WORKBENCH || m == Material.FURNACE || m == Material.ENDER_CHEST || m == Material.POWERED_RAIL || m == Material.DETECTOR_RAIL || m == Material.ACTIVATOR_RAIL || m == Material.RAILS)){
                e.setCancelled(true);
                omp.updateInventory();
            }
        }
    }

    public void handlePhysicalAction(){
        if(!omp.isOpMode()){
            if(a == Action.PHYSICAL && (b == null || b.getType() != Material.STONE_PLATE && b.getType() != Material.WOOD_PLATE)){
                e.setCancelled(true);
            }
        }
    }

    public void handleMiniGameSigns(){
        if(b != null && (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN)){
            MiniGameArena arena = MiniGameArena.getMiniGameArena(b.getLocation());

            if(arena == null)
                return;
            
            String name = "§7§l" + arena.getType().getSignName() + " " + arena.getArenaID();
            if(arena.getState() != State.CLOSED){
                if(arena.getState() != State.ENDING && arena.getState() != State.RESTARTING){
                    if((arena.getState() == State.WAITING || arena.getState() == State.STARTING) && arena.getPlayers() == arena.getType().getMaxPlayers()){
                        p.sendMessage(HubMessages.SERVER_FULL.get(omp, name));
                    }
                    else{
                        p.sendMessage(HubMessages.SERVER_JOINING.get(omp, name));
                        omp.toMiniGame(arena);
                    }
                }
                else{
                    p.sendMessage(HubMessages.SERVER_RESTARTING.get(omp, name));
                }
            }
            else{
                p.sendMessage(HubMessages.SERVER_CLOSED.get(omp, name));
            }
        }
    }

    public boolean handleCosmeticPerks(){
        if(item.getType() == Material.ENDER_CHEST && item.getItemMeta().getDisplayName().equals("§9§nCosmetic Perks")){
            e.setCancelled(true);
            omp.updateInventory();

            if(!omp.isInLapisParkour())
                new CosmeticPerksInv().open(p);

            return true;
        }
        return false;
    }

    public boolean handleSettings(){
        if(item.getType() == Material.REDSTONE_TORCH_ON && item.getItemMeta().getDisplayName().equals("§c§n" + Messages.WORD_SETTINGS.get(omp))){
            e.setCancelled(true);
            omp.updateInventory();

            if(!omp.isInLapisParkour()){
                new SettingsInv(p).open(p);
                p.playEffect(p.getLocation(), Effect.STEP_SOUND, 152);
            }

            return true;
        }
        return false;
    }

    public boolean handleFly(){
        if(item.getType() == Material.FEATHER && item.getItemMeta().getDisplayName().equals("§f§nFly")){
            if(omp.hasPerms(VIPRank.IRON_VIP)){
                if(!omp.isInLapisParkour()){
                    p.playEffect(p.getLocation(), Effect.STEP_SOUND, 80);
                    p.setAllowFlight(!p.getAllowFlight());
                    p.setFlying(p.getAllowFlight());
                    p.sendMessage(Messages.CMD_TOGGLE_FLY.get(omp));
                }
            }
            else{
                omp.requiredVIPRank(VIPRank.IRON_VIP);
            }

            return true;
        }
        return false;
    }

    public boolean handleWrittenBook(){
        return item.getType() != Material.WRITTEN_BOOK;
    }

    public boolean handleAchievements(){
        if(item.getType() == Material.EXP_BOTTLE && item.getItemMeta().getDisplayName().equals("§d§nAchievements")){
            e.setCancelled(true);
            omp.updateInventory();

            p.sendMessage("§a§o" + HubMessages.WORD_COMING_SOON.get(omp) + "...");
            // TODO ADD ACHIEVEMENTS \\

            return true;
        }
        return false;
    }

    public boolean handleServerSelector(){
        if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().equals("§3§nServer Selector")){
            e.setCancelled(true);
            omp.updateInventory();

            api.getServerSelector().open(p);

            return true;
        }
        return false;
    }

    public void handleKickSign(){
        if(b != null && b.getType() == Material.WALL_SIGN && b.getLocation().getBlockX() == -18 && b.getLocation().getBlockZ() == 2){
            p.kickPlayer(HubMessages.KICK_SIGN.get(omp));
        }
    }

    public void handleMindCraft(){
        if(omp.isInMindcraft()){
            final MindCraftPlayer mcp = omp.getMindCraftPlayer();
            
            if(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null){
                if(item.getType() == Material.TNT && item.getItemMeta().getDisplayName().equals(HubMessages.RESET_COLORS.get(omp))){
                    e.setCancelled(true);
                    omp.updateInventory();

                    p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5, 1);

                    List<String> blocksFromTurn = mcp.getBlocksFromTurns();
                    blocksFromTurn.set(0, "0|0|0|0");

                    List<Block> blocks = hub.getMindCraft().getBlocksForTurn().get(0);

                    for(Block bl : blocks){
                        p.sendBlockChange(bl.getLocation(), Material.WOOL, (byte) 0);
                    }
                }
                else if(item.getType() == Material.REDSTONE_TORCH_ON && item.getItemMeta().getDisplayName().equals(HubMessages.END_TURN.get(omp))){
                    e.setCancelled(true);
                    omp.updateInventory();

                    List<String> blocksFromTurn = new ArrayList<>(mcp.getBlocksFromTurns());

                    String newTurnNext = blocksFromTurn.get(0);
                    if(!newTurnNext.contains("0")){
                        List<String> blockStatusFromTurn = new ArrayList<>(mcp.getStatusFromTurns());

                        int currentTurn = mcp.getCurrentTurn();
                        String newTurn = "0|0|0|0";

                        blocksFromTurn.set(0, newTurn);
                        blocksFromTurn.set(currentTurn, newTurnNext);

                        mcp.setBlocksFromTurns(blocksFromTurn);

                        String[] correctTurn = mcp.getCorrectTurn().split("\\|");
                        String[] thisTurn = newTurnNext.split("\\|");

                        List<String> correctTurnInts = Arrays.asList(correctTurn);
                        List<String> thisTurnInts = Arrays.asList(thisTurn);

                        String status = "";
                        int correct = 0;
                        int otherPlace = 0;
                        int incorrect = 0;

                        for(int iR = 3; iR > -1; iR--){
                            if(correctTurnInts.get(iR).equals(thisTurnInts.get(iR))){
                                correct++;
                                correctTurnInts.set(iR, "0");
                            }
                        }
                        for(int iR = 3; iR > -1; iR--){
                            if(correctTurnInts.contains(thisTurnInts.get(iR))){
                                otherPlace++;
                                correctTurnInts.set(iR, "0");
                            }
                            else{
                                if(!correctTurnInts.get(iR).equals("0")){
                                    incorrect++;
                                }
                            }
                        }

                        if(correct + otherPlace + incorrect == 5){
                            otherPlace--;
                        }

                        if(correct != 0){
                            for(int iR = 0; iR < correct; iR++){
                                status += "|" + "5";
                            }
                        }
                        if(otherPlace != 0){
                            for(int iR = 0; iR < otherPlace; iR++){
                                status += "|" + "4";
                            }
                        }
                        if(incorrect != 0){
                            for(int iR = 0; iR < incorrect; iR++){
                                status += "|" + "0";
                            }
                        }

                        status = status.substring(1);

                        blockStatusFromTurn.set(currentTurn, status);
                        mcp.setStatusFromTurns(blockStatusFromTurn);

                        mcp.setCurrentTurn(currentTurn +1);

                        List<Block> blocks = hub.getMindCraft().getBlocksForTurn().get(0);
                        List<Block> blocks2 = hub.getMindCraft().getBlocksForTurn().get(currentTurn);

                        for(Block bl : blocks){
                            p.sendBlockChange(bl.getLocation(), Material.WOOL, (byte) 0);
                        }
                        for(Block bl : blocks2){
                            p.sendBlockChange(bl.getLocation(), Material.WOOL, (byte) Integer.parseInt(thisTurn[blocks2.indexOf(bl)]));
                        }

                        if(mcp.getCorrectTurn().equals(newTurnNext)){
                            p.getInventory().clear();
                            p.sendMessage("§c§lMindCraft §8| " + HubMessages.RIGHT_COMBINATION.get(omp));
                            p.sendMessage("§c§lMindCraft §8| §c§l+1 Win");
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);

                            blocksFromTurn.set(11, mcp.getCorrectTurn());
                            mcp.setBlocksFromTurns(blocksFromTurn);

                            mcp.addWin();

                            if(mcp.getBestGame() != -1){
                                if(currentTurn < mcp.getBestGame()){
                                    mcp.setBestGame(currentTurn);
                                }
                            }
                            else{
                                Database.get().insert("MasterMind-BestGame", "uuid`, `turns", omp.getUUID().toString() + "', '" + currentTurn);
                            }

                            FireWork fw = new FireWork(p.getLocation());
                            fw.randomize();
                            fw.build();

                            Title t = new Title("§c§lMindCraft", HubMessages.YOU_WON.get(omp) + " §c+1 Win", 20, 40, 20);
                            t.send(p);

                            p.getInventory().clear();
                            omp.updateInventory();

                            new BukkitRunnable(){
                                public void run(){
                                    mcp.reset();
                                }
                            }.runTaskLater(hub, 100);
                        }
                        else if(currentTurn == 10){
                            blocksFromTurn.set(11, mcp.getCorrectTurn());
                            mcp.setBlocksFromTurns(blocksFromTurn);

                            p.sendMessage("§c§lMindCraft §8| " + HubMessages.YOU_LOST.get(omp));

                            Title t = new Title("§c§lMindCraft", HubMessages.YOU_LOST.get(omp), 20, 40, 20);
                            t.send(p);

                            p.getInventory().clear();
                            omp.updateInventory();

                            new BukkitRunnable(){
                                public void run(){
                                    mcp.reset();
                                }
                            }.runTaskLater(hub, 100);
                        }
                        else{
                            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
                            p.sendMessage("§c§lMindCraft §8| §7Correct: §a" + correct + " §7" + HubMessages.WORD_OTHER_PLACE.get(omp) + ": §e" + otherPlace + " §7Incorrect: §c" + incorrect);
                        }
                    }
                    else{
                        p.sendMessage(HubMessages.NOT_USED_ALL_SLOTS.get(omp));
                    }
                }
            }
            if(a == Action.RIGHT_CLICK_BLOCK){
                List<Block> blocks = hub.getMindCraft().getBlocksForTurn().get(0);
                if(blocks.contains(b) && item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null){
                    for(final MindCraftColor color : MindCraftColor.values()){
                        if(item.getType() == Material.WOOL && item.getItemMeta().getDisplayName().equals(color.getName())){
                            e.setCancelled(true);
                            omp.updateInventory();

                            List<String> blocksFromTurn = mcp.getBlocksFromTurns();
                            String turn = blocksFromTurn.get(0);
                            String[] turnBlocks = turn.split("\\|");
                            turnBlocks[blocks.indexOf(b)] = "" + color.getData();
                            String newTurn = "";
                            for(String s : turnBlocks){
                                newTurn += "|" + s;
                            }
                            newTurn = newTurn.substring(1);
                            blocksFromTurn.set(0, newTurn);

                            mcp.setBlocksFromTurns(blocksFromTurn);

                            new BukkitRunnable(){
                                public void run(){
                                    p.sendBlockChange(b.getLocation(), Material.WOOL, color.getData());
                                }
                            }.runTaskLater(hub, 1);
                        }
                    }
                }
            }
        }
    }
}
