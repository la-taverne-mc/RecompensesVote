package fr.neolithic.recompensesvote.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.neolithic.recompensesvote.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class FlyEffect extends BukkitRunnable {
	private final Player player;
	private int counter;
	
	public FlyEffect(Player player, int counter) {
		this.player = player;
		this.counter = counter;
		
		this.player.setAllowFlight(true);
		if (!Main.effect.containsKey(player.getUniqueId())) {
			Main.effect.put(player.getUniqueId(), "fly");
		}

		Main.runningTasks.add(this);
	}
	
	@Override
	public void run() {
		if (player.isOnline() && !player.isDead() && counter > 0) {
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§eIl te reste §a" + convertTime(counter)));
			counter--;
		}
		else {
			stop();
			Main.runningTasks.remove(this);
		}
	}

	public void stop() {
		if (counter <= 0 || (player.isDead() && player.isOnline())) {
			Main.effectTime.remove(player.getUniqueId());
			Main.effect.remove(player.getUniqueId());
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
					TextComponent.fromLegacyText("§eBon retour sur terre !"));
		} else {
			Main.effectTime.putIfAbsent(player.getUniqueId(), counter);
		}

		this.player.setAllowFlight(false);
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
