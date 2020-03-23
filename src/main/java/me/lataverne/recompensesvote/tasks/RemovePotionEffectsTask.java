package me.lataverne.recompensesvote.tasks;

import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;

import me.lataverne.recompensesvote.Main;

public class RemovePotionEffectsTask extends BukkitRunnable {
    private final UUID playerUuid;

    public RemovePotionEffectsTask(UUID playerUuid) {
        this.playerUuid = playerUuid;
    }

    @Override
    public void run() {
        Main.effect.remove(playerUuid);
        Main.effectTime.remove(playerUuid);
    }
}