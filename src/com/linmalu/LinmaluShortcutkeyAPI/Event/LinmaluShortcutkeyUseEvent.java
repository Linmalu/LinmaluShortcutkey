package com.linmalu.LinmaluShortcutkeyAPI.Event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.linmalu.LinmaluShortcutkey.LinmaluKeyboardData;

public class LinmaluShortcutkeyUseEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private LinmaluKeyboardData lkd;

	public static HandlerList getHandlerList()
	{
		return handlers;
	}
	public LinmaluShortcutkeyUseEvent(Player player, LinmaluKeyboardData lkd)
	{
		this.player = player;
		this.lkd = lkd;
	}
	public HandlerList getHandlers()
	{
		return handlers;
	}
	public Player getPlayer()
	{
		return player;
	}
	public LinmaluKeyboardData getLinmaluKeyboardData()
	{
		return lkd;
	}
}
