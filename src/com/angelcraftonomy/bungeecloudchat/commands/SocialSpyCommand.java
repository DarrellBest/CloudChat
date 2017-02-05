package com.angelcraftonomy.bungeecloudchat.commands;

import com.angelcraftonomy.bungeecloudchat.CloudChatSingleton;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class SocialSpyCommand extends Command {

	private CloudChatSingleton playerLists;

	public SocialSpyCommand(String name, String permission, String[] aliases) {
		super(name, permission, aliases);
		playerLists = CloudChatSingleton.getInstance();
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender.hasPermission(this.getPermission()))
			if (playerLists.isInSocialSpy(sender.getName()))
				playerLists.removePlayerGlobal(sender.getName());
			else
				playerLists.addPlayerGlobal(sender.getName());
	}

}
