package me.O_o_Fadi_o_O.Hub.events;

import me.O_o_Fadi_o_O.Hub.Hub;
import me.O_o_Fadi_o_O.Hub.Inv.JoinItems;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener{
	
	Hub plugin;
	 
	public JoinEvent(Hub instance) {
	plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e){
		
		Player p = e.getPlayer();
		
		e.setJoinMessage("§2§l§o>> " + p.getName() + " §2§o(§a§oHub§2§o)");
		
		JoinItems.giveItems(p);
		
		if(!plugin.containsElement("name", "OMT", p.getName())){
			plugin.insertElement(p.getName(), "OMT", 0);
		}
		if(!plugin.containsElement("name", "POINTS", p.getName())){
			plugin.insertElement(p.getName(), "POINTS", 0);
		}
		if(!plugin.containsPath("name", "MiniGameCoins", p.getName())){
			plugin.insertPath(p.getName(), "MiniGameCoins", "coins", 0);
		}
		if(!plugin.containsPath("name", "Fireworks-Passes", p.getName())){
			plugin.insertPath(p.getName(), "Fireworks-Passes", "passes", 0);
		}
		
		Hub.omt.put(p.getName(), plugin.getOMT(p));
	
		Hub.points.put(p.getName(), plugin.getVIPPoints(p));
		
		Hub.coins.put(p.getName(), Hub.getMiniGameCoins(p));
		
		Hub.FireworkPasses.put(p.getName(), plugin.getFireworkPasses(p));
		
		if(plugin.containsPath("name", "Gadgets-Paintballs", p.getName())){
			Hub.gadgetsPaintballs.put(p.getName(), 1);
		}
		if(plugin.containsPath("name", "Gadgets-CreeperLauncher", p.getName())){
			Hub.gadgetsCreeperLauncher.put(p.getName(), 1);
		}
		if(plugin.containsPath("name", "Gadgets-PetRide", p.getName())){
			Hub.gadgetsPetRide.put(p.getName(), 1);
		}
		if(plugin.containsPath("name", "Gadgets-BookExplosion", p.getName())){
			Hub.gadgetsBookExplosion.put(p.getName(), 1);
		}
		
		if(plugin.containsElement("name", "MUSHROOMCOW", p.getName())){
			Hub.mushroomcow.put(p.getName(), plugin.getMushroomCowName(p));
		}
		if(plugin.containsElement("name", "PIG", p.getName())){
			Hub.pig.put(p.getName(), plugin.getPigName(p));
		}
		if(plugin.containsElement("name", "WOLF", p.getName())){
			Hub.wolf.put(p.getName(), plugin.getWolfName(p));
		}
		if(plugin.containsElement("name", "SHEEP", p.getName())){
			Hub.sheep.put(p.getName(), plugin.getSheepName(p));
		}
		if(plugin.containsElement("name", "HORSE", p.getName())){
			Hub.horse.put(p.getName(), plugin.getHorseName(p));
		}
		if(plugin.containsElement("name", "MAGMACUBE", p.getName())){
			Hub.magmacube.put(p.getName(), plugin.getMagmaCubeName(p));
		}
		if(plugin.containsElement("name", "SLIME", p.getName())){
			Hub.slime.put(p.getName(), plugin.getSlimeName(p));
		}
		if(plugin.containsElement("name", "COW", p.getName())){
			Hub.cow.put(p.getName(), plugin.getCowName(p));
		}
		if(plugin.containsElement("name", "SILVERFISH", p.getName())){
			Hub.silverfish.put(p.getName(), plugin.getSilverfishName(p));
		}
		if(plugin.containsElement("name", "OCELOT", p.getName())){
			Hub.ocelot.put(p.getName(), plugin.getOcelotName(p));
		}
		
		if(plugin.containsElement("name", "WHITE", p.getName())){
			Hub.white.put(p.getName(), plugin.getWhiteWardrobe(p));
		}
		if(plugin.containsElement("name", "BLUE", p.getName())){
			Hub.blue.put(p.getName(), plugin.getBlueWardrobe(p));
		}
		if(plugin.containsElement("name", "GREEN", p.getName())){
			Hub.green.put(p.getName(), plugin.getGreenWardrobe(p));
		}
		if(plugin.containsElement("name", "BLACK", p.getName())){
			Hub.black.put(p.getName(), plugin.getBlackWardrobe(p));
		}
		if(plugin.containsElement("name", "LIGHTBLUE", p.getName())){
			Hub.lightblue.put(p.getName(), plugin.getLightBlueWardrobe(p));
		}
		if(plugin.containsElement("name", "PINK", p.getName())){
			Hub.pink.put(p.getName(), plugin.getPinkWardrobe(p));
		}
		if(plugin.containsElement("name", "LIGHTGREEN", p.getName())){
			Hub.lightgreen.put(p.getName(), plugin.getLightGreenWardrobe(p));
		}
		if(plugin.containsElement("name", "DARKBLUE", p.getName())){
			Hub.darkblue.put(p.getName(), plugin.getDarkBlueWardrobe(p));
		}
		if(plugin.containsElement("name", "PURPLE", p.getName())){
			Hub.purple.put(p.getName(), plugin.getPurpleWardrobe(p));
		}
		if(plugin.containsElement("name", "ORANGE", p.getName())){
			Hub.orange.put(p.getName(), plugin.getOrangeWardrobe(p));
		}
		if(plugin.containsElement("name", "RED", p.getName())){
			Hub.red.put(p.getName(), plugin.getRedWardrobe(p));
		}
		if(plugin.containsElement("name", "CYAN", p.getName())){
			Hub.cyan.put(p.getName(), plugin.getCyanWardrobe(p));
		}
		if(plugin.containsElement("name", "YELLOW", p.getName())){
			Hub.yellow.put(p.getName(), plugin.getYellowWardrobe(p));
		}
		if(plugin.containsElement("name", "GRAY", p.getName())){
			Hub.gray.put(p.getName(), plugin.getGrayWardrobe(p));
		}
		
		if(plugin.containsElement("name", "CHATCOLORDARKRED", p.getName())){
			Hub.chatcolordarkred.put(p.getName(), plugin.getChatColorDarkRed(p));
		}
		if(plugin.containsElement("name", "CHATCOLORLIGHTGREEN", p.getName())){
			Hub.chatcolorlightgreen.put(p.getName(), plugin.getChatColorLightGreen(p));
		}
		if(plugin.containsElement("name", "CHATCOLORDARKGRAY", p.getName())){
			Hub.chatcolordarkgray.put(p.getName(), plugin.getChatColorDarkGray(p));
		}
		if(plugin.containsElement("name", "CHATCOLORRED", p.getName())){
			Hub.chatcolorred.put(p.getName(), plugin.getChatColorRed(p));
		}
		if(plugin.containsElement("name", "CHATCOLORWHITE", p.getName())){
			Hub.chatcolorwhite.put(p.getName(), plugin.getChatColorWhite(p));
		}
		if(plugin.containsElement("name", "CHATCOLORLIGHTBLUE", p.getName())){
			Hub.chatcolorlightblue.put(p.getName(), plugin.getChatColorLightBlue(p));
		}
		if(plugin.containsElement("name", "CHATCOLORPINK", p.getName())){
			Hub.chatcolorpink.put(p.getName(), plugin.getChatColorPink(p));
		}
		if(plugin.containsElement("name", "CHATCOLORBLUE", p.getName())){
			Hub.chatcolorblue.put(p.getName(), plugin.getChatColorBlue(p));
		}
		if(plugin.containsElement("name", "CHATCOLORDARKBLUE", p.getName())){
			Hub.chatcolordarkblue.put(p.getName(), plugin.getChatColorDarkBlue(p));
		}
		if(plugin.containsElement("name", "CHATCOLORGREEN", p.getName())){
			Hub.chatcolorgreen.put(p.getName(), plugin.getChatColorGreen(p));
		}
		if(plugin.containsElement("name", "CHATCOLORBLACK", p.getName())){
			Hub.chatcolorblack.put(p.getName(), plugin.getChatColorBlack(p));
		}
		
		if(plugin.containsElement("name", "TRAILSANGRYVILLAGER", p.getName())){
			Hub.trailsangryvillager.put(p.getName(), plugin.getTrailAngryVillager(p));
		}
		if(plugin.containsElement("name", "TRAILSHEARTS", p.getName())){
			Hub.trailshearts.put(p.getName(), plugin.getTrailHearts(p));
		}
		if(plugin.containsElement("name", "TRAILSBUBBLE", p.getName())){
			Hub.trailsbubbles.put(p.getName(), plugin.getTrailBubble(p));
		}
		if(plugin.containsElement("name", "TRAILSCRIT", p.getName())){
			Hub.trailscrit.put(p.getName(), plugin.getTrailCrit(p));
		}
		if(plugin.containsElement("name", "TRAILSETABLE", p.getName())){
			Hub.trailsenchantmenttable.put(p.getName(), plugin.getTrailETable(p));
		}
		if(plugin.containsElement("name", "TRAILSEXPLODE", p.getName())){
			Hub.trailsexplode.put(p.getName(), plugin.getTrailExplode(p));
		}
		if(plugin.containsElement("name", "TRAILSFIREWORK", p.getName())){
			Hub.trailsfirework.put(p.getName(), plugin.getTrailFirework(p));
		}
		if(plugin.containsElement("name", "TRAILSHAPPYVILLAGER", p.getName())){
			Hub.trailshappyvillager.put(p.getName(), plugin.getTrailHappyVillager(p));
		}
		if(plugin.containsElement("name", "TRAILSMOBSPAWNER", p.getName())){
			Hub.trailsmobspawner.put(p.getName(), plugin.getTrailMobSpawner(p));
		}
		if(plugin.containsElement("name", "TRAILSMUSIC", p.getName())){
			Hub.trailsnote.put(p.getName(), plugin.getTrailMusic(p));
		}
		if(plugin.containsElement("name", "TRAILSSLIME", p.getName())){
			Hub.trailsslime.put(p.getName(), plugin.getTrailSlime(p));
		}
		if(plugin.containsElement("name", "TRAILSSMOKE", p.getName())){
			Hub.trailssmoke.put(p.getName(), plugin.getTrailSmoke(p));
		}
		if(plugin.containsElement("name", "TRAILSSNOW", p.getName())){
			Hub.trailssnow.put(p.getName(), plugin.getTrailSnow(p));
		}
		if(plugin.containsElement("name", "TRAILSWATER", p.getName())){
			Hub.trailswater.put(p.getName(), plugin.getTrailWater(p));
		}
		if(plugin.containsElement("name", "TRAILSWITCH", p.getName())){
			Hub.trailswitch.put(p.getName(), plugin.getTrailWitch(p));
		}
		
		if(plugin.containsElement("name", "CHATCOLOR", p.getName())){
			Hub.chatcolor.put(p, plugin.getChatColor(p));
		}
		else{
			plugin.insertColorCode(p.getName(), "CHATCOLOR", "7");
			Hub.chatcolor.put(p, "7");
		}
		
		if(plugin.containsHats("name", "Hats-Bedrock", p.getName())){
			Hub.hatsBedrock.put(p.getName(), plugin.getHat(p, "Hats-Bedrock", "bedrock"));
		}
		if(plugin.containsHats("name", "Hats-BlackGlass", p.getName())){
			Hub.hatsBlackGlass.put(p.getName(), plugin.getHat(p, "Hats-BlackGlass", "blackglass"));
		}
		if(plugin.containsHats("name", "Hats-Cactus", p.getName())){
			Hub.hatsCactus.put(p.getName(), plugin.getHat(p, "Hats-Cactus", "cactus"));
		}
		if(plugin.containsHats("name", "Hats-CoalBlock", p.getName())){
			Hub.hatsCoalBlock.put(p.getName(), plugin.getHat(p, "Hats-CoalBlock", "coalblock"));
		}
		if(plugin.containsHats("name", "Hats-CoalOre", p.getName())){
			Hub.hatsCoalOre.put(p.getName(), plugin.getHat(p, "Hats-CoalOre", "coalore"));
		}
		if(plugin.containsHats("name", "Hats-Furnace", p.getName())){
			Hub.hatsFurnace.put(p.getName(), plugin.getHat(p, "Hats-Furnace", "furnace"));
		}
		if(plugin.containsHats("name", "Hats-Glass", p.getName())){
			Hub.hatsGlass.put(p.getName(), plugin.getHat(p, "Hats-Glass", "glass"));
		}
		if(plugin.containsHats("name", "Hats-Grass", p.getName())){
			Hub.hatsGrass.put(p.getName(), plugin.getHat(p, "Hats-Grass", "grass"));
		}
		if(plugin.containsHats("name", "Hats-GreenGlass", p.getName())){
			Hub.hatsGreenGlass.put(p.getName(), plugin.getHat(p, "Hats-GreenGlass", "greenglass"));
		}
		if(plugin.containsHats("name", "Hats-HayBale", p.getName())){
			Hub.hatsHayBale.put(p.getName(), plugin.getHat(p, "Hats-HayBale", "haybale"));
		}
		if(plugin.containsHats("name", "Hats-Ice", p.getName())){
			Hub.hatsIce.put(p.getName(), plugin.getHat(p, "Hats-Ice", "ice"));
		}
		if(plugin.containsHats("name", "Hats-LapisBlock", p.getName())){
			Hub.hatsLapisBlock.put(p.getName(), plugin.getHat(p, "Hats-LapisBlock", "lapisblock"));
		}
		if(plugin.containsHats("name", "Hats-LapisOre", p.getName())){
			Hub.hatsLapisOre.put(p.getName(), plugin.getHat(p, "Hats-LapisOre", "lapisore"));
		}
		if(plugin.containsHats("name", "Hats-Leaves", p.getName())){
			Hub.hatsLeaves.put(p.getName(), plugin.getHat(p, "Hats-Leaves", "leaves"));
		}
		if(plugin.containsHats("name", "Hats-MagentaGlass", p.getName())){
			Hub.hatsMagentaGlass.put(p.getName(), plugin.getHat(p, "Hats-MagentaGlass", "magentaglass"));
		}
		if(plugin.containsHats("name", "Hats-Melon", p.getName())){
			Hub.hatsMelon.put(p.getName(), plugin.getHat(p, "Hats-Melon", "melon"));
		}
		if(plugin.containsHats("name", "Hats-Mycelium", p.getName())){
			Hub.hatsMycelium.put(p.getName(), plugin.getHat(p, "Hats-Mycelium", "mycelium"));
		}
		if(plugin.containsHats("name", "Hats-OrangeGlass", p.getName())){
			Hub.hatsOrangeGlass.put(p.getName(), plugin.getHat(p, "Hats-OrangeGlass", "orangeglass"));
		}
		if(plugin.containsHats("name", "Hats-QuartzBlock", p.getName())){
			Hub.hatsQuartzBlock.put(p.getName(), plugin.getHat(p, "Hats-QuartzBlock", "quartzblock"));
		}
		if(plugin.containsHats("name", "Hats-QuartzOre", p.getName())){
			Hub.hatsQuartzOre.put(p.getName(), plugin.getHat(p, "Hats-QuartzOre", "quartzore"));
		}
		if(plugin.containsHats("name", "Hats-RedGlass", p.getName())){
			Hub.hatsRedGlass.put(p.getName(), plugin.getHat(p, "Hats-RedGlass", "redglass"));
		}
		if(plugin.containsHats("name", "Hats-RedstoneBlock", p.getName())){
			Hub.hatsRedstoneBlock.put(p.getName(), plugin.getHat(p, "Hats-RedstoneBlock", "redstoneblock"));
		}
		if(plugin.containsHats("name", "Hats-RedstoneOre", p.getName())){
			Hub.hatsRedstoneOre.put(p.getName(), plugin.getHat(p, "Hats-RedstoneOre", "redstoneore"));
		}
		if(plugin.containsHats("name", "Hats-Snow", p.getName())){
			Hub.hatsSnow.put(p.getName(), plugin.getHat(p, "Hats-Snow", "snow"));
		}
		if(plugin.containsHats("name", "Hats-StoneBricks", p.getName())){
			Hub.hatsStoneBricks.put(p.getName(), plugin.getHat(p, "Hats-StoneBricks", "stonebricks"));
		}
		if(plugin.containsHats("name", "Hats-TNT", p.getName())){
			Hub.hatsTNT.put(p.getName(), plugin.getHat(p, "Hats-TNT", "tnt"));
		}
		if(plugin.containsHats("name", "Hats-Workbench", p.getName())){
			Hub.hatsWorkbench.put(p.getName(), plugin.getHat(p, "Hats-Workbench", "workbench"));
		}
		if(plugin.containsHats("name", "Hats-YellowGlass", p.getName())){
			Hub.hatsYellowGlass.put(p.getName(), plugin.getHat(p, "Hats-YellowGlass", "yellowglass"));
		}
		
		if(plugin.containsDisguises("name", "Dis-Witch", p.getName())){
			Hub.disWitch.put(p.getName(), plugin.getDisguise(p, "Dis-Witch", "witch"));
		}
		if(plugin.containsDisguises("name", "Dis-Bat", p.getName())){
			Hub.disBat.put(p.getName(), plugin.getDisguise(p, "Dis-Bat", "bat"));
		}
		if(plugin.containsDisguises("name", "Dis-Chicken", p.getName())){
			Hub.disChicken.put(p.getName(), plugin.getDisguise(p, "Dis-Chicken", "chicken"));
		}
		if(plugin.containsDisguises("name", "Dis-Ocelot", p.getName())){
			Hub.disOcelot.put(p.getName(), plugin.getDisguise(p, "Dis-Ocelot", "ocelot"));
		}
		if(plugin.containsDisguises("name", "Dis-MushroomCow", p.getName())){
			Hub.disMushroomCow.put(p.getName(), plugin.getDisguise(p, "Dis-MushroomCow", "mushroomcow"));
		}
		if(plugin.containsDisguises("name", "Dis-Squid", p.getName())){
			Hub.disSquid.put(p.getName(), plugin.getDisguise(p, "Dis-Squid", "squid"));
		}
		if(plugin.containsDisguises("name", "Dis-Slime", p.getName())){
			Hub.disSlime.put(p.getName(), plugin.getDisguise(p, "Dis-Slime", "slime"));
		}
		if(plugin.containsDisguises("name", "Dis-ZombiePigman", p.getName())){
			Hub.disZombiePigman.put(p.getName(), plugin.getDisguise(p, "Dis-ZombiePigman", "zombiepigman"));
		}
		if(plugin.containsDisguises("name", "Dis-MagmaCube", p.getName())){
			Hub.disMagmaCube.put(p.getName(), plugin.getDisguise(p, "Dis-MagmaCube", "magmacube"));
		}
		if(plugin.containsDisguises("name", "Dis-Skeleton", p.getName())){
			Hub.disSkeleton.put(p.getName(), plugin.getDisguise(p, "Dis-Skeleton", "skeleton"));
		}
		if(plugin.containsDisguises("name", "Dis-Wolf", p.getName())){
			Hub.disWolf.put(p.getName(), plugin.getDisguise(p, "Dis-Wolf", "wolf"));
		}
		if(plugin.containsDisguises("name", "Dis-Spider", p.getName())){
			Hub.disSpider.put(p.getName(), plugin.getDisguise(p, "Dis-Spider", "spider"));
		}
		if(plugin.containsDisguises("name", "Dis-Silverfish", p.getName())){
			Hub.disSilverfish.put(p.getName(), plugin.getDisguise(p, "Dis-Silverfish", "silverfish"));
		}
		if(plugin.containsDisguises("name", "Dis-Sheep", p.getName())){
			Hub.disSheep.put(p.getName(), plugin.getDisguise(p, "Dis-Sheep", "sheep"));
		}
		if(plugin.containsDisguises("name", "Dis-CaveSpider", p.getName())){
			Hub.disCaveSpider.put(p.getName(), plugin.getDisguise(p, "Dis-CaveSpider", "cavespider"));
		}
		if(plugin.containsDisguises("name", "Dis-Creeper", p.getName())){
			Hub.disCreeper.put(p.getName(), plugin.getDisguise(p, "Dis-Creeper", "creeper"));
		}
		if(plugin.containsDisguises("name", "Dis-Cow", p.getName())){
			Hub.disCow.put(p.getName(), plugin.getDisguise(p, "Dis-Cow", "cow"));
		}
		
		if(!Hub.EnablePlayers.containsKey(p.getName())){
			Hub.EnablePlayers.put(p.getName(), true);
		}
		else{
			if(Hub.EnablePlayers.get(p.getName()) == false){
				for(Player player : Bukkit.getOnlinePlayers()){
					p.hidePlayer(player);
				}
			}
		}
		if(!Hub.EnableStacker.containsKey(p.getName())){
			Hub.EnableStacker.put(p.getName(), true);
		}
		if(!Hub.EnableScoreboard.containsKey(p.getName())){
			Hub.EnableScoreboard.put(p.getName(), true);
		}
		
		for(Player player : Bukkit.getOnlinePlayers()){
			if(Hub.EnablePlayers.containsKey(player)){
				if(Hub.EnablePlayers.get(player) == false){
					player.hidePlayer(p);
				}
			}
		}
		
		p.chat("/spawn");
	}
}
