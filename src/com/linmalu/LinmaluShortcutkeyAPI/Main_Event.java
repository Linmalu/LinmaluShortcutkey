package com.linmalu.LinmaluShortcutkeyAPI;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.linmalu.LinmaluLibrary.API.LinmaluCheckVersion;
import com.linmalu.LinmaluShortcutkeyAPI.Config.LinmaluConfig;
import com.linmalu.LinmaluShortcutkeyAPI.Config.ShortcutkeyData;
import com.linmalu.LinmaluShortcutkeyAPI.Event.LinmaluShortcutkeyUseEvent;

public class Main_Event implements Listener
{
	@EventHandler
	public void Event(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		if(player.isOp())
		{
			new LinmaluCheckVersion(Main.getMain(), player, Main.getMain().getTitle() + ChatColor.GREEN + "최신버전이 존재합니다.");
		}
	}
	@EventHandler
	public void Event(PlayerChannelEvent event)
	{
		if(event.getChannel().equals(Main_Channel.channel))
		{
			event.getPlayer().sendPluginMessage(Main.getMain(), Main_Channel.channel, new byte[]{0});
		}
	}
	@EventHandler
	public void Event(LinmaluShortcutkeyUseEvent event)
	{
		Player player = event.getPlayer();
		LinmaluConfig config = Main.getMain().getPlayerConfig();
		ShortcutkeyData sd = config.getShortcutkeyData(player.getName(), event.getLinmaluKeyboardData());
		if(config.contains(player.getName(), sd))
		{
			player.chat(sd.getChat());
		}
		else
		{
			config = Main.getMain().getMainConfig();
			sd = config.getShortcutkeyData(player.getName(), event.getLinmaluKeyboardData());
			if(config.contains(player.getName(), sd))
			{
				player.chat(sd.getChat());
			}
		}
	}
}
