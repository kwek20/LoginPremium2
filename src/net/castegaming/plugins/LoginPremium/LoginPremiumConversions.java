package net.castegaming.plugins.LoginPremium;

import java.util.Date;
import java.util.LinkedList;

import net.castegaming.plugins.LoginPremium.managers.Manager;
import net.castegaming.plugins.LoginPremium.util.Parse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
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
		result = addSpaces(result);
		result = result.replaceAll("\\<.*?\\>", "");
		
		return result;
	}
	
	/**
	 * @param result
	 * @return 
	 */
	private static String addSpaces(String result) {
		String finalResult = "";
		for (String s : result.split("<space>")){
			if (s.equals("")) continue;
			if (!s.endsWith(" "))s += " ";
			finalResult += s;
		}
		return finalResult;
	}

	public static void addManager(Manager m){
		managers.add(m);
	}

	public static void clearManagers() {
		managers.clear();
	}

	/**
	 * @param location
	 * @return
	 */
	public static String toLocString(Location l) {
		return l.getWorld().getName() + ":" + l.getX() + ":" + l.getY() + ":" + l.getZ() + ":" + l.getYaw() + ":" + l.getPitch();
	}

	/**
	 * @param object
	 * @return
	 */
	public static Location toLocation(String loc) {
		String[] strings = loc.split(":");;
		if (strings.length == 1) return null;
		
		World w = Bukkit.getServer().getWorld(strings[0]);
		if (w == null) return null;
		
		double[] doubles = new double[5];
		
		for (int i=0; i < 5; i++){
			Double d;
			if ((d = Parse.parseDouble(strings[i+1])) == null){
				return null;
			}
			doubles[i] = d;
		}
		
		return new Location(w, doubles[0], doubles[1], doubles[2], (float) doubles[3], (float) doubles[4]);
	}
}
