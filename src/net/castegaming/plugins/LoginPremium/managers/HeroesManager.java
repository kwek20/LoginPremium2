package net.castegaming.plugins.LoginPremium.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;

public class HeroesManager extends Manager {

	@Override
	public String Convert(Player player, String what) {
		Heroes plugin = (Heroes) Bukkit.getServer().getPluginManager().getPlugin("Heroes");
		Hero hero = plugin.getCharacterManager().getHero(player);
		what = what.replaceAll("<heroclass", hero.getHeroClass().getName());
		what = what.replaceAll("<herolevel>", hero.getLevel() + "");
		what = what.replaceAll("<mana>", hero.getMana() + "");
		what = what.replaceAll("<mana%>", (hero.getMana() / hero.getMaxMana() * 100) + "");
		return what;
	}

}
