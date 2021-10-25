package ru.thehelpix.vortexinventory.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Log {

    public static String parse(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static void log(String text) {
        Bukkit.getConsoleSender().sendMessage(parse(text));
    }
}
