package com.angelcraftonomy.bungeecloudchat.listener;

import com.angelcraftonomy.bungeecloudchat.CloudChat;
import com.angelcraftonomy.bungeecloudchat.Manager;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class QuitListener implements Listener {

	private Manager playerLists;
	private CloudChat cloudChat;
	private ProxiedPlayer player;

	public QuitListener(CloudChat cloudChat) {
		this.playerLists = Manager.getInstance();
		this.cloudChat = cloudChat;
	}

	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent event) {
		player = event.getPlayer();
		if (!playerLists.isInGlobal(event.getPlayer().getName())) {
			playerLists.addPlayerGlobal(event.getPlayer().getName());
		}
		for (ProxiedPlayer p : cloudChat.getProxy().getPlayers())
			p.sendMessage(new ComponentBuilder(player.getName()).color(ChatColor.WHITE).append("logged off. Sad day")
					.color(ChatColor.GREEN).append(" :(").color(ChatColor.WHITE).create());

		// add playtime
		playerLists.addPlaytime(player.getName(), System.currentTimeMillis());

		playerLists.updateTitles();
	}
}
