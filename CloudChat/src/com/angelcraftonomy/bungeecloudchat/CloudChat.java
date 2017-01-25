package com.angelcraftonomy.bungeecloudchat;

import java.util.logging.Level;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class CloudChat extends Plugin implements Listener {
	private static CloudChat self;
	public static ServerInfo hub;

	@Override
	public void onEnable() {
		self = this;
		getProxy().getLogger().log(Level.INFO,
				"BungeeCloudChat may or may not enable!");
		new CloudChatManager(self);
		getProxy().getLogger().log(Level.INFO, "BungeeCloudChat is enabled!");
	}

	@Override
	public void onDisable() {
		getProxy().getLogger().log(Level.INFO, "BungeeCloudChat is disabling!");
	}

	public static CloudChat getPlugin() {
		return self;
	}
}
