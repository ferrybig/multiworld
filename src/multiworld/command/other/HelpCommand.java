/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.other;

import multiworld.Utils;
import multiworld.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Fernando
 */
public class HelpCommand extends Command
{

	public HelpCommand()
	{super("help");
	}

	@Override
	public void runCommand(CommandSender sender, String[] arguments)
	{
		String helpType = "main";
		if(arguments.length != 0)
		{
			String tmp = arguments[0].toLowerCase();
			if(tmp.equals("user") || tmp.equals("admin") || tmp.equals("advanced")|| tmp.equals("world"))
			{
				helpType = tmp;
			}
		}
		Utils.sendMessage(sender,ChatColor.BLUE+"Now printing all commands for help type: "+ helpType, 12);
		if(helpType.equals("main"))
		{
			this.sendMainHelp(sender);
		}
		else if(helpType.equals("user"))
		{
			this.sendUserHelp(sender);
		}
		else if(helpType.equals("admin"))
		{
			this.sendAdminHelp(sender);
		}
		else if(helpType.equals("advanced"))
		{
			this.sendAdvancedHelp(sender);
		}
		else if(helpType.equals("world"))
		{
			this.sendWorldHelp(sender);
		}
		
		
		
		
	}
	
	private void sendMainHelp(CommandSender sender)
	{
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw help" +                                  ChatColor.GREEN + " - shows this help text",12);
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw help user" +                             ChatColor.GREEN + " - shows the help text for user commands",12);
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw help admin" +                            ChatColor.GREEN + " - shows the help text for admin commands",12);
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw help advanced" +                         ChatColor.GREEN + " - shows the help text for advanced admin commands",12);
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw help world" +                            ChatColor.GREEN + " - shows the help text for advanced world management",12);
	}
	
	private void sendUserHelp(CommandSender sender)
	{
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw list" +                                  ChatColor.GREEN + " - list all the worlds on the server",12);
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw help" +                                  ChatColor.GREEN + " - shows this help text",12);
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw goto <world name>" +                     ChatColor.GREEN + " - warps you to <world name>",12);
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw debug" +                                 ChatColor.GREEN + " - shows debug information",12);
	}
	
	private void sendAdminHelp(CommandSender sender)
	{
		
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw load" +                                  ChatColor.GREEN + " - Reload multiworld",12);
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw save" +                                  ChatColor.GREEN + " - Saves multiworld",12);
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw move <player> <world>" +                 ChatColor.GREEN + " - Moves a player to a world",12);
	}
	
	private void sendAdvancedHelp(CommandSender sender)
	{
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw getflag <world> <flagname>" +            ChatColor.GREEN + " - Get a flag",12);
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw setflag <world> <flagname> <newvalue>" + ChatColor.GREEN + " - Set a flag",12);
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw flaglist" +                              ChatColor.GREEN + " - List all flags",12);
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw link <target world> [to world]" +        ChatColor.GREEN + " - Link portals to worlds",12);
	}

	private void sendWorldHelp(CommandSender sender)
	{
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw create <world name> [type] [seed]" +     ChatColor.GREEN + " - Makes a new <type> world named <world name>",12);
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw delete <world name>" +                   ChatColor.GREEN + " - Removes <world name> from the multiworld world list",12);
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw unload <world name>" +                   ChatColor.GREEN + " - Unloads <world name> from the world list",12);
		Utils.sendMessage(sender, ChatColor.YELLOW + "/mw load <world name>" +                     ChatColor.GREEN + " - Loads <world name> from the world list",12);
	}
}
