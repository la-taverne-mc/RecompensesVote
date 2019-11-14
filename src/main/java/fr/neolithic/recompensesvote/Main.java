package fr.neolithic.recompensesvote;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import fr.neolithic.recompensesvote.listeners.ArmorListener;
import fr.neolithic.recompensesvote.listeners.Listeners;

public class Main extends JavaPlugin {
	public static HashMap<UUID, Boolean> wearingBoots = new HashMap<UUID, Boolean>();
	public static HashMap<UUID, Integer> effectTime = new HashMap<UUID, Integer>();
	public static HashMap<UUID, String> effect = new HashMap<UUID, String>();
	
	@Override
	public void onEnable() {
		registerRecipes();
		
		getServer().getPluginManager().registerEvents(new ArmorListener(this), this);
		getServer().getPluginManager().registerEvents(new Listeners(this), this);

		Commands commandExecutor = new Commands();
		getCommand("selectvotereward").setExecutor(commandExecutor);
		getCommand("item").setExecutor(commandExecutor);
		
		System.out.println("RecompensesVote has been successfully enabled");
	}

	@Override
	public void onDisable() {
		System.out.println("RecompensesVote has been successfully disabled");
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
