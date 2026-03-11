package fadidev.orbitmines.api.utils;

import fadidev.orbitmines.api.utils.enums.perks.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.entity.Sheep;

import java.util.Arrays;
import java.util.List;

public class ColorUtils {

	public static List<Color> VALUES = Arrays.asList(Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW, Color.ELYTRA);;
	public static List<Color> WARDROBE = Arrays.asList(Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.NAVY, Color.ORANGE, Color.PURPLE, Color.RED, Color.TEAL, Color.WHITE, Color.YELLOW, Color.ELYTRA);
	
	public static Color random(List<Color> colors){
		return colors.get(Utils.RANDOM.nextInt(colors.size()));
	}

	public static DyeColor getNext(Sheep s){
		int dye = s.getColor().getDyeData();
		
		if(dye == 15)
            dye = 0;
		else
            dye = dye +1;
		
		return DyeColor.getByDyeData((byte) dye);
	}

    public static String getName(org.bukkit.Color color){
        if(color == org.bukkit.Color.AQUA){ return "§bLight Blue";}
        else if(color == org.bukkit.Color.BLACK){ return "§0Black";}
        else if(color == org.bukkit.Color.BLUE){ return "§9Blue";}
        else if(color == org.bukkit.Color.FUCHSIA){ return "§dPink";}
        else if(color == org.bukkit.Color.GRAY){ return "§8Dark Gray";}
        else if(color == org.bukkit.Color.GREEN){ return "§2Green";}
        else if(color == org.bukkit.Color.LIME){ return "§aLight Green";}
        else if(color == org.bukkit.Color.MAROON){ return "§4Dark Red";}
        else if(color == org.bukkit.Color.NAVY){ return "§1Dark Blue";}
        else if(color == org.bukkit.Color.ORANGE){ return "§6Orange";}
        else if(color == org.bukkit.Color.PURPLE){ return "§5Purple";}
        else if(color == org.bukkit.Color.RED){ return "§cRed";}
        else if(color == org.bukkit.Color.SILVER){ return "§7Gray";}
        else if(color == org.bukkit.Color.TEAL){ return "§3Cyan";}
        else if(color == org.bukkit.Color.WHITE){ return "§fWhite";}
        else if(color == org.bukkit.Color.YELLOW){ return "§eYellow";}
        else{ return "";}
    }

    public static short getDurability(org.bukkit.Color color){
        if(color == org.bukkit.Color.AQUA){ return 12;}
        else if(color == org.bukkit.Color.BLACK){ return 0;}
        else if(color == org.bukkit.Color.BLUE){ return 4;}
        else if(color == org.bukkit.Color.FUCHSIA){ return 9;}
        else if(color == org.bukkit.Color.GRAY){ return 8;}
        else if(color == org.bukkit.Color.GREEN){ return 2;}
        else if(color == org.bukkit.Color.LIME){ return 10;}
        else if(color == org.bukkit.Color.MAROON){ return 0;} //REDSTONE
        else if(color == org.bukkit.Color.NAVY){ return 12;}
        else if(color == org.bukkit.Color.ORANGE){ return 14;}
        else if(color == org.bukkit.Color.PURPLE){ return 5;}
        else if(color == org.bukkit.Color.RED){ return 1;}
        else if(color == org.bukkit.Color.SILVER){ return 7;}
        else if(color == org.bukkit.Color.TEAL){ return 6;}
        else if(color == org.bukkit.Color.WHITE){ return 15;}
        else if(color == org.bukkit.Color.YELLOW){ return 11;}
        else{ return 0;}
    }
	
	public static org.bukkit.Color parse(String color){
		if(color.equals("AQUA")){ return org.bukkit.Color.AQUA;}
		else if(color.equals("BLACK")){ return org.bukkit.Color.BLACK;}
		else if(color.equals("BLUE")){ return org.bukkit.Color.BLUE;}
		else if(color.equals("FUCHSIA")){ return org.bukkit.Color.FUCHSIA;}
		else if(color.equals("GRAY")){ return org.bukkit.Color.GRAY;}
		else if(color.equals("GREEN")){ return org.bukkit.Color.GREEN;}
		else if(color.equals("LIME")){ return org.bukkit.Color.LIME;}
		else if(color.equals("MAROON")){ return org.bukkit.Color.MAROON;}
		else if(color.equals("NAVY")){ return org.bukkit.Color.NAVY;}
		else if(color.equals("OLIVE")){ return org.bukkit.Color.OLIVE;}
		else if(color.equals("ORANGE")){ return org.bukkit.Color.ORANGE;}
		else if(color.equals("PURPLE")){ return org.bukkit.Color.PURPLE;}
		else if(color.equals("RED")){ return org.bukkit.Color.RED;}
		else if(color.equals("SILVER")){ return org.bukkit.Color.SILVER;}
		else if(color.equals("TEAL")){ return org.bukkit.Color.TEAL;}
		else if(color.equals("WHITE")){ return org.bukkit.Color.WHITE;}
		else if(color.equals("YELLOW")){ return org.bukkit.Color.YELLOW;}
		else{ return null;}
	}

    public static String parse(org.bukkit.Color color){
        if(color == org.bukkit.Color.AQUA){ return "AQUA";}
        else if(color == org.bukkit.Color.BLACK){ return "BLACK";}
        else if(color == org.bukkit.Color.BLUE){ return "BLUE";}
        else if(color == org.bukkit.Color.FUCHSIA){ return "FUCHSIA";}
        else if(color == org.bukkit.Color.GRAY){ return "GRAY";}
        else if(color == org.bukkit.Color.GREEN){ return "GREEN";}
        else if(color == org.bukkit.Color.LIME){ return "LIME";}
        else if(color == org.bukkit.Color.MAROON){ return "MAROON";}
        else if(color == org.bukkit.Color.NAVY){ return "NAVY";}
        else if(color == org.bukkit.Color.OLIVE){ return "OLIVE";}
        else if(color == org.bukkit.Color.ORANGE){ return "ORANGE";}
        else if(color == org.bukkit.Color.PURPLE){ return "PURPLE";}
        else if(color == org.bukkit.Color.RED){ return "RED";}
        else if(color == org.bukkit.Color.SILVER){ return "SILVER";}
        else if(color == org.bukkit.Color.TEAL){ return "TEAL";}
        else if(color == org.bukkit.Color.WHITE){ return "WHITE";}
        else if(color == org.bukkit.Color.YELLOW){ return "YELLOW";}
        else{ return null;}
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
