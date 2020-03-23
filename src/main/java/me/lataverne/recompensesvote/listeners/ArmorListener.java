package me.lataverne.recompensesvote.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import me.lataverne.recompensesvote.Items;
import me.lataverne.recompensesvote.tasks.BootsTask;

public class ArmorListener implements Listener {
	private Plugin plugin;
	
	public ArmorListener() {
		this.plugin = Bukkit.getPluginManager().getPlugin("RecompensesVote");
	}
	
	@EventHandler
	public void inventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getType().equals(InventoryType.CRAFTING)) {
			if ((event.getSlotType().equals(SlotType.ARMOR) && event.getSlot() == 36 && !event.isShiftClick() && !event.getClick().equals(ClickType.NUMBER_KEY))) {
				if (event.getCursor() == null) return;
				
				if (Items.GIANT_BOOTS.compareTo(event.getCursor())) {
					new BootsTask((Player) event.getWhoClicked()).runTaskTimer(plugin, 1, 20);
				}
			}
			else if (event.isShiftClick()) {
				if (event.getWhoClicked().getInventory().getBoots() == null) {
					if (event.getCurrentItem() == null) return;
					
					if (Items.GIANT_BOOTS.compareTo(event.getCurrentItem())) {
						new BootsTask((Player) event.getWhoClicked()).runTaskTimer(plugin, 1, 20);
					}
				}
			}
			else if (event.getClick().equals(ClickType.NUMBER_KEY)) {
				if (event.getInventory().getItem(event.getHotbarButton() + 36) == null) return;
				
				if (Items.GIANT_BOOTS.compareTo(event.getInventory().getItem(event.getHotbarButton() + 36))) {
					new BootsTask((Player) event.getWhoClicked()).runTaskTimer(plugin, 1, 20);
				}
			}
		}
	}
	
	@EventHandler
	public void inventoryDrag(InventoryDragEvent event) {
		if (event.getInventory().getType().equals(InventoryType.CRAFTING)) {
			if (event.getOldCursor() == null) return;
			
			if (Items.GIANT_BOOTS.compareTo(event.getOldCursor()) && event.getInventorySlots().contains(36)) {
				new BootsTask((Player) event.getWhoClicked()).runTaskTimer(plugin, 1, 20);
			}
		}
	}
	
	@EventHandler
	public void dispenseArmorEvent(BlockDispenseArmorEvent event) {
		if (event.getItem() == null) return;
		
		if (Items.GIANT_BOOTS.compareTo(event.getItem())) {
			new BootsTask((Player) event.getTargetEntity()).runTaskTimer(plugin, 0, 20);
		}
	}
	
	@EventHandler
	public void playerInteractEvent(PlayerInteractEvent event) {
		if (event.getItem() == null) return;
		
		if (event.getPlayer().getInventory().getBoots() == null) {
			if (event.getClickedBlock() != null && event.getClickedBlock().getType().isInteractable()) {
				return;
			}
			
			if (Items.GIANT_BOOTS.compareTo(event.getItem())) {
				new BootsTask(event.getPlayer()).runTaskTimer(plugin, 1, 20);
			}
		}
	}
}
