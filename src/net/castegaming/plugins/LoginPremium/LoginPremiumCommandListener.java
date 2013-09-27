package net.castegaming.plugins.LoginPremium;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class LoginPremiumCommandListener implements Listener{
	
	public LoginPremium plugin;
	public CommandSender sender;
	
	//links to the main class
    public LoginPremiumCommandListener(LoginPremium plugin){
            this.plugin = plugin;
    }
    
    //handles all the commands coming from the main class
	public boolean commandHandler(CommandSender sender, Command cmd, String[] args) {
    	this.sender = sender;
    	String command = cmd.getName();

    	if(command.equalsIgnoreCase("lp") || command.equalsIgnoreCase("loginpremium")){
    		if (sender.hasPermission("loginpremium.use")){	
	    		if (args.length == 0){
	    			sender.sendMessage("Try /help LoginPremium for info about Login Premium");
	    			return true;
	    		} else if(args[0].startsWith("r")){
	    	        if (sender.hasPermission("loginpremium.reload")){
	    	        	reload();
	    	        } else {
	    	        	sender.sendMessage("You do not have the permission to do that");
	    	        }
	    	    } else if(args[0].startsWith("s")){
	    	    	if (sender.hasPermission("loginpremium.set")){
		    	    	if (args.length == 1){
		    	    		availableOptions();
		    	    	} else if(args[1].equalsIgnoreCase("useprivateloginmessage")){
		    	    		setValues(args, "useprivateloginmessage");
		    	   		} else if(args[1].equalsIgnoreCase("usepublicloginmessage")){
		    	   			setValues(args, "usepublicloginmessage");
		    	   		} else if(args[1].equalsIgnoreCase("usepubliclogoutmessage")){
		    	   			setValues(args, "usepubliclogoutmessage");
		    	    	} else if (args[1].startsWith("usesendmessage")){
		    	   			setValues(args, "usesendmessageiflessthensetplayerson");
		    	    	} else if(args[1].equalsIgnoreCase("kickoverride")){
		    	    		setValues(args, "kickoverride");
		    	    	} else if (args[1].equalsIgnoreCase("kickmessage")){
		    	    		setMessage(args, "kickmessage");
		    	    	} else if (args[1].equalsIgnoreCase("MessagePublicLogin")){
		    	    		setMessage(args, "MessagePublicLogin");
		    	    	} else if (args[1].equalsIgnoreCase("MessagePrivateLogin")){
		    	    		setMessage(args, "MessagePrivateLogin");
		    	    	} else if (args[1].equalsIgnoreCase("MessagePublicLogout")){
		    	    		setMessage(args, "MessagePublicLogout");
		    	    	} else if (args[1].equalsIgnoreCase("sendlogincap")){
		    	    		setInt("sendlogincap", args);
		    	    	} else if (args[1].equalsIgnoreCase("spawn")){
		    	    		if (sender instanceof Player){
			    	    		plugin.getConfig().set("spawn", ((Player)sender).getLocation());
			    	    		sender.sendMessage("The spawn has been set to your location.");
			    	    		return true;
		    	    		} else {
		    	    			sender.sendMessage("You need to be online to set this value.");
		    	    		}
		    	    	} else if (args[1].equalsIgnoreCase("usespawn")){
		    	    		setValues(args, "usespawn");
		    	    	} else if (args[1].equalsIgnoreCase("overridekickmessage")){
		    	    		setValues(args, "overridekickmessage");
		    	    	} else if (args[1].equalsIgnoreCase("overridemessages")){
		    	    		setValues(args, "overridemessages");
		    	    	} else if(args[1].equalsIgnoreCase("default")){
		    	            Message("config.yml", "default");
		    	            plugin.saveResource("config.yml", true);
		    	            plugin.reloadConfig();
		    	    	} else {
		    	    		availableOptions();
		    	    	}
	    	    	} else {
	    	    		sender.sendMessage("You do not have the permission to do that");
	    	    	}
	    	    } else {
	    	    	infoMessage(sender);
	    	    }
    		} else {
    			infoMessage(sender);
    		}
    		return true;
    	}
    	return false;
    }
	
	/**
	 * 
	 * @param sender
	 */
    public void infoMessage(CommandSender sender){
    	sender.sendMessage(ChatColor.GOLD + "------[ " + ChatColor.DARK_GREEN + "Loginpremium" + ChatColor.GRAY + " Version " + plugin.getDescription().getVersion() + ChatColor.GOLD + " ]------");
		sender.sendMessage(ChatColor.GRAY + plugin.getDescription().getDescription());
		sender.sendMessage(ChatColor.GRAY + "Author: " + ChatColor.DARK_AQUA + ChatColor.ITALIC + plugin.getDescription().getAuthors());
		sender.sendMessage(ChatColor.GRAY + "Created for: " + ChatColor.DARK_GREEN + plugin.getDescription().getWebsite());
		sender.sendMessage(ChatColor.GOLD + "-------------------------------------");
    }
	
	
	/**
	 * reloads the config.yml
	 */
    public void reload(){
    	plugin.reloadConfig();
    	sender.sendMessage("[LoginPremium] Has been reloaded");

    }
    
    public void availableOptions(){
    	sender.sendMessage("Available options: usesendmessageiflessthensetplayerson, " +
    			"useprivateloginmessage, usepublicloginmessage, usepubliclogoutmessage, " +
    			"kickoverride, kickmessage, MessagePublicLogin, MessagePrivateLogin, " +
    			"MessagePublicLogout, sendlogincap, overridekickmessage, overridemessages, spawn, usespawn " +
    			"and default");
    }
    
    /**
     * sends a message to the sender
     * @param arg
     * @param value
     */
    public void Message(String arg, String value){
    	sender.sendMessage(arg + " has been set to: " + value);
    }
    
    /**
     * sends the true or false message to the sender
     */
    public void truefalse(){
    	sender.sendMessage("Please define true(t) or false(f)");
    }
    
    /**
     * sets the custom message to what the user typed
     * @param arg
     * @param what
     */
    public void setMessage(String[] arg, String what){
    	if (arg.length > 2){
    		sender.sendMessage(what + " has been changed");
        	String result = LoginPremiumConversions.ArrayToString(arg);
            plugin.getConfig().set(what, result);
            plugin.saveConfig();
    	} else {
    		sender.sendMessage("Please typ something");
    	}
    } 
    
    
   /**
    * detects if value is true or false, and sets the configs according to that
    * @param args
    * @param what
    */
    public void setValues(String[] args, String what){
    	if ( args.length > 2){
			if(args[2].startsWith("f") || args[2].startsWith("F")){
				plugin.getConfig().set(what, false);
				Message(what, "false");
				plugin.saveConfig();
			} else if (args[2].startsWith("t") || args[2].startsWith("T")){
				plugin.getConfig().set(what, true);
				Message(what, "true");
				plugin.saveConfig();
			} else {
				truefalse();
			}
		} else {
			plugin.getConfig().set(what, !plugin.getConfig().getBoolean(what, true));
			Message(what, plugin.getConfig().getBoolean(what) + "");
        }
    }
    
    public void setInt(String what, String[] args){
    	if (args.length > 2){
	    	try{
				int oldString = Integer.parseInt(args[2].toString());
				if (oldString > 0){
					plugin.getConfig().set(what, oldString);
					sender.sendMessage(what + " has been set to: " + oldString);
					plugin.saveConfig();
				} else {
					sender.sendMessage("Please use a number greater then 0");
				}
				
			}catch ( NumberFormatException e ){
				sender.sendMessage("Please " + ChatColor.DARK_RED + "ONLY" + ChatColor.RESET + " use numbers.");
			}
    	} else {
    		sender.sendMessage("Please define a number.");
    	}
    }
}
