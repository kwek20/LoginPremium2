package net.castegaming.plugins.LoginPremium.managers;

import org.bukkit.entity.Player;

public abstract class PrefixManager extends Manager {

	public PrefixManager() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String Convert(Player player, String what) {
		what = what.replace("<prefix>", getPlayerPrefix(player, "prefix"));
		what = what.replace("<prefixcolor>", getPlayerPrefix(player, "color"));
		what = what.replace("<suffix>", getPlayerPrefix(player, "suffix"));
		return what;
	}
	
	public abstract String getPlayerPrefix(Player player, String what);

}
