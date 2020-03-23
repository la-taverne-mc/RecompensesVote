package me.lataverne.recompensesvote.tasks;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.lataverne.recompensesvote.Items;
import me.lataverne.recompensesvote.Main;

public class BootsTask extends BukkitRunnable {
	private double totalDistance;
	private Location lastLocation;
	private final Player player;
	
	public BootsTask(Player player) {
		this.player = player;
		this.lastLocation = this.player.getLocation();
		Main.runningTasks.put(player.getUniqueId().toString() + "-boots", this);
	}
	
	@Override
	public void run() {
		if (player.getInventory().getBoots() == null || !Items.GIANT_BOOTS.compareTo(player.getInventory().getBoots())) {
			stop();
			Main.runningTasks.remove(player.getUniqueId().toString() + "-boots");
			return;
		}
		
		if (player.isOnline()) {
			totalDistance += lastLocation.distance(player.getLocation());
			lastLocation = player.getLocation();

			if (totalDistance >= 75) {
				ItemStack boots = player.getInventory().getBoots();
				Damageable boots_meta = (Damageable) boots.getItemMeta();
				boots_meta.setDamage(boots_meta.getDamage() + (int) Math.round(totalDistance) / 75);
				boots.setItemMeta((ItemMeta) boots_meta);
				
				if (boots_meta.getDamage() >= 65) {
					player.getInventory().setBoots(null);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1, 1);
				}
				else {
					player.getInventory().setBoots(boots);
				}
				
				totalDistance = totalDistance % 75;
			}
		}
		else {
			stop();
			Main.runningTasks.remove(player.getUniqueId().toString() + "-boots");
		}
	}

	public void stop() {
		if (player.getInventory().getBoots() == null || !Items.GIANT_BOOTS.compareTo(player.getInventory().getBoots())) {
			Main.wearingBoots.remove(player.getUniqueId());
		}
		else if (!Main.wearingBoots.contains(player.getUniqueId())) {
			Main.wearingBoots.add(player.getUniqueId());
		}

		this.cancel();
	}
}
