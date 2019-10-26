package fr.poc4.recompensesvote.listeners;

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

import com.google.common.collect.Lists;

import fr.poc4.recompensesvote.Main;
import fr.poc4.recompensesvote.tasks.BootsTask;

public class ArmorListener implements Listener {
	private List<Material> blockedMaterials;
	private Plugin plugin;
	
	public ArmorListener(Plugin plugin) {
		this.plugin = plugin;
		blockedMaterials = Lists.newArrayList();
		
		blockedMaterials.add(Material.CHEST);
		blockedMaterials.add(Material.CRAFTING_TABLE);
		blockedMaterials.add(Material.FURNACE);
		blockedMaterials.add(Material.DISPENSER);
		blockedMaterials.add(Material.ENCHANTING_TABLE);
		blockedMaterials.add(Material.ENDER_CHEST);
		blockedMaterials.add(Material.SHULKER_BOX);
		blockedMaterials.add(Material.BLACK_SHULKER_BOX);
		blockedMaterials.add(Material.BLUE_SHULKER_BOX);
		blockedMaterials.add(Material.BROWN_SHULKER_BOX);
		blockedMaterials.add(Material.CYAN_SHULKER_BOX);
		blockedMaterials.add(Material.GRAY_SHULKER_BOX);
		blockedMaterials.add(Material.GREEN_SHULKER_BOX);
		blockedMaterials.add(Material.LIGHT_BLUE_SHULKER_BOX);
		blockedMaterials.add(Material.LIGHT_GRAY_SHULKER_BOX);
		blockedMaterials.add(Material.LIME_SHULKER_BOX);
		blockedMaterials.add(Material.MAGENTA_SHULKER_BOX);
		blockedMaterials.add(Material.ORANGE_SHULKER_BOX);
		blockedMaterials.add(Material.PINK_SHULKER_BOX);
		blockedMaterials.add(Material.PURPLE_SHULKER_BOX);
		blockedMaterials.add(Material.RED_SHULKER_BOX);
		blockedMaterials.add(Material.WHITE_SHULKER_BOX);
		blockedMaterials.add(Material.YELLOW_SHULKER_BOX);
		blockedMaterials.add(Material.ACACIA_SIGN);
		blockedMaterials.add(Material.ACACIA_WALL_SIGN);
		blockedMaterials.add(Material.BIRCH_SIGN);
		blockedMaterials.add(Material.BIRCH_WALL_SIGN);
		blockedMaterials.add(Material.DARK_OAK_SIGN);
		blockedMaterials.add(Material.DARK_OAK_WALL_SIGN);
		blockedMaterials.add(Material.JUNGLE_SIGN);
		blockedMaterials.add(Material.JUNGLE_WALL_SIGN);
		blockedMaterials.add(Material.OAK_SIGN);
		blockedMaterials.add(Material.OAK_WALL_SIGN);
		blockedMaterials.add(Material.SPRUCE_SIGN);
		blockedMaterials.add(Material.SPRUCE_WALL_SIGN);
		blockedMaterials.add(Material.BLACK_BED);
		blockedMaterials.add(Material.BLUE_BED);
		blockedMaterials.add(Material.BROWN_BED);
		blockedMaterials.add(Material.CYAN_BED);
		blockedMaterials.add(Material.GRAY_BED);
		blockedMaterials.add(Material.GREEN_BED);
		blockedMaterials.add(Material.LIGHT_BLUE_BED);
		blockedMaterials.add(Material.LIGHT_GRAY_BED);
		blockedMaterials.add(Material.LIME_BED);
		blockedMaterials.add(Material.MAGENTA_BED);
		blockedMaterials.add(Material.ORANGE_BED);
		blockedMaterials.add(Material.PINK_BED);
		blockedMaterials.add(Material.PURPLE_BED);
		blockedMaterials.add(Material.RED_BED);
		blockedMaterials.add(Material.WHITE_BED);
		blockedMaterials.add(Material.YELLOW_BED);
		blockedMaterials.add(Material.LOOM);
		blockedMaterials.add(Material.BARREL);
		blockedMaterials.add(Material.SMOKER);
		blockedMaterials.add(Material.BLAST_FURNACE);
		blockedMaterials.add(Material.CARTOGRAPHY_TABLE);
		blockedMaterials.add(Material.NOTE_BLOCK);
		blockedMaterials.add(Material.GRINDSTONE);
		blockedMaterials.add(Material.DROPPER);
		blockedMaterials.add(Material.STONECUTTER);
		blockedMaterials.add(Material.BELL);
		blockedMaterials.add(Material.LEVER);
		blockedMaterials.add(Material.ACACIA_BUTTON);
		blockedMaterials.add(Material.BIRCH_BUTTON);
		blockedMaterials.add(Material.DARK_OAK_BUTTON);
		blockedMaterials.add(Material.JUNGLE_BUTTON);
		blockedMaterials.add(Material.OAK_BUTTON);
		blockedMaterials.add(Material.SPRUCE_BUTTON);
		blockedMaterials.add(Material.STONE_BUTTON);
		blockedMaterials.add(Material.ACACIA_TRAPDOOR);
		blockedMaterials.add(Material.BIRCH_TRAPDOOR);
		blockedMaterials.add(Material.DARK_OAK_TRAPDOOR);
		blockedMaterials.add(Material.JUNGLE_TRAPDOOR);
		blockedMaterials.add(Material.OAK_TRAPDOOR);
		blockedMaterials.add(Material.SPRUCE_TRAPDOOR);
		blockedMaterials.add(Material.ACACIA_FENCE_GATE);
		blockedMaterials.add(Material.BIRCH_FENCE_GATE);
		blockedMaterials.add(Material.DARK_OAK_FENCE_GATE);
		blockedMaterials.add(Material.JUNGLE_FENCE_GATE);
		blockedMaterials.add(Material.OAK_FENCE_GATE);
		blockedMaterials.add(Material.SPRUCE_FENCE_GATE);
		blockedMaterials.add(Material.DAYLIGHT_DETECTOR);
		blockedMaterials.add(Material.REPEATER);
		blockedMaterials.add(Material.COMPARATOR);
		blockedMaterials.add(Material.ACACIA_DOOR);
		blockedMaterials.add(Material.BIRCH_DOOR);
		blockedMaterials.add(Material.DARK_OAK_DOOR);
		blockedMaterials.add(Material.JUNGLE_DOOR);
		blockedMaterials.add(Material.OAK_DOOR);
		blockedMaterials.add(Material.SPRUCE_DOOR);
		blockedMaterials.add(Material.BEACON);
		blockedMaterials.add(Material.BREWING_STAND);
		blockedMaterials.add(Material.ANVIL);
		blockedMaterials.add(Material.CHIPPED_ANVIL);
		blockedMaterials.add(Material.DAMAGED_ANVIL);
		blockedMaterials.add(Material.CAKE);
		blockedMaterials.add(Material.FLOWER_POT);
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
