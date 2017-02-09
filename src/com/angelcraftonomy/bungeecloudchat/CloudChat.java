package com.angelcraftonomy.bungeecloudchat;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.angelcraftonomy.bungeecloudchat.listener.ChatListener;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

@SuppressWarnings("unused")
public class CloudChat extends Plugin implements Listener {

	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_BLACK = "\u001B[30m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_PURPLE = "\u001B[35m";
	private static final String ANSI_CYAN = "\u001B[36m";
	private static final String ANSI_WHITE = "\u001B[37m";
	private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	private static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	private static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	private static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	private File mainDirectory;
	private File globalFile;
	private File channelsFile;
	private File staffFile;
	private File socialSpyFile;
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

		logger.log(Level.INFO, ANSI_GREEN + "CloudChat is enabling!");

		// Check if first load
		mainDirectory = new File(getDataFolder(), "");
		if (!mainDirectory.exists()) {
			logger.log(Level.INFO, ANSI_GREEN + "CloudChat is loading for the first time!");
			mainDirectory.mkdir();
		}

		// Check Channels file
		this.channelsFile = new File(getDataFolder() + "/channelstate.ser");
		if (!channelsFile.exists()) {
			logger.log(Level.INFO, ANSI_GREEN + "CloudChat is creating channels state.");
			state.saveChannels(channelsFile);
		} else {
			logger.log(Level.INFO, ANSI_GREEN + "CloudChat is loading channels state.");
			state.loadChannels(channelsFile);
		}

		// Check Global file
		this.globalFile = new File(getDataFolder() + "/globalstate.ser");
		if (!globalFile.exists()) {
			logger.log(Level.INFO, ANSI_GREEN + "CloudChat is creating global channel state.");
			state.saveGlobal(globalFile);
		} else {
			logger.log(Level.INFO, ANSI_GREEN + "CloudChat is loading global channel state.");
			state.loadGlobal(globalFile);
		}

		// Check Social Spy file
		this.socialSpyFile = new File(getDataFolder() + "/socialspystate.ser");
		if (!socialSpyFile.exists()) {
			logger.log(Level.INFO, ANSI_GREEN + "CloudChat is creating social spy channel state.");
			state.saveSocialSpy(socialSpyFile);
		} else {
			logger.log(Level.INFO, ANSI_GREEN + "CloudChat is loading social spy channel state.");
			state.loadSocialSpy(socialSpyFile);
		}

		// Check staff file
		this.staffFile = new File(getDataFolder() + "/staffstate.ser");
		if (!staffFile.exists()) {
			logger.log(Level.INFO, ANSI_GREEN + "CloudChat is creating staff channel state.");
			state.saveStaff(staffFile);
		} else {
			logger.log(Level.INFO, ANSI_GREEN + "CloudChat is loading staff channel state.");
			state.loadStaff(staffFile);
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
		logger.log(Level.INFO, ANSI_GREEN + "CloudChat is saving all data!");
		state.saveGlobal(globalFile);
		state.saveChannels(channelsFile);
		state.saveSocialSpy(socialSpyFile);
		state.saveStaff(staffFile);
		logger.log(Level.INFO, ANSI_GREEN + "CloudChat is disabling!");
	}

	public static CloudChat getPlugin() {
		return self;
	}
}
