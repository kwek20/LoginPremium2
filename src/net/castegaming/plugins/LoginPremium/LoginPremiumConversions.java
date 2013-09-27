package net.castegaming.plugins.LoginPremium;

import java.util.Date;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class LoginPremiumConversions {
	
	public static Permission permission;
	public static Chat chat;
	private static FactionsConvertor factions;
	
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
	
	//Changes the text to text with colors
	public static String changeTextToColors(String text){
    	text = ChatColor.translateAlternateColorCodes('&', text);
    	return text;
    }
	
	//Changess the text to text with variables
	public static String changeTextToContent(String text, Player player){
		
		String result = text.replace("<name>", player.getName());
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
		
		if (LoginPremium.isVaultEnabled() || LoginPremium.isPexEnabled()){
			result = result.replace("<prefix>", getPlayerPrefix(player, "prefix"));
			result = result.replace("<prefixcolor>", getPlayerPrefix(player, "color"));
			result = result.replace("<suffix>", getPlayerPrefix(player, "suffix"));
		}
		if (LoginPremium.isFactionsEnabled() && factions != null){
			result = result.replace("<title>", factions.getPlayerTitle(player, "title"));
			result = result.replace("<tag>", factions.getPlayerTag(player, "tag"));
		}
		if (LoginPremium.isTownyEnabled()){
			result = result.replace("<townname>", TownyConvertor.getTownyThings(player, "townname"));
			result = result.replace("<towntag>", TownyConvertor.getTownyThings(player, "towntag"));
			result = result.replace("<towntitle>", TownyConvertor.getTownyThings(player, "towntitle"));
			result = result.replace("<nationname>", TownyConvertor.getTownyThings(player, "nationname"));
			result = result.replace("<nationtag>", TownyConvertor.getTownyThings(player, "nationtag"));
			result = result.replace("<surname>", TownyConvertor.getTownyThings(player, "surname"));
			result = result.replace("<friendsonline>", TownyConvertor.getTownyThings(player, "friendsonline"));
		}
		result = result.replaceAll("\\<.*?\\>", "");
		return result;
	}
	
	/**
	 * prefix, suffix or color
	 * @param player
	 * @param what
	 * @return
	 */
	public static String getPlayerPrefix(Player player, String what){
		if (LoginPremium.isPexEnabled()){
			String string = "";
			if (what == "prefix"){
				return PermissionsEx.getUser(player).getPrefix(player.getWorld().toString());
			} else if (what == "suffix"){
				return PermissionsEx.getUser(player).getSuffix(player.getWorld().toString());
			} else if (what == "color"){
				string = PermissionsEx.getUser(player).getPrefix(player.getWorld().toString());
				if (string.startsWith("&")) {
					string = string.substring(0 , 2);
				}
				return string;
			} else {
				return "";
			}
		} else if (LoginPremium.isVaultEnabled()){
			String group = permission.getPrimaryGroup(player.getWorld(), player.getName());
			if (what == "prefix"){
				if (chat.getPlayerPrefix(player.getWorld(), player.getName()).length() > 0){
					return chat.getPlayerPrefix(player.getWorld(), player.getName());
				} else {
					return chat.getGroupPrefix(player.getWorld(), group);
				}
			} else if (what == "suffix"){
				if (chat.getPlayerSuffix(player.getWorld(), player.getName()).length() > 0){
					return chat.getPlayerSuffix(player.getWorld(), player.getName());
				} else {
					return chat.getGroupSuffix(player.getWorld(), group);
				}
			} else if (what == "color"){
				String prefix = chat.getPlayerPrefix(player.getWorld(), player.getName());
				if (prefix.length() < 1){
					prefix = chat.getGroupPrefix(player.getWorld(), group);
				}
				
				if (prefix.startsWith("&")) {
					prefix = prefix.substring(0 , 2);
				} else if (prefix.substring(0, prefix.length()-1).endsWith("&")){
					prefix = prefix.substring(prefix.length()-2, prefix.length());
				}
				return prefix;
			} else {
				return "";
			}
		} else {
			return "";
		}
	}
	
	public static boolean setupPermissions(){
        RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }
	
	public static boolean setupChat(){
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }
	
	public static void setupFactions(){
		String version = LoginPremium.getFactionsVersion();
		if (version.startsWith("2.")){
			factions = new Factions20convertor();
		} else if (version.startsWith("1.6") || version.startsWith("1.7") || version.startsWith("1.8")){
			factions = new Factions1678convertor();
		}
	}
}
