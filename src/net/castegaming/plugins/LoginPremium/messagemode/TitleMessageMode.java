package net.castegaming.plugins.LoginPremium.messagemode;

import net.castegaming.plugins.LoginPremium.util.Title;

import org.bukkit.entity.Player;

/**
 * Project GhastLegionUtil<br/>
 * Class net.brord.plugins.ghastlegionutil.util.counter.message.TitleMessageMode.java<br/>
 * @author Brord
 * @since Jul 22, 2015, 2:21:26 AM
 */
public class TitleMessageMode implements MessageMode {

	/**
	 * @see net.MessageMode.plugins.ghastlegionutil.util.counter.message.CounterMessageMode#message(org.bukkit.entity.Player, java.lang.String)
	 */
	@Override
	public void message(Player player, String message) {
		//1 second total 5 + 10 + 5 = 20 ticks
		new Title(message, "", 5, 10, 5).send(player);
	}

	/**
	 * @see net.MessageMode.plugins.ghastlegionutil.util.counter.message.CounterMessageMode#clear(org.bukkit.entity.Player)
	 */
	@Override
	public void clear(Player player) {
		
	}

}
