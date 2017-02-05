package com.angelcraftonomy.bungeecloudchat;

import com.angelcraftonomy.bungeecloudchat.commands.SocialSpyCommand;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.PluginManager;

public class CommandExecuter extends Command {

	private CloudChatSingleton playerLists;
	private SocialSpyCommand ssCommand;

	public CommandExecuter(String name, String permission, String[] aliases, PluginManager pluginManager,
			CloudChat cloudChat) {
		super(name, permission, aliases);
		playerLists = CloudChatSingleton.getInstance();

		// List commands here
		ssCommand = new SocialSpyCommand(cloudChat, this, "SocialSpy", "SS", "CloudChat.SocialSpy");

	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (args.length == 1)
			toggleGlobal(sender);

		if (args.length == 2) {
			String commandName = args[1];

			if (commandName.equalsIgnoreCase(ssCommand.getName())
					|| commandName.equalsIgnoreCase(ssCommand.getAlias())) {
				ssCommand.initialize(sender, args);
				ssCommand.run();
				ssCommand.cleanup();
			}
		}
	}

	// Might need to make a new command for this, but this way players can still
	// do /cloudchat
	// instead of /cloudchat global or something like that
	private void toggleGlobal(CommandSender sender) {
		if (playerLists.isInGlobal(sender.getName()))
			playerLists.removePlayerGlobal(sender.getName());
		else
			playerLists.addPlayerGlobal(sender.getName());
	}

}
