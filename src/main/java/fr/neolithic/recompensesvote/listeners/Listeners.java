package fr.neolithic.recompensesvote.listeners;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.event.block.CauldronLevelChangeEvent.ChangeReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.neolithic.recompensesvote.Items;
import fr.neolithic.recompensesvote.Main;
import fr.neolithic.recompensesvote.tasks.BootsTask;
import fr.neolithic.recompensesvote.tasks.BottleFillTask;
import fr.neolithic.recompensesvote.tasks.CreeperEffect;
import fr.neolithic.recompensesvote.tasks.FlyEffect;
import fr.neolithic.recompensesvote.tasks.IndianSpearTask;
import fr.neolithic.recompensesvote.tasks.PhantomEffect;
import fr.neolithic.recompensesvote.tasks.PotionBrewTask;
import fr.neolithic.recompensesvote.util.EntityHider;
import fr.neolithic.recompensesvote.util.nbtapi.NBTCompound;
import fr.neolithic.recompensesvote.util.nbtapi.NBTEntity;

public class Listeners implements Listener {
	private final JavaPlugin plugin;
	private EntityHider entityHider;
	
	public Listeners(JavaPlugin plugin, EntityHider entityHider) {
		this.plugin = plugin;
		this.entityHider = entityHider;
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		if (Main.fillingBottle.containsKey(event.getPlayer()) && event.getItemDrop().getItemStack().getType() == Material.POTION) {
			ItemMeta meta = event.getItemDrop().getItemStack().getItemMeta();
			meta.setCustomModelData(Main.fillingBottle.get(event.getPlayer()));
			event.getItemDrop().getItemStack().setItemMeta(meta);
		}
	}

	@EventHandler
	public void onBrew(BrewEvent event) {
		BrewerInventory inv = event.getContents();
		
		if (inv.getIngredient().getType() != Material.GUNPOWDER && inv.getIngredient().getType() != Material.DRAGON_BREATH) {
			Integer firstModelData = null;
			Integer secondModelData = null;
			Integer thirdModelData = null;

			if (inv.getItem(0) != null && inv.getItem(0).getItemMeta().hasCustomModelData()) {
				firstModelData = inv.getItem(0).getItemMeta().getCustomModelData();
			}
			if (inv.getItem(1) != null && inv.getItem(1).getItemMeta().hasCustomModelData()) {
				secondModelData = inv.getItem(1).getItemMeta().getCustomModelData();
			}
			if (inv.getItem(2) != null && inv.getItem(2).getItemMeta().hasCustomModelData()) {
				thirdModelData = inv.getItem(2).getItemMeta().getCustomModelData();
			}

			if (firstModelData != null || secondModelData != null || thirdModelData != null) {
				new PotionBrewTask(inv, firstModelData, secondModelData, thirdModelData).runTask(plugin);
			}
		}
	}

