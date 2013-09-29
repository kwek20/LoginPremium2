package net.castegaming.plugins.LoginPremium.convertors;

import org.bukkit.entity.Player;

public abstract class FactionsConvertor {

	public abstract String getPlayerTag(Player player, String string);
	
	public abstract String getPlayerTitle(Player player, String string);
}
