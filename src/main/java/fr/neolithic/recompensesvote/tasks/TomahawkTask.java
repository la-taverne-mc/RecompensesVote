package fr.neolithic.recompensesvote.tasks;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Trident;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class TomahawkTask extends BukkitRunnable {
    private Trident trajectoryGuide;

    public TomahawkTask(Location loc, Vector velocity) {
        trajectoryGuide = (Trident) loc.getWorld().spawnEntity(loc, EntityType.TRIDENT);
        trajectoryGuide.setVelocity(velocity);
    }

    @Override
    public void run() {
        if (trajectoryGuide.isInBlock()) {
            this.cancel();
        }
    }
}