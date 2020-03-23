package me.lataverne.recompensesvote.tasks;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.lataverne.recompensesvote.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class PhantomEffect extends BukkitRunnable {
	private final Player player;
	private int counter;
	
	public PhantomEffect(Player player, int counter) {
		this.player = player;
		this.counter = counter;
		
		if (!Main.effect.containsKey(player.getUniqueId())) {
			Main.effect.put(player.getUniqueId(), "phantom");
		}

		Main.runningTasks.put(player.getUniqueId().toString(), this);
	}
	
	@Override
	public void run() {
		if (player.isOnline() && !player.isDead() && counter > 0) {
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§eIl te reste §a" + convertTime(counter)));
			
			if (counter  % 5 == 0) {
				List<Entity> entities = player.getNearbyEntities(25, 50, 25);
				
				for (Entity entity : entities) {
					if (entity.getType().equals(EntityType.PHANTOM)) {
						entity.remove();
					}
				}
			}
			
			counter--;
		}
		else {
			stop();
			Main.runningTasks.remove(player.getUniqueId().toString());
		}
	}

	public void stop() {
		if (counter <= 0 || (player.isDead() && player.isOnline())) {
			Main.effectTime.remove(player.getUniqueId());
			Main.effect.remove(player.getUniqueId());
		} else {
			if (Main.effectTime.replace(player.getUniqueId(), counter) == null) {
				Main.effectTime.put(player.getUniqueId(), counter);
			}
		}

		this.cancel();
	}
	
	private String convertTime(int time) {
		String time_string = "";
		if (time >= 3600) {
			time_string += String.valueOf(time / 3600) + "h";
			time = time % 3600;
		}
		if (time >= 60) {
			time_string += String.valueOf(time / 60) + "min";
			time = time % 60;
		}
		if (time >= 1) {
			time_string += String.valueOf(time) + "s";
		}
		return time_string;
	}
}
