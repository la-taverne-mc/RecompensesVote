package fr.neolithic.recompensesvote.tasks;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import fr.neolithic.recompensesvote.listeners.EntityHider;

public class TomahawkTask extends BukkitRunnable {
    private Trident trajectoryGuide;
    private ArmorStand tomahawkHolder;

    public TomahawkTask(ItemStack tomahawk, EntityHider entityHider, Location loc, Vector velocity) {
        trajectoryGuide = (Trident) loc.getWorld().spawnEntity(loc, EntityType.TRIDENT);
        trajectoryGuide.setBounce(false);
        trajectoryGuide.setGravity(false);
        trajectoryGuide.setVelocity(velocity.multiply(0.1));

        for (Entity entity : trajectoryGuide.getNearbyEntities(64, 64, 64)) {
            if (entity instanceof Player) {
                // entityHider.hideEntity((Player) entity, trajectoryGuide);
            }
        }

        tomahawkHolder = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0.375, -0.9, 0), EntityType.ARMOR_STAND);
        tomahawkHolder.setArms(true);
        tomahawkHolder.setRightArmPose(EulerAngle.ZERO);
        tomahawkHolder.getEquipment().setItemInMainHand(tomahawk);
        tomahawkHolder.setGravity(false);
        tomahawkHolder.setInvulnerable(true);
        tomahawkHolder.setVisible(true);
        tomahawkHolder.addScoreboardTag("tomahawk");
    }

    @Override
    public void run() {
        if (trajectoryGuide.isInBlock()) {
            trajectoryGuide.remove();
            this.cancel();
        }

        Location loc = trajectoryGuide.getLocation();
        Vector vec = new Vector(0.375, -0.9, 0);
        loc.add(vec);
        loc.setYaw(-loc.getYaw());

        tomahawkHolder.teleport(loc);
    }
}