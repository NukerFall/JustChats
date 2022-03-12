package me.FortiBrine.JustChats.main;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.FortiBrine.JustChats.commands.ReloadCommand;
import me.FortiBrine.JustChats.listeners.MainListener;

public class Main extends JavaPlugin {

	public File messageFile = new File(getDataFolder()+File.separator+"messages.yml");
	
	@Override
	public void onEnable() {
		File config = new File(getDataFolder()+File.separator+"config.yml");
		if (!config.exists()) {
			getConfig().options().copyDefaults(true);
			saveDefaultConfig();
		}
		if (!messageFile.exists()) saveResource("messages.yml", false);
		
		this.getCommand("jcreload").setExecutor(new ReloadCommand(this));
		Bukkit.getPluginManager().registerEvents(new MainListener(this), this);
	}
}
