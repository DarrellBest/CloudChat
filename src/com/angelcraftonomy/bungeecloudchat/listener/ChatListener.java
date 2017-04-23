package com.angelcraftonomy.bungeecloudchat.listener;

import com.angelcraftonomy.bungeecloudchat.ChannelManager;
import com.angelcraftonomy.bungeecloudchat.CloudChat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {

	private ChannelManager playerLists;
	private CloudChat cloudChat;
	private ProxiedPlayer player;
	private ChatEvent chatEvent;

	public ChatListener(CloudChat cloudChat) {
		this.playerLists = ChannelManager.getInstance();
		this.cloudChat = cloudChat;
	}

	// on-chat listener
	@EventHandler
	public void onPlayerChat(ChatEvent chatEvent) {
		player = (ProxiedPlayer) chatEvent.getSender();

		// if the sender is not the console and the chat event is not a command!
		if (!chatEvent.isCommand()) {
			String nickname = player.getName();
			nickname = playerLists.getNickname(nickname);
			this.chatEvent = chatEvent;

			// if the player has staff chat on, take priority over global
			if (playerLists.isInStaff(player.getName()) && player.hasPermission(ChannelManager.STAFF_PERMISSION)) {
				chatEvent.setCancelled(true);
				this.sendToStaffChannel(chatEvent.getMessage(), nickname);
			}

			// If the player has cloud chat toggled on
			if (playerLists.isInGlobal(player.getName()) && player.hasPermission(ChannelManager.GLOBAL_PERMISSION)) {
				chatEvent.setCancelled(true);
				this.sendtoGolbalChannel(chatEvent.getMessage(), nickname);
			} else {
				// send all non global chats to socialspy
				if (playerLists.isInSocialSpy(player.getName()))
					this.sendToSocialSpyChannel(chatEvent.getMessage(), player.getName());
			}
		}
		// if it is a player and it is a command
		if (chatEvent.isCommand()) {
			// spies on commands
			this.sendToCommandSpyChannel(chatEvent.getMessage(), player.getName());
		}
	}

	private void sendtoGolbalChannel(String message, String nickname) {
		String name = ((ProxiedPlayer) chatEvent.getSender()).getName();
		name = playerLists.getNickname(name);
		// send it all players on the proxy instead (all servers)
		for (ProxiedPlayer pp : this.cloudChat.getProxy().getPlayers())
			if (((pp instanceof ProxiedPlayer)) && (pp.hasPermission(ChannelManager.GLOBAL_PERMISSION)))
				pp.sendMessage(new TextComponent(ChatColor.WHITE + "[" + ChatColor.YELLOW + "Global" + ChatColor.WHITE
						+ "] " + ChatColor.GOLD + nickname + ChatColor.WHITE + ": " + message));
	}

	private void sendToSocialSpyChannel(String message, String nickname) {
		// all messages sent to players with social spy enabled
		for (ProxiedPlayer pp : this.cloudChat.getProxy().getPlayers()) {
			// if the player has the permission and has social spy enabled
			if (pp.hasPermission(ChannelManager.SOCIALSPY_PERMISSION) && playerLists.isInSocialSpy(pp.getName()))
				if (!player.getName().equals(pp.getName()))
					pp.sendMessage(new TextComponent(
							ChatColor.WHITE + "[" + ChatColor.GRAY + "SocialSpy" + ChatColor.WHITE + "] "
									+ ChatColor.GOLD + nickname + ChatColor.WHITE + ": " + ChatColor.GRAY + message));
		}
	}

	private void sendToStaffChannel(String message, String nickname) {
		for (ProxiedPlayer pp : this.cloudChat.getProxy().getPlayers())
			if (pp instanceof ProxiedPlayer && pp.hasPermission(ChannelManager.STAFF_PERMISSION)) {
				pp.sendMessage(new TextComponent(ChatColor.WHITE + "[" + ChatColor.AQUA + "Staff" + ChatColor.WHITE
						+ "] " + ChatColor.GOLD + nickname + ChatColor.WHITE + ": " + ChatColor.AQUA + message));
			}
	}

	private void sendToCommandSpyChannel(String message, String nickname) {
		// all messages sent to players with Command spy enabled
		for (ProxiedPlayer pp : this.cloudChat.getProxy().getPlayers()) {
			// if the player has the permission and has command spy enabled
			if (pp.hasPermission(ChannelManager.COMMANDSPY_PERMISISON) && playerLists.isInCommandSpy(pp.getName()))
				if (!player.getName().equals(pp.getName()))
					pp.sendMessage(new TextComponent(
							ChatColor.WHITE + "[" + ChatColor.GRAY + "CommandSpy" + ChatColor.WHITE + "] "
									+ ChatColor.GOLD + nickname + ChatColor.WHITE + ": " + ChatColor.GRAY + message));
		}
	}

}
