package me.bgorman.DoubleXP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger
{
  private static void log(String msg)
  {
    msg = ChatColor.translateAlternateColorCodes('&', "&f[&aDoubleXP&f]&a by bgorman " + msg);
    Bukkit.getServer().getConsoleSender().sendMessage(msg);
  }

  public static void info(String msg) {
    log(msg);
  }
}