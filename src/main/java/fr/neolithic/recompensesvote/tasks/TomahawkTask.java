package fr.neolithic.recompensesvote.tasks;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.neolithic.recompensesvote.listeners.EntityHider;

public class TomahawkTask extends BukkitRunnable {
    private Trident trajectoryGuide;

    public TomahawkTask(EntityHider entityHider, Location loc, Vector velocity) {
        trajectoryGuide = (Trident) loc.getWorld().spawnEntity(loc, EntityType.TRIDENT);
        trajectoryGuide.setVelocity(velocity);
        for (Entity entity : trajectoryGuide.getNearbyEntities(64, 64, 64)) {
            if (entity instanceof Player) {
                entityHider.hideEntity((Player) entity, trajectoryGuide);
            }
        }
    }

    @Override
    public void run() {
        if (trajectoryGuide.isInBlock()) {
            trajectoryGuide.remove();
            this.cancel();
        }
    }
}