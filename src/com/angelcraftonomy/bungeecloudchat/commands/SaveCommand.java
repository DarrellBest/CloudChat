package com.angelcraftonomy.bungeecloudchat.commands;

import java.io.File;

import com.angelcraftonomy.bungeecloudchat.CloudChat;
import com.angelcraftonomy.bungeecloudchat.Manager;
import com.angelcraftonomy.bungeecloudchat.extenders.CommandExtender;
import com.angelcraftonomy.bungeecloudchat.interfaces.CommandInterface;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class SaveCommand extends CommandExtender implements CommandInterface {

	private Manager data;

	public SaveCommand(CloudChat cloudChat, Command command, String name, String alias, String permission) {
		super(cloudChat, command, name, alias, permission);
		data = Manager.getInstance();
	}

	@Override
	public void initialize(CommandSender sender, String[] args) {
		this.setSender(sender);
		this.setArgs(args);
	}

	@Override
	public void run() {
		if (this.getSender().hasPermission(this.getPermission())) {
			File file = this.getCloudChat().getFolder();
			sendMessage("Saving channels...");
			data.saveChannels(new File(file + CloudChat.CHANNELS_FILE));
			sendMessage("Saving global channel...");
			data.saveGlobal(new File(file + CloudChat.GLOBAL_FILE));
			sendMessage("Saving nicknames...");
			data.saveNicknames(new File(file + CloudChat.NICK_FILE));
			sendMessage("Saving social spy channel...");
			data.saveSocialSpy(new File(file + CloudChat.SOCIALSPY_FILE));
			sendMessage("Saving saving staff channel...");
			data.saveStaff(new File(file + CloudChat.STAFF_FILE));
			sendMessage("DONE");
		} else {
			this.sendNoPermMessage();
		}
	}

	@Override
	public void cleanup() {
	}

	@Override
	public void sendNoPermMessage() {
		sendMessage("You do not have the permission: " + this.getPermission());
	}
}
