package me.lataverne.recompensesvote.tasks;

import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class PotionBrewTask extends BukkitRunnable {
    private BrewerInventory inventory;
    private Integer firstModelData;
    private Integer secondModelData;
    private Integer thirdModelData;

    public PotionBrewTask(BrewerInventory inventory, Integer firstModelData, Integer secondModelData, Integer thirdModelData) {
        this.inventory = inventory;
        this.firstModelData = firstModelData;
        this.secondModelData = secondModelData;
        this.thirdModelData = thirdModelData;
    }

    @Override
    public void run() {
        if (firstModelData != null) {
            ItemMeta meta = inventory.getItem(0).getItemMeta();
            meta.setCustomModelData(firstModelData);
            inventory.getItem(0).setItemMeta(meta);
        }
        if (secondModelData != null) {
            ItemMeta meta = inventory.getItem(1).getItemMeta();
            meta.setCustomModelData(secondModelData);
            inventory.getItem(1).setItemMeta(meta);
        }
        if (thirdModelData != null) {
            ItemMeta meta = inventory.getItem(2).getItemMeta();
            meta.setCustomModelData(thirdModelData);
            inventory.getItem(2).setItemMeta(meta);
        }
    }
}