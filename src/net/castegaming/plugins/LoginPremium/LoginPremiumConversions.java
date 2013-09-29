package net.castegaming.plugins.LoginPremium;

import java.util.Date;
import java.util.LinkedList;

import net.castegaming.plugins.LoginPremium.managers.Manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LoginPremiumConversions {
	
	private static LinkedList<Manager> managers = new LinkedList<Manager>();
	
	//changes an array to a string
	public static String ArrayToString(String[] args){
		String result = "";
        if (args.length > 0) {
            result = args[2];
            for (int i=3; i<args.length; i++) {
                result = result + " " + args[i];
            }
        }
		return result;
	}
	
	//Changess the text to text with variables
	public static String changeTextToContent(String text, Player player){
		String result = text;
		result = result.replace("<name>", player.getName());
		result = result.replace("<nickname>", player.getDisplayName());
		result = result.replace("<xplevel>", String.valueOf(player.getLevel()));
		result = result.replace("<world>", player.getWorld().getName());
		result = result.replace("<ip>", String.valueOf(player.getAddress()));
		result = result.replace("<op>", String.valueOf(player.isOp()));
		result = result.replace("<maxplayers>", String.valueOf(player.getServer().getMaxPlayers()));
		result = result.replace("<whitelisted>", String.valueOf(player.isWhitelisted()));
		result = result.replace("<motd>", player.getServer().getMotd());
		result = result.replace("<viewdistance>", String.valueOf(player.getServer().getViewDistance()));;
		result = result.replace("<playertime>", String.valueOf(player.getPlayerTime()));
		result = result.replace("<lastlogin>", String.valueOf(new Date(Bukkit.getServer().getOfflinePlayer(player.getName()).getLastPlayed())));
		result = result.replace("<firstlogin>", String.valueOf(new Date(Bukkit.getServer().getOfflinePlayer(player.getName()).getFirstPlayed())));
		result = result.replace("<gamemode>", player.getGameMode().toString());
		
		for (Manager m : managers){
			result = m.Convert(player, result);
		}
		
		result = ChatColor.translateAlternateColorCodes('&', result);
		result = result.replaceAll("\\<.*?\\>", "");
		
		return result;
	}
	
	public static void addManager(Manager m){
		managers.add(m);
	}

	public static void clearManagers() {
		managers.clear();
	}
}
