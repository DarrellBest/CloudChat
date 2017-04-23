package com.angelcraftonomy.bungeecloudchat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class ChannelManager implements Serializable {

	private static final long serialVersionUID = -4606175994628692132L;

	private static ChannelManager INSTANCE = null;

	public static ChannelManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ChannelManager();
		}
		return INSTANCE;
	}

	// chat groups
	private ArrayList<ArrayList<String>> channels;
	private ArrayList<String> global;
	private ArrayList<String> socialSpy;
	private ArrayList<String> staff;
	private ArrayList<String> commandSpy;

	// nicknames
	private HashMap<String, String> nicknames = new HashMap<>();

	// test
	private Integer test;

	// Max nickname length
	private int nickLength;

	// permissions
	public static final String GLOBAL_PERMISSION = "cloudchat.use";
	public static final String SOCIALSPY_PERMISSION = "cloudchat.socialspy";
	public static final String STAFF_PERMISSION = "cloudchat.staff";
	public static final String LIST_PERMISSION = "cloudchat.list";
	public static final String TEST_PERMISSION = "cloudchat.test";
	public static final String NICK_PERMISSION = "cloudchat.nick";
	public static final String SAVE_PERMISSION = "cloudchat.save";
	public static final String COMMANDSPY_PERMISISON = "cloudchat.commandspy";

	// Private constructor prevents instantiation from other classes
	private ChannelManager() {
		global = new ArrayList<>();
		socialSpy = new ArrayList<>();
		staff = new ArrayList<>();
		commandSpy = new ArrayList<>();
		test = new Integer(0);

		// add all channels to the list of channels
		channels = new ArrayList<>();
		channels.add(global);
		channels.add(staff);

		this.nickLength = 15;
	}

	public void addNickname(String player, String nick) {
		nicknames.put(player, nick.replace("&", "§"));
	}

	public String getNickname(String player) {
		String retVal = null;
		Boolean contains = null;
		// Will not return false, so it must be done this way
		if (nicknames != null)
			contains = nicknames.containsKey(player);
		if (contains != null)
			retVal = nicknames.get(player);
		if (retVal == null)
			retVal = player;
		return retVal;
	}

	public void removeFromAllChannels(String player) {
		this.removePlayerGlobal(player);
		this.removePlayerStaff(player);
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

	public void addPlayerCommandSpy(String player) {
		if (!this.commandSpy.contains(player))
			this.commandSpy.add(player);
	}

	public void removePlayerCommandSpy(String player) {
		if (this.commandSpy.contains(player))
			this.commandSpy.remove(player);
	}

	public boolean isInCommandSpy(String player) {
		boolean retVal = false;

		if (this.commandSpy.contains(player))
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

	public int getTest() {
		return test;
	}

	public void changeTest(int test) {
		this.test = test;
	}

	// Save methods
	public void saveChannels(File file) {
		try {
			FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath());
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(global);
			out.close();
			fileOut.close();
			System.out.printf("CloudChat channels data is saved");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void saveGlobal(File file) {
		try {
			FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath());
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this.global);
			out.close();
			fileOut.close();
			System.out.printf("CloudChat global data is saved");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void saveStaff(File file) {
		try {
			FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath());
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this.staff);
			out.close();
			fileOut.close();
			System.out.printf("CloudChat staff data is saved");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void saveSocialSpy(File file) {
		try {
			FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath());
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this.socialSpy);
			out.close();
			fileOut.close();
			System.out.printf("CloudChat social spy data saved");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void saveCommandSpy(File file) {
		try {
			FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath());
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this.commandSpy);
			out.close();
			fileOut.close();
			System.out.println("CloudChat command spy saved");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void saveNicknames(File file) {
		try {
			FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath());
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this.nicknames);
			out.close();
			fileOut.close();
			System.out.printf("CloudChat nicknames saved");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	// Load methods
	public void loadChannels(File file) {
		try {
			FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
			ObjectInputStream in = new ObjectInputStream(fileIn);
			channels = (ArrayList<ArrayList<String>>) in.readObject();
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

	public void loadGlobal(File file) {
		try {
			FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
			ObjectInputStream in = new ObjectInputStream(fileIn);
			global = (ArrayList<String>) in.readObject();
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

	public void loadStaff(File file) {
		try {
			FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
			ObjectInputStream in = new ObjectInputStream(fileIn);
			this.staff = (ArrayList<String>) in.readObject();
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

	public void loadSocialSpy(File file) {
		try {
			FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
			ObjectInputStream in = new ObjectInputStream(fileIn);
			this.socialSpy = (ArrayList<String>) in.readObject();
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

	public void loadCommandSpy(File file) {
		try {
			FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
			ObjectInputStream in = new ObjectInputStream(fileIn);
			this.commandSpy = (ArrayList<String>) in.readObject();
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

	public void loadNicknames(File file) {
		try {
			FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
			ObjectInputStream in = new ObjectInputStream(fileIn);
			this.nicknames = (HashMap<String, String>) in.readObject();
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

	public int getMaxSize() {
		return this.nickLength;
	}
}
