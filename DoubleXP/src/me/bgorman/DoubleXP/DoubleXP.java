package me.bgorman.DoubleXP;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DoubleXP extends JavaPlugin implements Listener{	
	
	int xpMultiplier;
	int playerCount;
	
	public void onEnable() {
		this.saveDefaultConfig();
		getServer().getPluginManager().registerEvents((Listener) this, this);
	    Logger.info("enabled");
		xpMultiplier = getConfig().getInt("dxp");
		playerCount = getConfig().getInt("dpc");
	}	  	
	public void onDisable() {
		Logger.info("disabled");
	}	
	
	public void logToFile(String message){		
		try {
			File dataFolder = getDataFolder();
			if(!dataFolder.exists()) dataFolder.mkdir();
			
			File saveTo = new File(getDataFolder(), "data.txt");
			if(!saveTo.exists()) saveTo.createNewFile();
					
			FileWriter fw = new FileWriter(saveTo, true);
			PrintWriter pw = new PrintWriter(fw);
			
			pw.println(message);		
			pw.flush();
			pw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {		
		if(command.getName().equalsIgnoreCase("dxp") && sender.isOp()){			
			if (args.length != 1) {
				sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.DARK_GRAY + "DoubleXP" + ChatColor.AQUA + "] " + "xp multiplier is currently " + Integer.toString(xpMultiplier));
				return true;
			}
			xpMultiplier = Integer.parseInt(args[0]);
			sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.DARK_GRAY + "DoubleXP" + ChatColor.AQUA + "] " + "xp multiplier is now " + args[0]);
			Date dt = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = df.format(dt);
			logToFile("[" + time + "] " + sender.getName() + " changed dxp to " + Integer.toString(xpMultiplier));
		}		
		if(command.getName().equalsIgnoreCase("dpc") && sender.isOp()){			
			if (args.length != 1) {
				sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.DARK_GRAY + "DoubleXP" + ChatColor.AQUA + "] " + "player count needed is currently " + Integer.toString(playerCount));
				return true;
			}
			playerCount = Integer.parseInt(args[0]);
			sender.sendMessage(ChatColor.AQUA + "[" + ChatColor.DARK_GRAY + "DoubleXP" + ChatColor.AQUA + "] " + "playercount needed for xp multiplier is now " + args[0]);
			Date dt = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = df.format(dt);
			logToFile("[" + time + "] " + sender.getName() + " changed dpc to " + Integer.toString(playerCount));
			if(Bukkit.getOnlinePlayers().size() < playerCount) {
				getServer().dispatchCommand(getServer().getConsoleSender(), "bc " + ChatColor.RED + Integer.toString(playerCount-Bukkit.getOnlinePlayers().size()) + ChatColor.GOLD + " more player(s) needed for xp multiplier!");
			}
			if(Bukkit.getOnlinePlayers().size() == playerCount) {
			getServer().dispatchCommand(getServer().getConsoleSender(), "bc " + ChatColor.RED + Integer.toString(xpMultiplier) + ChatColor.GOLD + " xp multiplier is now active!");
			}		
		}
		return true;
	}	
	@EventHandler
	public void onExpChange(PlayerExpChangeEvent e) {
		int exp = e.getAmount();		
		if (Bukkit.getOnlinePlayers().size() >= playerCount){
			e.setAmount(exp * xpMultiplier);
		}		
	}	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if(Bukkit.getOnlinePlayers().size() < playerCount) {
				getServer().dispatchCommand(getServer().getConsoleSender(), "bc " + ChatColor.RED + Integer.toString(playerCount-Bukkit.getOnlinePlayers().size()) + ChatColor.GOLD + " more player(s) needed for xp multiplier!");
		}
		if(Bukkit.getOnlinePlayers().size() == playerCount) {
			getServer().dispatchCommand(getServer().getConsoleSender(), "bc " + ChatColor.RED + Integer.toString(xpMultiplier) + ChatColor.GOLD + " xp multiplier is now active!");
		}
	}	
}