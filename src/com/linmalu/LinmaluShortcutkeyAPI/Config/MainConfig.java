package com.linmalu.LinmaluShortcutkeyAPI.Config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.linmalu.LinmaluShortcutkey.LinmaluKeyboardData;
import com.linmalu.LinmaluShortcutkeyAPI.Main;
import com.linmalu.library.api.LinmaluYamlConfiguration;

public class MainConfig extends LinmaluConfig
{
	private final File file = new File(Main.getMain().getDataFolder(), "config.yml");
	private final String Alt = ".Alt";
	private final String Ctrl = ".Ctrl";
	private final String Shift = ".Shift";
	private final String Chat = ".Chat";
	private final ArrayList<ShortcutkeyData> keys = new ArrayList<>();

	public MainConfig()
	{
		reload();
		save();
	}
	public void reload()
	{
		keys.clear();
		LinmaluYamlConfiguration config = LinmaluYamlConfiguration.loadConfiguration(file);
		for(String key : config.getKeys(false))
		{
			try
			{
				int keyCode = Integer.parseInt(key);
				keys.add(new ShortcutkeyData(config.getBoolean(keyCode + Alt), config.getBoolean(keyCode + Ctrl), config.getBoolean(keyCode + Shift), keyCode, config.getString(keyCode + Chat)));
			}
			catch(Exception e)
			{
				Bukkit.broadcastMessage(Main.getMain().getTitle() + ChatColor.GOLD + key + ChatColor.YELLOW + "가 숫자가 아닙니다.");
			}
		}
	}
	public void save()
	{
		LinmaluYamlConfiguration config = new LinmaluYamlConfiguration();
		for(ShortcutkeyData sd : keys)
		{
			config.set(sd.getKeyCode() + Alt, sd.isAlt());
			config.set(sd.getKeyCode() + Ctrl, sd.isCtrl());
			config.set(sd.getKeyCode() + Shift, sd.isShift());
			config.set(sd.getKeyCode() + Chat, sd.getChat());
		}
		try
		{
			config.save(file);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public ShortcutkeyData getShortcutkeyData(String player, boolean alt, boolean ctrl, boolean shift, int keyCode)
	{
		for(ShortcutkeyData sd : keys)
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
		for(ShortcutkeyData sd : keys)
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
		keys.add(sd);
		save();
	}
	public boolean contains(String player, ShortcutkeyData sd)
	{
		return keys.contains(sd);
	}
	public ArrayList<String> getList(String player)
	{
		ArrayList<String> list = new ArrayList<>();
		for(ShortcutkeyData sd : keys)
		{
			list.add(ChatColor.GOLD + Alt.replace(".", "") + ChatColor.GRAY + " : " + ChatColor.YELLOW + sd.isAlt() + ChatColor.WHITE + " / " + ChatColor.GOLD + Ctrl.replace(".", "") + ChatColor.GRAY + " : " + ChatColor.YELLOW + sd.isCtrl() + ChatColor.WHITE + " / " + ChatColor.GOLD + Shift.replace(".", "") + ChatColor.GRAY + " : " + ChatColor.YELLOW + sd.isShift() + ChatColor.WHITE + " / " + ChatColor.GOLD + "KeyCode" + ChatColor.GRAY + " : " + ChatColor.YELLOW + sd.getKeyCode() + ChatColor.WHITE + " / " + ChatColor.GOLD + Chat.replace(".", "") + ChatColor.GRAY + " : " + ChatColor.YELLOW + sd.getChat());
		}
		return list;
	}
	public void remove(String player, ShortcutkeyData sd)
	{
		keys.remove(sd);
		save();
	}
	public void clear(String player)
	{
		keys.clear();
		save();
	}
}
