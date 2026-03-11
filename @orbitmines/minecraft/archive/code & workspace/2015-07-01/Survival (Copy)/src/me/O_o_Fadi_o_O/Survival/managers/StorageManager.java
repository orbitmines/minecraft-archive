package me.O_o_Fadi_o_O.Survival.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.O_o_Fadi_o_O.Survival.utils.ChatColor;
import me.O_o_Fadi_o_O.Survival.utils.Disguise;
import me.O_o_Fadi_o_O.Survival.utils.Pet;
import me.O_o_Fadi_o_O.Survival.utils.Server;
import me.O_o_Fadi_o_O.Survival.utils.StaffRank;
import me.O_o_Fadi_o_O.Survival.utils.Trail;
import me.O_o_Fadi_o_O.Survival.utils.TrailType;
import me.O_o_Fadi_o_O.Survival.utils.VIPRank;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class StorageManager {
	
	/*
	 * Server
	 */
	
	public static HashMap<Integer, List<Block>> worldportals = new HashMap<Integer, List<Block>>();
	
	public static Location pvpspawn;

}
