package fadidev.orbitmines.api.runnables.orbitmines;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.Particle;
import fadidev.orbitmines.api.handlers.Podium;
import fadidev.orbitmines.api.runnables.OMRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

public class PodiumRunnable extends OMRunnable {

	private OrbitMinesAPI api;

	public PodiumRunnable() {
		super(TimeUnit.SECOND, 1);

        this.api = OrbitMinesAPI.getApi();
	}

	@Override
	public void run() {
        for(Podium podium : api.getPodia()){
            podium.update();
        }
	}
}
