package com.angelcraftonomy.bungeecloudchat;

import java.util.ArrayList;

public class CloudChatSingleton {

	private ArrayList<String> global;
	private ArrayList<String> socialSpy;

	// Private constructor prevents instantiation from other classes
	private CloudChatSingleton() {
		global = new ArrayList<>();
		socialSpy = new ArrayList<>();
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

	public void addPlayerSocialspy(String player) {
		if (!this.socialSpy.contains(player))
			this.socialSpy.add(player);
	}

	public void removePlayerSocialspy(String player) {
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

}
