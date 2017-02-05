package com.angelcraftonomy.bungeecloudchat;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.angelcraftonomy.bungeecloudchat.listener.ChatListener;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class CloudChat extends Plugin implements Listener {
	private static CloudChat self;
	private Logger logger;
	private PluginManager pluginManager;
	private CommandExecuter commands;
	private ChatListener listener;
	public static ServerInfo hub;

	@Override
	public void onEnable() {
		self = this;
		logger = getProxy().getLogger();
		pluginManager = getProxy().getPluginManager();
		commands = new CommandExecuter("CloudChat", "CloudChat.use", new String[] { "cc" }, pluginManager, this);
		listener = new ChatListener(this);

		logger.log(Level.INFO, "CloudChat is enabling!");

		// register command
		logger.log(Level.INFO, "CloudChat is registering commands!");
		pluginManager.registerCommand(this, commands);

		// register listener
		logger.log(Level.INFO, "CloudChat is registering listeners!");
		pluginManager.registerListener(this, listener);

	}

	@Override
	public void onDisable() {
		logger.log(Level.INFO, "CloudChat is disabling!");
	}

	public static CloudChat getPlugin() {
		return self;
	}
}
