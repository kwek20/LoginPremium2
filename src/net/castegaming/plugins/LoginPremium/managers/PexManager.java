/**
 * 
 */
package net.castegaming.plugins.LoginPremium.managers;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 * @author Brord
 *
 */
public class PexManager extends PrefixManager {
	
	@Override
	public String getPlayerPrefix(Player player, String what){
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
	}

}
