package com.angelcraftonomy.bungeecloudchat.commands;

import com.angelcraftonomy.bungeecloudchat.CloudChat;
import com.angelcraftonomy.bungeecloudchat.ChannelManager;
import com.angelcraftonomy.bungeecloudchat.extenders.CommandExtender;
import com.angelcraftonomy.bungeecloudchat.interfaces.CommandInterface;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CommandSpyCommand extends CommandExtender implements CommandInterface {

	private ChannelManager playerLists;

	public CommandSpyCommand(CloudChat cloudChat, Command command, String name, String alias, String permission) {
		super(cloudChat, command, name, alias, permission);
		playerLists = ChannelManager.getInstance();
	}

	@Override
	public void initialize(CommandSender sender, String[] args) {
		this.setSender(sender);
		this.setArgs(args);
	}

	@Override
	public void run() {
		CommandSender sender = this.getSender();
		if (sender.hasPermission(this.getPermission()))
			if (playerLists.isInCommandSpy(sender.getName())) {
				playerLists.removePlayerCommandSpy(sender.getName());
				this.sendMessage("You turned Command Spy off!");
			} else {
				playerLists.addPlayerCommandSpy(sender.getName());
				this.sendMessage("You turned Command Spy on!");
			}
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
