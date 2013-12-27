package net.castegaming.plugins.LoginPremium;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
	
	//links to the main class
    public LoginPremiumPlayerListener(LoginPremium plugin){
            this.plugin = plugin;
    }
    
    //Handles what happends when a player joins
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event){
    	
    	if (plugin.getConfig().getBoolean("overridemessages", true)){
    		event.setJoinMessage(null);
    	}
    	
    	this.player = event.getPlayer();
    	int playerson = Bukkit.getOnlinePlayers().length;
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
    		try {
    			event.getPlayer().teleport((Location) plugin.getConfig().get("spawn"));
    		} catch (Exception e){
    			plugin.log("WARNING the spawn location is not properly set! \"/lp s spawn\"");
    		}
    	}
    }
    
  //Handles what happends when a player leaves
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLeave(PlayerQuitEvent event){
    	
    	this.player = event.getPlayer();
    	int playerson = Bukkit.getOnlinePlayers().length;
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
        					int player = new Random().nextInt(Bukkit.getServer().getOnlinePlayers().length-1);
        					if (!Bukkit.getServer().getOnlinePlayers()[player].hasPermission("loginpremium.loginoverride")){
        						String message = converse(plugin.getConfig().getString("kickedmessage", "<prefix><name>&r<suffix> has logged in, so we needed to make room :("));
        						Bukkit.getServer().getOnlinePlayers()[player].kickPlayer(message);
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
		Bukkit.broadcastMessage(text);
	}
	
	//Gets the message MessagePrivateLogin from the config, and sends it to the player
	public void MessagePrivateLogin(){
		String text = converse(plugin.getConfig().getString("MessagePrivateLogin", ""));
		player.sendMessage(text);
	}
	
	//Gets the message MessagePublicLogout from the config, and broadcasts it
	private void MessagePublicLogout() {
		String text = converse(plugin.getConfig().getString("MessagePublicLogout", ""));
		Bukkit.broadcastMessage(text);
	}
	
	//Converses from plain text to variables & colors
	public String converse(String text){
		return LoginPremiumConversions.changeTextToContent(text, player);
	}

}
