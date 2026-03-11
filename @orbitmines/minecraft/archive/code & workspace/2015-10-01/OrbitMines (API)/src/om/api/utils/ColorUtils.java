package om.api.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Sheep;

public class ColorUtils {
	
	private static List<Color> values = Arrays.asList(Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW);;
	private static List<Color> wardrobe = Arrays.asList(Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.NAVY, Color.ORANGE, Color.PURPLE, Color.RED, Color.TEAL, Color.WHITE, Color.YELLOW);
	
	public static List<Color> values(){
		return values;
	}
	
	public static List<Color> getWardrobe(){
		return wardrobe;
	}
	
	public static String getColor(Color color){
		if(color == Color.AQUA){ return "§b";}
		else if(color == Color.BLACK){ return "§0";}
		else if(color == Color.BLUE){ return "§9";}
		else if(color == Color.FUCHSIA){ return "§d";}
		else if(color == Color.GRAY){ return "§8";}
		else if(color == Color.GREEN){ return "§2";}
		else if(color == Color.LIME){ return "§a";}
		else if(color == Color.MAROON){ return "§4";}
		else if(color == Color.NAVY){ return "§1";}
		else if(color == Color.ORANGE){ return "§6";}
		else if(color == Color.PURPLE){ return "§5";}
		else if(color == Color.RED){ return "§c";}
		else if(color == Color.SILVER){ return "§7";}
		else if(color == Color.TEAL){ return "§3";}
		else if(color == Color.WHITE){ return "§f";}
		else if(color == Color.YELLOW){ return "§e";}
		else{ return "";}
	}
	
	public static String getName(Color color){
		if(color == Color.AQUA){ return "§bLight Blue";}
		else if(color == Color.BLACK){ return "§0Black";}
		else if(color == Color.BLUE){ return "§9Blue";}
		else if(color == Color.FUCHSIA){ return "§dPink";}
		else if(color == Color.GRAY){ return "§8Dark Gray";}
		else if(color == Color.GREEN){ return "§2Green";}
		else if(color == Color.LIME){ return "§aLight Green";}
		else if(color == Color.MAROON){ return "§4Dark Red";}
		else if(color == Color.NAVY){ return "§1Dark Blue";}
		else if(color == Color.ORANGE){ return "§6Orange";}
		else if(color == Color.PURPLE){ return "§5Purple";}
		else if(color == Color.RED){ return "§cRed";}
		else if(color == Color.SILVER){ return "§7Gray";}
		else if(color == Color.TEAL){ return "§3Cyan";}
		else if(color == Color.WHITE){ return "§fWhite";}
		else if(color == Color.YELLOW){ return "§eYellow";}
		else{ return "";}
	}
	
	public static String getDatabaseName(Color color){
		if(color == Color.AQUA){ return "LightBlue";}
		else if(color == Color.BLACK){ return "Black";}
		else if(color == Color.BLUE){ return "Blue";}
		else if(color == Color.FUCHSIA){ return "Pink";}
		else if(color == Color.GRAY){ return "Gray";}
		else if(color == Color.GREEN){ return "Green";}
		else if(color == Color.LIME){ return "LightGreen";}
		else if(color == Color.MAROON){ return "DarkRed";}
		else if(color == Color.NAVY){ return "DarkBlue";}
		else if(color == Color.ORANGE){ return "Orange";}
		else if(color == Color.PURPLE){ return "Purple";}
		else if(color == Color.RED){ return "Red";}
		else if(color == Color.SILVER){ return "LightGray";}
		else if(color == Color.TEAL){ return "Cyan";}
		else if(color == Color.WHITE){ return "White";}
		else if(color == Color.YELLOW){ return "Yellow";}
		else{ return "";}
	}
	
	public static short getDurability(Color color){
		if(color == Color.AQUA){ return 12;}
		else if(color == Color.BLACK){ return 0;}
		else if(color == Color.BLUE){ return 4;}
		else if(color == Color.FUCHSIA){ return 9;}
		else if(color == Color.GRAY){ return 8;}
		else if(color == Color.GREEN){ return 2;}
		else if(color == Color.LIME){ return 10;}
		else if(color == Color.MAROON){ return 0;} //REDSTONE
		else if(color == Color.NAVY){ return 12;}
		else if(color == Color.ORANGE){ return 14;}
		else if(color == Color.PURPLE){ return 5;}
		else if(color == Color.RED){ return 1;}
		else if(color == Color.SILVER){ return 7;}
		else if(color == Color.TEAL){ return 6;}
		else if(color == Color.WHITE){ return 15;}
		else if(color == Color.YELLOW){ return 11;}
		else{ return 0;}
	}
	
