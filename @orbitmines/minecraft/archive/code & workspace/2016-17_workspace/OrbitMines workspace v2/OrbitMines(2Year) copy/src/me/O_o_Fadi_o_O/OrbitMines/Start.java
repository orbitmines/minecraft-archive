package me.O_o_Fadi_o_O.OrbitMines;

import me.O_o_Fadi_o_O.OrbitMines.NMS.*;
import me.O_o_Fadi_o_O.OrbitMines.events.*;
import me.O_o_Fadi_o_O.OrbitMines.passes.DBPassword;
import me.O_o_Fadi_o_O.OrbitMines.removed.DespawnEvent;
import me.O_o_Fadi_o_O.OrbitMines.removed.PlayerInteract;
import me.O_o_Fadi_o_O.OrbitMines.removed.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.ServerSelectorInv;
import me.O_o_Fadi_o_O.OrbitMines.removed.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import org.bukkit.plugin.java.JavaPlugin;

import static sun.net.www.protocol.http.AuthCacheValue.Type.Server;

public class Start extends JavaPlugin {
	
	private void registerAllEvents(){
		getServer().getPluginManager().registerEvents(new BreakEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new ClickEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new CommandPreprocessEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new DamageEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new EntityDamage(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new EntityDeath(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new EntityInteractEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new InteractAtEntityEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new PlaceEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new PlayerChat(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new PlayerInteract(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new PlayerMove(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new SignEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new VoteEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new WorldChangeEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		
		if(ServerData.isServer(Server.HUB, Server.KITPVP, Server.CREATIVE, Server.MINIGAMES)){
			getServer().getPluginManager().registerEvents(new BlockChangeEvent(), this);//Hub, KitPvP, Creative, MiniGames
			getServer().getPluginManager().registerEvents(new DropEvent(), this);//Hub, KitPvP, Creative, MiniGames
			getServer().getPluginManager().registerEvents(new ExplodeEvent(), this);//Hub, KitPvP, Creative, MiniGames
			getServer().getPluginManager().registerEvents(new FoodEvent(), this);//Hub, KitPvP, Creative, MiniGames
			getServer().getPluginManager().registerEvents(new PickupEvent(), this);//Hub, KitPvP, Creative, MiniGames
			getServer().getPluginManager().registerEvents(new ProjectileHit(), this);//Hub, KitPvP, Creative, MiniGames
		}
		
		if(ServerData.isServer(Server.HUB, Server.MINIGAMES)){
			getServer().getPluginManager().registerEvents(new AnimationEvent(), this);//Hub, MiniGames
			getServer().getPluginManager().registerEvents(new DespawnEvent(), this);//Hub, MiniGames
		}
		if(ServerData.isServer(Server.KITPVP)){
			getServer().getPluginManager().registerEvents(new ExpChangeEvent(), this);//KitPvP
			getServer().getPluginManager().registerEvents(new FadeEvent(), this);//KitPvP
			getServer().getPluginManager().registerEvents(new ShootBowEvent(), this);//KitPvP
			getServer().getPluginManager().registerEvents(new TeleportEvent(), this);//KitPvP
		}
		if(ServerData.isServer(Server.HUB, Server.CREATIVE, Server.MINIGAMES)){
			getServer().getPluginManager().registerEvents(new BlockFormEvent(), this);//Hub, Creative, MiniGames
		}
		if(ServerData.isServer(Server.PRISON, Server.MINIGAMES)){
			getServer().getPluginManager().registerEvents(new ChunkEvent(), this);//KitPvP
		}
		if(ServerData.isServer(Server.MINIGAMES)){
			getServer().getPluginManager().registerEvents(new InvEvent(), this);//MiniGames
			getServer().getPluginManager().registerEvents(new ToggleFlightEvent(), this);//MiniGames
		}
		if(ServerData.isServer(Server.CREATIVE)){
			getServer().getPluginManager().registerEvents(new SpawnEvent(), this);//Creative
		}
	}
}
