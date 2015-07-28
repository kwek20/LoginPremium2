package net.castegaming.plugins.LoginPremium.messagemode;

import java.lang.reflect.Constructor;

import net.castegaming.plugins.LoginPremium.util.ReflectionUtils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Project LoginPremium<br/>
 * Class net.castegaming.plugins.LoginPremium.messagemode.AictionBarMessage.java<br/>
 * @author Brord
 * @since Jul 28, 2015, 1:45:12 AM
 */
public class ActionBarMessage implements MessageMode {

	/**
	 * @see net.castegaming.plugins.LoginPremium.messagemode.MessageMode#message(org.bukkit.entity.Player, java.lang.String)
	 */
	@Override
	public void message(Player player, String message) {
        sendMessage(player, message);
        return;
	}

	/**
	 * @param message
	 */
	private void sendMessage(Player player, String message) {
		try {
        	Object icbc;
            if(getServerVersion().equalsIgnoreCase("v1_8_R2") || getServerVersion().equalsIgnoreCase("v1_8_R3")){
                icbc = getNmsClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class
                		).invoke(null, "{'text': '" + message + "'}");
            } else {
                icbc = getNmsClass("ChatSerializer").getMethod("a", String.class)
                		.invoke(null, "{'text': '" + message + "'}");
            }
            
            Object ppoc = getNmsClass("PacketPlayOutChat").getConstructor(getNmsClass("IChatBaseComponent"), Byte.TYPE
            		).newInstance(icbc, (byte)2);
            
            Object nmsp = ReflectionUtils.getHandle(player);
            Object pcon = nmsp.getClass().getField("playerConnection").get(nmsp);
            pcon.getClass().getMethod("sendPacket", getNmsClass("Packet")).invoke(pcon, ppoc);
        } catch(Exception e){
            e.printStackTrace();
        }
	}

	/**
	 * @see net.castegaming.plugins.LoginPremium.messagemode.MessageMode#clear(org.bukkit.entity.Player)
	 */
	@Override
	public void clear(Player player) {
		sendMessage(player, "");
	}

	
	public static String getServerVersion(){
        return Bukkit.getServer().getClass().getPackage().getName().substring(23);
    }
	
	/**
	 * @param string
	 * @return
	 */
	private Class<?> getNmsClass(String string) {
		return ReflectionUtils.getCraftClass(string);
	}
}