	@EventHandler
	public void onInteraction(PlayerInteractEvent event) {
		if (event.getItem() != null) {
			if (event.getItem().getType() == Material.GLASS_BOTTLE && event.getItem().getItemMeta().hasCustomModelData()) {
				if (event.getHand() == EquipmentSlot.HAND) {
					for (Block block : event.getPlayer().getLineOfSight(null, 5)) {
						if (block.getType() == Material.WATER) {
							new BottleFillTask(event.getPlayer(), event.getItem().getItemMeta().getCustomModelData(), event.getPlayer().getInventory().getHeldItemSlot()).runTask(plugin);
						}
					}
				}
				else {
					for (Block block : event.getPlayer().getLineOfSight(null, 5)) {
						if (block.getType() == Material.WATER) {
							new BottleFillTask(event.getPlayer(), event.getItem().getItemMeta().getCustomModelData(), 40).runTask(plugin);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onCauldronUse(CauldronLevelChangeEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();

			if (player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() && (event.getReason() == ChangeReason.BOTTLE_EMPTY || event.getReason() == ChangeReason.BOTTLE_FILL) && (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)) {
				Integer itemModel = player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData();
				if (event.getReason() == ChangeReason.BOTTLE_EMPTY) {
					event.setCancelled(true);
					Levelled level = (Levelled) event.getBlock().getBlockData();
					level.setLevel(level.getLevel() + 1);
					event.getBlock().setBlockData((BlockData) level);

					ItemStack item = new ItemStack(Material.GLASS_BOTTLE);
					ItemMeta itemMeta = item.getItemMeta();
					itemMeta.setCustomModelData(itemModel);
					item.setItemMeta(itemMeta);

					if (player.getInventory().getItemInMainHand().getAmount() > 1) {
						player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
						player.getInventory().addItem(item);
					}
					else {
						player.getInventory().setItemInMainHand(item);
					}

					player.getWorld().playSound(event.getBlock().getLocation(), Sound.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1, 1);
				}
				else if (event.getReason() == ChangeReason.BOTTLE_FILL) {
					event.setCancelled(true);
					Levelled level = (Levelled) event.getBlock().getBlockData();
					level.setLevel(level.getLevel() - 1);
					event.getBlock().setBlockData((BlockData) level);

					ItemStack item = new ItemStack(Material.POTION);
					PotionMeta itemMeta = (PotionMeta) item.getItemMeta();
					itemMeta.setCustomModelData(itemModel);
					itemMeta.setBasePotionData(new PotionData(PotionType.WATER));
					item.setItemMeta((ItemMeta) itemMeta);

					if (player.getInventory().getItemInMainHand().getAmount() > 1) {
						player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
						player.getInventory().addItem(item);
					} else {
						player.getInventory().setItemInMainHand(item);
					}
					
					player.getWorld().playSound(event.getBlock().getLocation(), Sound.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1, 1);
				}
			}
		}
	}

	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
		if (event.getEntity() instanceof Trident && event.getEntity().getShooter() instanceof Player) {
			Trident trident = (Trident) event.getEntity();
			NBTCompound tridentTags = new NBTEntity(trident).getCompound("Trident").getCompound("tag");
			if (tridentTags.getInteger("CustomModelData") == 1) {
				new IndianSpearTask(entityHider, (Player) trident.getShooter(), trident, tridentTags.getInteger("Damage")).runTaskLater(plugin, 3);
			}
		}
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Trident && event.getHitEntity() instanceof Ageable) {
			Trident trident = (Trident) event.getEntity();
			Ageable target = (Ageable) event.getHitEntity();
			NBTCompound tridentTags = new NBTEntity(trident).getCompound("Trident").getCompound("tag");
			if (tridentTags.getInteger("CustomModelData") == 1) {
				if ((target.getType().equals(EntityType.DONKEY) || target.getType().equals(EntityType.HORSE) || target.getType().equals(EntityType.MULE)) && target.getHealth() - trident.getDamage() <= 0 && !target.isAdult()) {
					target.getWorld().dropItemNaturally(target.getLocation(), Items.RAW_HORSE.getItem());
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerHit(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Ageable) {
			Player damager = (Player) event.getDamager();
			Ageable target = (Ageable) event.getEntity();
			
			if (event.getCause().equals(DamageCause.ENTITY_ATTACK) || event.getCause().equals(DamageCause.ENTITY_SWEEP_ATTACK)) {
				if (Items.BASEBALL_BAT.compareTo(damager.getInventory().getItemInMainHand())) {
					damager.getWorld().playSound(damager.getLocation(), Sound.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, 1, (float) 1.12);
				}
				
				else if (Items.ULU.compareTo(damager.getInventory().getItemInMainHand())) {
					if (event.getEntityType().equals(EntityType.POLAR_BEAR) && target.getHealth() - event.getDamage() <= 0 && !target.isAdult()) {
						target.getWorld().dropItemNaturally(target.getLocation(), Items.RAW_BEAR.getItem());
					}
				}
				
				else if (Items.INDIAN_SPEAR.compareTo(damager.getInventory().getItemInMainHand())) {
					if ((event.getEntityType().equals(EntityType.DONKEY) || event.getEntityType().equals(EntityType.HORSE) || event.getEntityType().equals(EntityType.MULE)) && target.getHealth() - event.getDamage() <= 0 && !target.isAdult()) {
						target.getWorld().dropItemNaturally(target.getLocation(), Items.RAW_HORSE.getItem());
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onConsume(PlayerItemConsumeEvent event) {
		if (Items.COOKED_BEAR.compareTo(event.getItem())) {
			Player player = event.getPlayer();
			player.setFoodLevel(20);
			player.setSaturation((float) Math.max(player.getSaturation(), 16));
		}
		
		else if (Items.COOKED_HORSE.compareTo(event.getItem())) {
			Player player = event.getPlayer();
			player.setFoodLevel(player.getFoodLevel() + 3);
			player.setSaturation((float) Math.max(player.getSaturation(), 13.5));
		}

		else if (Items.MINING_POTION.compareTo(event.getItem())) {
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 12000, 2));
		}

		else if (Items.SWIMING_POTION.compareTo(event.getItem())) {
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 12000, 0));
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 12000, 0));
		}

		else if (Items.PHANTOM_POTION.compareTo(event.getItem())) {
			if (Main.effect.containsKey(event.getPlayer().getUniqueId())) {
				event.getPlayer().sendMessage("§eTu ne peux pas boire cette potion tant que tu en as une autre active");
				event.setCancelled(true);
				return;
			}

			new PhantomEffect(event.getPlayer(), 1200).runTaskTimer(plugin, 0, 20);
		}

		else if (Items.CREEPER_POTION.compareTo(event.getItem())) {
			if (Main.effect.containsKey(event.getPlayer().getUniqueId())) {
				event.getPlayer().sendMessage("§eTu ne peux pas boire cette potion tant que tu en as une autre active");
				event.setCancelled(true);
				return;
			}

			new CreeperEffect(event.getPlayer(), 1200).runTaskTimer(plugin, 0, 20);
		}
		
		else if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
			if (Items.FLY_1.compareTo(event.getItem())) {
				if (Main.effect.containsKey(event.getPlayer().getUniqueId())) {
					event.getPlayer().sendMessage("§eTu ne peux pas boire cette potion tant que tu en as une autre active");
					event.setCancelled(true);
					return;
				}
				
				new FlyEffect(event.getPlayer(), 150).runTaskTimer(plugin, 0, 20);
			}
			
			else if (Items.FLY_2.compareTo(event.getItem())) {
				if (Main.effect.containsKey(event.getPlayer().getUniqueId())) {
					event.getPlayer().sendMessage("§eTu ne peux pas boire cette potion tant que tu en as une autre active");
					event.setCancelled(true);
					return;
				}
				
				new FlyEffect(event.getPlayer(), 300).runTaskTimer(plugin, 0, 20);
			}
			
			else if (Items.FLY_3.compareTo(event.getItem())) {
				if (Main.effect.containsKey(event.getPlayer().getUniqueId())) {
					event.getPlayer().sendMessage("§eTu ne peux pas boire cette potion tant que tu en as une autre active");
					event.setCancelled(true);
					return;
				}
				
				new FlyEffect(event.getPlayer(), 600).runTaskTimer(plugin, 0, 20);
			}
			
			else if (Items.FLY_4.compareTo(event.getItem())) {
				if (Main.effect.containsKey(event.getPlayer().getUniqueId())) {
					event.getPlayer().sendMessage("§eTu ne peux pas boire cette potion tant que tu en as une autre active");
					event.setCancelled(true);
					return;
				}
				
				new FlyEffect(event.getPlayer(), 1200).runTaskTimer(plugin, 0, 20);
			}
		}

		if (event.getItem().getType() == Material.POTION && event.getItem().getItemMeta().hasCustomModelData()) {
			ItemStack emptyBottle = new ItemStack(Material.GLASS_BOTTLE);
			ItemMeta emptyBottleMeta = emptyBottle.getItemMeta();
			emptyBottleMeta.setCustomModelData(event.getItem().getItemMeta().getCustomModelData());
			emptyBottle.setItemMeta(emptyBottleMeta);

			Player player = event.getPlayer();
			Integer slot; 
			if (player.getInventory().getItemInMainHand().equals(event.getItem())) {
				slot = player.getInventory().getHeldItemSlot();
			}
			else {
				slot = 40;
			}

			new BukkitRunnable() {
				@Override
				public void run() {
					player.getInventory().setItem(slot, emptyBottle);
				}
			}.runTask(plugin);
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if (Main.effect.get(event.getPlayer().getUniqueId()) != null) {
			switch (Main.effect.get(event.getPlayer().getUniqueId())) {
				case "fly":
					new FlyEffect(event.getPlayer(), Main.effectTime.get(event.getPlayer().getUniqueId())).runTaskTimer(plugin, 0, 20);
					break;
					
				case "phantom":
					new PhantomEffect(event.getPlayer(), Main.effectTime.get(event.getPlayer().getUniqueId())).runTaskTimer(plugin, 0, 20);
					break;
			}
		}

		if (Main.wearingBoots.contains(event.getPlayer().getUniqueId())) {
			new BootsTask(event.getPlayer()).runTaskTimer(plugin, 1, 20);
		}
	}
	
	@EventHandler
	public void onItemPickup(EntityPickupItemEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (Items.RAW_HORSE.compareTo(event.getItem().getItemStack())) {
				player.discoverRecipe(new NamespacedKey(plugin, "cooked_horse"));
				player.discoverRecipe(new NamespacedKey(plugin, "cooked_horse_from_smoking"));
				player.discoverRecipe(new NamespacedKey(plugin, "cooked_horse_from_campfire_cooking"));
			}
			
			else if (Items.RAW_BEAR.compareTo(event.getItem().getItemStack())) {
				player.discoverRecipe(new NamespacedKey(plugin, "cooked_bear"));
				player.discoverRecipe(new NamespacedKey(plugin, "cooked_bear_from_smoking"));
				player.discoverRecipe(new NamespacedKey(plugin, "cooked_bear_from_campfire_cooking"));
			}
		}
	}
	
	@EventHandler
	public void onPlayerBlockPlace(BlockPlaceEvent event) {
		if (Items.CANDY_CANE.compareTo(event.getItemInHand())) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerBlockBreak(BlockBreakEvent event) {
		if (Items.GOBLIN_PICKAXE.compareTo(event.getPlayer().getInventory().getItemInMainHand())) {
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
		ItemStack item;
		Set<Integer> slot = new HashSet<Integer>();

		switch (event.getAction()) {
			case PLACE_ALL:
			case PLACE_ONE:
			case PLACE_SOME:
			case SWAP_WITH_CURSOR:
				slot.add(event.getRawSlot());
				item = event.getCursor();
				break;

			case HOTBAR_SWAP:
				slot.add(event.getRawSlot());
				item = event.getWhoClicked().getInventory().getItem(event.getHotbarButton());
				break;
			
			case MOVE_TO_OTHER_INVENTORY:
				slot.add(1337);
				item = event.getCurrentItem();
				break;

			default:
				return;
		}

		
		event.setCancelled(blockItemModifications(event.getInventory().getType(), slot, item));
	}
	
	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) {
		event.setCancelled(blockItemModifications(event.getInventory().getType(), event.getRawSlots(), event.getOldCursor()));
	}
	
	private boolean blockItemModifications(InventoryType invType, Set<Integer> slots, ItemStack item) {
		switch (invType) {
			case ANVIL:
				if (slots.contains(0) || slots.contains(1) || slots.contains(2) || slots.contains(1337)) {
					if (Items.blockedItemsContains(item)) {
						return true;
					}
				}
				break;

			case BREWING:
				if (slots.contains(0) || slots.contains(1) || slots.contains(2) || slots.contains(3) || slots.contains(4) || slots.contains(1337)) {
					if (Items.blockedItemsContains(item)) {
						return true;
					}
				}
				break;

			case ENCHANTING:
				if (slots.contains(0) || slots.contains(1) || slots.contains(1337)) {
					if (Items.blockedItemsContains(item)) {
						return true;
					}
				}
				break;

			default:
				break;
		}

		return false;
	}
}
