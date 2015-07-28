package net.castegaming.plugins.LoginPremium.messagemode;


import net.castegaming.plugins.LoginPremium.util.StatusBarAPI;

import org.bukkit.entity.Player;

/**
 * A MessageMode based on the dragon/boss health bar.<br/>
 * <br/>
 * Project GhastLegionUtil<br/>
 * Class net.brord.plugins.ghastlegionutil.util.counter.message.DragonBarMessageMode.java<br/>
 * @author Brord
 * @since 16 jul. 2014, 01:17:44
 */
public class DragonBarMessageMode implements MessageMode {

	/**
	  * @see net.MessageMode.plugins.ghastlegionutil.util.counter.message.CounterMessageMode#message(org.bukkit.entity.Player, java.lang.String)
	  */
	@Override
	public void message(Player player, String message) {
		StatusBarAPI.setStatusBar(player, message, 0);
	}

	/**
	  * @see net.MessageMode.plugins.ghastlegionutil.util.counter.message.CounterMessageMode#clear(org.bukkit.entity.Player)
	  */
	@Override
	public void clear(Player player) {
		StatusBarAPI.removeStatusBar(player);
	}
}
