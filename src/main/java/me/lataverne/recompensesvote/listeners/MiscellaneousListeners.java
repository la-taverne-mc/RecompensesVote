package me.lataverne.recompensesvote.listeners;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.lataverne.recompensesvote.Items;
import me.lataverne.recompensesvote.Main;
import me.lataverne.recompensesvote.tasks.BootsTask;
import me.lataverne.recompensesvote.tasks.FlyEffect;
import me.lataverne.recompensesvote.tasks.PhantomEffect;

public class MiscellaneousListeners implements Listener {
	private final Plugin plugin;
	
	public MiscellaneousListeners() {
		plugin = Bukkit.getPluginManager().getPlugin("RecompensesVote");
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
	public void onPlayerBlockPlace(BlockPlaceEvent event) {
		if (Items.CANDY_CANE.compareTo(event.getItemInHand())) {
			event.setCancelled(true);
		}
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
