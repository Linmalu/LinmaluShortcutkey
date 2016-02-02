package com.linmalu.LinmaluShortcutkeyAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.linmalu.LinmaluShortcutkeyAPI.Config.MainConfig;
import com.linmalu.LinmaluShortcutkeyAPI.Config.PlayerConfig;

public final class Main extends JavaPlugin
{
	private static Main main;
	private MainConfig mainConfig;
	private PlayerConfig playerConfig;

	public void onEnable()
	{
		main = this;
		getCommand(getDescription().getName()).setExecutor(new Main_Command());
		getServer().getPluginManager().registerEvents(new Main_Event(), this);
		Bukkit.getMessenger().registerIncomingPluginChannel(this, Main_Channel.channel, new Main_Channel());
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, Main_Channel.channel);
		for(Player player : Bukkit.getOnlinePlayers())
		{
			player.sendPluginMessage(Main.getMain(), Main_Channel.channel, new byte[]{0});
		}
		mainConfig = new MainConfig();
		playerConfig = new PlayerConfig();
		getLogger().info("제작 : 린마루");
	}
	public void onDisable()
	{
		getLogger().info("제작 : 린마루");
	}
	public static Main getMain()
	{
		return main;
	}
	public MainConfig getMainConfig()
	{
		return mainConfig;
	}
	public PlayerConfig getPlayerConfig()
	{
		return playerConfig;
	}
	public String getTitle()
	{
		return ChatColor.AQUA + "[" + getDescription().getDescription() + "] ";
	}
}
