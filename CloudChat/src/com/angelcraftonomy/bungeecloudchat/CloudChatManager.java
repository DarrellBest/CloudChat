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
	public List<String> input = new ArrayList<String>();

	// Enter the command /cloudchat to toggle on
	public CloudChatManager(CloudChat cloudChat) {
		super("CloudChat", "CloudChatManager.use", new String[0]);
		this.cloudChat = cloudChat;
		this.cloudChat.getProxy().getPluginManager()
				.registerCommand(this.cloudChat, this);
		this.cloudChat.getProxy().getPluginManager()
				.registerListener(this.cloudChat, this);
	}

	@Override
	public void execute(CommandSender s, String[] args) {
		if (!this.input.contains(s.getName())) {
			this.input.add(s.getName());
			s.sendMessage(new TextComponent(ChatColor.GREEN
					+ "CloudChat toggled on."));
			return;
		} 

		this.input.remove(s.getName());
		s.sendMessage(new TextComponent(ChatColor.RED
				+ "CloudChat toggled off."));
	}

	@EventHandler
	public void onPlayerChat(ChatEvent e) {
		if ((e.getSender() instanceof ProxiedPlayer)) {
			ProxiedPlayer s = (ProxiedPlayer) e.getSender();
			if ((this.input.contains(s.getName())) && (!e.isCommand())
					&& (s.hasPermission("CloudChatManager.use"))) {
				e.setCancelled(true);
				for (ProxiedPlayer pl : this.cloudChat.getProxy().getPlayers())
					if (((pl instanceof ProxiedPlayer))
							&& (pl.hasPermission("CloudChatManager.use")))
						pl.sendMessage(new TextComponent(ChatColor.WHITE + "["
								+ ChatColor.YELLOW
								+ s.getServer().getInfo().getName()
								+ ChatColor.WHITE + "] " + ChatColor.GOLD
								+ s.getName() + ChatColor.WHITE + ": "
								+ e.getMessage()));
				return;
			}
			return;
		}
	}
}
