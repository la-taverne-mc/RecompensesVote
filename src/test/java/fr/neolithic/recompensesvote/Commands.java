package fr.neolithic.recompensesvote;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

public class Commands implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("test") && sender instanceof Player) {
			Player player = (Player) sender;
			for(Map.Entry<String, String> entry : Main.effect.entrySet()) {
				player.sendMessage(entry.getKey() + " : " + entry.getValue() + " : " + String.valueOf(Main.effectTime.get(entry.getKey())));
			}
			
			return true;
		}
		
		else if (command.getName().equalsIgnoreCase("selectvotereward")) {
			Player receiver = null;
			Float randomPercent = new Random().nextFloat();
			Float lastPercent = 0.f;
			Float total = 0.f;
			
			if (args.length != 1 && sender instanceof Player) {
				receiver = (Player) sender;
			}
			else if (Bukkit.getPlayerExact(args[0]) != null) {
				receiver = Bukkit.getPlayerExact(args[0]);
			}
			else {
				sender.sendMessage("§cInvalid player name");
			}
			
			for (Map.Entry<ItemStack, Float> reward : Main.votingRewards.entrySet()) {
				total += reward.getValue();
			}
			
			for (Map.Entry<ItemStack, Float> reward : Main.votingRewards.entrySet()) {
				Float percent = reward.getValue() / total;
				if (randomPercent >= lastPercent && randomPercent < lastPercent + percent) {
					receiver.getInventory().addItem(reward.getKey());
					return true;
				}
				lastPercent += percent;
			}
			
			return false;
		}
		
		else if (command.getName().equalsIgnoreCase("item") && sender instanceof Player && args.length == 1) {
			Player player = (Player) sender;
			if (Main.items.containsKey(args[0])) {
				player.getInventory().addItem(Main.items.get(args[0]));
			}
			else {
				player.sendMessage("§cInvalid item name");
			}
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("item")) {
			if (args.length == 1) {
				List<String> list = Lists.newArrayList();
				
				for (Map.Entry<String, ItemStack> item : Main.items.entrySet()) {
					if (item.getKey().toLowerCase().startsWith(args[0].toLowerCase())) {
						list.add(item.getKey());
					}
				}
				
				return list;
			}

			return null;
		}
		
		return null;
	}
}
