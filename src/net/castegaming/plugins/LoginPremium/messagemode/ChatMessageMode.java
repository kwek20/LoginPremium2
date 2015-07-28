package net.castegaming.plugins.LoginPremium.messagemode;

import org.bukkit.entity.Player;

/**
 * {@link ChatMessageMode} is a MessageMode based on chat. <br/>
 * Its intent is to send a message through chat<br/>
 * <br/>
 * Project GhastLegionUtil<br/>
 * Class net.brord.plugins.ghastlegionutil.util.counter.CounterChatMessageMode.java<br/>
 * @author Brord
 * @since 16 jul. 2014, 01:08:11
 */
public class ChatMessageMode implements MessageMode {

	/**
	 * @see net.MessageMode.plugins.ghastlegionutil.util.counter.message.CounterMessageMode#message(org.bukkit.entity.Player)
	 */
	@Override
	public void message(Player player, String message) {
		player.sendMessage(message);
	}

	/**
	  * @see net.MessageMode.plugins.ghastlegionutil.util.counter.message.CounterMessageMode#clear(org.bukkit.entity.Player)
	  */
	@Override
	public void clear(Player player) {
		//no need to clear
	}

}
