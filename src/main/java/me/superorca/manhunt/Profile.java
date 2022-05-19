package me.superorca.manhunt;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Profile {
    public static Map<Player, Profile> profiles = new HashMap<>();
    private final Map<World, Location> location = new HashMap<>();
    private final Player player;
    private Profile tracking;
    private Role role;

    public Profile(final Player player) {
        this.player = player;
    }

    public static Profile get(final Player player) {
        return profiles.get(player);
    }

    public static Profile add(final Player player) {
        return profiles.put(player, new Profile(player));
    }

    public static void remove(final Player player) {
        profiles.remove(player);
    }

    public static Collection<Profile> getProfiles() {
        return profiles.values();
    }

    public void update() {
        this.location.put(player.getWorld(), player.getLocation());
    }

    public Location getLastLocation(World world) {
        return location.get(world);
    }

    public Profile getTracking() {
        return tracking;
    }

    public void setTracking(Profile profile) {
        this.tracking = profile;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Player getPlayer() {
        return player;
    }
}
