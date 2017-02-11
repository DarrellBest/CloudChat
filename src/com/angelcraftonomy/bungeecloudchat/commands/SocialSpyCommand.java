package com.angelcraftonomy.bungeecloudchat.commands;

import com.angelcraftonomy.bungeecloudchat.CloudChat;
import com.angelcraftonomy.bungeecloudchat.CloudChatSingleton;
import com.angelcraftonomy.bungeecloudchat.extenders.CommandExtender;
import com.angelcraftonomy.bungeecloudchat.interfaces.CommandInterface;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class SocialSpyCommand extends CommandExtender implements CommandInterface {

	private CloudChatSingleton playerLists;

	public SocialSpyCommand(CloudChat cloudChat, Command command, String name, String alias, String permission) {
		super(cloudChat, command, name, alias, permission);
		playerLists = CloudChatSingleton.getInstance();
	}

	@Override
	public void initialize(CommandSender sender, String[] args) {
		this.setSender(sender);
		this.setArgs(args);
	}

	@Override
	public void run() {
		CommandSender sender = this.getSender();
		this.sendMessage("You are attempting to turn Social Spy on or off.");
		if (sender.hasPermission(this.getPermission()))
			if (playerLists.isInSocialSpy(sender.getName())) {
				playerLists.removePlayerSocialSpy(sender.getName());
				this.sendMessage("You turned Social Spy off!");
			} else {
				playerLists.addPlayerSocialSpy(sender.getName());
				this.sendMessage("You turned Social Spy on!");
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
