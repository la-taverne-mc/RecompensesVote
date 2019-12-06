package fr.neolithic.recompensesvote.tasks;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import fr.neolithic.recompensesvote.Items;
import fr.neolithic.recompensesvote.util.EntityHider;

public class IndianSpearTask extends BukkitRunnable {
    private Trident trident;

    public IndianSpearTask(EntityHider entityHider, Player player, Trident trident, Integer damage) {
        this.trident = trident;

        if (damage + 2 <= 249) {
            ItemStack tridentItem = Items.INDIAN_SPEAR.getItem();
            Damageable tridentItemMeta = (Damageable) tridentItem.getItemMeta();
            tridentItemMeta.setDamage(damage + 2);
            tridentItem.setItemMeta((ItemMeta) tridentItemMeta);

            if (Items.INDIAN_SPEAR.compareTo(player.getInventory().getItemInMainHand())) {
                player.getInventory().setItemInMainHand(tridentItem);
            }
            else if (Items.INDIAN_SPEAR.compareTo(player.getInventory().getItemInOffHand())) {
                player.getInventory().setItemInOffHand(tridentItem);
            }
            else {
                ItemStack currentTrident = Items.INDIAN_SPEAR.getItem();
                Damageable currentTridentMeta = (Damageable) currentTrident.getItemMeta();
                currentTridentMeta.setDamage(damage);
                currentTrident.setItemMeta((ItemMeta) currentTridentMeta);

                player.getInventory().setItem(player.getInventory().first(currentTrident), tridentItem);
            }
        }
        else {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1, 1);
        }

        for (Entity entity : trident.getNearbyEntities(64, 64, 64)) {
            if (entity instanceof Player) {
                entityHider.hideEntity((Player) entity, trident);
            }
        }

        player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1, 1);
    }

    @Override
    public void run() {
        trident.remove();
    }
}