package me.superorca.manhunt.events;

import me.superorca.manhunt.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent e) {
        final Player player = e.getPlayer();

        Profile.remove(player);
    }
}
