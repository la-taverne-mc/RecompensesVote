package fr.neolithic.recompensesvote;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.neolithic.recompensesvote.listeners.ArmorListener;
import fr.neolithic.recompensesvote.listeners.Listeners;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	public static HashMap<String, ItemStack> items = new HashMap<String, ItemStack>();
	public static HashMap<ItemStack, Float> votingRewards = new HashMap<ItemStack, Float>();
	
	public static HashMap<String, Integer> effectTime = new HashMap<String, Integer>();
	public static HashMap<String, String> effect = new HashMap<String, String>();
	
	@Override
	public void onEnable() {
		registerItems();
		registerVotingRewards();
		
		getServer().getPluginManager().registerEvents(new ArmorListener(this), this);
		getServer().getPluginManager().registerEvents(new Listeners(this), this);
		getCommand("test").setExecutor(new Commands());
		getCommand("selectvotereward").setExecutor(new Commands());
		getCommand("item").setExecutor(new Commands());
		
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Reload Complete !");
	}
	
	@SuppressWarnings("deprecation")
	private void registerItems() {
		ItemStack fly1min = new ItemStack(Material.POTION);
		ItemStack fly5min = new ItemStack(Material.POTION);
		ItemStack fly10min = new ItemStack(Material.POTION);
		ItemStack fly20min = new ItemStack(Material.POTION);
		ItemStack antiPhantom = new ItemStack(Material.POTION);
		ItemStack antiCreeper = new ItemStack(Material.POTION);
		ItemStack miningPotion = new ItemStack(Material.POTION);
		ItemStack swimingPotion = new ItemStack(Material.POTION);
		ItemStack inuitAxe = new ItemStack(Material.STONE_AXE);
		ItemStack indianAxe = new ItemStack(Material.STONE_AXE);
		ItemStack baseballBat = new ItemStack(Material.WOODEN_SWORD);
		ItemStack goblinPickaxe = new ItemStack(Material.GOLDEN_PICKAXE);
		ItemStack giantBoots = new ItemStack(Material.LEATHER_BOOTS);
		ItemStack cookedHorse = new ItemStack(Material.COOKED_BEEF);
		ItemStack cookedBear = new ItemStack(Material.COOKED_BEEF);
		ItemStack rawHorse = new ItemStack(Material.BEEF);
		ItemStack rawBear = new ItemStack(Material.BEEF);
		ItemStack legendaryDirt = new ItemStack(Material.DIRT);
		
		PotionMeta fly1min_meta = (PotionMeta) fly1min.getItemMeta();
		PotionMeta fly5min_meta = (PotionMeta) fly5min.getItemMeta();
		PotionMeta fly10min_meta = (PotionMeta) fly10min.getItemMeta();
		PotionMeta fly20min_meta = (PotionMeta) fly20min.getItemMeta();
		PotionMeta antiPhantom_meta = (PotionMeta) antiPhantom.getItemMeta();
		PotionMeta antiCreeper_meta = (PotionMeta) antiCreeper.getItemMeta();
		PotionMeta miningPotion_meta = (PotionMeta) miningPotion.getItemMeta();
		PotionMeta swimingPotion_meta = (PotionMeta) swimingPotion.getItemMeta();
		ItemMeta inuitAxe_meta = inuitAxe.getItemMeta();
		ItemMeta indianAxe_meta = indianAxe.getItemMeta();
		ItemMeta baseballBat_meta = baseballBat.getItemMeta();
		ItemMeta goblinPickaxe_meta = goblinPickaxe.getItemMeta();
		ItemMeta giantBoots_meta = giantBoots.getItemMeta();
		ItemMeta cookedHorse_meta = cookedHorse.getItemMeta();
		ItemMeta cookedBear_meta = cookedBear.getItemMeta();
		ItemMeta rawHorse_meta = rawHorse.getItemMeta();
		ItemMeta rawBear_meta = rawBear.getItemMeta();
		ItemMeta legendaryDirt_meta = legendaryDirt.getItemMeta();

		fly1min_meta.setColor(Color.fromRGB(255, 243, 122));
		fly1min_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		fly1min_meta.setDisplayName("§bPotion de Vol 1min");
		fly1min_meta.setLore(Arrays.asList("§eBoire cette potion", "§evous permet de voler", "§ependant §61min"));
		fly5min_meta.setColor(Color.fromRGB(255, 243, 122));
		fly5min_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		fly5min_meta.setDisplayName("§bPotion de Vol 5min");
		fly5min_meta.setLore(Arrays.asList("§eBoire cette potion", "§evous permet de voler", "§ependant §65min"));
		fly10min_meta.setColor(Color.fromRGB(255, 243, 122));
		fly10min_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		fly10min_meta.setDisplayName("§bPotion de Vol 10min");
		fly10min_meta.setLore(Arrays.asList("§eBoire cette potion", "§evous permet de voler", "§ependant §610min"));
		fly20min_meta.setColor(Color.fromRGB(255, 243, 122));
		fly20min_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		fly20min_meta.setDisplayName("§bPotion de Vol 20min");
		fly20min_meta.setLore(Arrays.asList("§eBoire cette potion", "§evous permet de voler", "§ependant §620min"));
		antiPhantom_meta.setColor(Color.fromRGB(0, 43, 112));
		antiPhantom_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		antiPhantom_meta.setDisplayName("§bPotion Anti Phantom");
		antiPhantom_meta.setLore(Arrays.asList("§eBoire cette potion vous", "§epermet d'§liminer les", "§ephantom autour de vous", "§ependant §620min"));
		antiCreeper_meta.setColor(Color.fromRGB(0, 135, 6));
		antiCreeper_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		antiCreeper_meta.setDisplayName("§bSang de Creeper");
		antiCreeper_meta.setLore(Arrays.asList("§eBoire cette potion vous", "§epermet de faire fuir les", "§ecreeper autour de vous", "§ependant §620min"));
		miningPotion_meta.setColor(Color.fromRGB(186, 34, 7));
		miningPotion_meta.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 12000, 2), true);
		miningPotion_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		miningPotion_meta.setDisplayName("§bFiole de Sang de Nain");
		miningPotion_meta.setLore(Arrays.asList("§eBoire cette fiole vous", "§epermettra de miner", "§eaussi vite qu'un nain"));
		swimingPotion_meta.setColor(Color.fromRGB(7, 162, 186));
		swimingPotion_meta.addCustomEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 12000, 1), true);
		swimingPotion_meta.addCustomEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 12000, 1), true);
		swimingPotion_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		swimingPotion_meta.setDisplayName("§bPotion d'Homme Poisson");
		swimingPotion_meta.setLore(Arrays.asList("§eBoire cette potion vous", "§epermettra de devenir", "§eaussi § l'aise sous l'eau", "§eque n'importe quel §tre", "§esous-marin"));
		inuitAxe_meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("1de4ca9e-fe66-4222-966d-73f226e8fecd"), "generic.attackDamage", 4, Operation.ADD_NUMBER, EquipmentSlot.HAND));
		inuitAxe_meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.fromString("c26bf64f-1a1a-4425-bc4e-77dc56845f8b"), "generic.attackSpeed", -0.65, Operation.MULTIPLY_SCALAR_1, EquipmentSlot.HAND));
		inuitAxe_meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("10cea9ed-2627-4a6e-b904-52228bbff57b"), "generic.attackDamage", 4, Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND));
		inuitAxe_meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.fromString("48c680ff-6d3a-4bff-b62c-2badd65cf045"), "generic.attackSpeed", -0.65, Operation.MULTIPLY_SCALAR_1, EquipmentSlot.OFF_HAND));
		inuitAxe_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		inuitAxe_meta.setDisplayName("§bHache Inuit");
		inuitAxe_meta.setLore(Arrays.asList("§ePermet d'obtenir du", "§esteak d'ours"));
		indianAxe_meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("b3fb96db-e490-4233-b618-292f965c974c"), "generic.attackDamage", 4, Operation.ADD_NUMBER, EquipmentSlot.HAND));
		indianAxe_meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.fromString("63590bf7-46ea-4ca4-b8c7-fc6c99942af3"), "generic.attackSpeed", -0.65, Operation.MULTIPLY_SCALAR_1, EquipmentSlot.HAND));
		indianAxe_meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("d0b9f488-95ac-4028-8706-762e5aadfb00"), "generic.attackDamage", 4, Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND));
		indianAxe_meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.fromString("fe3ba83f-76a6-44ba-9cfe-02fd12dc306c"), "generic.attackSpeed", -0.65, Operation.MULTIPLY_SCALAR_1, EquipmentSlot.OFF_HAND));
		indianAxe_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		indianAxe_meta.setDisplayName("§bHache Indienne");
		indianAxe_meta.setLore(Arrays.asList("§ePermet d'obtenir du", "§esteak de cheval"));
		baseballBat_meta.addEnchant(Enchantment.KNOCKBACK, 15, true);
		baseballBat_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		baseballBat_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		baseballBat_meta.setCustomModelData(1);
		baseballBat_meta.setDisplayName("§bBatte de Baseball");
		baseballBat_meta.setLore(Arrays.asList("§7Recul XV", "§c /!\\ Attention /!\\", "§c  Cet item est §", "§c  usage unique"));
		goblinPickaxe_meta.setDisplayName("§bPioche de Gobelin");
		goblinPickaxe_meta.setLore(Arrays.asList("§eCette pioche vous conf§re", "§eune pr§cision accrue et", "§evous permet d'extraire", "§edes min§raux pr§cieux", "§edes roches"));
		giantBoots_meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.fromString("ec3474c3-b57c-4fbf-ba1f-a7c5ac9292c5"), "generic.armor", 1, Operation.ADD_NUMBER, EquipmentSlot.FEET));
		giantBoots_meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("dcc66b53-a22c-48cc-afbd-274a02967392"), "generic.movementSpeed", 0.75, Operation.MULTIPLY_SCALAR_1, EquipmentSlot.FEET));
		giantBoots_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		giantBoots_meta.setDisplayName("§bBottes de G§ant");
		giantBoots_meta.setLore(Arrays.asList("§eCes bottes semblent §mettre", "§eune certaine forme de magie", "§eet vous permettraient", "§ed'aller plus vite", "", "§cAttention la durabilit§", "§cbaisse avec la distance", "§cparcourue"));
		cookedHorse_meta.setDisplayName("§fSteak de Cheval");
		cookedBear_meta.setDisplayName("§fSteak d'Ours");
		rawHorse_meta.setDisplayName("§fCheval Cru");
		rawBear_meta.setDisplayName("§fOurs Cru");
		legendaryDirt_meta.setDisplayName("§cTerre du Cocu");
		legendaryDirt_meta.setLore(Arrays.asList("§eD'apr§s les dires c'est", "§el'objet le plus rare de", "§etout le jeu"));
		
		fly1min.setItemMeta(fly1min_meta);
		fly5min.setItemMeta(fly5min_meta);
		fly10min.setItemMeta(fly10min_meta);
		fly20min.setItemMeta(fly20min_meta);
		antiPhantom.setItemMeta(antiPhantom_meta);
		antiCreeper.setItemMeta(antiCreeper_meta);
		miningPotion.setItemMeta(miningPotion_meta);
		swimingPotion.setItemMeta(swimingPotion_meta);
		inuitAxe.setItemMeta(inuitAxe_meta);
		indianAxe.setItemMeta(indianAxe_meta);
		baseballBat.setItemMeta(baseballBat_meta);
		goblinPickaxe.setItemMeta(goblinPickaxe_meta);
		giantBoots.setItemMeta(giantBoots_meta);
		cookedHorse.setItemMeta(cookedHorse_meta);
		cookedBear.setItemMeta(cookedBear_meta);
		rawHorse.setItemMeta(rawHorse_meta);
		rawBear.setItemMeta(rawBear_meta);
		legendaryDirt.setItemMeta(legendaryDirt_meta);
		
		items.put("fly1min", fly1min); // x
		items.put("fly5min", fly5min); // x
		items.put("fly10min", fly10min); // x
		items.put("fly20min", fly20min); // x
		items.put("antiPhantom", antiPhantom); // x
		items.put("antiCreeper", antiCreeper);
		items.put("miningPotion", miningPotion); // x
		items.put("swimingPotion", swimingPotion); // x
		items.put("inuitAxe", inuitAxe); // x
		items.put("indianAxe", indianAxe); // x
		items.put("baseballBat", baseballBat); // x
		items.put("goblinPickaxe", goblinPickaxe); // x
		items.put("giantBoots", giantBoots);
		items.put("cookedHorse", cookedHorse); // x
		items.put("cookedBear", cookedBear); // x
		items.put("rawHorse", rawHorse); // x
		items.put("rawBear", rawBear); // x
		items.put("legendaryDirt", legendaryDirt); // x
		
		getServer().addRecipe(new FurnaceRecipe(new NamespacedKey(this, "cooked_horse"), cookedHorse, new RecipeChoice.ExactChoice(rawHorse), (float) 0.35, 200));
		getServer().addRecipe(new FurnaceRecipe(new NamespacedKey(this, "cooked_bear"), cookedBear, new RecipeChoice.ExactChoice(rawBear), (float) 0.35, 200));
		getServer().addRecipe(new SmokingRecipe(new NamespacedKey(this, "cooked_horse_from_smoking"), cookedHorse, new RecipeChoice.ExactChoice(rawHorse), (float) 0.35, 100));
		getServer().addRecipe(new SmokingRecipe(new NamespacedKey(this, "cooked_bear_from_smoking"), cookedBear, new RecipeChoice.ExactChoice(rawBear), (float) 0.35, 100));
		getServer().addRecipe(new CampfireRecipe(new NamespacedKey(this, "cooked_horse_from_campfire_cooking"), cookedHorse, new RecipeChoice.ExactChoice(rawHorse), (float) 0.35, 600));
		getServer().addRecipe(new CampfireRecipe(new NamespacedKey(this, "cooked_bear_from_campfire_cooking"), cookedBear, new RecipeChoice.ExactChoice(rawBear), (float) 0.35, 600));
	}
	
	private void registerVotingRewards() {
		votingRewards.put(items.get("fly1min"), 0.22f);
		votingRewards.put(items.get("fly5min"), 0.15f);
		votingRewards.put(items.get("fly10min"), 0.10f);
		votingRewards.put(items.get("fly20min"), 0.05f);
		votingRewards.put(items.get("antiPhantom"), 0.17f);
		votingRewards.put(items.get("antiCreeper"), 0.16f);
		votingRewards.put(items.get("miningPotion"), 0.20f);
		votingRewards.put(items.get("swimingPotion"), 0.12f);
		votingRewards.put(items.get("inuitAxe"), 0.18f);
		votingRewards.put(items.get("indianAxe"), 0.21f);
		votingRewards.put(items.get("baseballBat"), 0.05f);
		votingRewards.put(items.get("goblinPickaxe"), 0.10f);
		votingRewards.put(items.get("giantBoots"), 0.12f);
		votingRewards.put(items.get("legendaryDirt"), 0.001f);
		votingRewards.put(new ItemStack(Material.TOTEM_OF_UNDYING, 1), 0.05f);
		votingRewards.put(new ItemStack(Material.SEA_LANTERN, 32), 0.05f);
		votingRewards.put(new ItemStack(Material.SHULKER_SHELL, 1), 0.25f);
		votingRewards.put(new ItemStack(Material.SHULKER_BOX, 1), 0.10f);
		votingRewards.put(new ItemStack(Material.BIRCH_LOG, 64), 0.08f);
		votingRewards.put(new ItemStack(Material.SPRUCE_LOG, 64), 0.08f);
		votingRewards.put(new ItemStack(Material.OAK_LOG, 64), 0.08f);
		votingRewards.put(new ItemStack(Material.ELYTRA, 1), 0.05f);
		votingRewards.put(new ItemStack(Material.NETHER_QUARTZ_ORE, 32), 0.20f);
		votingRewards.put(new ItemStack(Material.EXPERIENCE_BOTTLE, 32), 0.10f);
		ItemStack firework = new ItemStack(Material.FIREWORK_ROCKET, 64);
		FireworkMeta firework_meta = (FireworkMeta) firework.getItemMeta();
		firework_meta.setPower(3);
		firework.setItemMeta(firework_meta);
		votingRewards.put(firework, 0.10f);
		ItemStack silkTouchBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
		EnchantmentStorageMeta silkTouchBook_meta = (EnchantmentStorageMeta) silkTouchBook.getItemMeta();
		silkTouchBook_meta.addEnchant(Enchantment.SILK_TOUCH, 1, false);
		silkTouchBook.setItemMeta((ItemMeta) silkTouchBook_meta);
		votingRewards.put(silkTouchBook, 0.09f);
		ItemStack fortuneBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
		EnchantmentStorageMeta fortuneBook_meta = (EnchantmentStorageMeta) silkTouchBook.getItemMeta();
		fortuneBook_meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, false);
		fortuneBook.setItemMeta((ItemMeta) fortuneBook_meta);
		votingRewards.put(fortuneBook, 0.07f);
	}
}
