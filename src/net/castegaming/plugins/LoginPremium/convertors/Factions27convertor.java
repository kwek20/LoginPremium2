package net.castegaming.plugins.LoginPremium.convertors;

import net.castegaming.plugins.LoginPremium.LoginPremium;

import org.bukkit.entity.Player;

import com.massivecraft.factions.entity.MPlayer;

public class Factions27convertor extends FactionsConvertor {

	@Override
	public String getPlayerTag(Player player, String string) {
		MPlayer mplayer = MPlayer.get(player);
		if (mplayer.hasFaction()){
			if (!mplayer.getFaction().isDefault() || LoginPremium.plugin.getConfig().getBoolean("disablefactionswildernesstag", true)){
				return mplayer.getFaction().getName();
			} else {
				return "";
			}
		} else {
			return "";
		}
		
	}

	@Override
	public String getPlayerTitle(Player player, String string) {
		MPlayer mplayer = MPlayer.get(player);
		if (mplayer != null){
			if (mplayer.hasFaction()){
				if (mplayer.hasTitle()){
					return mplayer.getTitle();
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
