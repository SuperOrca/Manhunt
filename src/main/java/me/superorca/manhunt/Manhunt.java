package me.superorca.manhunt;

import me.superorca.manhunt.commands.ManhuntCommand;
import me.superorca.manhunt.events.InteractEvent;
import me.superorca.manhunt.events.JoinEvent;
import me.superorca.manhunt.events.QuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public final class Manhunt extends JavaPlugin {

    @Override
    public void onEnable() {
        final BukkitCommandHandler ch = BukkitCommandHandler.create(this);
        final PluginManager pm = getServer().getPluginManager();

        ch.register(new ManhuntCommand());
        pm.registerEvents(new InteractEvent(), this);
        pm.registerEvents(new JoinEvent(), this);
        pm.registerEvents(new QuitEvent(), this);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Profile profile : Profile.getProfiles()) {
                    profile.update();
                }
            }
        }.runTaskTimerAsynchronously(this, 0L, 50L);
    }
}
