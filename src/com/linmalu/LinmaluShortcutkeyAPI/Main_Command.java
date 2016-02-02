package com.linmalu.LinmaluShortcutkeyAPI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.linmalu.LinmaluShortcutkeyAPI.Config.LinmaluConfig;
import com.linmalu.LinmaluShortcutkeyAPI.Config.ShortcutkeyData;
import com.linmalu.library.api.LinmaluTellraw;
import com.linmalu.library.api.LinmaluVersion;

public class Main_Command implements CommandExecutor
{
	private final String AllPlayerName = "전체";
	public Main_Command()
	{
		Main.getMain().getCommand(Main.getMain().getDescription().getName()).setTabCompleter(new TabCompleter()
		{			
			public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
			{
				ArrayList<String> list = new ArrayList<>();
				if(sender.isOp())
				{
					if(args.length == 1)
					{
						list.add("추가");
						list.add("목록");
						list.add("삭제");
						list.add("초기화");
						list.add("리로드");
						list.add("reload");
					}
					else if(args.length == 2 && (args[0].equals("추가") || args[0].equals("목록") || args[0].equals("삭제") || args[0].equals("초기화")))
					{
						list.add(AllPlayerName);
						for(Player player : Bukkit.getOnlinePlayers())
						{
							list.add(player.getName().toLowerCase());
						}
					}
					else if((args.length == 3 || args.length == 4 || args.length == 5) && (args[0].equals("추가") || args[0].equals("삭제")))
					{
						list.add("true");
						list.add("false");
					}
				}
				return list;
			}
		});
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(sender.isOp())
		{
			if(args.length > 6 && args[0].equals("추가"))
			{
				LinmaluConfig config = args[1].equals(AllPlayerName) ? Main.getMain().getMainConfig() : Main.getMain().getPlayerConfig();
				try
				{
					String chat = "";
					for(int i = 6; i < args.length; i++)
					{
						chat += args[i];
						if(i != args.length)
						{
							chat += " ";
						}
					}
					ShortcutkeyData sd = config.getShortcutkeyData(args[1], Boolean.parseBoolean(args[2]), Boolean.parseBoolean(args[3]), Boolean.parseBoolean(args[4]), Integer.parseInt(args[5]));
					if(config.contains(args[1], sd))
					{
						sd.setChat(chat);
						config.addShortcutkeyData(args[1], sd);
						sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "단축키가 수정되었습니다.");
					}
					else
					{
						sd.setChat(chat);
						config.addShortcutkeyData(args[1], sd);
						sender.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "단축키가 등록되었습니다.");
					}
				}
				catch(Exception e)
				{
					sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "키코드가 숫자가 아닙니다.");
				}
				return true;
			}
			else if(args.length == 2 && args[0].equals("목록"))
			{
				LinmaluConfig config = args[1].equals(AllPlayerName) ? Main.getMain().getMainConfig() : Main.getMain().getPlayerConfig();
				sender.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "단축키 목록");
				for(String msg : config.getList(args[1]))
				{
					sender.sendMessage(msg);
				}
				return true;
			}
			else if(args.length == 6 && args[0].equals("삭제"))
			{
				LinmaluConfig config = args[1].equals(AllPlayerName) ? Main.getMain().getMainConfig() : Main.getMain().getPlayerConfig();
				try
				{
					ShortcutkeyData sd = config.getShortcutkeyData(args[1], Boolean.parseBoolean(args[2]), Boolean.parseBoolean(args[3]), Boolean.parseBoolean(args[4]), Integer.parseInt(args[5]));
					if(config.contains(args[1], sd))
					{
						config.remove(args[1], sd);
						sender.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "단축키가 삭제되었습니다.");
					}
					else
					{
						sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "단축키가 등록되어있지 않습니다.");
					}
				}
				catch(Exception e)
				{
					sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "키코드가 숫자가 아닙니다.");
				}
				return true;
			}
			else if(args.length == 2 && args[0].equals("초기화"))
			{
				LinmaluConfig config = args[1].equals(AllPlayerName) ? Main.getMain().getMainConfig() : Main.getMain().getPlayerConfig();
				config.clear(args[1]);
				sender.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "단축키가 초기화되었습니다.");
				return true;
			}
			else if(args.length == 1 && (args[0].equals("리로드") || args[0].equals("reload")))
			{
				Main.getMain().getMainConfig().reload();
				Main.getMain().getPlayerConfig().reload();
				sender.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "리로드가 완료되었습니다.");
				return true;
			}
			sender.sendMessage(ChatColor.GREEN + " = = = = = [ Linmalu Shortcutkey API ] = = = = =");
			LinmaluTellraw.sendCmdChat(sender, "/" + label + " 추가 ", ChatColor.GOLD + "/" + label + " 추가 <전체/플레이어이름> <Alt(true/false)> <Ctrl(true/false)> <Shift(true/false)> <Keycode> <채팅> (채팅)..." + ChatColor.GRAY + " : 단축키 추가");
			LinmaluTellraw.sendCmdChat(sender, "/" + label + " 목록 ", ChatColor.GOLD + "/" + label + " 목록 <전체/플레이어이름>" + ChatColor.GRAY + " : 단축키 목록");
			LinmaluTellraw.sendCmdChat(sender, "/" + label + " 삭제 ", ChatColor.GOLD + "/" + label + " 삭제 <전체/플레이어이름> <Alt(true/false)> <Ctrl(true/false)> <Shift(true/false)> <Keycode> <채팅> (채팅)..." + ChatColor.GRAY + " : 단축키 삭제");
			LinmaluTellraw.sendCmdChat(sender, "/" + label + " 초기화 ", ChatColor.GOLD + "/" + label + " 초기화 <전체/플레이어이름>" + ChatColor.GRAY + " : 단축키 초기화");
			LinmaluTellraw.sendCmdChat(sender, "/" + label + " 리로드 ", ChatColor.GOLD + "/" + label + " 리로드" + ChatColor.GRAY + " : 플러그인 리로드");
			sender.sendMessage(ChatColor.YELLOW + "제작자 : " + ChatColor.AQUA + "린마루(Linmalu)" + ChatColor.WHITE + " - http://blog.linmalu.com");
			sender.sendMessage(ChatColor.YELLOW + "카페 : " + ChatColor.WHITE + "http://cafe.naver.com/craftproducer");
			LinmaluVersion.check(Main.getMain(), sender, Main.getMain().getTitle() + ChatColor.GREEN + "최신버전이 존재합니다.");
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "권한이 없습니다.");
		}
		return true;
	}
}
