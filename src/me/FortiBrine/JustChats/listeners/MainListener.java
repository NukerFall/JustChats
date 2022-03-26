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

public class MainListener implements Listener {

	private YamlConfiguration config;
	
	public MainListener(Main plugin) {
		plugin.getPluginManager().registerEvents(this, plugin);
		config = plugin.getConfig();
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
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
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', permissionMessage));
			return;
		}
		if (args.size() < 1) {
			String usage = section.getString("usage", "/"+command+" <сообщение>");
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', usage));
			return;
		}
		String msg = "";
		for (String s : args) {
			msg += s + " ";
		}
		msg = msg.trim();
		String format = section.getString("format", "%player: %message");
		format = format.replace("%player", player.getName());
		format = format.replace("%message", msg);
		for (Player recepient : Bukkit.getOnlinePlayers()) {
			if (recepient.hasPermission(permission)) recepient.sendMessage(ChatColor.translateAlternateColorCodes('&', format));
		}
	}
}
