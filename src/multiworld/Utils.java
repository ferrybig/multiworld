/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import multiworld.data.DataHandler;
import multiworld.data.InternalWorld;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Fernando
 */
public class Utils implements java.io.Serializable
{
	/**
	 * The start of al the permission strings
	 */
	public static final String PERMISSION_STARTER = "multiworld.";
	/**
	 * The start of al command permission nodes
	 */
	public static final String COMMAND_STARTER = "command.";
	private static final long serialVersionUID = 98487365L;

	/**
	 * Gets the name of the input CommandSender
	 * @param sender The CommandSender obj to get the name from
	 * @return The name of it
	 */
	public static String getPlayerName(CommandSender sender)
	{
		return sender.getName();
	}

	/**
	 * Have the player the permission?
	 * @param player The player to test whit
	 * @param permission
	 * @return The result
	 */
	public static boolean hasPermission(Player player, String permission)
	{
		return player.hasPermission(PERMISSION_STARTER + permission);
	}

	public static String[] parseArguments(String[] list)
	{
		int numberOfArguments = list.length;
		boolean hasFoundToken = false;
		ArrayList<String> argList = new ArrayList<String>(numberOfArguments);
		String tmp = null;
		int index = 0;
		for (String i : list)
		{
			int quotes = Integer.numberOfTrailingZeros(i.concat(" ").split("\"").length - 1);
			if (quotes == 0)
			{
				hasFoundToken = !hasFoundToken;

				if (hasFoundToken)
				{
					numberOfArguments--;
					tmp = i.concat(" ");
				}
				else
				{
					argList.add(index++, tmp.concat(i).replaceAll("\"\"", "\u0000").replaceAll("\"", "").replaceAll("\u0000", "\""));
					tmp = null;
				}
			}
			else if (hasFoundToken)
			{
				numberOfArguments--;
				tmp = tmp.concat(i).concat(" ");
			}
			else
			{
				argList.add(index++, i.replaceAll("\"\"", "\u0000").replaceAll("\"", "").replaceAll("\u0000", "\""));
			}
		}
		if (tmp != null)
		{
			argList.add(tmp);
		}
		return argList.toArray(new String[argList.size()]);
	}

	public static void checkWorldName(String name) throws InvalidWorldNameException
	{
		if (Character.isLetterOrDigit(name.charAt(0)) && Character.isLetterOrDigit(name.charAt(name.length() - 1)))
		{
			for (char i : name.toCharArray())
			{
				if (Character.isLetterOrDigit(i) || Character.getType(i) == Character.SPACE_SEPARATOR || i == '_' || i == '-' || i == ',')
				{
					continue;
				}
				throw new InvalidWorldNameException(name);
			}
		}
		else
		{
			throw new InvalidWorldNameException(name);
		}
	}

	public static void main(String[] arg) throws IOException
	{
		System.out.print("Testing: ");
		for (String test : parseArguments(new BufferedReader(new InputStreamReader(System.in)).readLine().split(" ")))
		{
			System.out.println(test);
		}
	}

	/**
	 * Can the follwing commandsender use the command?
	 * @param sender to test whit
	 * @param command The command to test
	 * @throws PermissionException If the sender dont have the permission
	 */
	public static void canUseCommand(CommandSender sender, String command) throws PermissionException
	{
		if (!(sender instanceof Player))
		{
			return;
		}
		if (!hasPermission((Player) sender, COMMAND_STARTER.concat(command)))
		{
			throw new PermissionException();
		}
	}

	public static InternalWorld getWorld(String name, DataHandler handler, boolean mustBeLoaded) throws UnknownWorldException
	{
		Utils.checkWorldName(name);
		InternalWorld worldObj = handler.getInternalWorld(name, mustBeLoaded);
		if (worldObj == null)
		{
			throw new UnknownWorldException(name);
		}
		return worldObj;
	}

	public static void sendMessage(CommandSender s, String msg)
	{
		sendMessage(s, msg, 5);
	}

	public static void sendMessage(CommandSender s, String msg, int spaces)
	{
		if (s instanceof ConsoleCommandSender)
		{
			s.sendMessage(msg);
			return;
		}
		final int maxLineLenght = 60;
		if (msg.length() > maxLineLenght)
		{
			if (spaces > maxLineLenght)
			{
				throw new IllegalArgumentException();
			}
			char[] spaceChars = new char[spaces];
			for (int i = 0; i < spaceChars.length; i++)
			{
				spaceChars[i] = ' ';
			}
			String spaceString = new String(spaceChars);
			char color = 'f';
			int charsLeft = 60;
			String[] parts = msg.split(" ");
			StringBuilder b = new StringBuilder(maxLineLenght);
			for (String i : parts)
			{
				if (i.lastIndexOf(0x00A7) != -1)
				{
					color = i.charAt(i.lastIndexOf(0x00A7) + 1);
				}
				if ((charsLeft - i.length()) < 1)
				{
					s.sendMessage(b.toString());
					charsLeft = maxLineLenght - spaces;
					b = new StringBuilder(maxLineLenght);
					b.append(spaceString);
					b.append('\u00A7').append(color);
				}
				charsLeft -= i.length() + 1;
				b.append(i).append(" ");

			}
			if (b.length() != 0)
			{
				s.sendMessage(b.toString());
			}
		}
		else
		{
			s.sendMessage(msg);
		}
	}
}
