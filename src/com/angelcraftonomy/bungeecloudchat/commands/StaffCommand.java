package com.angelcraftonomy.bungeecloudchat.commands;

import com.angelcraftonomy.bungeecloudchat.CloudChat;
import com.angelcraftonomy.bungeecloudchat.CloudChatSingleton;
import com.angelcraftonomy.bungeecloudchat.extenders.CommandExtender;
import com.angelcraftonomy.bungeecloudchat.interfaces.CommandInterface;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class StaffCommand extends CommandExtender implements CommandInterface {

	private CloudChatSingleton playerLists;

	public StaffCommand(CloudChat cloudChat, Command command, String name, String alias, String permission) {
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
		if (sender.hasPermission(this.getPermission()))
			if (playerLists.isInStaff(sender.getName())) {
				playerLists.removePlayerStaff(sender.getName());
				this.sendMessage("You left Staff chat!");
			} else {
				playerLists.addPlayerStaff(sender.getName());
				this.sendMessage("You entered Staff chat!");
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
		super.sendNoPermMessage();
		sendMessage("You do not have the permission: " + this.getPermission());
	}

}
