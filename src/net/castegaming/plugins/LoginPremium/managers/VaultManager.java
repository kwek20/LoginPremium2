/**
 * 
 */
package net.castegaming.plugins.LoginPremium.managers;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * @author Brord
 *
 */
public class VaultManager extends PrefixManager {
	
	public Permission permission;
	public Chat chat;

	public VaultManager() {
		setupPermissions();
		setupChat();
	}
	
	@Override
	public String getPlayerPrefix(Player player, String what){
		OfflinePlayer p = Bukkit.getOfflinePlayer(player.getUniqueId());
		String wn = player.getWorld().getName();
		String group = permission.getPrimaryGroup(wn, p);
		
		if (what.contains("prefix")){
			String prefix;
			if ((prefix = chat.getPlayerPrefix(wn, p)) != null && prefix.length() > 0){
				return chat.getPlayerPrefix(wn, p);
			} else {
				return chat.getGroupPrefix(wn, group);
			}
		} else if (what.contains("suffix")){
			String suffix;
			if ((suffix = chat.getPlayerSuffix(wn, p))!= null && suffix.length() > 0){
				return suffix;
			} else {
				return chat.getGroupSuffix(player.getWorld(), group);
			}
		} else if (what.contains("color")){
			String prefix;
			if ((prefix = chat.getPlayerPrefix(wn, p)) != null && prefix.length() > 0){
				
			} else {
				prefix = chat.getGroupPrefix(wn, group);
			}
			
			if(prefix.length() > 1){
				if (prefix.startsWith("&")) {
					prefix = prefix.substring(0 , 2);
				} else if (prefix.substring(0, prefix.length()-1).endsWith("&")){
					prefix = prefix.substring(prefix.length()-2, prefix.length());
				}
			} else {prefix = "";}
			return prefix;
		} else {
			return "";
		}
	}
	
	public boolean setupPermissions(){
        RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }
	
	public boolean setupChat(){
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

}
