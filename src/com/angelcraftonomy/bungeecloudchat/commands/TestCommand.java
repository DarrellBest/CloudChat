package com.angelcraftonomy.bungeecloudchat.commands;

import com.angelcraftonomy.bungeecloudchat.CloudChat;
import com.angelcraftonomy.bungeecloudchat.Manager;
import com.angelcraftonomy.bungeecloudchat.extenders.CommandExtender;
import com.angelcraftonomy.bungeecloudchat.interfaces.CommandInterface;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class TestCommand extends CommandExtender implements CommandInterface {

	private Manager dataStore;

	public TestCommand(CloudChat cloudChat, Command command, String name, String alias, String permission) {
		super(cloudChat, command, name, alias, permission);
		dataStore = Manager.getInstance();
	}

	@Override
	public void initialize(CommandSender sender, String[] args) {
		this.setSender(sender);
		this.setArgs(args);
	}

	@Override
	public void run() {
		CommandSender sender = this.getSender();
		if (sender.hasPermission(this.getPermission())) {
			if (this.getArgs()[1].equals("print")) {
				this.sendMessage("The test number is: " + dataStore.getTest());
			} else {
				dataStore.changeTest(Integer.parseInt(this.getArgs()[1]));
				this.sendMessage("You changed the test number!");
			}
		} else
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
