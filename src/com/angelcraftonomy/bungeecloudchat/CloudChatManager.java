package com.angelcraftonomy.bungeecloudchat;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class CloudChatManager extends Command implements Listener {
	public CloudChat cloudChat;
	public List<String> input = new ArrayList<>();

	public CloudChatManager(CloudChat cloudChat) {
		super("CloudChat", "CloudChatManager.use", new String[] { "cc" });
		this.cloudChat = cloudChat;

		// register command
		this.cloudChat.getProxy().getPluginManager().registerCommand(this.cloudChat, this);

		// register listener
		this.cloudChat.getProxy().getPluginManager().registerListener(this.cloudChat, this);
	}

	// Enter the command /cloudchat to toggle on
	@Override
	public void execute(CommandSender s, String[] args) {
		if (!this.input.contains(s.getName())) {
			this.input.add(s.getName());
			s.sendMessage(new TextComponent(ChatColor.GREEN + "CloudChat toggled on."));
			return;
		}

		this.input.remove(s.getName());
		s.sendMessage(new TextComponent(ChatColor.RED + "CloudChat toggled off."));
	}

	// on-chat listener
	@EventHandler
	public void onPlayerChat(ChatEvent e) {
		// if the sender is not the console
		if ((e.getSender() instanceof ProxiedPlayer)) {
			ProxiedPlayer s = (ProxiedPlayer) e.getSender();

			// If the player has cloud chat toggled on
			if ((this.input.contains(s.getName())) && (!e.isCommand()) && (s.hasPermission("CloudChatManager.use"))) {
				// cancel the message in the single server chat
				e.setCancelled(true);
				// send it all players on the proxy instead (all servers)
				for (ProxiedPlayer pl : this.cloudChat.getProxy().getPlayers())
					if (((pl instanceof ProxiedPlayer)) && (pl.hasPermission("CloudChatManager.use")))
						pl.sendMessage(new TextComponent(ChatColor.WHITE + "[" + ChatColor.YELLOW
								+ s.getServer().getInfo().getName() + ChatColor.WHITE + "] " + ChatColor.GOLD
								+ s.getName() + ChatColor.WHITE + ": " + e.getMessage()));
				return;
			}
			return;
		}
	}
}
