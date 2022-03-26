package me.FortiBrine.JustChats.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import me.FortiBrine.JustChats.main.Main;

public class ReloadCommand implements CommandExecutor {

	private Main plugin;
	
	public ReloadCommand(Main plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		plugin.reloadConfig();
		arg0.sendMessage(messageConfig.getString("reload", "Êîíôèã ïåðåçàãðóæåí!")); // переведи весь код в UTF-8
		return true;
	}
}
