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

import com.linmalu.LinmaluLibrary.API.LinmaluCheckVersion;
import com.linmalu.LinmaluLibrary.API.LinmaluTellraw;
import com.linmalu.LinmaluShortcutkeyAPI.Config.LinmaluConfig;
import com.linmalu.LinmaluShortcutkeyAPI.Config.ShortcutkeyData;

public class Main_Command implements CommandExecutor
{
	private final String AllPlayerName = "��ü";
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
						list.add("�߰�");
						list.add("���");
						list.add("����");
						list.add("�ʱ�ȭ");
						list.add("���ε�");
						list.add("reload");
					}
					else if(args.length == 2 && (args[0].equals("�߰�") || args[0].equals("���") || args[0].equals("����") || args[0].equals("�ʱ�ȭ")))
					{
						list.add(AllPlayerName);
						for(Player player : Bukkit.getOnlinePlayers())
						{
							list.add(player.getName().toLowerCase());
						}
					}
					else if((args.length == 3 || args.length == 4 || args.length == 5) && (args[0].equals("�߰�") || args[0].equals("����")))
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
			if(args.length > 6 && args[0].equals("�߰�"))
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
						sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "����Ű�� �����Ǿ����ϴ�.");
					}
					else
					{
						sd.setChat(chat);
						config.addShortcutkeyData(args[1], sd);
						sender.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "����Ű�� ��ϵǾ����ϴ�.");
					}
				}
				catch(Exception e)
				{
					sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "Ű�ڵ尡 ���ڰ� �ƴմϴ�.");
				}
				return true;
			}
			else if(args.length == 2 && args[0].equals("���"))
			{
				LinmaluConfig config = args[1].equals("AllPlayerName") ? Main.getMain().getMainConfig() : Main.getMain().getPlayerConfig();
				sender.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "����Ű ���");
				for(String msg : config.getList(args[1]))
				{
					sender.sendMessage(msg);
				}
				return true;
			}
			else if(args.length == 6 && args[0].equals("����"))
			{
				LinmaluConfig config = args[1].equals(AllPlayerName) ? Main.getMain().getMainConfig() : Main.getMain().getPlayerConfig();
				try
				{
					ShortcutkeyData sd = config.getShortcutkeyData(args[1], Boolean.parseBoolean(args[2]), Boolean.parseBoolean(args[3]), Boolean.parseBoolean(args[4]), Integer.parseInt(args[5]));
					if(config.contains(args[1], sd))
					{
						config.remove(args[1], sd);
						sender.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "����Ű�� �����Ǿ����ϴ�.");
					}
					else
					{
						sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "����Ű�� ��ϵǾ����� �ʽ��ϴ�.");
					}
				}
				catch(Exception e)
				{
					sender.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "Ű�ڵ尡 ���ڰ� �ƴմϴ�.");
				}
				return true;
			}
			else if(args.length == 2 && args[0].equals("�ʱ�ȭ"))
			{
				LinmaluConfig config = args[1].equals(AllPlayerName) ? Main.getMain().getMainConfig() : Main.getMain().getPlayerConfig();
				config.clear(args[1]);
				sender.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "����Ű�� �ʱ�ȭ�Ǿ����ϴ�.");
				return true;
			}
			else if(args.length == 1 && (args[0].equals("���ε�") || args[0].equals("reload")))
			{
				Main.getMain().getMainConfig().reload();
				Main.getMain().getPlayerConfig().reload();
				sender.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "���ε尡 �Ϸ�Ǿ����ϴ�.");
				return true;
			}
			sender.sendMessage(ChatColor.GREEN + " = = = = = [ Linmalu Shortcutkey API ] = = = = =");
			LinmaluTellraw.sendCmdChat(sender, "/" + label + " �߰� ", ChatColor.GOLD + "/" + label + " �߰� <��ü/�÷��̾��̸�> <Alt(true/false)> <Ctrl(true/false)> <Shift(true/false)> <Keycode> <ä��> (ä��)..." + ChatColor.GRAY + " : ����Ű �߰�");
			LinmaluTellraw.sendCmdChat(sender, "/" + label + " ��� ", ChatColor.GOLD + "/" + label + " ��� <��ü/�÷��̾��̸�>" + ChatColor.GRAY + " : ����Ű ���");
			LinmaluTellraw.sendCmdChat(sender, "/" + label + " ���� ", ChatColor.GOLD + "/" + label + " ���� <��ü/�÷��̾��̸�> <Alt(true/false)> <Ctrl(true/false)> <Shift(true/false)> <Keycode> <ä��> (ä��)..." + ChatColor.GRAY + " : ����Ű ����");
			LinmaluTellraw.sendCmdChat(sender, "/" + label + " �ʱ�ȭ ", ChatColor.GOLD + "/" + label + " �ʱ�ȭ <��ü/�÷��̾��̸�>" + ChatColor.GRAY + " : ����Ű �ʱ�ȭ");
			LinmaluTellraw.sendCmdChat(sender, "/" + label + " ���ε� ", ChatColor.GOLD + "/" + label + " ���ε�" + ChatColor.GRAY + " : �÷����� ���ε�");
			sender.sendMessage(ChatColor.YELLOW + "������ : " + ChatColor.AQUA + "������(Linmalu)" + ChatColor.WHITE + " - http://blog.linmalu.com");
			sender.sendMessage(ChatColor.YELLOW + "ī�� : " + ChatColor.WHITE + "http://cafe.naver.com/craftproducer");
			new LinmaluCheckVersion(Main.getMain(), sender, Main.getMain().getTitle() + ChatColor.GREEN + "�ֽŹ����� �����մϴ�.");
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "������ �����ϴ�.");
		}
		return true;
	}
}
