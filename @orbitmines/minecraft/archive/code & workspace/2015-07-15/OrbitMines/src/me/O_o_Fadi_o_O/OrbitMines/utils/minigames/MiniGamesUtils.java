package me.O_o_Fadi_o_O.OrbitMines.utils.minigames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.MiniGameType;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class MiniGamesUtils {

	public enum InvType {
		
		KITS,
		PERKS;
		
	}
	
	public enum Rarity {
		
		COMMON,
		RARE,
		LEGENDARY;
		
		public String getName(){
			switch(this){
				case COMMON:
					return "§a§lCommon";
				case LEGENDARY:
					return "§c§lLegendary";
				case RARE:
					return "§6§lRare";
				default:
					return null;
			}
		}
		
		public String getColor(){
			switch(this){
				case COMMON:
					return "§a";
				case LEGENDARY:
					return "§c";
				case RARE:
					return "§6";
				default:
					return null;
			}
		}
		
		public int getAmount(){
			switch(this){
				case COMMON:
					return 15;
				case LEGENDARY:
					return 1;
				case RARE:
					return 5;
				default:
					return 0;
			}
		}
	}
	
	public enum TicketType {
		
		// SG Kits \\
		RUNNER_KIT(0),
		FIGHTER_KIT(1),
		WARRIOR_KIT(3),
		BOMBER_KIT(4),
		ARCHER_KIT(5),
		
		// SG Game Effects \\
		WHITE_ARMOR(6),
		BLUE_ARMOR(7),
		GREEN_ARMOR(8),
		BLACK_ARMOR(9),
		LIGHT_BLUE_ARMOR(10),
		PINK_ARMOR(11),
		LIGHT_GREEN_ARMOR(12),
		DARK_BLUE_ARMOR(13),
		PURPLE_ARMOR(14),
		ORANGE_ARMOR(15),
		RED_ARMOR(16),
		CYAN_ARMOR(17),
		YELLOW_ARMOR(18),
		GRAY_ARMOR(19),
		ENABLE_POTIONS(20),
		DOUBLE_LOOT(21),
		ENABLE_POTIONS_PLAYER(22),
		DOUBLE_LOOT_PLAYER(23);
		
		TicketType(int ticketid){}
		
		public String getName(){
			switch(this){
				case ARCHER_KIT:
					return "Archer Kit";
				case BOMBER_KIT:
					return "Bomber Kit";
				case FIGHTER_KIT:
					return "Fighter Kit";
				case RUNNER_KIT:
					return "Runner Kit";
				case WARRIOR_KIT:
					return "Warrior Kit";
				case BLACK_ARMOR:
					return "Black Armor";
				case BLUE_ARMOR:
					return "Blue Armor";
				case CYAN_ARMOR:
					return "Cyan Armor";
				case DARK_BLUE_ARMOR:
					return "Dark Blue Armor";
				case GRAY_ARMOR:
					return "Gray Armor";
				case GREEN_ARMOR:
					return "Green Armor";
				case LIGHT_BLUE_ARMOR:
					return "Light Blue Armor";
				case LIGHT_GREEN_ARMOR:
					return "Light Green Armor";
				case ORANGE_ARMOR:
					return "Orange Armor";
				case PINK_ARMOR:
					return "Pink Armor";
				case PURPLE_ARMOR:
					return "Purple Armor";
				case RED_ARMOR:
					return "Red Armor";
				case WHITE_ARMOR:
					return "White Armor";
				case YELLOW_ARMOR:
					return "Yellow Armor";
				case DOUBLE_LOOT:
					return "Double Loot [All]";
				case DOUBLE_LOOT_PLAYER:
					return "Double Loot [Solo]";
				case ENABLE_POTIONS:
					return "Enable Potions [All]";
				case ENABLE_POTIONS_PLAYER:
					return "Enable Potions [Solo]";
			}
			return null;
		}
		
		public String getNameWithGame(){
			switch(this){
				case ARCHER_KIT:
					return "Archer Kit (SG)";
				case BOMBER_KIT:
					return "Bomber Kit (SG)";
				case FIGHTER_KIT:
					return "Fighter Kit (SG)";
				case RUNNER_KIT:
					return "Runner Kit (SG)";
				case WARRIOR_KIT:
					return "Warrior Kit (SG)";
				case BLACK_ARMOR:
					return "Black Armor (SG)";
				case BLUE_ARMOR:
					return "Blue Armor (SG)";
				case CYAN_ARMOR:
					return "Cyan Armor (SG)";
				case DARK_BLUE_ARMOR:
					return "Dark Blue Armor (SG)";
				case GRAY_ARMOR:
					return "Gray Armor (SG)";
				case GREEN_ARMOR:
					return "Green Armor (SG)";
				case LIGHT_BLUE_ARMOR:
					return "Light Blue Armor (SG)";
				case LIGHT_GREEN_ARMOR:
					return "Light Green Armor (SG)";
				case ORANGE_ARMOR:
					return "Orange Armor (SG)";
				case PINK_ARMOR:
					return "Pink Armor (SG)";
				case PURPLE_ARMOR:
					return "Purple Armor (SG)";
				case RED_ARMOR:
					return "Red Armor (SG)";
				case WHITE_ARMOR:
					return "White Armor (SG)";
				case YELLOW_ARMOR:
					return "Yellow Armor (SG)";
				case DOUBLE_LOOT:
					return "Double Loot [All] (SG)";
				case DOUBLE_LOOT_PLAYER:
					return "Double Loot [Solo] (SG)";
				case ENABLE_POTIONS:
					return "Enable Potions [All] (SG)";
				case ENABLE_POTIONS_PLAYER:
					return "Enable Potions [Solo] (SG)";
			}
			return null;
		}
		
		public MiniGameType getGameType(){
			switch(this){
				case ARCHER_KIT:
					return MiniGameType.SURVIVAL_GAMES;
				case BLACK_ARMOR:
					return MiniGameType.SURVIVAL_GAMES;
				case BLUE_ARMOR:
					return MiniGameType.SURVIVAL_GAMES;
				case BOMBER_KIT:
					return MiniGameType.SURVIVAL_GAMES;
				case CYAN_ARMOR:
					return MiniGameType.SURVIVAL_GAMES;
				case DARK_BLUE_ARMOR:
					return MiniGameType.SURVIVAL_GAMES;
				case DOUBLE_LOOT:
					return MiniGameType.SURVIVAL_GAMES;
				case DOUBLE_LOOT_PLAYER:
					return MiniGameType.SURVIVAL_GAMES;
				case ENABLE_POTIONS:
					return MiniGameType.SURVIVAL_GAMES;
				case ENABLE_POTIONS_PLAYER:
					return MiniGameType.SURVIVAL_GAMES;
				case FIGHTER_KIT:
					return MiniGameType.SURVIVAL_GAMES;
				case GRAY_ARMOR:
					return MiniGameType.SURVIVAL_GAMES;
				case GREEN_ARMOR:
					return MiniGameType.SURVIVAL_GAMES;
				case LIGHT_BLUE_ARMOR:
					return MiniGameType.SURVIVAL_GAMES;
				case LIGHT_GREEN_ARMOR:
					return MiniGameType.SURVIVAL_GAMES;
				case ORANGE_ARMOR:
					return MiniGameType.SURVIVAL_GAMES;
				case PINK_ARMOR:
					return MiniGameType.SURVIVAL_GAMES;
				case PURPLE_ARMOR:
					return MiniGameType.SURVIVAL_GAMES;
				case RED_ARMOR:
					return MiniGameType.SURVIVAL_GAMES;
				case RUNNER_KIT:
					return MiniGameType.SURVIVAL_GAMES;
				case WARRIOR_KIT:
					return MiniGameType.SURVIVAL_GAMES;
				case WHITE_ARMOR:
					return MiniGameType.SURVIVAL_GAMES;
				case YELLOW_ARMOR:
					return MiniGameType.SURVIVAL_GAMES;
			}
			return null;
		}
		
		public Rarity getRarity(){
			switch(this){
				case ARCHER_KIT:
					return Rarity.RARE;
				case BLACK_ARMOR:
					return Rarity.LEGENDARY;
				case BLUE_ARMOR:
					return Rarity.RARE;
				case BOMBER_KIT:
					return Rarity.LEGENDARY;
				case CYAN_ARMOR:
					return Rarity.COMMON;
				case DARK_BLUE_ARMOR:
					return Rarity.COMMON;
				case DOUBLE_LOOT:
					return Rarity.RARE;
				case DOUBLE_LOOT_PLAYER:
					return Rarity.LEGENDARY;
				case ENABLE_POTIONS:
					return Rarity.RARE;
				case ENABLE_POTIONS_PLAYER:
					return Rarity.LEGENDARY;
				case FIGHTER_KIT:
					return Rarity.RARE;
				case GRAY_ARMOR:
					return Rarity.COMMON;
				case GREEN_ARMOR:
					return Rarity.COMMON;
				case LIGHT_BLUE_ARMOR:
					return Rarity.RARE;
				case LIGHT_GREEN_ARMOR:
					return Rarity.RARE;
				case ORANGE_ARMOR:
					return Rarity.COMMON;
				case PINK_ARMOR:
					return Rarity.RARE;
				case PURPLE_ARMOR:
					return Rarity.COMMON;
				case RED_ARMOR:
					return Rarity.LEGENDARY;
				case RUNNER_KIT:
					return Rarity.COMMON;
				case WARRIOR_KIT:
					return Rarity.COMMON;
				case WHITE_ARMOR:
					return Rarity.COMMON;
				case YELLOW_ARMOR:
					return Rarity.RARE;
			}
			return null;
		}
		
		public ItemStack getItemStack(){
			switch(this){
				case ARCHER_KIT:
					return new ItemStack(Material.BOW);
				case BLACK_ARMOR:
					return Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.BLACK);
				case BLUE_ARMOR:
					return Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.BLUE);
				case BOMBER_KIT:
					return new ItemStack(Material.TNT);
				case CYAN_ARMOR:
					return Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.TEAL);
				case DARK_BLUE_ARMOR:
					return Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.NAVY);
				case DOUBLE_LOOT:
					return new ItemStack(Material.CHEST);
				case DOUBLE_LOOT_PLAYER:
					return new ItemStack(Material.CHEST);
				case ENABLE_POTIONS:
					return new ItemStack(Material.POTION);
				case ENABLE_POTIONS_PLAYER:
					return new ItemStack(Material.POTION);
				case FIGHTER_KIT:
					return new ItemStack(Material.STONE_AXE);
				case GRAY_ARMOR:
					return Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.GRAY);
				case GREEN_ARMOR:
					return Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.GREEN);
				case LIGHT_BLUE_ARMOR:
					return Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.AQUA);
				case LIGHT_GREEN_ARMOR:
					return Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.LIME);
				case ORANGE_ARMOR:
					return Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.ORANGE);
				case PINK_ARMOR:
					return Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.FUCHSIA);
				case PURPLE_ARMOR:
					return Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.PURPLE);
				case RED_ARMOR:
					return Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.RED);
				case RUNNER_KIT:
					return new ItemStack(Material.LEATHER_BOOTS);
				case WARRIOR_KIT:
					return new ItemStack(Material.IRON_CHESTPLATE);
				case WHITE_ARMOR:
					return Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.WHITE);
				case YELLOW_ARMOR:
					return Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.YELLOW);
			}
			return null;
		}
		
		public int getSlot(){
			switch(this){
				case ARCHER_KIT:
					return 13;
				case BLACK_ARMOR:
					return 13;
				case BLUE_ARMOR:
					return 21;
				case BOMBER_KIT:
					return 15;
				case CYAN_ARMOR:
					return 20;
				case DARK_BLUE_ARMOR:
					return 23;
				case DOUBLE_LOOT:
					return 33;
				case DOUBLE_LOOT_PLAYER:
					return 32;
				case ENABLE_POTIONS:
					return 29;
				case ENABLE_POTIONS_PLAYER:
					return 30;
				case FIGHTER_KIT:
					return 21;
				case GRAY_ARMOR:
					return 11;
				case GREEN_ARMOR:
					return 14;
				case LIGHT_BLUE_ARMOR:
					return 16;
				case LIGHT_GREEN_ARMOR:
					return 25;
				case ORANGE_ARMOR:
					return 19;
				case PINK_ARMOR:
					return 10;
				case PURPLE_ARMOR:
					return 24;
				case RED_ARMOR:
					return 22;
				case RUNNER_KIT:
					return 11;
				case WARRIOR_KIT:
					return 23;
				case WHITE_ARMOR:
					return 12;
				case YELLOW_ARMOR:
					return 15;
			}
			return 0;
		}
		
		public List<String> getDescription(){
			switch(this){
				case ARCHER_KIT:
					return Arrays.asList("Start with 1 Bow and 2 Arrows.", "One time use.");
				case BLACK_ARMOR:
					return Arrays.asList("Your Leather Armor will be Black!", "Permanent");
				case BLUE_ARMOR:
					return Arrays.asList("Your Leather Armor will be Blue!", "Permanent");
				case BOMBER_KIT:
					return Arrays.asList("Start with 2 TNT.", "One time use.");
				case CYAN_ARMOR:
					return Arrays.asList("Your Leather Armor will be Cyan!", "Permanent");
				case DARK_BLUE_ARMOR:
					return Arrays.asList("Your Leather Armor will be Dark Blue!", "Permanent");
				case DOUBLE_LOOT:
					return Arrays.asList("All players will find double loot in Chests!", "One time use.");
				case DOUBLE_LOOT_PLAYER:
					return Arrays.asList("You will find double loot in Chests!", "One time use.");
				case ENABLE_POTIONS:
					return Arrays.asList("All players can find potions in Chests!", "One time use.");
				case ENABLE_POTIONS_PLAYER:
					return Arrays.asList("You can find potions in Chests!", "One time use.");
				case FIGHTER_KIT:
					return Arrays.asList("Start with a Stone or Wooden weapon.", "One time use.");
				case GRAY_ARMOR:
					return Arrays.asList("Your Leather Armor will be Gray!", "Permanent");
				case GREEN_ARMOR:
					return Arrays.asList("Your Leather Armor will be Green!", "Permanent");
				case LIGHT_BLUE_ARMOR:
					return Arrays.asList("Your Leather Armor will be Light Blue!", "Permanent");
				case LIGHT_GREEN_ARMOR:
					return Arrays.asList("Your Leather Armor will be Light Green!", "Permanent");
				case ORANGE_ARMOR:
					return Arrays.asList("Your Leather Armor will be Orange!", "Permanent");
				case PINK_ARMOR:
					return Arrays.asList("Your Leather Armor will be Pink!", "Permanent");
				case PURPLE_ARMOR:
					return Arrays.asList("Your Leather Armor will be Purple!", "Permanent");
				case RED_ARMOR:
					return Arrays.asList("Your Leather Armor will be Red!", "Permanent");
				case RUNNER_KIT:
					return Arrays.asList("You can't deal/take damage in the first 30s.", "One time use.");
				case WARRIOR_KIT:
					return Arrays.asList("Start with two armor items.", "One time use.");
				case WHITE_ARMOR:
					return Arrays.asList("Your Leather Armor will be White!", "Permanent");
				case YELLOW_ARMOR:
					return Arrays.asList("Your Leather Armor will be Yellow!", "Permanent");
			}
			return new ArrayList<String>();
		}
		
		public int getMaxAmount(){
			switch(this){
				case ARCHER_KIT:
					return 3;
				case BOMBER_KIT:
					return 3;
				case FIGHTER_KIT:
					return 3;
				case RUNNER_KIT:
					return 3;
				case WARRIOR_KIT:
					return 3;
				default:
					return 1;
			}
		}
		
		public static Ticket getTicket(ItemStack item){
			TicketType type = null;
			for(TicketType tickettype : TicketType.values()){
				if(item.getItemMeta().getDisplayName().endsWith(tickettype.getNameWithGame())){
					type = tickettype;
				}
			}
			
			if(type.getMaxAmount() != 1){
				return new Ticket(type, Integer.parseInt(item.getItemMeta().getDisplayName().split(" ")[0].substring(2).replace("x", "")));
			}
			return new Ticket(type, 1);
		}
		
		public static ItemStack getRandom(){
			List<Ticket> tickets = new ArrayList<Ticket>();
			for(TicketType type : TicketType.values()){
				for(int i = 0; i < type.getRarity().getAmount(); i++){
					tickets.add(new Ticket(type, new Random().nextInt(type.getMaxAmount()) +1));
				}
			}
			
			Ticket ticket = tickets.get(new Random().nextInt(tickets.size()));
			TicketType type = ticket.getType();
			
			ItemStack item = type.getItemStack();
			ItemMeta meta = item.getItemMeta();
			if(type.getMaxAmount() != 1){
				meta.setDisplayName(type.getRarity().getColor() + ticket.getAmount() + "x " + type.getNameWithGame());
			}
			else{
				meta.setDisplayName(type.getRarity().getColor() + type.getNameWithGame());
			}
			item.setItemMeta(meta);
			
			return item;
		}
		
		public static TicketType fromID(int ticketid){
			for(TicketType type : TicketType.values()){
				if(type.ordinal() == ticketid){
					return type;
				}
			}
			return null;
		}
	}
}
