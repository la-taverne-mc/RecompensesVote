package fr.neolithic.recompensesvote.listeners;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import fr.neolithic.recompensesvote.Main;
import fr.neolithic.recompensesvote.tasks.CreeperEffect;
import fr.neolithic.recompensesvote.tasks.FlyEffect;
import fr.neolithic.recompensesvote.tasks.PhantomEffect;

public class Listeners implements Listener {
	private final JavaPlugin plugin;
	
	public Listeners(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerHit(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			org.bukkit.entity.Damageable target = (org.bukkit.entity.Damageable) event.getEntity();
			
			if (event.getCause().equals(DamageCause.ENTITY_ATTACK) || event.getCause().equals(DamageCause.ENTITY_SWEEP_ATTACK)) {
				ItemStack damagerWeapon = damager.getInventory().getItemInMainHand().clone();
				
				if (damagerWeapon.getItemMeta() instanceof Damageable) {
					Damageable damagerWeapon_meta = (Damageable) damagerWeapon.getItemMeta();
					damagerWeapon_meta.setDamage(0);
					damagerWeapon.setItemMeta((ItemMeta) damagerWeapon_meta);
				}
				
				if (damagerWeapon.equals(Main.items.get("baseballBat"))) {
					damager.getWorld().playSound(damager.getLocation(), Sound.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, 1, (float) 1.12);
				}
				
				else if (damagerWeapon.equals(Main.items.get("inuitAxe"))) {
					if (event.getEntityType().equals(EntityType.POLAR_BEAR) && (target.getHealth() - event.getDamage() <= 0)) {
						target.getWorld().dropItemNaturally(target.getLocation(), Main.items.get("rawBear"));
					}
				}
				
				else if (damagerWeapon.equals(Main.items.get("indianAxe"))) {
					if ((event.getEntityType().equals(EntityType.DONKEY) || event.getEntityType().equals(EntityType.HORSE) || event.getEntityType().equals(EntityType.MULE)) && target.getHealth() - event.getDamage() <= 0) {
						target.getWorld().dropItemNaturally(target.getLocation(), Main.items.get("rawHorse"));
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onConsume(PlayerItemConsumeEvent event) {
		if (event.getItem().isSimilar(Main.items.get("cookedBear"))) {
			Player player = event.getPlayer();
			player.setFoodLevel(20);
			player.setSaturation((float) Math.max(player.getSaturation(), 16));
		}
		
		else if (event.getItem().isSimilar(Main.items.get("cookedHorse"))) {
			Player player = event.getPlayer();
			player.setFoodLevel(player.getFoodLevel() + 3);
			player.setSaturation((float) Math.max(player.getSaturation(), 13.5));
		}
		
		else if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
			if (event.getItem().isSimilar(Main.items.get("fly1min"))) {
				if (Main.effect.containsKey(event.getPlayer().getName())) {
					event.getPlayer().sendMessage("§eTu ne peux pas boire cette potion tant que tu en as une autre active");
					event.setCancelled(true);
					return;
				}
				
				new FlyEffect(event.getPlayer(), 60).runTaskTimer(plugin, 0, 20);
			}
			
			else if (event.getItem().isSimilar(Main.items.get("fly5min"))) {
				if (Main.effect.containsKey(event.getPlayer().getName())) {
					event.getPlayer().sendMessage("§eTu ne peux pas boire cette potion tant que tu en as une autre active");
					event.setCancelled(true);
					return;
				}
				
				new FlyEffect(event.getPlayer(), 300).runTaskTimer(plugin, 0, 20);
			}
			
			else if (event.getItem().isSimilar(Main.items.get("fly10min"))) {
				if (Main.effect.containsKey(event.getPlayer().getName())) {
					event.getPlayer().sendMessage("§eTu ne peux pas boire cette potion tant que tu en as une autre active");
					event.setCancelled(true);
					return;
				}
				
				new FlyEffect(event.getPlayer(), 600).runTaskTimer(plugin, 0, 20);
			}
			
			else if (event.getItem().isSimilar(Main.items.get("fly20min"))) {
				if (Main.effect.containsKey(event.getPlayer().getName())) {
					event.getPlayer().sendMessage("§eTu ne peux pas boire cette potion tant que tu en as une autre active");
					event.setCancelled(true);
					return;
				}
				
				new FlyEffect(event.getPlayer(), 1200).runTaskTimer(plugin, 0, 20);
			}
			
			else if (event.getItem().isSimilar(Main.items.get("antiPhantom"))) {
				if (Main.effect.containsKey(event.getPlayer().getName())) {
					event.getPlayer().sendMessage("§eTu ne peux pas boire cette potion tant que tu en as une autre active");
					event.setCancelled(true);
					return;
				}
				
				new PhantomEffect(event.getPlayer(), 1200).runTaskTimer(plugin, 0, 20);
			}
			
			else if (event.getItem().isSimilar(Main.items.get("antiCreeper"))) {
				if (Main.effect.containsKey(event.getPlayer().getName())) {
					event.getPlayer().sendMessage("§eTu ne peux pas boire cette potion tant que tu en as une autre active");
					event.setCancelled(true);
					return;
				}
				
				new CreeperEffect(event.getPlayer(), 1200).runTaskTimer(plugin, 0, 20);
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		String playerEffect = Main.effect.get(event.getPlayer().getName());
		
		if (playerEffect != null) {
			switch (playerEffect) {
			case "fly":
				new FlyEffect(event.getPlayer(), Main.effectTime.get(event.getPlayer().getName())).runTaskTimer(plugin, 0, 20);
				break;
			case "phantom":
				new PhantomEffect(event.getPlayer(), Main.effectTime.get(event.getPlayer().getName())).runTaskTimer(plugin, 0, 20);
				break;
			}
		}
	}
	
	@EventHandler
	public void onItemPickup(EntityPickupItemEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (event.getItem().getItemStack().equals(Main.items.get("rawHorse"))) {
				player.discoverRecipe(new NamespacedKey(plugin, "cooked_horse"));
				player.discoverRecipe(new NamespacedKey(plugin, "cooked_horse_from_smoking"));
				player.discoverRecipe(new NamespacedKey(plugin, "cooked_horse_from_campfire_cooking"));
			}
			
			else if (event.getItem().getItemStack().equals(Main.items.get("rawBear"))) {
				player.discoverRecipe(new NamespacedKey(plugin, "cooked_bear"));
				player.discoverRecipe(new NamespacedKey(plugin, "cooked_bear_from_smoking"));
				player.discoverRecipe(new NamespacedKey(plugin, "cooked_bear_from_campfire_cooking"));
			}
		}
	}
	
	@EventHandler
	public void onPlayerBlockPlace(BlockPlaceEvent event) {
		if (event.getItemInHand().isSimilar(Main.items.get("legendaryDirt"))) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerBlockBreak(BlockBreakEvent event) {
		ItemStack playerTool = event.getPlayer().getInventory().getItemInMainHand().clone();
		
		if (playerTool.getItemMeta() instanceof Damageable) {
			Damageable playerTool_meta = (Damageable) playerTool.getItemMeta();
			playerTool_meta.setDamage(0);
			playerTool.setItemMeta((ItemMeta) playerTool_meta);
		}
		
		if (playerTool.equals(Main.items.get("goblinPickaxe"))) {
			if (event.getBlock().getType().equals(Material.STONE)) {
				Location blockLocation = event.getBlock().getLocation();
				event.setDropItems(false);
				Random random = new Random();
				int lootSelector = random.nextInt(65);
				
				if (lootSelector >= 0 && lootSelector <= 14) {
					blockLocation.getWorld().dropItemNaturally(blockLocation, new ItemStack(Material.IRON_NUGGET, getRandomNumberInRange(7, 11)));
				}
				else if (lootSelector >= 15 && lootSelector <= 29) {
					blockLocation.getWorld().dropItemNaturally(blockLocation, new ItemStack(Material.LAPIS_LAZULI, getRandomNumberInRange(6, 10)));
				}
				else if (lootSelector >= 30 && lootSelector <= 39) {
					blockLocation.getWorld().dropItemNaturally(blockLocation, new ItemStack(Material.REDSTONE, getRandomNumberInRange(6, 10)));
				}
				else if (lootSelector >= 40 && lootSelector <= 49) {
					blockLocation.getWorld().dropItemNaturally(blockLocation, new ItemStack(Material.GOLD_NUGGET, getRandomNumberInRange(6, 10)));
				}
				else if (lootSelector >= 50 && lootSelector <= 57) {
					blockLocation.getWorld().dropItemNaturally(blockLocation, new ItemStack(Material.DIAMOND, getRandomNumberInRange(1, 2)));
				}
				else if (lootSelector >= 58 && lootSelector <= 64) {
					blockLocation.getWorld().dropItemNaturally(blockLocation, new ItemStack(Material.EMERALD, getRandomNumberInRange(1, 2)));
				}
			}
			
			else if (event.getBlock().getType().equals(Material.NETHERRACK)) {
				Location blockLocation = event.getBlock().getLocation();
				event.setDropItems(false);
				blockLocation.getWorld().dropItemNaturally(blockLocation, new ItemStack(Material.QUARTZ, getRandomNumberInRange(1, 5)));
			}
		}
	}
	
	private static int getRandomNumberInRange(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getAction().equals(InventoryAction.PLACE_ALL) || event.getAction().equals(InventoryAction.PLACE_ONE) || event.getAction().equals(InventoryAction.PLACE_SOME)) {
			Set<Integer> slot = new HashSet<Integer>();
			slot.add(event.getRawSlot());
			event.setCancelled(blockItemModifications(event.getInventory().getType(), slot, event.getCursor()));
		}
	}
	
	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) {
		event.setCancelled(blockItemModifications(event.getInventory().getType(), event.getRawSlots(), event.getOldCursor()));
	}
	
	private boolean blockItemModifications(InventoryType invType, Set<Integer> slots, ItemStack item) {
		switch (invType) {
		case ANVIL:
			if (slots.contains(0) || slots.contains(1) || slots.contains(2)) {
				if (item.getItemMeta() instanceof Damageable) {
					Damageable item_meta = (Damageable) item.getItemMeta();
					item_meta.setDamage(0);
					item.setItemMeta((ItemMeta) item_meta);
				}
				
				for (Map.Entry<String, ItemStack> entry : Main.items.entrySet()) {
					if (entry.getValue().isSimilar(item)) {
						return true;
					}
				}
			}
			break;
		case BREWING:
			if (slots.contains(0) || slots.contains(1) || slots.contains(2) || slots.contains(3) || slots.contains(4)) {
				if (item.getItemMeta() instanceof Damageable) {
					Damageable item_meta = (Damageable) item.getItemMeta();
					item_meta.setDamage(0);
					item.setItemMeta((ItemMeta) item_meta);
				}
				
				for (Map.Entry<String, ItemStack> entry : Main.items.entrySet()) {
					if (entry.getValue().isSimilar(item)) {
						return true;
					}
				}
			}
			break;
		case ENCHANTING:
			if (slots.contains(0) || slots.contains(1)) {
				if (item.getItemMeta() instanceof Damageable) {
					Damageable item_meta = (Damageable) item.getItemMeta();
					item_meta.setDamage(0);
					item.setItemMeta((ItemMeta) item_meta);
				}
				
				for (Map.Entry<String, ItemStack> entry : Main.items.entrySet()) {
					if (entry.getValue().isSimilar(item)) {
						return true;
					}
				}
			}
			break;
		default:
			break;
		}
		return false;
	}
}
