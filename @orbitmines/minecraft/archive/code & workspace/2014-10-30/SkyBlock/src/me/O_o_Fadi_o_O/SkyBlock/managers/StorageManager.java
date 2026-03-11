package me.O_o_Fadi_o_O.SkyBlock.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.O_o_Fadi_o_O.SkyBlock.utils.IslandRank;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class StorageManager {

	public static int IslandsAmount = 0;
	public static List<String> Challenges = new ArrayList<String>();
	
	public static HashMap<Player, Integer> PlayersIslandInvite = new HashMap<Player, Integer>();
	
	public static HashMap<Player, Boolean> PlayerHasIsland = new HashMap<Player, Boolean>();
	public static HashMap<Player, Integer> PlayersIslandNumber = new HashMap<Player, Integer>();
	public static HashMap<Player, IslandRank> PlayersIslandRank = new HashMap<Player, IslandRank>();
	public static HashMap<Player, Location> PlayersIslandHomeLocation = new HashMap<Player, Location>();
	public static HashMap<Player, List<String>> PlayersChallengesCompleteAmount = new HashMap<Player, List<String>>();
	
	public static HashMap<Integer, Boolean> IslandTeleportEnabled = new HashMap<Integer, Boolean>();
	public static HashMap<Integer, String> IslandCreatedDate = new HashMap<Integer, String>();
	public static HashMap<Integer, Location> IslandLocation = new HashMap<Integer, Location>();
	public static HashMap<Integer, String> IslandOwner = new HashMap<Integer, String>();
	public static HashMap<Integer, List<String>> IslandMembers = new HashMap<Integer, List<String>>();
	
}
