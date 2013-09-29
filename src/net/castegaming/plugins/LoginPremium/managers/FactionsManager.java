package net.castegaming.plugins.LoginPremium.managers;

import net.castegaming.plugins.LoginPremium.LoginPremium;
import net.castegaming.plugins.LoginPremium.convertors.Factions1678convertor;
import net.castegaming.plugins.LoginPremium.convertors.Factions20convertor;
import net.castegaming.plugins.LoginPremium.convertors.FactionsConvertor;

import org.bukkit.entity.Player;

public class FactionsManager extends Manager {
	
	private FactionsConvertor factions;

	public FactionsManager() {
		setupFactions();
	}

	@Override
	public String Convert(Player player, String what) {
		what = what.replace("<title>", factions.getPlayerTitle(player, "title"));
		what = what.replace("<tag>", factions.getPlayerTag(player, "tag"));
		return what;
	}
	
	public void setupFactions(){
		String version = LoginPremium.getFactionsVersion();
		if (version.startsWith("2.")){
			factions = new Factions20convertor();
		} else if (version.startsWith("1.6") || version.startsWith("1.7") || version.startsWith("1.8")){
			factions = new Factions1678convertor();
		}
	}

}
