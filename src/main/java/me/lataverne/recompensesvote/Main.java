package me.lataverne.recompensesvote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import com.google.common.collect.Lists;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.lataverne.recompensesvote.listeners.ArmorListener;
import me.lataverne.recompensesvote.listeners.BaseballBatHitListener;
import me.lataverne.recompensesvote.listeners.BottleFillListener;
import me.lataverne.recompensesvote.listeners.CustomFoodListener;
import me.lataverne.recompensesvote.listeners.GoblinPickaxeListener;
import me.lataverne.recompensesvote.listeners.MiscellaneousListeners;
import me.lataverne.recompensesvote.listeners.PotionConsumeListener;
import me.lataverne.recompensesvote.tasks.BootsTask;
import me.lataverne.recompensesvote.tasks.CreeperEffect;
import me.lataverne.recompensesvote.tasks.FlyEffect;
import me.lataverne.recompensesvote.tasks.PhantomEffect;
import me.lataverne.recompensesvote.utils.FileManager;

public class Main extends JavaPlugin {
	public static HashMap<UUID, String> effect = new HashMap<UUID, String>();
	public static HashMap<UUID, Integer> effectTime = new HashMap<UUID, Integer>();
	
	public static List<UUID> wearingBoots = Lists.newArrayList();
	public static HashMap<String, Object> runningTasks = new HashMap<String, Object>();

	public static HashMap<Player, Integer> fillingBottle = new HashMap<Player, Integer>();

	private FileManager saveFile;
	private FileConfiguration saveContent;
	
	@Override
	public void onEnable() {
		registerListeners();

		Commands commandExecutor = new Commands();
		getCommand("selectvotereward").setExecutor(commandExecutor);
		getCommand("item").setExecutor(commandExecutor);

		saveFile = new FileManager(this, "save.yml");
		saveContent = saveFile.getContent();

		ConfigurationSection effectSection = saveContent.getConfigurationSection("effect");
		if (effectSection != null) {
			for (Map.Entry<String, Object> entry : effectSection.getValues(false).entrySet()) {
				effect.put(UUID.fromString(entry.getKey()), (String) entry.getValue());
			}
		}

		ConfigurationSection effectTimeSection = saveContent.getConfigurationSection("effectTime");
		if (effectTimeSection != null) {
			for (Map.Entry<String, Object> entry : effectTimeSection.getValues(false).entrySet()) {
				effectTime.put(UUID.fromString(entry.getKey()), (Integer) entry.getValue());
			}
		}

		for (String entry : saveContent.getStringList("wearingBoots")) {
			wearingBoots.add(UUID.fromString(entry));
		}

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (effect.get(player.getUniqueId()) != null) {
				switch (effect.get(player.getUniqueId())) {
					case "fly":
						new FlyEffect(player, effectTime.get(player.getUniqueId())).runTaskTimer(this, 0, 20);
						break;

					case "phantom":
						new PhantomEffect(player, effectTime.get(player.getUniqueId())).runTaskTimer(this, 0, 20);
						break;
				}
			}

			if (wearingBoots.contains(player.getUniqueId())) {
				new BootsTask(player).runTaskTimer(this, 1, 20);
			}
		}
		
		getLogger().log(Level.INFO, "RecompensesVote has been successfully enabled");
	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new ArmorListener(), this);
		getServer().getPluginManager().registerEvents(new BaseballBatHitListener(), this);
		getServer().getPluginManager().registerEvents(new BottleFillListener(), this);
		getServer().getPluginManager().registerEvents(new CustomFoodListener(), this);
		getServer().getPluginManager().registerEvents(new GoblinPickaxeListener(), this);
		getServer().getPluginManager().registerEvents(new MiscellaneousListeners(), this);
		getServer().getPluginManager().registerEvents(new PotionConsumeListener(), this);
	}

	@Override
	public void onDisable() {
		for (Object task : runningTasks.values()) {
			String className = task.getClass().getSimpleName();

			if (className.equals("BootsTask")) {
				((BootsTask) task).stop();
			}
			else if (className.equals("CreeperEffect")) {
				((CreeperEffect) task).stop();
			} 
			else if (className.equals("FlyEffect")) {
				((FlyEffect) task).stop();
			} 
			else if (className.equals("PhantomEffect")) {
				((PhantomEffect) task).stop();
			}
		}

		runningTasks.clear();

		HashMap<String, String> effectToString = new HashMap<String, String>();
		HashMap<String, Integer> effectTimeToString = new HashMap<String, Integer>();
		List<String> wearingBootsToString = Lists.newArrayList();
		
		for (Map.Entry<UUID, String> entry : effect.entrySet()) {
			effectToString.put(entry.getKey().toString(), entry.getValue());
		}

		for (Map.Entry<UUID, Integer> entry : effectTime.entrySet()) {
			effectTimeToString.put(entry.getKey().toString(), entry.getValue());
		}

		for (UUID uuid : wearingBoots) {
			wearingBootsToString.add(uuid.toString());
		}

		saveContent.createSection("effect", effectToString);
		saveContent.createSection("effectTime", effectTimeToString);
		saveContent.set("wearingBoots", wearingBootsToString);

		saveFile.save();

		getLogger().log(Level.INFO, "RecompensesVote has been successfully disabled");
	}
}
