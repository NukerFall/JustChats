package me.FortiBrine.JustChats.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import me.FortiBrine.JustChats.main.Main;

@SuppressWarnings("unused")
public class MainListener implements Listener {

	private Main plugin;
	public MainListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		FileConfiguration config = plugin.getConfig();
		
		Player player = e.getPlayer();
		String[] message = e.getMessage().split(" ");
		char[] label = message[0].toCharArray();
		
		List<String> args = new ArrayList<String>();
		for (String i : message) {
			args.add(i);
		}
		args.remove(0);
		
		String command = "";
		for (int i = 1; i < label.length; i++) {
			command += label[i];
		}
		
		Set<String> commands = config.getKeys(false);
		if (!commands.contains(command)) return;
		e.setCancelled(true);
		
		ConfigurationSection section = config.getConfigurationSection(command);
		String permission = section.getString("adminchat.use", command+".use");
		
		if (!player.hasPermission(permission)) {
			String permissionMessage = section.getString("permission-message", "&cВы не имеет прав не выполнение этой команды!");
			permissionMessage = permissionMessage.replace("&", "§");
			
			player.sendMessage(permissionMessage);
			return;
		}
		if (args.size() < 1) {
			String usage = section.getString("usage", "/"+command+" <сообщение>");
			usage = usage.replace("&", "§");
			
			player.sendMessage(usage);
			return;
		}
		
		String msg = "";
		for (String s : args) {
			msg += s + " ";
		}
		msg = msg.trim();
		
		String format = section.getString("format", "%player: %message");
		format = format.replace("&", "§");
		format = format.replace("%player", player.getName());
		format = format.replace("%message", msg);
		
		for (Player player2 : Bukkit.getOnlinePlayers()) {
			if (!player2.hasPermission(permission)) continue;
			
			player2.sendMessage(format);
		}
	}
}
