package com.angelcraftonomy.bungeecloudchat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class CloudChatSingleton implements Serializable {

	private static final long serialVersionUID = -4606175994628692132L;

	private static CloudChatSingleton INSTANCE = null;

	public static synchronized CloudChatSingleton getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CloudChatSingleton();
		}
		return INSTANCE;
	}

	// chat groups
	private ArrayList<ArrayList<String>> channels;
	private ArrayList<String> global;
	private ArrayList<String> socialSpy;
	private ArrayList<String> staff;
	private int test;

	// permissions
	public static final String GLOBAL_PERMISSION = "cloudchat.use";
	public static final String SOCIALSPY_PERMISSION = "cloudchat.socialspy";
	public static final String STAFF_PERMISSION = "cloudchat.staff";
	public static final String LIST_PERMISSION = "cloudchat.list";
	public static final String TEST_PERMISSION = "cloudchat.test";

	// Private constructor prevents instantiation from other classes
	private CloudChatSingleton() {
		global = new ArrayList<>();
		socialSpy = new ArrayList<>();
		staff = new ArrayList<>();
		test = 0;

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
	// private static class SingletonHolder {
	// private static final CloudChatSingleton INSTANCE = new
	// CloudChatSingleton();
	// }

	// public static CloudChatSingleton getInstance() {
	// return SingletonHolder.INSTANCE;
	// }

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

	public void save(File file) {
		try {
			// FileWriter fw = new FileWriter()

			FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath());
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			System.out.printf("CloudChat data is saved in ccstate.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void load(File file) {
		try {
			FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
			ObjectInputStream in = new ObjectInputStream(fileIn);
			INSTANCE = (CloudChatSingleton) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Class not found");
			c.printStackTrace();
			return;
		}
	}

	public ArrayList<String> channelsToString() {
		ArrayList<String> lines = new ArrayList<>();
		String temp = "";

		// Global list
		lines.add("Channel: Global");
		for (String player : this.global) {
			temp = temp.concat(" " + player);
		}
		lines.add(temp);
		temp = "";

		// Staff list
		lines.add("Channel: Staff");
		for (String player : this.staff) {
			temp = temp.concat(" " + player);
		}
		lines.add(temp);
		temp = "";

		// Social Spy list
		lines.add("Channel: SocialSpy");
		for (String player : socialSpy) {
			temp = temp.concat(" " + player);
		}
		lines.add(temp);

		return lines;
	}

	public int getTest() {
		return test;
	}

	public void changeTest(int test) {
		test = this.test;
	}
}
