package me.O_o_Fadi_o_O.SpigotSpleef.managers;

import java.util.Arrays;

public class SettingsManager {

	public static void loadSettings(){
		StorageManager.maxplayers = ConfigManager.config.getInt("Settings.MaxPlayers");
		StorageManager.minplayers = ConfigManager.config.getInt("Settings.MinPlayers");
		
		StorageManager.newplayerunlockedkits = Arrays.asList(ConfigManager.config.getString("Settings.NewPlayerUnlockedKits").replace(" ", "").split("\\|"));
		StorageManager.newplayertokens = ConfigManager.config.getInt("Settings.NewPlayerTokens");
		
		StorageManager.startingmessageat = Arrays.asList(ConfigManager.config.getString("Settings.StartingMessage").replace(" ", "").split("\\|"));
		StorageManager.endingmessageat = Arrays.asList(ConfigManager.config.getString("Settings.EndingMessage").replace(" ", "").split("\\|"));

		StorageManager.waittimeminutes = ConfigManager.config.getInt("Settings.WaitTimeMinutes");
		StorageManager.waittimeseconds = ConfigManager.config.getInt("Settings.WaitTimeSeconds");

		StorageManager.warmuptimeseconds = ConfigManager.config.getInt("Settings.WarmupTimeSeconds");

		StorageManager.maxgametimeminutes = ConfigManager.config.getInt("Settings.MaxGameTimeMinutes");
		StorageManager.maxgametimeseconds = ConfigManager.config.getInt("Settings.MaxGameTimeSeconds");

		StorageManager.endingtimeseconds = ConfigManager.config.getInt("Settings.EndingTimeSeconds");

		StorageManager.canpunch = ConfigManager.config.getBoolean("Settings.CanPunch");

		StorageManager.useperarenachat = ConfigManager.config.getBoolean("Settings.UsePerArenaChat");

		StorageManager.diewhenyis = ConfigManager.config.getInt("Settings.DieWhenYIs");
	}
}
