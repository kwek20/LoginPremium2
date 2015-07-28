package net.castegaming.plugins.LoginPremium;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.castegaming.plugins.LoginPremium.messagemode.ActionBarMessage;
import net.castegaming.plugins.LoginPremium.messagemode.ChatMessageMode;
import net.castegaming.plugins.LoginPremium.messagemode.DragonBarMessageMode;
import net.castegaming.plugins.LoginPremium.messagemode.MessageMode;
import net.castegaming.plugins.LoginPremium.messagemode.TitleMessageMode;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoginPremiumPlayerListener implements Listener{
	
	private Player player;
	
	public LoginPremium plugin;
	
	private List<MessageGroup> groups;

	private List<String> mutedPlayers = new LinkedList<>();
	
	//links to the main class
    public LoginPremiumPlayerListener(LoginPremium plugin){
            this.plugin = plugin;
            loadGroups();
    }
    
    /**
	 * 
	 */
	public void loadGroups() {
		groups = new LinkedList<MessageGroup>();
		for (String key : plugin.getConfig().getConfigurationSection("user-login").getKeys(false)){
			MessageMode mode = null;
			switch(plugin.getConfig().getString("user-login." + key)){
			case "title": mode = new TitleMessageMode();
			case "bossbar": mode = new DragonBarMessageMode();
			case "action": mode = new ActionBarMessage();
			case "none": break;
			case "chat":
			default: mode = new ChatMessageMode();
			}
			
			groups.add(new MessageGroup(key, mode));
		}
	}

	//Handles what happends when a player joins
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event){
    	
    	if (plugin.getConfig().getBoolean("overridemessages", true)){
    		event.setJoinMessage(null);
    	}
    	
    	this.player = event.getPlayer();
    	int playerson = Bukkit.getOnlinePlayers().size();
    	int sendEveryonesLoginIfUnder = plugin.getConfig().getInt("sendlogincap", 5);
    	
    	if ((plugin.getConfig().getBoolean("usesendmessageiflessthensetplayerson", false) && playerson < sendEveryonesLoginIfUnder) || (player.hasPermission("loginpremium.loginmessage") && plugin.getConfig().getBoolean("usepublicloginmessage", true))){
    		if (plugin.getConfig().getBoolean("overridemessages", true)){
        		event.setJoinMessage(null);
        		MessagePublicLogin();
        	} else {
        		event.setJoinMessage(converse(plugin.getConfig().getString("MessagePublicLogin", "")));
        	}
    	}  
    	
    	if (plugin.getConfig().getBoolean("useprivateloginmessage", true)){
    		MessagePrivateLogin(); 
    	}
    	
    	if (plugin.getConfig().getBoolean("usespawn", false) && plugin.getConfig().contains("spawn")){
    		Location l = LoginPremiumConversions.toLocation(plugin.getConfig().getString("spawn"));
    		if (l != null) event.getPlayer().teleport(l);
    		else plugin.log("WARNING the spawn location is not properly set! \"" + plugin.getConfig().getString("spawn") + "\"");
    	}
    }
    
  //Handles what happends when a player leaves
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLeave(PlayerQuitEvent event){
    	
    	this.player = event.getPlayer();
    	int playerson = Bukkit.getOnlinePlayers().size();
    	int sendEveryonesLoginIfUnder = plugin.getConfig().getInt("sendlogincap", 5);
    	
    	if ((plugin.getConfig().getBoolean("usesendmessageiflessthensetplayerson", true) && playerson < sendEveryonesLoginIfUnder+1) || (player.hasPermission("loginpremium.loginmessage") && plugin.getConfig().getBoolean("usepubliclogoutmessage", true))){
    		if (plugin.getConfig().getBoolean("overridemessages", true)){
        		event.setQuitMessage(null);
        		MessagePublicLogout();
        	} else {
        		event.setQuitMessage(converse(plugin.getConfig().getString("MessagePublicLogout", "")));
        	}
    	}                       
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerKick(PlayerKickEvent event) {
    	if (plugin.getConfig().getBoolean("overridekickmessage", true)){
    		event.setLeaveMessage(null);
    	}
    }
    
  //Handles what happends when a player tries to join
    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerLogin(PlayerLoginEvent event) {
    	this.player = event.getPlayer();
        if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL)
        	if (plugin.getConfig().getBoolean("usekickoverride", true) == true){
        		if (event.getPlayer().hasPermission("loginpremium.loginoverride")){
        			if (plugin.getConfig().getBoolean("kickplayeronfull", false)){
        				boolean notkicked = true;
        				while (notkicked){
        					int player = new Random().nextInt(Bukkit.getServer().getOnlinePlayers().size()-1);
        					if (!Bukkit.getServer().getOnlinePlayers().toArray(new Player[0])[player].hasPermission("loginpremium.loginoverride")){
        						String message = converse(plugin.getConfig().getString("kickedmessage", "<prefix><name>&r<suffix> has logged in, so we needed to make room :("));
        						Bukkit.getServer().getOnlinePlayers().toArray(new Player[0])[player].kickPlayer(message);
        						notkicked = false;
        					}
        				}
        			}
        			event.allow();
        		} else {
        			String text = converse(plugin.getConfig().getString("kickmessage"));
        			event.setKickMessage(text);
        		}
    		}
    }
    
    //Gets the message MessagePublicLogin from the config, and broadcasts it
	private void MessagePublicLogin() {
		String text = converse(plugin.getConfig().getString("MessagePublicLogin", ""));
		broadcast(text);
	}
	
	//Gets the message MessagePrivateLogin from the config, and sends it to the player
	public void MessagePrivateLogin(){
		String text = converse(plugin.getConfig().getString("MessagePrivateLogin", ""));
		message(player, text);
	}
	
	//Gets the message MessagePublicLogout from the config, and broadcasts it
	private void MessagePublicLogout() {
		String text = converse(plugin.getConfig().getString("MessagePublicLogout", ""));
		broadcast(text);
	}
	
	/**
	 * @param text
	 */
	private void broadcast(String text) {
		for (Player p : Bukkit.getOnlinePlayers()){
			message(p, text);
		}
	}

	/**
	 * @param p
	 * @param text
	 */
	private void message(Player p, String text) {
		MessageMode m = getMessageMode();
		if (m == null) return;
		
		if (!hasOptedOut(p))
			m.message(p, text);
	}

	/**
	 * @return
	 */
	private MessageMode getMessageMode() {
		MessageGroup mg = null;
		for (MessageGroup m : groups){
			if (player.hasPermission(m.getName())){
				if (mg == null){
					mg = m;
				} else {
					plugin.log("player " + player.getName() + " belongs to multiple groups! remove other permissions if needed.");
					break;
				}
			}
		}
		
		mg = getMessageGroupByName("other");
		if (mg != null) return mg.getMessagemode();
		
		return null;
	}
	
	/**
	 * @return
	 */
	private MessageGroup getMessageGroupByName(String name) {
		for (MessageGroup m : groups){
			if (m.getName().equals(name)) return m;
		}
		
		return null;
	}
	
	private boolean hasOptedOut(Player p){
		return mutedPlayers.contains(p.getName());
	}
	
	/**
	 * @param sender
	 */
	public void addMute(CommandSender sender) {
		mutedPlayers.add(sender.getName());
	}
	
	/**
	 * @param sender
	 */
	public void removeMute(CommandSender sender) {
		mutedPlayers.remove(sender.getName());
	}

	//Converses from plain text to variables & colors
	public String converse(String text){
		return LoginPremiumConversions.changeTextToContent(text, player);
	}
	
	private class MessageGroup {
		private MessageMode messagemode;
		private String name;

		/**
		 * 
		 */
		public MessageGroup(String name, MessageMode mode) {
			this.name = name;
			this.messagemode = mode;
		}
		
		/**
		 * @return the messagemode
		 */
		public MessageMode getMessagemode() {
			return messagemode;
		}
		
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
	}
}
