package com.angelcraftonomy.bungeecloudchat;

import com.angelcraftonomy.bungeecloudchat.commands.ListCommand;
import com.angelcraftonomy.bungeecloudchat.commands.NicknameCommand;
import com.angelcraftonomy.bungeecloudchat.commands.SaveCommand;
import com.angelcraftonomy.bungeecloudchat.commands.SocialSpyCommand;
import com.angelcraftonomy.bungeecloudchat.commands.StaffCommand;
import com.angelcraftonomy.bungeecloudchat.commands.TestCommand;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.PluginManager;

public class CommandExecuter extends Command {

	private CommandSender sender;
	private CloudChatSingleton playerLists;
	private SocialSpyCommand socialSpyCommand;
	private StaffCommand staffCommand;
	private ListCommand listCommand;
	private TestCommand testCommand;
	private NicknameCommand nickCommand;
	private SaveCommand saveCommand;
	private ChatColor colorOne;
	private ChatColor colorTwo;

	public CommandExecuter(String name, String permission, String[] aliases, PluginManager pluginManager,
			CloudChat cloudChat) {
		super(name, permission, aliases);
		playerLists = CloudChatSingleton.getInstance();

		// List commands here
		socialSpyCommand = new SocialSpyCommand(cloudChat, this, "socialspy", "ss",
				CloudChatSingleton.SOCIALSPY_PERMISSION);
		staffCommand = new StaffCommand(cloudChat, this, "staff", "s", CloudChatSingleton.STAFF_PERMISSION);
		listCommand = new ListCommand(cloudChat, this, "list", "l", CloudChatSingleton.LIST_PERMISSION);
		testCommand = new TestCommand(cloudChat, this, "test", "t", CloudChatSingleton.TEST_PERMISSION);
		nickCommand = new NicknameCommand(cloudChat, this, "nick", "n", CloudChatSingleton.NICK_PERMISSION);
		saveCommand = new SaveCommand(cloudChat, this, "save", "sa", CloudChatSingleton.SAVE_PERMISSION);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		this.sender = sender;
		String commandName;

		if (args.length == 0)
			toggleGlobal(sender);

		if (args.length == 1) {
			commandName = args[0];
			if (commandName.equalsIgnoreCase(socialSpyCommand.getName())
					|| commandName.equalsIgnoreCase(socialSpyCommand.getAlias())) {
				socialSpyCommand.initialize(sender, args);
				socialSpyCommand.run();
				socialSpyCommand.cleanup();
			}

			if (commandName.equalsIgnoreCase(staffCommand.getName())
					|| commandName.equalsIgnoreCase(staffCommand.getAlias())) {
				staffCommand.initialize(sender, args);
				staffCommand.run();
				staffCommand.cleanup();
			}

			if (commandName.equalsIgnoreCase(listCommand.getName())
					|| commandName.equalsIgnoreCase(listCommand.getAlias())) {
				listCommand.initialize(sender, args);
				listCommand.run();
				listCommand.cleanup();
			}

			if (commandName.equalsIgnoreCase(saveCommand.getName())
					|| commandName.equalsIgnoreCase(saveCommand.getAlias())) {
				saveCommand.initialize(sender, args);
				saveCommand.run();
				saveCommand.cleanup();
			}

		}

		if (args.length == 2) {
			commandName = args[0];

			if (commandName.equalsIgnoreCase(testCommand.getName())
					|| commandName.equalsIgnoreCase(testCommand.getAlias())) {
				testCommand.initialize(sender, args);
				testCommand.run();
				testCommand.cleanup();
			}

			if (commandName.equalsIgnoreCase(nickCommand.getName())
					|| commandName.equalsIgnoreCase(nickCommand.getAlias())) {
				nickCommand.initialize(sender, args);
				nickCommand.run();
				nickCommand.cleanup();
			}
		}

	}

	// Might need to make a new command for this, but this way players can still
	// do /cloudchat
	// instead of /cloudchat global or something like that
	private void toggleGlobal(CommandSender sender) {
		if (playerLists.isInGlobal(sender.getName())) {
			playerLists.removePlayerGlobal(sender.getName());
			this.sendMessage("You left Global chat!");
		} else {
			playerLists.removeFromAllChannels(sender.getName());
			playerLists.addPlayerGlobal(sender.getName());
			this.sendMessage("You entered Global chat!");
		}
	}

	// send player a message
	private void sendMessage(String message) {
		this.colorOne = ChatColor.GREEN;
		this.colorTwo = ChatColor.YELLOW;
		ProxiedPlayer player = (ProxiedPlayer) sender;
		player.sendMessage(
				new ComponentBuilder("[CloudChat] ").color(colorOne).append(message).color(colorTwo).create());
	}

}
