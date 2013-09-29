package net.castegaming.plugins.LoginPremium.managers;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class TownyManager extends Manager {

	@Override
	public String Convert(Player player, String result) {
		result = result.replace("<townname>", getTownyThings(player, "townname"));
		result = result.replace("<towntag>", getTownyThings(player, "towntag"));
		result = result.replace("<towntitle>", getTownyThings(player, "towntitle"));
		result = result.replace("<nationname>", getTownyThings(player, "nationname"));
		result = result.replace("<nationtag>", getTownyThings(player, "nationtag"));
		result = result.replace("<surname>", getTownyThings(player, "surname"));
		result = result.replace("<friendsonline>", getTownyThings(player, "friendsonline"));
		return result;
	}

	private String getTownyThings(Player player, String what) {
		try {
			Resident r = TownyUniverse.getDataSource().getResident(player.getName());
			
			if (r != null){
				if (what.equals("townname")){
					if (r.hasTown()){
						what =  r.getTown().getName() + " ";
					}
				} if (what.equals("towntag")){
					if (r.hasTown() && r.getTown().hasTag()){
						what =  r.getTown().getTag() + " ";
					} 
				} if (what.equals("nationname")){
					if (r.hasNation()){
						what =  r.getTown().getNation().getName() + " ";
					}
				} if (what.equals("nationtag")){
					if (r.hasNation() && r.getTown().getNation().hasTag()){
						what =  r.getTown().getNation().getTag() + " ";
					}
				} if (what.equals("towntitle")){
					if (r.hasTitle()){
						what =  r.getTitle() + " ";
					} 
				} if (what.equals("surname")){
					if (r.hasSurname()){
						what = r.getSurname() + " ";
					} 
				} if (what.equals("friendsonline")){
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