	public static Color random(List<Color> colors){
		return colors.get(new Random().nextInt(colors.size()));
	}
	
	public static Color parse(String color){
		if(color.equals("AQUA")){ return Color.AQUA;}
		else if(color.equals("BLACK")){ return Color.BLACK;}
		else if(color.equals("BLUE")){ return Color.BLUE;}
		else if(color.equals("FUCHSIA")){ return Color.FUCHSIA;}
		else if(color.equals("GRAY")){ return Color.GRAY;}
		else if(color.equals("GREEN")){ return Color.GREEN;}
		else if(color.equals("LIME")){ return Color.LIME;}
		else if(color.equals("MAROON")){ return Color.MAROON;}
		else if(color.equals("NAVY")){ return Color.NAVY;}
		else if(color.equals("OLIVE")){ return Color.OLIVE;}
		else if(color.equals("ORANGE")){ return Color.ORANGE;}
		else if(color.equals("PURPLE")){ return Color.PURPLE;}
		else if(color.equals("RED")){ return Color.RED;}
		else if(color.equals("SILVER")){ return Color.SILVER;}
		else if(color.equals("TEAL")){ return Color.TEAL;}
		else if(color.equals("WHITE")){ return Color.WHITE;}
		else if(color.equals("YELLOW")){ return Color.YELLOW;}
		else{ return null;}
	}
	
	public static String parse(Color color){
		if(color == Color.AQUA){ return "AQUA";}
		else if(color == Color.BLACK){ return "BLACK";}
		else if(color == Color.BLUE){ return "BLUE";}
		else if(color == Color.FUCHSIA){ return "FUCHSIA";}
		else if(color == Color.GRAY){ return "GRAY";}
		else if(color == Color.GREEN){ return "GREEN";}
		else if(color == Color.LIME){ return "LIME";}
		else if(color == Color.MAROON){ return "MAROON";}
		else if(color == Color.NAVY){ return "NAVY";}
		else if(color == Color.OLIVE){ return "OLIVE";}
		else if(color == Color.ORANGE){ return "ORANGE";}
		else if(color == Color.PURPLE){ return "PURPLE";}
		else if(color == Color.RED){ return "RED";}
		else if(color == Color.SILVER){ return "SILVER";}
		else if(color == Color.TEAL){ return "TEAL";}
		else if(color == Color.WHITE){ return "WHITE";}
		else if(color == Color.YELLOW){ return "YELLOW";}
		else{ return null;}
	}
	
	
	@SuppressWarnings("deprecation")
	public static DyeColor getNext(Sheep s){
		int DyeInt = s.getColor().getDyeData();
		
		if(DyeInt == 15){
			DyeInt = 0;
		}
		else{
			DyeInt = DyeInt +1;
		}
		
		return DyeColor.getByDyeData((byte) DyeInt);
	}
	
	public static Material getMaterial(Type type){
		switch(type){
			case BALL:
				return Material.FIREWORK_CHARGE;
			case BALL_LARGE:
				return Material.FIREBALL;
			case BURST:
				return Material.TNT;
			case CREEPER:
				return Material.SKULL_ITEM;
			case STAR:
				return Material.NETHER_STAR;
			default:
				return Material.FIREWORK_CHARGE;
		}
	}
	
	public static short getDurability(Type type){
		switch(type){
			case CREEPER:
				return 4;
			default:
				return 0;
		}
	}
	
	public static String getName(Type type){
		switch(type){
			case BALL:
				return "§e§lSmall";
			case BALL_LARGE:
				return "§6§lLarge";
			case BURST:
				return "§c§lSpecial";
			case CREEPER:
				return "§a§lCreeper";
			case STAR:
				return "§f§lStar";
			default:
				return "§e§lSmall";
		}
	}
	
	public static String getName(DyeColor dyecolor){
		switch(dyecolor){
			case BLACK:
				return "§0§lBlack";
			case BLUE:
				return "§1§lBlue";
			case BROWN:
				return "§f§lBrown";
			case CYAN:
				return "§3§lCyan";
			case GRAY:
				return "§8§lGray";
			case GREEN:
				return "§2§lGreen";
			case LIGHT_BLUE:
				return "§b§lLight Blue";
			case LIME:
				return "§a§lLight Green";
			case MAGENTA:
				return "§d§lMagenta";
			case ORANGE:
				return "§6§lOrange";
			case PINK:
				return "§d§lPink";
			case PURPLE:
				return "§5§lPurple";
			case RED:
				return "§c§lRed";
			case SILVER:
				return "§7§lLight Gray";
			case WHITE:
				return "§f§lWhite";
			case YELLOW:
				return "§e§lYellow";
			default:
				return null;
		}
	}
}
