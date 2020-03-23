package me.lataverne.recompensesvote.listeners;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.lataverne.recompensesvote.Items;

public class BaseballBatHitListener implements Listener {
	@EventHandler
	public void onPlayerHit(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			
			if (event.getCause().equals(DamageCause.ENTITY_ATTACK) || event.getCause().equals(DamageCause.ENTITY_SWEEP_ATTACK)) {
				if (Items.BASEBALL_BAT.compareTo(damager.getInventory().getItemInMainHand())) {
					damager.getWorld().playSound(damager.getLocation(), Sound.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, 1, 1.12f);
				}
			}
		}
	}
}