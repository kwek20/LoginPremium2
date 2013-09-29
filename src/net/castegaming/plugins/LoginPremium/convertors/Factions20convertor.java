package net.castegaming.plugins.LoginPremium.convertors;

import net.castegaming.plugins.LoginPremium.LoginPremium;

import org.bukkit.entity.Player;

import com.massivecraft.factions.entity.UPlayer;

public class Factions20convertor extends FactionsConvertor {

	@Override
	public String getPlayerTag(Player player, String string) {
		UPlayer uplayer = UPlayer.get(player);
		if (uplayer != null){
			if (uplayer.hasFaction() && !uplayer.getFaction().isDefault() || LoginPremium.plugin.getConfig().getBoolean("disablefactionswildernesstag", true)){
				return uplayer.getFaction().getName();
			} else {
				return "";
			}
		} else {
			return "";
		}
		
	}

	@Override
	public String getPlayerTitle(Player player, String string) {
		UPlayer uplayer = UPlayer.get(player);
		if (uplayer != null){
			if (uplayer.hasFaction()){
				if (uplayer.hasTitle()){
					return uplayer.getTitle();
				} else {
					return "";
				}
			} else {
				return "";
			}
		} else {
			return "";
		}
		
	}

}
