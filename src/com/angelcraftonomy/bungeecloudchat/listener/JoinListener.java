package com.angelcraftonomy.bungeecloudchat.listener;

import com.angelcraftonomy.bungeecloudchat.ChannelManager;
import com.angelcraftonomy.bungeecloudchat.CloudChat;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinListener implements Listener {

	private ChannelManager playerLists;
	private CloudChat cloudChat;
	private ProxiedPlayer player;
	private ChatEvent chatEvent;

	public JoinListener(CloudChat cloudChat) {
		this.playerLists = ChannelManager.getInstance();
		this.cloudChat = cloudChat;
	}

	// on player login, make sure they default into global chat
	@EventHandler
	public void onPostLogin(PostLoginEvent event) {
		if (!playerLists.isInGlobal(event.getPlayer().getName())) {
			playerLists.addPlayerGlobal(event.getPlayer().getName());
		}
	}
}
