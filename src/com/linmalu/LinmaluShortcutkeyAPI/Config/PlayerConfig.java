package com.linmalu.LinmaluShortcutkeyAPI.Config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.linmalu.LinmaluLibrary.API.LinmaluYamlConfiguration;
import com.linmalu.LinmaluShortcutkey.LinmaluKeyboardData;
import com.linmalu.LinmaluShortcutkeyAPI.Main;

public class PlayerConfig extends LinmaluConfig
{
	private final File folder = new File(Main.getMain().getDataFolder(), "players");
	private final String Alt = ".Alt";
	private final String Ctrl = ".Ctrl";
	private final String Shift = ".Shift";
	private final String Chat = ".Chat";
	private final HashMap<String, ArrayList<ShortcutkeyData>> players = new HashMap<>();

	public PlayerConfig()
	{
		reload();
		save();
	}
	public void reload()
	{
		players.clear();
		if(folder.exists())
		{
			for(File file : folder.listFiles())
			{
				if(file.getName().endsWith(".yml"))
				{
					LinmaluYamlConfiguration config = LinmaluYamlConfiguration.loadConfiguration(file);
					ArrayList<ShortcutkeyData> list = new ArrayList<>();
					for(String key : config.getKeys(false))
					{
						try
						{
							list.add(new ShortcutkeyData(config.getBoolean(key + Alt), config.getBoolean(key + Ctrl), config.getBoolean(key + Shift), Integer.parseInt(key), config.getString(key + Chat)));
						}
						catch(Exception e)
						{
							Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.GOLD + key + ChatColor.YELLOW + "가 숫자가 아닙니다.");
						}
					}
					players.put(file.getName().replace(".yml", "").toLowerCase(), list);
				}
			}
		}
		else
		{
			folder.mkdirs();
		}
	}
	public void save()
	{
		for(String player : players.keySet())
		{
			LinmaluYamlConfiguration config = new LinmaluYamlConfiguration();
			for(ShortcutkeyData sd : players.get(player))
			{
				config.set(sd.getKeyCode() + Alt, sd.isAlt());
				config.set(sd.getKeyCode() + Ctrl, sd.isCtrl());
				config.set(sd.getKeyCode() + Shift, sd.isShift());
				config.set(sd.getKeyCode() + Chat, sd.getChat());
			}
			try
			{
				config.save(new File(folder, player + ".yml"));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	public ShortcutkeyData getShortcutkeyData(String player, boolean alt, boolean ctrl, boolean shift, int keyCode)
	{
		checkPlayer(player);
		for(ShortcutkeyData sd : players.get(player.toLowerCase()))
		{
			if(alt == sd.isAlt() && ctrl == sd.isCtrl() && shift == sd.isShift() && keyCode == sd.getKeyCode())
			{
				return sd;
			}
		}
		return new ShortcutkeyData(alt, ctrl, shift, keyCode, "");
	}
	public ShortcutkeyData getShortcutkeyData(String player, LinmaluKeyboardData lkd)
	{
		checkPlayer(player);
		for(ShortcutkeyData sd : players.get(player.toLowerCase()))
		{
			if(lkd.isAlt() == sd.isAlt() && lkd.isCtrl() == sd.isCtrl() && lkd.isShift() == sd.isShift() && lkd.getLinmaluKeyboard().getKeyCode() == sd.getKeyCode())
			{
				return sd;
			}
		}
		return new ShortcutkeyData(lkd.isAlt(), lkd.isCtrl(), lkd.isShift(), lkd.getLinmaluKeyboard().getKeyCode(), "");
	}
	public void addShortcutkeyData(String player, ShortcutkeyData sd)
	{
		checkPlayer(player);
		players.get(player.toLowerCase()).add(sd);
		save();
	}
	public void checkPlayer(String player)
	{
		if(!players.containsKey(player.toLowerCase()))
		{
			players.put(player.toLowerCase(), new ArrayList<ShortcutkeyData>());
		}
	}
	public boolean contains(String player, ShortcutkeyData sd)
	{
		checkPlayer(player);
		return players.get(player.toLowerCase()).contains(sd);
	}
	public ArrayList<String> getList(String player)
	{
		checkPlayer(player);
		ArrayList<String> list = new ArrayList<>();
		for(ShortcutkeyData sd : players.get(player.toLowerCase()))
		{
			list.add(ChatColor.GOLD + Alt.replace(".", "") + ChatColor.GRAY + " : " + ChatColor.YELLOW + sd.isAlt() + ChatColor.WHITE + " / " + ChatColor.GOLD + Ctrl.replace(".", "") + ChatColor.GRAY + " : " + ChatColor.YELLOW + sd.isCtrl() + ChatColor.WHITE + " / " + ChatColor.GOLD + Shift.replace(".", "") + ChatColor.GRAY + " : " + ChatColor.YELLOW + sd.isShift() + ChatColor.WHITE + " / " + ChatColor.GOLD + "KeyCode" + ChatColor.GRAY + " : " + ChatColor.YELLOW + sd.getKeyCode() + ChatColor.WHITE + " / " + ChatColor.GOLD + Chat.replace(".", "") + ChatColor.GRAY + " : " + ChatColor.YELLOW + sd.getChat());
		}
		return list;
	}
	public void remove(String player, ShortcutkeyData sd)
	{
		checkPlayer(player);
		players.get(player.toLowerCase()).remove(sd);
		save();
	}
	public void clear(String player)
	{
		checkPlayer(player);
		players.get(player.toLowerCase()).clear();
		save();
	}
}
