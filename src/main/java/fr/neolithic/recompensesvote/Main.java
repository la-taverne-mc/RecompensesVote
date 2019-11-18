package fr.neolithic.recompensesvote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import com.google.common.collect.Lists;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import fr.neolithic.recompensesvote.listeners.ArmorListener;
import fr.neolithic.recompensesvote.listeners.Listeners;
import fr.neolithic.recompensesvote.tasks.BootsTask;
import fr.neolithic.recompensesvote.tasks.CreeperEffect;
import fr.neolithic.recompensesvote.tasks.FlyEffect;
import fr.neolithic.recompensesvote.tasks.PhantomEffect;

public class Main extends JavaPlugin {
	public static HashMap<UUID, String> effect = new HashMap<UUID, String>();
	public static HashMap<UUID, Integer> effectTime = new HashMap<UUID, Integer>();
	
	public static List<UUID> wearingBoots = Lists.newArrayList();
	public static List<Object> runningTasks = Lists.newArrayList();

	private FileManager saveFile;
	private FileConfiguration saveContent;
	
	@Override
	public void onEnable() {
		registerRecipes();
		
		getServer().getPluginManager().registerEvents(new ArmorListener(this), this);
		getServer().getPluginManager().registerEvents(new Listeners(this), this);

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

	@Override
	public void onDisable() {
		for (Object task : runningTasks) {
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

	@SuppressWarnings("deprecation")
	private void registerRecipes() {
		getServer().addRecipe(new FurnaceRecipe(new NamespacedKey(this, "cooked_horse"), Items.COOKED_HORSE.getItem(), new RecipeChoice.ExactChoice(Items.RAW_HORSE.getItem()), (float) 0.35, 200));
		getServer().addRecipe(new FurnaceRecipe(new NamespacedKey(this, "cooked_bear"), Items.COOKED_BEAR.getItem(), new RecipeChoice.ExactChoice(Items.RAW_BEAR.getItem()), (float) 0.35, 200));
		getServer().addRecipe(new SmokingRecipe(new NamespacedKey(this, "cooked_horse_from_smoking"), Items.COOKED_HORSE.getItem(), new RecipeChoice.ExactChoice(Items.RAW_HORSE.getItem()), (float) 0.35, 100));
		getServer().addRecipe(new SmokingRecipe(new NamespacedKey(this, "cooked_bear_from_smoking"), Items.COOKED_BEAR.getItem(), new RecipeChoice.ExactChoice(Items.RAW_BEAR.getItem()), (float) 0.35, 100));
		getServer().addRecipe(new CampfireRecipe(new NamespacedKey(this, "cooked_horse_from_campfire_cooking"), Items.COOKED_HORSE.getItem(), new RecipeChoice.ExactChoice(Items.RAW_HORSE.getItem()), (float) 0.35, 600));
		getServer().addRecipe(new CampfireRecipe(new NamespacedKey(this, "cooked_bear_from_campfire_cooking"), Items.COOKED_BEAR.getItem(), new RecipeChoice.ExactChoice(Items.RAW_BEAR.getItem()), (float) 0.35, 600));
	}
}
