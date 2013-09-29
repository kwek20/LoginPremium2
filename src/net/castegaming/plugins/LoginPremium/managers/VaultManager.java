/**
 * 
 */
package net.castegaming.plugins.LoginPremium.managers;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
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
		String group = permission.getPrimaryGroup(player.getWorld(), player.getName());
		
		if (what.contains("prefix")){
			if (chat.getPlayerPrefix(player.getWorld(), player.getName()).length() > 0){
				return chat.getPlayerPrefix(player.getWorld(), player.getName());
			} else {
				return chat.getGroupPrefix(player.getWorld(), group);
			}
		} else if (what.contains("suffix")){
			if (chat.getPlayerSuffix(player.getWorld(), player.getName()).length() > 0){
				return chat.getPlayerSuffix(player.getWorld(), player.getName());
			} else {
				return chat.getGroupSuffix(player.getWorld(), group);
			}
		} else if (what.contains("color")){
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
