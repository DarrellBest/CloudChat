package com.angelcraftonomy.bungeecloudchat.extenders;

import java.util.ArrayList;
import java.util.Random;

import com.angelcraftonomy.bungeecloudchat.CloudChat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

// To be extended!

public class CommandExtender {

	private CloudChat cloudChat;
	private CommandSender sender;
	private Command command;
	private ChatColor colorOne;
	private ChatColor colorTwo;
	private String name;
	private String alias;
	private String permission;
	private String[] args;

	public CommandExtender(CloudChat cloudChat, Command command, String name, String alias, String permission) {
		this.cloudChat = cloudChat;
		this.command = command;
		this.colorOne = ChatColor.GREEN;
		this.colorTwo = ChatColor.YELLOW;
		this.name = name;
		this.alias = alias;
		this.permission = permission;
	}

	public String getName() {
		return name;
	}

	public String getAlias() {
		return alias;
	}

	public String getPermission() {
		return permission;
	}

	// sends the no perm message
	protected void sendNoPermMessage() {
		// ChatComponent
		ProxiedPlayer player = getPlayer();
		player.sendMessage(new ComponentBuilder("[CloudChat] ").color(colorOne)
				.append("You do not have permission to use this command.").color(colorTwo).create());
	}

	// send player a message
	protected void sendMessage(String message) {
		ProxiedPlayer player = getPlayer();
		ChatColor colorOne = ChatColor.GREEN;
		ChatColor colorTwo = ChatColor.YELLOW;
		player.sendMessage(
				new ComponentBuilder("[CloudChat] ").color(colorOne).append(message).color(colorTwo).create());
	}

	// send player a message
	protected void sendMessage(String message, ProxiedPlayer player) {
		ChatColor colorOne = ChatColor.GREEN;
		ChatColor colorTwo = ChatColor.YELLOW;
		player.sendMessage(
				new ComponentBuilder("[CloudChat] ").color(colorOne).append(message).color(colorTwo).create());
	}

	protected void sendMessage(ArrayList<String> messages) {
		ProxiedPlayer player = getPlayer();
		ChatColor colorOne = getRandomColor();
		ChatColor colorTwo = getRandomColor();
		for (String message : messages) {
			player.sendMessage(new ComponentBuilder(" ").color(colorOne).append(message).color(colorTwo).create());
		}
	}

	// returns a random color code
	protected ChatColor getRandomColor() {
		ChatColor retVal;
		ArrayList<ChatColor> colors = new ArrayList<>();
		colors.add(ChatColor.AQUA);
		colors.add(ChatColor.BLUE);
		colors.add(ChatColor.GOLD);
		colors.add(ChatColor.GREEN);
		colors.add(ChatColor.LIGHT_PURPLE);
		colors.add(ChatColor.RED);
		colors.add(ChatColor.YELLOW);

		Random randGen = new Random();
		int randInt = randGen.nextInt(colors.size());

		retVal = colors.get(randInt);
		return retVal;
	}

	// get a list of online players
	protected ArrayList<ProxiedPlayer> getOnlinePlayers() {
		ArrayList<ProxiedPlayer> players = new ArrayList<>(cloudChat.getProxy().getPlayers());
		return players;
	}

	protected ProxiedPlayer getPlayer() {
		ArrayList<ProxiedPlayer> players = getOnlinePlayers();
		ProxiedPlayer player = players.get(players.indexOf(sender));
		return player;
	}

	public CloudChat getCloudChat() {
		return this.cloudChat;
	}

	public CommandSender getSender() {
		return sender;
	}

	public String[] getArgs() {
		return args;
	}

	public void setSender(CommandSender sender) {
		this.sender = sender;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public Command getCommand() {
		return command;
	}

}
