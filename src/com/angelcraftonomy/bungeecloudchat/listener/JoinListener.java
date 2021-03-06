package com.angelcraftonomy.bungeecloudchat.listener;

import com.angelcraftonomy.bungeecloudchat.CloudChat;
import com.angelcraftonomy.bungeecloudchat.Manager;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinListener implements Listener {

	private Manager playerLists;
	private CloudChat cloudChat;
	private ProxiedPlayer player;

	public JoinListener(CloudChat cloudChat) {
		this.playerLists = Manager.getInstance();
		this.cloudChat = cloudChat;
	}

	// on player login, make sure they default into global chat
	@EventHandler
	public void onPostLogin(PostLoginEvent event) {
		player = event.getPlayer();

		// join welcome message
		if (!playerLists.isInGlobal(event.getPlayer().getName())) {
			playerLists.addPlayerGlobal(event.getPlayer().getName());
		}
		for (ProxiedPlayer p : cloudChat.getProxy().getPlayers())
			p.sendMessage(new ComponentBuilder("Please welcome back ").color(ChatColor.WHITE).append(player.getName())
					.color(ChatColor.GREEN).append("!").color(ChatColor.WHITE).create());

		// set player time to 0 if new
		playerLists.setPlaytime(player.getName(), new Long(0));

		// save login time
		playerLists.addJointime(player.getName(), System.currentTimeMillis());
	}
}
