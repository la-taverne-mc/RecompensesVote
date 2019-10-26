package fr.poc4.recompensesvote.tasks;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import fr.poc4.recompensesvote.Main;

public class BootsTask extends BukkitRunnable {
	private double totalDistance;
	private Location lastLocation;
	private Player player;
	
	public BootsTask(Player player) {
		this.player = player;
		this.lastLocation = this.player.getLocation();
	}
	
	@Override
	public void run() {
		if (player.getInventory().getBoots() == null) {
			this.cancel();
			return;
		}
		
		ItemStack boots = player.getInventory().getBoots().clone();
		
		if (boots.getItemMeta() instanceof Damageable) {
			Damageable boots_meta = (Damageable) boots.getItemMeta();
			boots_meta.setDamage(0);
			boots.setItemMeta((ItemMeta) boots_meta);
		}
		
		if (boots.equals(Main.items.get("giantBoots"))) {
			totalDistance += lastLocation.distance(player.getLocation());
			lastLocation = player.getLocation();

			if (totalDistance >= 75) {
				totalDistance -= 75;
				
				boots = player.getInventory().getBoots();
				Damageable boots_meta = (Damageable) boots.getItemMeta();
				boots_meta.setDamage(boots_meta.getDamage() + 1);
				boots.setItemMeta((ItemMeta) boots_meta);
				
				if (boots_meta.getDamage() >= 65) {
					player.getInventory().setBoots(null);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1, 1);
				}
				else {
					player.getInventory().setBoots(boots);
				}
			}
		}
		else {
			this.cancel();
		}
	}
}
