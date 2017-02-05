package com.angelcraftonomy.bungeecloudchat.interfaces;

import net.md_5.bungee.api.CommandSender;

public interface CommandInterface {
	public void initialize(CommandSender sender, String[] args);

	public void run();

	public void cleanup();

	public void sendNoPermMessage();
}
