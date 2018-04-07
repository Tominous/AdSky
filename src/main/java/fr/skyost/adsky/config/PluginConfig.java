package fr.skyost.adsky.config;

import fr.skyost.adsky.utils.Skyoconfig;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class PluginConfig extends Skyoconfig {

	@ConfigOptions(name = "server.url")
	public String serverUrl = "http://yourwebsite.com/adsky/";
	@ConfigOptions(name = "server.plugin-key")
	public String serverPluginKey = "Paste your plugin key here.";
	@ConfigOptions(name = "server.event-scheduled")
	public boolean serverEventScheduled = false;

	@ConfigOptions(name = "ads.preferred-hour")
	public int adsPreferredHour = 12;
	@ConfigOptions(name = "ads.distribution-function")
	public String adsDistributionFunction = "((-1/n) * (x-h)^2) + log(n)";
	@ConfigOptions(name = "ads.world-blacklist")
	public List<String> adsWorldBlackList = Arrays.asList("WorldA", "WorldB", "WorldC");

	public Calendar getAdsPreferredHour() {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, adsPreferredHour);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	public PluginConfig(Plugin plugin) {
		super(new File(plugin.getDataFolder(), "config.yml"), Arrays.asList(plugin.getName() + " configuration file"));
	}

}