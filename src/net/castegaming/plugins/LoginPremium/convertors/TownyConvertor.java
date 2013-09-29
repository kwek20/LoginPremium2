package net.castegaming.plugins.LoginPremium.convertors;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class TownyConvertor {
	private TownyConvertor(){
		
	}
	
	public static String getTownyThings(Player player, String what){
		try {
			Resident r = TownyUniverse.getDataSource().getResident(player.getName());
			
			if (r != null){
				if (what.equals("townname")){
					if (r.hasTown()){
						return r.getTown().getName() + " ";
					}
				} if (what.equals("towntag")){
					if (r.hasTown() && r.getTown().hasTag()){
						return r.getTown().getTag() + " ";
					} 
				} else if (what.equals("nationname")){
					if (r.hasNation()){
						return r.getTown().getNation().getName() + " ";
					}
				} else if (what.equals("nationtag")){
					if (r.hasNation() && r.getTown().getNation().hasTag()){
						return r.getTown().getNation().getTag() + " ";
					}
				} else if (what.equals("towntitle")){
					if (r.hasTitle()){
						return r.getTitle() + " ";
					} 
				} else if (what.equals("surname")){
					if (r.hasSurname()){
						return r.getSurname() + " ";
					} 
				} else if (what.equals("friendsonline")){
					if (r.getFriends().size() > 0){
						List<Resident> friends = r.getFriends();
						for (Resident friend : friends){
							if (Bukkit.getServer().getPlayer(friend.getName()) == null){
								friends.remove(friend);
							}
						}
						return Arrays.toString(friends.toArray()).replaceAll("[", "").replaceAll("]", "") + " ";
					}
				}
			}
			return "";
		} catch (Exception e) {
			return "";
		}
	}
}
