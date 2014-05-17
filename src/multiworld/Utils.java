/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld;

import java.util.ArrayList;
import multiworld.command.CommandStack;
import multiworld.command.MessageType;
import multiworld.data.DataHandler;
import multiworld.data.InternalWorld;
import multiworld.translation.Translation;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.util.ChatPaginator;

/**
 *
 * @author Fernando
 */
public class Utils implements java.io.Serializable
{
	/**
	 * The start of al command permission nodes
	 */
	public static final String COMMAND_STARTER = "command.";
	/**
	 * The start of al the permission strings
	 */
	public static final String PERMISSION_STARTER = "multiworld.";
	private static final long serialVersionUID = 98487365L;

	/**
	 * Can the follwing commandsender use the command?
	 * <p>
	 * @param sender to test whit
	 * @param command The command to test
	 * @return
	 */
	public static boolean canUseCommand(CommandStack sender, String command)
	{
		if (!hasPermission(sender, COMMAND_STARTER.concat(command)))
		{
			sender.sendMessage(MessageType.ERROR, Translation.LACKING_PERMISSIONS);
			return false;
		}
		return true;
	}

	public static boolean checkWorldName(String name)
	{
		if (name.isEmpty())
		{
			return false;
		}
		if (Character.isLetterOrDigit(name.charAt(0)) && Character.isLetterOrDigit(name.charAt(name.length() - 1)))
		{
			for (char i : name.toCharArray())
			{
				if (Character.isLetterOrDigit(i) || Character.getType(i) == Character.SPACE_SEPARATOR || i == '_' || i == '-' || i == ',')
				{
					continue;
				}
				return false;
			}
		}
		else
		{
			return false;
		}
		return true;
	}

	/**
	 * Gets the name of the input CommandSender
	 * <p>
	 * @param sender The CommandSender obj to get the name from
	 * @return The name of it
	 */
	public static String getPlayerName(CommandSender sender)
	{
		return sender.getName();
	}

	public static InternalWorld getWorld(String name, DataHandler handler, boolean mustBeLoaded) throws UnknownWorldException
	{
		Utils.checkWorldName(name);
		InternalWorld worldObj = handler.getWorldManager().getInternalWorld(name, mustBeLoaded);
		if (worldObj == null)
		{
			throw new UnknownWorldException(name);
		}
		return worldObj;
	}

	/**
	 * Have the player the permission?
	 * <p>
	 * @param stack
	 * @param permission
	 * @return The result
	 */
	public static boolean hasPermission(CommandStack stack, String permission)
	{
		return stack.hasPermission(PERMISSION_STARTER + permission);
	}

	public static boolean hasPermission(CommandSender sender, String permission)
	{
		return sender.hasPermission(PERMISSION_STARTER + permission);
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
				tmp = tmp + i + " ";
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

	public static void sendMessage(CommandSender s, String msg)
	{

		sendMessage(s, msg, 5);
	}

	/**
	 * Sends a command sender a message in a friendly way
	 * <p>
	 * @param s the commandsender to send to
	 * @param msg the message
	 * @param spaces The amount of spaces before the message if it doesn't fit
	 */
	public static void sendMessage(CommandSender s, String msg, int spaces)
	{
		char[] spaceChars = new char[spaces];
		for (int i = 0; i < spaceChars.length; i++)
		{
			spaceChars[i] = ' ';
		}
		String spaceString = new String(spaceChars);
		sendMessage(s, msg, spaceString, false);
	}

	/**
	 *
	 * @param s the value of s
	 * @param msg the value of msg
	 * @param prefix the value of prefix
	 * @param addPrefixToFirstOutput the value of addPrefixToFirstOutput
	 */
	public static void sendMessage(CommandSender s, String msg, String prefix, boolean addPrefixToFirstOutput)
	{
		if (msg.contains("\n"))
		{
			for (String str : msg.split("\n"))
			{
				sendMessage0(s, str, prefix, addPrefixToFirstOutput);
				// addded another method to make this call less expensive since .contains on a long string takes long
			}
			return;
		}
		sendMessage0(s, msg, prefix, addPrefixToFirstOutput);
	}

	private static void sendMessage0(CommandSender s, String msg, String prefix, boolean addPrefixToFirstOutput)
	{
		if (s instanceof ConsoleCommandSender)
		{
			if (addPrefixToFirstOutput)
			{
				s.sendMessage(prefix + msg);
			}
			else
			{
				s.sendMessage(msg);
			}
			return;
		}
		final int prefixSubstract = countOccurrences(prefix, ChatColor.COLOR_CHAR) * 2;
		final int prefixLength = prefix.length() - prefixSubstract;
		final int maxLineLenght = ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH;
		if ((msg.length() + (addPrefixToFirstOutput ? prefixLength : 0)) > maxLineLenght)
		{
			char color;
			{
				final int lastIndexOf = prefix.lastIndexOf(ChatColor.COLOR_CHAR);
				if (lastIndexOf != -1)
				{
					color = prefix.charAt(lastIndexOf + 1);
				}
				else
				{
					color = 'f';
				}
			}
			int charsLeft = 60;
			String[] parts = msg.split(" ");
			StringBuilder b = new StringBuilder(maxLineLenght);
			if (addPrefixToFirstOutput)
			{
				b.append(prefix);
				charsLeft -= prefixLength;
			}
			for (String i : parts)
			{
				if (i.lastIndexOf(0x00A7) != -1)
				{
					assert i.lastIndexOf(0x00A7) + 1 < i.length();
					color = i.charAt(i.lastIndexOf(0x00A7) + 1);
				}
				if ((charsLeft - i.length()) < 1)
				{
					s.sendMessage(b.toString());
					charsLeft = maxLineLenght - prefixLength;
					b.setLength(0);
					b = new StringBuilder(maxLineLenght);
					b.append(prefix);
					b.append('\u00A7').append(color);
				}
				charsLeft -= i.length() + 1;
				charsLeft += countOccurrences(i, ChatColor.COLOR_CHAR) * 2;
				b.append(i).append(" ");
			}
			if (b.length() != 0)
			{
				s.sendMessage(b.toString());
			}
		}
		else
		{
			s.sendMessage(addPrefixToFirstOutput ? prefix + msg : msg);
		}
	}

	public static int countOccurrences(String haystack, char needle)
	{
		int count = 0;
		char[] contents = haystack.toCharArray();
		for (int i = 0; i < contents.length; i++)
		{
			if (contents[i] == needle)
			{
				count++;
			}
		}
		return count;
	}

	private Utils()
	{
	}

}
