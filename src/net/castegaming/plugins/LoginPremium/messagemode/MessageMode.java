package net.castegaming.plugins.LoginPremium.messagemode;

import org.bukkit.entity.Player;

/**
 * A {@link MessageMode} defines in what manner we inform the {@link Player} of something<br/>
 * <br/>
 * Project GhastLegionUtil<br/>
 * Class net.brord.plugins.ghastlegionutil.util.counter.CounterMessageMode.java<br/>
 * @author Brord
 * @since 16 jul. 2014, 01:07:03
 */
public interface MessageMode {

	/**
	 * We want to message a player! How? 0.o
	 * @param player The player to send to
	 * @param message the message
	 */
	void message(Player player, String message);
	
	/**
	 * We want to clear the {@link Player} message<br/>
	 * This is sometimes needed
	 * @param player the player to clear
	 */
	void clear(Player player);
}
