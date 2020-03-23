package me.lataverne.recompensesvote.listeners;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import me.lataverne.recompensesvote.Items;

public class GoblinPickaxeListener implements Listener {
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
}