package com.angelcraftonomy.bungeecloudchat.listener;

import com.angelcraftonomy.bungeecloudchat.CloudChat;
import com.angelcraftonomy.bungeecloudchat.CloudChatSingleton;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {

	private CloudChatSingleton playerLists;
	private CloudChat cloudChat;

	public ChatListener(CloudChat cloudChat) {
		this.playerLists = CloudChatSingleton.getInstance();
		this.cloudChat = cloudChat;
	}

	// on-chat listener
	@EventHandler
	public void onPlayerChat(ChatEvent chatEvent) {
		// if the sender is not the console
		if ((chatEvent.getSender() instanceof ProxiedPlayer) && !chatEvent.isCommand()) {
			ProxiedPlayer player = (ProxiedPlayer) chatEvent.getSender();

			// if the player has staff chat on, take priority over global
			if (playerLists.isInStaff(player.getName()) && player.hasPermission(CloudChatSingleton.STAFF_PERMISSION)) {
				// cancel the message in the single server chat
				chatEvent.setCancelled(true);
				for (ProxiedPlayer pp : this.cloudChat.getProxy().getPlayers()) {
					if (pp instanceof ProxiedPlayer && pp.hasPermission(CloudChatSingleton.STAFF_PERMISSION)) {
						pp.sendMessage(new TextComponent(ChatColor.WHITE + "[" + ChatColor.YELLOW
								+ player.getServer().getInfo().getName() + ChatColor.WHITE + "] " + ChatColor.GOLD
								+ player.getName() + ChatColor.BLUE + ": " + chatEvent.getMessage()));
					}
				}
			}
			// If the player has cloud chat toggled on
			else if (playerLists.isInGlobal(player.getName())
					&& player.hasPermission(CloudChatSingleton.GLOBAL_PERMISSION)) {
				// cancel the message in the single server chat
				chatEvent.setCancelled(true);
				// send it all players on the proxy instead (all servers)
				for (ProxiedPlayer pp : this.cloudChat.getProxy().getPlayers())
					if (((pp instanceof ProxiedPlayer)) && (pp.hasPermission("cloudchat.use")))
						pp.sendMessage(new TextComponent(ChatColor.WHITE + "[" + ChatColor.YELLOW
								+ player.getServer().getInfo().getName() + ChatColor.WHITE + "] " + ChatColor.GOLD
								+ player.getName() + ChatColor.WHITE + ": " + chatEvent.getMessage()));
			}

			// all messages sent to players with socialspy enabled
			for (ProxiedPlayer pp : this.cloudChat.getProxy().getPlayers()) {
				// if the player has the permission and has social spy
				// enabled
				if (pp.hasPermission(CloudChatSingleton.SOCIALSPY_PERMISSION)
						&& playerLists.isInSocialSpy(pp.getName()))
					pp.sendMessage(new TextComponent(ChatColor.WHITE + "[" + ChatColor.YELLOW + "SocialSpy"
							+ ChatColor.WHITE + "] " + ChatColor.GOLD + player.getName() + ChatColor.RED + ": "
							+ chatEvent.getMessage()));
			}
		}
	}

}
