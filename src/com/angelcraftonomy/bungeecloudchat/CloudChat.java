package com.angelcraftonomy.bungeecloudchat;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.angelcraftonomy.bungeecloudchat.listener.ChatListener;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class CloudChat extends Plugin implements Listener {
	// Check if first load
	private File varTmpFile;
	private File varTmpDir;
	private static CloudChat self;
	private Logger logger;
	private PluginManager pluginManager;
	private CommandExecuter commands;
	private ChatListener listener;
	private CloudChatSingleton state;
	public static ServerInfo hub;

	@Override
	public void onEnable() {
		self = this;
		logger = getProxy().getLogger();
		pluginManager = getProxy().getPluginManager();
		state = CloudChatSingleton.getInstance();

		logger.log(Level.INFO, "CloudChat is enabling!");

		// Check if first load
		varTmpDir = new File(getDataFolder(), "");
		if (!varTmpDir.exists())
			varTmpDir.mkdir();
		varTmpFile = new File(getDataFolder() + "/ccstate.ser");

		// Save fresh if first startup. Load if one exists already
		if (!varTmpFile.exists()) {
			logger.log(Level.INFO, "CloudChat is loading for the first time!");
			state.save(varTmpFile);
		} else {
			state.load(varTmpFile);
			logger.log(Level.INFO, "CloudChat loading state.");
		}

		// register command executer
		logger.log(Level.INFO, "CloudChat is registering commands!");
		commands = new CommandExecuter("cloudchat", "cloudchat.use", new String[] { "cc" }, pluginManager, this);
		pluginManager.registerCommand(this, commands);

		// register listeners
		logger.log(Level.INFO, "CloudChat is registering listeners!");
		listener = new ChatListener(this);
		pluginManager.registerListener(this, listener);

	}

	@Override
	public void onDisable() {
		logger.log(Level.INFO, "CloudChat is saving!");
		state.save(varTmpFile);
		logger.log(Level.INFO, "CloudChat is disabling!");
	}

	public static CloudChat getPlugin() {
		return self;
	}
}
