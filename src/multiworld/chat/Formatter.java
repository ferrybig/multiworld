/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.chat;

import multiworld.flags.FlagValue;
import multiworld.worldgen.SpeedLevel;
import org.bukkit.ChatColor;

/**
 *
 * @author Fernando
 */
public class Formatter
{
	private final static String BOOLEAN_TRUE = ChatColor.GREEN + "True";
	private final static String BOOLEAN_FALSE = ChatColor.RED + "False";

	public static String printBoolean(boolean b)
	{
		return (b ? BOOLEAN_TRUE : BOOLEAN_FALSE);
	}
	private final static String UNKNOWN_FLAG = ChatColor.GOLD + "Unknown";

	public static String printFlag(FlagValue flag)
	{
		if (flag == FlagValue.UNKNOWN)
		{
			return UNKNOWN_FLAG;
		}
		return printBoolean(flag.getAsBoolean());
	}

	public static String printObject(Object input)
	{
		if (input instanceof Boolean)
		{
			return printBoolean((Boolean) input);
		}
		if (input instanceof FlagValue)
		{
			return printFlag((FlagValue) input);
		}
		if (input instanceof String)
		{
			return (String) input;
		}
		return input.toString();
	}

	public static String createList(Object... args)
	{
		StringBuilder out = new StringBuilder();
		out.append(ChatColor.BLUE).append("< ");
		for (int i = 0; i < args.length; i++)
		{
			out.append(args[i]).append(ChatColor.BLUE).append(" >-< ");
		}
		out.setLength(out.length() - 3);
		return out.toString();
	}

	public static String createList(ChatColor color, Object... args)
	{
		ChatColor[] colors = new ChatColor[args.length];
		for(int i = 0; i < colors.length; i++)
		{
			colors[i] = color;
		}
		return createList(colors,args);
	}
	public static String createList(ChatColor[] color, Object... args)
	{
		if (color.length != args.length)
		{
			throw new IllegalArgumentException(color.length + "!=" + args.length);
		}
		StringBuilder out = new StringBuilder();
		out.append(ChatColor.BLUE).append("< ");
		for (int i = 0; i < args.length; i++)
		{
			out.append(color[i]).append(args[i]).append(ChatColor.BLUE).append(" >-< ");
		}
		out.setLength(out.length() - 3);
		return out.toString();
	}

	public static String printSpeed(SpeedLevel speedLevel)
	{
		return speedLevel.name();
	}
}
