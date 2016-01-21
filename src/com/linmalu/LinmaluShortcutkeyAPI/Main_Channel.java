package com.linmalu.LinmaluShortcutkeyAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.linmalu.LinmaluShortcutkey.LinmaluKeyboardData;
import com.linmalu.LinmaluShortcutkeyAPI.Event.LinmaluShortcutkeyJoinEvent;
import com.linmalu.LinmaluShortcutkeyAPI.Event.LinmaluShortcutkeyUseEvent;

public class Main_Channel implements PluginMessageListener
{
	public static final String channel = "LinmaluShortcutkey";

	public void onPluginMessageReceived(String channel, Player player, byte[] message)
	{
		if(Main_Channel.channel.equals(channel))
		{
			if(message.length == 1 && message[0] == 0)
			{
				LinmaluShortcutkeyJoinEvent event = new LinmaluShortcutkeyJoinEvent(player);
				Bukkit.getServer().getPluginManager().callEvent(event);
			}
			else if(message.length > 1 && message[0] == 1)
			{
				byte[] bytes = new byte[message.length - 1];
				for(int i = 0; i < bytes.length; i++)
				{
					bytes[i] = message[i + 1];
				}
				try
				{
					LinmaluShortcutkeyUseEvent event = new LinmaluShortcutkeyUseEvent(player, LinmaluKeyboardData.toLinmaluKeyboardData(bytes));
					Bukkit.getServer().getPluginManager().callEvent(event);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
