package me.superorca.manhunt.events;

import me.superorca.manhunt.Profile;
import me.superorca.manhunt.Role;
import me.superorca.manhunt.commands.ManhuntCommand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.Random;

import static me.superorca.manhunt.Utils.F;
import static me.superorca.manhunt.Utils.sendActionBar;

public class InteractEvent implements Listener {
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        final World world = player.getWorld();
        final Action action = e.getAction();
        final ItemStack item = e.getItem();
        final Profile profile = Profile.get(player);

        if (profile.getRole() != Role.HUNTER) return;
        if (item == null || item.getType() != Material.COMPASS) return;

        final CompassMeta meta = (CompassMeta) item.getItemMeta();
        if (meta == null) return;

        boolean update = updateCompass(profile, world, player, meta);

        if (update) {
            if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                profile.setTracking(ManhuntCommand.getSpeedrunners().get(new Random().nextInt(ManhuntCommand.getSpeedrunners().size())));
                sendActionBar(player, F("&fChanged tracker to track &a%s&f.", profile.getTracking().getPlayer().getName()));
                player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
            } else {
                sendActionBar(player, F("&fUpdated location of &a%s&f.", profile.getTracking().getPlayer().getName()));
            }
        } else {
            sendActionBar(player, F("&cUnable to track. Make sure you have selected a player to track by left clicking the compass."));
        }

        item.setItemMeta(meta);
    }

    public boolean updateCompass(Profile profile, World world, Player player, CompassMeta meta) {
        Profile tracking = profile.getTracking();

        if (tracking == null) return false;

        final Location location = profile.getTracking().getLastLocation(world);
        if (location == null) return false;
        player.setCompassTarget(location);

        if (world.getEnvironment() == World.Environment.NORMAL) {
            meta.setLodestone(null);
        } else {
            meta.setLodestone(location);
        }

        meta.setLodestoneTracked(false);

        return true;
    }
}
