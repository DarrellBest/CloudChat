package com.angelcraftonomy.bungeecloudchat.listener;

import com.angelcraftonomy.bungeecloudchat.CloudChat;
import com.angelcraftonomy.bungeecloudchat.CloudChatSingleton;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {

	private CloudChatSingleton playerLists;
	private CloudChat cloudChat;
	private ProxiedPlayer player;
	private ChatEvent chatEvent;

	public ChatListener(CloudChat cloudChat) {
		this.playerLists = CloudChatSingleton.getInstance();
		this.cloudChat = cloudChat;
	}

	// on-chat listener
	@EventHandler
	public void onPlayerChat(ChatEvent chatEvent) {
		player = (ProxiedPlayer) chatEvent.getSender();

		// if the sender is not the console and the chat event is not a command!
		if ((chatEvent.getSender() instanceof CommandSender) && !chatEvent.isCommand()) {
			String nickname = player.getName();
			nickname = playerLists.getNickname(nickname);
			this.chatEvent = chatEvent;

			// send all chats to socialspy
			this.sendToSocialSpyChannel(chatEvent.getMessage(), player.getName());

			// if the player has staff chat on, take priority over global
			if (playerLists.isInStaff(player.getName()) && player.hasPermission(CloudChatSingleton.STAFF_PERMISSION)) {
				chatEvent.setCancelled(true);
				this.sendToStaffChannel(chatEvent.getMessage(), nickname);
			}

			// If the player has cloud chat toggled on
			if (playerLists.isInGlobal(player.getName())
					&& player.hasPermission(CloudChatSingleton.GLOBAL_PERMISSION)) {
				chatEvent.setCancelled(true);
				this.sendtoGolbalChannel(chatEvent.getMessage(), nickname);
			}
		}
		// if it is a player and it is a command
		if ((chatEvent.getSender() instanceof CommandSender) && chatEvent.isCommand()) {
			// spies on commands
			this.sendToSocialSpyChannel(chatEvent.getMessage(), player.getName());
		}
	}

	private void sendtoGolbalChannel(String message, String nickname) {
		String name = ((ProxiedPlayer) chatEvent.getSender()).getName();
		name = playerLists.getNickname(name);
		// send it all players on the proxy instead (all servers)
		for (ProxiedPlayer pp : this.cloudChat.getProxy().getPlayers())
			if (((pp instanceof ProxiedPlayer)) && (pp.hasPermission(CloudChatSingleton.GLOBAL_PERMISSION)))
				pp.sendMessage(new TextComponent(ChatColor.WHITE + "[" + ChatColor.YELLOW
						+ player.getServer().getInfo().getName() + ChatColor.WHITE + "] " + ChatColor.WHITE + "["
						+ ChatColor.YELLOW + "Global" + ChatColor.WHITE + "] " + ChatColor.GOLD + nickname
						+ ChatColor.WHITE + ": " + message));
	}

	private void sendToSocialSpyChannel(String message, String nickname) {
		// all messages sent to players with social spy enabled
		for (ProxiedPlayer pp : this.cloudChat.getProxy().getPlayers()) {
			// if the player has the permission and has social spy enabled
			if (pp.hasPermission(CloudChatSingleton.SOCIALSPY_PERMISSION) && playerLists.isInSocialSpy(pp.getName()))
				pp.sendMessage(new TextComponent(ChatColor.WHITE + "[" + ChatColor.YELLOW
						+ player.getServer().getInfo().getName() + ChatColor.WHITE + "] " + ChatColor.WHITE + "["
						+ ChatColor.GRAY + "SocialSpy" + ChatColor.WHITE + "] " + ChatColor.GOLD + nickname
						+ ChatColor.WHITE + ": " + ChatColor.GRAY + message));
		}
	}

	private void sendToStaffChannel(String message, String nickname) {
		for (ProxiedPlayer pp : this.cloudChat.getProxy().getPlayers())
			if (pp instanceof ProxiedPlayer && pp.hasPermission(CloudChatSingleton.STAFF_PERMISSION)) {
				pp.sendMessage(new TextComponent(ChatColor.WHITE + "[" + ChatColor.YELLOW
						+ player.getServer().getInfo().getName() + ChatColor.WHITE + "] " + ChatColor.WHITE + "["
						+ ChatColor.AQUA + "Staff" + ChatColor.WHITE + "] " + ChatColor.GOLD + nickname
						+ ChatColor.WHITE + ": " + ChatColor.AQUA + message));
			}
	}

}
