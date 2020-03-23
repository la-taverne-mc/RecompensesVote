package me.lataverne.recompensesvote.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.lataverne.recompensesvote.Items;
import me.lataverne.recompensesvote.Main;
import me.lataverne.recompensesvote.tasks.CreeperEffect;
import me.lataverne.recompensesvote.tasks.FlyEffect;
import me.lataverne.recompensesvote.tasks.PhantomEffect;
import me.lataverne.recompensesvote.tasks.RemovePotionEffectsTask;

public class PotionConsumeListener implements Listener {
	private final Plugin plugin;
	
	public PotionConsumeListener() {
		plugin = Bukkit.getPluginManager().getPlugin("RecompensesVote");
	}
	
	@EventHandler
	public void onConsume(PlayerItemConsumeEvent event) {
		if (event.getItem().getType() == Material.MILK_BUCKET) {
			if (Main.runningTasks.containsKey(event.getPlayer().getUniqueId().toString())) {
				Object task = Main.runningTasks.get(event.getPlayer().getUniqueId().toString());
				String className = task.getClass().getSimpleName();

				if (className.equals("CreeperEffect")) {
					((CreeperEffect) task).stop();
				} 
				else if (className.equals("FlyEffect")) {
					((FlyEffect) task).stop();
				} 
				else if (className.equals("PhantomEffect")) {
					((PhantomEffect) task).stop();
				}

				Main.runningTasks.remove(event.getPlayer().getUniqueId().toString());
				new RemovePotionEffectsTask(event.getPlayer().getUniqueId()).runTaskLater(plugin, 40);
			}
		}

        else if (Items.MINING_POTION.compareTo(event.getItem())) {
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 12000, 2));
		}

		else if (Items.SWIMING_POTION.compareTo(event.getItem())) {
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 12000, 0));
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 12000, 0));
		}

		else if (Items.PHANTOM_POTION.compareTo(event.getItem())) {
			if (Main.effect.containsKey(event.getPlayer().getUniqueId())) {
				event.getPlayer().sendMessage("§eTu ne peux pas boire cette potion tant que tu en as une autre active");
				event.setCancelled(true);
				return;
			}

			new PhantomEffect(event.getPlayer(), 1200).runTaskTimer(plugin, 0, 20);
		}

		else if (Items.CREEPER_POTION.compareTo(event.getItem())) {
			if (Main.effect.containsKey(event.getPlayer().getUniqueId())) {
				event.getPlayer().sendMessage("§eTu ne peux pas boire cette potion tant que tu en as une autre active");
				event.setCancelled(true);
				return;
			}

			new CreeperEffect(event.getPlayer(), 1200).runTaskTimer(plugin, 0, 20);
		}
		
		else if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
			if (Items.FLY_1.compareTo(event.getItem())) {
				if (Main.effect.containsKey(event.getPlayer().getUniqueId())) {
					event.getPlayer().sendMessage("§eTu ne peux pas boire cette potion tant que tu en as une autre active");
					event.setCancelled(true);
					return;
				}
				
				new FlyEffect(event.getPlayer(), 150).runTaskTimer(plugin, 0, 20);
			}
			
			else if (Items.FLY_2.compareTo(event.getItem())) {
				if (Main.effect.containsKey(event.getPlayer().getUniqueId())) {
					event.getPlayer().sendMessage("§eTu ne peux pas boire cette potion tant que tu en as une autre active");
					event.setCancelled(true);
					return;
				}
				
				new FlyEffect(event.getPlayer(), 300).runTaskTimer(plugin, 0, 20);
			}
			
			else if (Items.FLY_3.compareTo(event.getItem())) {
				if (Main.effect.containsKey(event.getPlayer().getUniqueId())) {
					event.getPlayer().sendMessage("§eTu ne peux pas boire cette potion tant que tu en as une autre active");
					event.setCancelled(true);
					return;
				}
				
				new FlyEffect(event.getPlayer(), 600).runTaskTimer(plugin, 0, 20);
			}
			
			else if (Items.FLY_4.compareTo(event.getItem())) {
				if (Main.effect.containsKey(event.getPlayer().getUniqueId())) {
					event.getPlayer().sendMessage("§eTu ne peux pas boire cette potion tant que tu en as une autre active");
					event.setCancelled(true);
					return;
				}
				
				new FlyEffect(event.getPlayer(), 1200).runTaskTimer(plugin, 0, 20);
			}
        }
        
        if (event.getItem().getType() == Material.POTION && event.getItem().getItemMeta().hasCustomModelData()) {
			ItemStack emptyBottle = new ItemStack(Material.GLASS_BOTTLE);
			ItemMeta emptyBottleMeta = emptyBottle.getItemMeta();
			emptyBottleMeta.setCustomModelData(event.getItem().getItemMeta().getCustomModelData());
			emptyBottle.setItemMeta(emptyBottleMeta);

			Player player = event.getPlayer();
			Integer slot; 
			if (player.getInventory().getItemInMainHand().equals(event.getItem())) {
				slot = player.getInventory().getHeldItemSlot();
			}
			else {
				slot = 40;
			}

			new BukkitRunnable() {
				@Override
				public void run() {
					player.getInventory().setItem(slot, emptyBottle);
				}
			}.runTask(plugin);
		}
	}
}