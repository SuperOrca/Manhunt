package me.superorca.manhunt.events;

import me.superorca.manhunt.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
        final Player player = e.getPlayer();

        Profile.add(player).update();
    }
}
