package me.superorca.manhunt;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {
    public static String F(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String F(String message, Object... args) {
        return ChatColor.translateAlternateColorCodes('&', String.format(message, args));
    }

    public static void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }
}
