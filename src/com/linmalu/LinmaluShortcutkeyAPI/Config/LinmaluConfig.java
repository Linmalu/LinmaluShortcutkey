package com.linmalu.LinmaluShortcutkeyAPI.Config;

import java.util.ArrayList;

import com.linmalu.LinmaluShortcutkey.LinmaluKeyboardData;

public abstract class LinmaluConfig
{
	public abstract void save();
	public abstract void reload();
	public abstract ShortcutkeyData getShortcutkeyData(String player, boolean alt, boolean ctrl, boolean shift, int keyCode);
	public abstract ShortcutkeyData getShortcutkeyData(String player, LinmaluKeyboardData lkd);
	public abstract void addShortcutkeyData(String player, ShortcutkeyData sd);
	public abstract boolean contains(String player, ShortcutkeyData sd);
	public abstract ArrayList<String> getList(String player);
	public abstract void remove(String player, ShortcutkeyData sd);
	public abstract void clear(String player);
}
