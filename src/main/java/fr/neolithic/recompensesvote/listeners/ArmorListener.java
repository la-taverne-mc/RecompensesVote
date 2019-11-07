package fr.neolithic.recompensesvote.listeners;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import fr.neolithic.recompensesvote.Main;
import fr.neolithic.recompensesvote.tasks.BootsTask;

public class ArmorListener implements Listener {
	private List<Material> blockedMaterials;
	private Plugin plugin;
	
	public ArmorListener(Plugin plugin, List<Material> blockedMaterials) {
		this.plugin = plugin;
		this.blockedMaterials = blockedMaterials;
	}
	
	@EventHandler
	public void inventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getType().equals(InventoryType.CRAFTING)) {
			if ((event.getSlotType().equals(SlotType.ARMOR) && event.getSlot() == 36 && !event.isShiftClick() && !event.getClick().equals(ClickType.NUMBER_KEY))) {
				if (event.getCursor() == null) return;
				
				ItemStack item = event.getCursor().clone();
				
				if (item.getItemMeta() instanceof Damageable) {
					Damageable item_meta = (Damageable) item.getItemMeta();
					item_meta.setDamage(0);
					item.setItemMeta((ItemMeta) item_meta);
				}
				
				if (item.equals(Main.items.get("giantBoots"))) {
					event.getWhoClicked().sendMessage("equiped");
				}
			}
			else if (event.isShiftClick()) {
				if (event.getWhoClicked().getInventory().getBoots() == null) {
					if (event.getCurrentItem() == null) return;
					
					ItemStack item = event.getCurrentItem().clone();
					
					if (item.getItemMeta() instanceof Damageable) {
						Damageable item_meta = (Damageable) item.getItemMeta();
						item_meta.setDamage(0);
						item.setItemMeta((ItemMeta) item_meta);
					}
					
					if (item.equals(Main.items.get("giantBoots"))) {
						event.getWhoClicked().sendMessage("equiped");
					}
				}
			}
			else if (event.getClick().equals(ClickType.NUMBER_KEY)) {
				if (event.getInventory().getItem(event.getHotbarButton() + 36) == null) return;
				
				ItemStack item = event.getInventory().getItem(event.getHotbarButton() + 36).clone();
				
				if (item.getItemMeta() instanceof Damageable) {
					Damageable item_meta = (Damageable) item.getItemMeta();
					item_meta.setDamage(0);
					item.setItemMeta((ItemMeta) item_meta);
				}
				
				if (item.equals(Main.items.get("giantBoots"))) {
					event.getWhoClicked().sendMessage("equiped");
				}
			}
		}
	}
	
	@EventHandler
	public void inventoryDrag(InventoryDragEvent event) {
		if (event.getInventory().getType().equals(InventoryType.CRAFTING)) {
			if (event.getOldCursor() == null) return;
			
			ItemStack item = event.getOldCursor().clone();
			
			if (item.getItemMeta() instanceof Damageable) {
				Damageable item_meta = (Damageable) item.getItemMeta();
				item_meta.setDamage(0);
				item.setItemMeta((ItemMeta) item_meta);
			}
			
			if (item.equals(Main.items.get("giantBoots"))) {
				if (event.getInventorySlots().contains(36)) {
					event.getWhoClicked().sendMessage("equiped");
				}
			}
		}
	}
	
	@EventHandler
	public void dispenseArmorEvent(BlockDispenseArmorEvent event) {
		if (event.getItem() == null) return;
		
		ItemStack item = event.getItem().clone();
		
		if (item.getItemMeta() instanceof Damageable) {
			Damageable item_meta = (Damageable) item.getItemMeta();
			item_meta.setDamage(0);
			item.setItemMeta((ItemMeta) item_meta);
		}
		
		if (item.equals(Main.items.get("giantBoots"))) {
			event.getTargetEntity().sendMessage("equiped");
		}
	}
	
	@EventHandler
	public void playerInteractEvent(PlayerInteractEvent event) {
		if (event.getItem() == null) return;
		
		if (event.getPlayer().getInventory().getBoots() == null) {
			if (event.getClickedBlock() != null) {
				if (blockedMaterials.contains(event.getClickedBlock().getType()) || (event.getClickedBlock().getType().equals(Material.COMPOSTER) && ((org.bukkit.block.data.Levelled) event.getClickedBlock().getBlockData()).getLevel() == 8) || (event.getClickedBlock().getType().equals(Material.LECTERN) && ((org.bukkit.block.data.type.Lectern) event.getClickedBlock().getBlockData()).hasBook())) {
					return;
				}
			}
			
			ItemStack item = event.getItem().clone();
			
			if (item.getItemMeta() instanceof Damageable) {
				Damageable item_meta = (Damageable) item.getItemMeta();
				item_meta.setDamage(0);
				item.setItemMeta((ItemMeta) item_meta);
			}
			
			if (item.equals(Main.items.get("giantBoots"))) {
				event.getPlayer().sendMessage("equiped");
				new BootsTask(event.getPlayer()).runTaskTimer(plugin, 1, 20);
			}
		}
	}
}
