package net.castegaming.plugins.LoginPremium;

import org.bukkit.entity.Player;

import com.massivecraft.factions.P;

public class Factions1678convertor extends FactionsConvertor {

	@Override
	public String getPlayerTag(Player player, String string) {
		String tag =  P.p.getPlayerFactionTag(player);
		if (tag.startsWith("~") && LoginPremium.plugin.getConfig().getBoolean("disablefactionswildernesstag", true)){
			return "";
		} else {
			return tag + " ";
		}
	}

	@Override
	public String getPlayerTitle(Player player, String string) {
		String title = P.p.getPlayerTitle(player);
		if (title.length() < 1){
			return "";
		} else {
			return title + " ";
		}
	}

}
