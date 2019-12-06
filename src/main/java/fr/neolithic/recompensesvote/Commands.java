package fr.neolithic.recompensesvote;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

public class Commands implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("selectvotereward")) {
			Player receiver = null;
			Float randomPercent = new Random().nextFloat();
			Float lastPercent = 0f;
			Float total = 0f;
			
			if (args.length < 1 && sender instanceof Player) {
				receiver = (Player) sender;
			}
			else if (Bukkit.getPlayerExact(args[0]) != null) {
				receiver = Bukkit.getPlayerExact(args[0]);
			}
			else {
				sender.sendMessage("§cInvalid player name");
			}
			
			for (Items item : Items.values()) {
				total += item.getRewardChance();
			}
			
			for (Items item  : Items.values()) {
				Float percent = item.getRewardChance() / total;
				if (randomPercent >= lastPercent && randomPercent < lastPercent + percent) {
					receiver.getInventory().addItem(item.getItem());
					return true;
				}
				lastPercent += percent;
			}
			
			return false;
		}
		
		else if (command.getName().equalsIgnoreCase("item") && sender instanceof Player && args.length == 1) {
			Player player = (Player) sender;
			if (Items.contains(args[0]) && Items.getItemNamed(args[0]).isCustom()) {
				player.getInventory().addItem(Items.getItemNamed(args[0]).getItem());
				return true;
			}
			else {
				player.sendMessage("§cInvalid item name");
				return false;
			}
		}

		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("item")) {
			if (args.length == 1) {
				List<String> list = Lists.newArrayList();
				
				for (Items item : Items.values()) {
					if (item.isCustom() && item.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
						list.add(item.getName());
					}
				}
				
				return list;
			}
		}
		
		return null;
	}
}
