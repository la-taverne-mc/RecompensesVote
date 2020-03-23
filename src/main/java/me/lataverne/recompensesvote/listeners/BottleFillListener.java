package me.lataverne.recompensesvote.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.event.block.CauldronLevelChangeEvent.ChangeReason;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import me.lataverne.recompensesvote.Main;
import me.lataverne.recompensesvote.tasks.BottleFillTask;
import me.lataverne.recompensesvote.tasks.PotionBrewTask;

public class BottleFillListener implements Listener {
	private final Plugin plugin;
	
	public BottleFillListener() {
		plugin = Bukkit.getPluginManager().getPlugin("RecompensesVote");
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
}