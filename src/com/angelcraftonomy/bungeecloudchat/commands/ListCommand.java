package com.angelcraftonomy.bungeecloudchat.commands;

import com.angelcraftonomy.bungeecloudchat.Manager;
import com.angelcraftonomy.bungeecloudchat.CloudChat;
import com.angelcraftonomy.bungeecloudchat.extenders.CommandExtender;
import com.angelcraftonomy.bungeecloudchat.interfaces.CommandInterface;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ListCommand extends CommandExtender implements CommandInterface {

	private Manager channels;
	private ProxiedPlayer player;

	public ListCommand(CloudChat cloudChat, Command command, String name, String alias, String permission) {
		super(cloudChat, command, name, alias, permission);
		channels = Manager.getInstance();
	}

	@Override
	public void initialize(CommandSender sender, String[] args) {
		this.setSender(sender);
		this.setArgs(args);
		player = getPlayer();
	}

	@Override
	public void run() {
		for (String line : channels.channelsToString())
			if (line.contains("Channel"))
				player.sendMessage(new ComponentBuilder(line).color(ChatColor.BLUE).color(ChatColor.BOLD).create());
			else
				player.sendMessage(new ComponentBuilder(line).color(ChatColor.LIGHT_PURPLE).create());
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendNoPermMessage() {
		sendMessage("You do not have the permission: " + this.getPermission());
	}

}
