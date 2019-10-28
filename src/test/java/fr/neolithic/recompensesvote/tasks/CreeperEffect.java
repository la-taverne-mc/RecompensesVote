package fr.neolithic.recompensesvote.tasks;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.neolithic.recompensesvote.Main;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class CreeperEffect extends BukkitRunnable {
	private Player player;
	private int counter;
	
	public CreeperEffect(Player player, int counter) {
		this.player = player;
		this.counter = counter;
		
		if (!Main.effect.containsKey(this.player.getName())) {
			Main.effect.put(this.player.getName(), "creeper");
			Main.effectTime.put(this.player.getName(), this.counter);
		}
	}
	
	@Override
	public void run() {
		if (player.isOnline() && !player.isDead() && counter > 0) {
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§eIl te reste §a" + convertTime(counter)));
			
			for (Entity entity : player.getNearbyEntities(16.0, 16.0, 16.0)) {
				if (entity.getType().equals(EntityType.CREEPER) && entity.getName() == null && !entity.getScoreboardTags().contains("scared")) {
					ActiveMob mob = MythicMobs.inst().getMobManager().spawnMob("ScaredCreeper", entity.getLocation());
					mob.getEntity().getBukkitEntity().teleport(entity);
					entity.remove();
				}
			}
			
			counter--;
		}
		else {
			if (counter <= 0 || (player.isDead() && player.isOnline())) {
				Main.effect.remove(player.getName());
				Main.effectTime.remove(player.getName());
			}
			else {
				Main.effectTime.replace(player.getName(), counter);
			}
			
			this.cancel();
		}
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
