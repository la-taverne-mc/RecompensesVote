package me.lataverne.recompensesvote.tasks;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import me.lataverne.recompensesvote.Main;

public class BottleFillTask extends BukkitRunnable {
    private final Player player;
    private final int modelData;
    private int slot;
    
    public BottleFillTask(Player player, int modelData, int slot) {
        this.player = player;
        this.modelData = modelData;
        if (player.getInventory().firstEmpty() == -1) {
            this.slot = -1;
        }
        else {
            this.slot = slot;
        }

        Main.fillingBottle.put(player, modelData);
    }

    @Override
    public void run() {
        ItemStack waterBottle = new ItemStack(Material.POTION);
        PotionMeta waterBottleMeta = (PotionMeta) waterBottle.getItemMeta();
        waterBottleMeta.setBasePotionData(new PotionData(PotionType.WATER));
        waterBottle.setItemMeta(waterBottleMeta);

        if (slot != -1) {
            if (player.getInventory().getItem(slot).getType() == Material.GLASS_BOTTLE && player.getInventory().getItem(slot).getAmount() > 1) {
                this.slot = player.getInventory().first(waterBottle);
            }
    
            if (!player.getInventory().getItem(slot).getItemMeta().hasCustomModelData()) {
                ItemMeta itemMeta = player.getInventory().getItem(slot).getItemMeta();
                itemMeta.setCustomModelData(modelData);
                player.getInventory().getItem(slot).setItemMeta(itemMeta);
            }
        }
        
        Main.fillingBottle.remove(player);
    }
}