package me.lataverne.recompensesvote.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import me.lataverne.recompensesvote.Items;

public class CustomFoodListener implements Listener {
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
	}
}