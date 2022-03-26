package me.FortiBrine.JustChats.main;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import me.FortiBrine.JustChats.commands.ReloadCommand;
import me.FortiBrine.JustChats.listeners.MainListener;

public class Main extends JavaPlugin {

	public File messageFile = new File(getDataFolder(), "messages.yml");
	
	public void onEnable() {
		saveDefaultConfig();
		if (!messageFile.exists()) saveResource("messages.yml", false);
		new ReloadCommand(this);
		new MainListener(this);
	}
}
