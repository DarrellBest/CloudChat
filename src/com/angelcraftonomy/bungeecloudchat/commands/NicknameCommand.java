package com.angelcraftonomy.bungeecloudchat.commands;

import com.angelcraftonomy.bungeecloudchat.Manager;
import com.angelcraftonomy.bungeecloudchat.CloudChat;
import com.angelcraftonomy.bungeecloudchat.extenders.CommandExtender;
import com.angelcraftonomy.bungeecloudchat.interfaces.CommandInterface;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class NicknameCommand extends CommandExtender implements CommandInterface {

	private Manager nickNames;

	public NicknameCommand(CloudChat cloudChat, Command command, String name, String alias, String permission) {
		super(cloudChat, command, name, alias, permission);
		nickNames = Manager.getInstance();
	}

	@Override
	public void initialize(CommandSender sender, String[] args) {
		this.setSender(sender);
		this.setArgs(args);
	}

	@Override
	public void run() {
		CommandSender sender = this.getSender();
		String nick = getArgs()[1];
		if (nick.replaceAll("&\\S", "").length() >= nickNames.getMaxSize()) {
			sendMessage("Please set a nickname with " + nickNames.getMaxSize() + " characters or less");
			if (sender.getName().contains("UsuriousAngel"))
				sendMessage("Debug: " + nick.replaceAll("&\\S", ""));
			return;
		}

		String player = getPlayer().getName();
		if (sender.hasPermission(this.getPermission()))
			nickNames.addNickname(player, nick);
		else
			this.sendNoPermMessage();

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
