package com.angelcraftonomy.bungeecloudchat;

import java.util.ArrayList;

public class CloudChatSingleton {

	// chat groups
	private ArrayList<ArrayList<String>> channels;
	private ArrayList<String> global;
	private ArrayList<String> socialSpy;
	private ArrayList<String> staff;

	// permissions
	public static final String GLOBAL_PERMISSION = "cloudchat.use";
	public static final String SOCIALSPY_PERMISSION = "cloudchat.socialspy";
	public static final String STAFF_PERMISSION = "cloudchat.staff";
	public static final String LIST_PERMISSION = "cloudchat.list";

	// Private constructor prevents instantiation from other classes
	private CloudChatSingleton() {
		global = new ArrayList<>();
		socialSpy = new ArrayList<>();
		staff = new ArrayList<>();

		// add all channels to the list of channels
		channels = new ArrayList<>();
		channels.add(global);
		channels.add(staff);
	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		private static final CloudChatSingleton INSTANCE = new CloudChatSingleton();
	}

	public static CloudChatSingleton getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public boolean removeFromAllChannels(String player) {
		boolean retVal = false;
		for (ArrayList<String> channel : channels) {
			if (channel.contains(player)) {
				channel.remove(player);
				retVal = true;
			}
		}
		return retVal;
	}

	public void addPlayerSocialSpy(String player) {
		if (!this.socialSpy.contains(player))
			this.socialSpy.add(player);
	}

	public void removePlayerSocialSpy(String player) {
		if (this.socialSpy.contains(player))
			this.socialSpy.remove(player);
	}

	public boolean isInSocialSpy(String player) {
		boolean retVal = false;

		if (this.socialSpy.contains(player))
			retVal = true;

		return retVal;
	}

	public void addPlayerGlobal(String player) {
		if (!this.global.contains(player))
			this.global.add(player);
	}

	public void removePlayerGlobal(String player) {
		if (this.global.contains(player))
			this.global.remove(player);
	}

	public boolean isInGlobal(String player) {
		boolean retVal = false;

		if (this.global.contains(player))
			retVal = true;

		return retVal;
	}

	public void addPlayerStaff(String player) {
		if (!this.staff.contains(player))
			this.staff.add(player);
	}

	public void removePlayerStaff(String player) {
		if (this.staff.contains(player))
			this.staff.remove(player);
	}

	public boolean isInStaff(String player) {
		boolean retVal = false;

		if (this.staff.contains(player))
			retVal = true;

		return retVal;

	}

	public ArrayList<String> channelsToString() {
		ArrayList<String> lines = new ArrayList<>();
		String temp = "";
		for (ArrayList<String> channel : channels) {
			lines.add("Channel: " + channel.toString() + " ");
			for (String player : channel) {
				temp = temp.concat(player + " ");
			}

			lines.add(temp + "/n");
		}
		temp = "";
		lines.add("Channel: SocialSpy");
		for (String player : socialSpy) {
			temp.concat(player + " ");
		}
		lines.add(temp);
		return lines;
	}

}
