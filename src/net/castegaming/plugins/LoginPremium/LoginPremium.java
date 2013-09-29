package net.castegaming.plugins.LoginPremium;

import net.castegaming.plugins.LoginPremium.managers.FactionsManager;
import net.castegaming.plugins.LoginPremium.managers.HeroesManager;
import net.castegaming.plugins.LoginPremium.managers.PexManager;
import net.castegaming.plugins.LoginPremium.managers.TownyManager;
import net.castegaming.plugins.LoginPremium.managers.VaultManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class LoginPremium extends JavaPlugin{

	public static LoginPremium plugin;
	
	public void onEnable(){
		this.saveDefaultConfig();
		checkConfig();
		plugin = this;
		PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new LoginPremiumPlayerListener(this), this);
        addManagers();
	}
	
	public void addManagers(){
		if (isPexEnabled()){
        	LoginPremiumConversions.addManager(new PexManager());
        	log("PermissionsEX " + getPEXVersion() + " has been found. Using it :3");
        } else {
        	if (isVaultEnabled()){
        		LoginPremiumConversions.addManager(new VaultManager());
        		log("Vault " + getVaultVersion() + " has been found! Enabling groups :3");
        	} else {
        		log("PermissionsEX has not been found. Disabling our pEX features :(");
        	}
        }
        if (isFactionsEnabled()){
        	LoginPremiumConversions.addManager(new FactionsManager());
        	log("Factions " + getFactionsVersion() + " has been found. Using it :3");
        } else {
        	log("Factions has not been found. Disabling our Factions features :(");
        }
        if (isTownyEnabled()){
        	LoginPremiumConversions.addManager(new TownyManager());
        	log("Towny " + getTownyVersion() + " has been found. Using it :3");
        } else {
        	log("Towny has not been found. Disabling our Towny features :(");
        }
        if (isHeroesEnabled()){
        	LoginPremiumConversions.addManager(new HeroesManager());
        	log("Heroes " + getHeroesVersion() + " has been found. Using it :3");
        } else {
        	log("Heroes has not been found. Disabling our Heroes features :(");
        }
	}
	
	public void checkConfig(){
		YamlConfiguration config = YamlConfiguration.loadConfiguration(getResource("config.yml"));
		for (String key : config.getKeys(false)){
			if (getConfig().get(key) == null){
				getConfig().set(key, config.get(key));
				log("Added the config value: " + key);
			}
		}
	}
	 
	public void onDisable(){
	}
	
	//inits
	private LoginPremiumCommandListener commands = new LoginPremiumCommandListener(this);
	
	//Sends every command to their own class (LoginPremiumCoommandListener)
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		return commands.commandHandler(sender, cmd, args);
	}
	
	public static boolean isPexEnabled(){
		if (isVaultEnabled()){
			return false;
		} else {
			return Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx") != null;
		}
	}
	
	public static boolean isTownyEnabled(){
		return Bukkit.getServer().getPluginManager().getPlugin("Towny") != null;
	}
	
	public static boolean isVaultEnabled(){
		return Bukkit.getServer().getPluginManager().getPlugin("Vault") != null;
	}
	
	public static boolean isFactionsEnabled(){
		return Bukkit.getServer().getPluginManager().getPlugin("Factions") != null;
	}

	public static String getFactionsVersion() {
		if (isFactionsEnabled()){
			return Bukkit.getServer().getPluginManager().getPlugin("Factions").getDescription().getVersion();
		} else {
			return "0";
		}
	}
	
	public static String getPEXVersion() {
		if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx") != null){
			return Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx").getDescription().getVersion();
		} else {
			return "0";
		}
	}
	
	public static String getVaultVersion() {
		if (isVaultEnabled()){
			return Bukkit.getServer().getPluginManager().getPlugin("Vault").getDescription().getVersion();
		} else {
			return "0";
		}
	}
	
	public static String getTownyVersion() {
		if (isTownyEnabled()){
			return Bukkit.getServer().getPluginManager().getPlugin("Towny").getDescription().getVersion();
		} else {
			return "0";
		}
	}
	
	public void log(String message){
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.DARK_RED + "LoginPremium " + getDescription().getVersion() + ChatColor.DARK_GREEN + "] " + ChatColor.RESET + message);
	}

	public static boolean isHeroesEnabled() {
		return Bukkit.getServer().getPluginManager().getPlugin("Heroes") != null;
	}
	
	public static String getHeroesVersion() {
		if (isHeroesEnabled()){
			return Bukkit.getServer().getPluginManager().getPlugin("Heroes").getDescription().getVersion();
		} else {
			return "0";
		}
	}
}
