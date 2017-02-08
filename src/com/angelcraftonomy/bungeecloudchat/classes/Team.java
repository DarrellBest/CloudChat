package com.angelcraftonomy.bungeecloudchat.classes;

import java.util.ArrayList;

import com.angelcraftonomy.bungeecloudchat.CloudChat;
import com.angelcraftonomy.bungeecloudchat.CloudChatSingleton;
import com.angelcraftonomy.bungeecloudchat.extenders.CommandExtender;
import com.angelcraftonomy.bungeecloudchat.interfaces.CommandInterface;

import net.md_5.bungee.api.plugin.Command;

public class Team {
	
	// Array List of players on a team
	private ArrayList<String> players = new ArrayList<String>();
	
	//Team settings
	private String teamName;
	private String password;
	private String owner;
	
	
	public Team(String teamName, String password, String creator) {
		teamName = this.teamName;
		if(this.password != null){
			password = this.password;
		} else {
			password = null;
		}
		creator = this.owner;
	}
	
	public void addPlayer(String name){
	}
	
	public void removePlayer(String name){
	}
	
	public void removeAllPlayers(){
	}
	
	public void changePassword(String password){
		if(this.password != null){
			password = this.password;
		} else {
			password = null;
		}
	}
	
	public void changeOwner(String name){
		owner = name;
	}
	
}
