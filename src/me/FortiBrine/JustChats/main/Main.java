package me.FortiBrine.JustChats.main;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import me.FortiBrine.JustChats.commands.ReloadCommand;
import me.FortiBrine.JustChats.listeners.MainListener;

public class Main extends JavaPlugin {

	private File locale = new File(getDataFolder(), "locale.yml");
	private FileConfiguration lconf;

	public void onEnable() {
		saveDefaultConfig();
		reloadLocale();
		new ReloadCommand(this);
		new MainListener(this);
		send("&aPlugin enabled!");
	}
	
	public void onDisable() {
		send("&cPlugin disabled!");	
	}
	
	public void reloadLocale() {
		if (!locale.exists()) {
			saveResource("locale.yml", false);	
		}
		lconf = YamlConfiguration.loadConfiguration(locale);
	}
	
	public void send(String s) {
		getServer().getConsoleSender().sendMessage("[JustChats] " + ChatColor.translateAlternateColorCodes('&', s));
	}
}
