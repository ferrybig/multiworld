/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command;

import java.util.HashSet;
import java.util.Set;
import multiworld.CommandException;
import multiworld.Utils;
import multiworld.api.flag.FlagName;
import multiworld.worldgen.WorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Fernando
 */
public abstract class Command
{
	public static final String[] EMPTY_STRING_ARRAY = new String[0];
	private final String perm;

	/**
	 * No arg constructor
	 *
	 * @deprecated use Command(String) instead
	 */
	@Deprecated
	public Command()
	{
		this.perm = null;
	}

	/**
	 * The 1 arg contructor
	 *
	 * @param perm The permission that this command needs
	 */
	public Command(String perm)
	{
		this.perm = perm;
	}

	public void excute(CommandSender s, String[] arguments) throws CommandException
	{
		if (this.getPermissions() != null)
		{
			Utils.canUseCommand(s, this.getPermissions());
		}
		this.runCommand(s, arguments);
	}

	public String getPermissions()
	{
		return this.perm;
	}

	public String[] calculateMissingArguments(CommandSender sender, String commandName, String[] split)
	{
		return EMPTY_STRING_ARRAY;
	}

	protected final String[] calculateMissingArgumentsWorld(String worldName)
	{
		Set<World> worlds = new HashSet<World>();
		worlds.addAll(Bukkit.getWorlds());
		Set<String> found = new HashSet<String>(worlds.size());
		String lowerName = worldName.toLowerCase();
		for (World world : worlds)
		{
			if (world.getName().toLowerCase().startsWith(lowerName))
			{
				found.add(world.getName());
			}
		}
		return found.toArray(new String[found.size()]);
	}

	protected final String[] calculateMissingArgumentsWorldGenerator(String worldGen)
	{
		String lowerName = worldGen.toLowerCase();
		String otherPart = "";
		if (lowerName.contains(":"))
		{
			String[] spl = lowerName.split(":", 2);
			lowerName = spl[0];
			otherPart = ":" + spl[1];
			// TODO: other logic
		}
		Set<String> found = new HashSet<String>(WorldGenerator.values().length);
		for (WorldGenerator gen : WorldGenerator.values())
		{
			if (!gen.mayInList())
			{
				continue;
			}
			if (gen.getName().toLowerCase().startsWith(lowerName))
			{
				found.add(gen.getName() + otherPart);
			}
		}
		return found.toArray(new String[found.size()]);
	}

	protected final String[] calculateMissingArgumentsPlayer(String playerName)
	{
		Player[] players = Bukkit.getOnlinePlayers();

		Set<String> found = new HashSet<String>(players.length);
		String lowerName = playerName.toLowerCase();
		for (Player player : players)
		{
			if (player.getName().toLowerCase().startsWith(lowerName))
			{
				found.add(player.getName());
			}
		}
		return found.toArray(new String[found.size()]);
	}

	protected final String[] calculateMissingArgumentsFlagName(String flagName)
	{
		FlagName[] flags = FlagName.values();

		Set<String> found = new HashSet<String>(flags.length);
		String lowerName = flagName.toLowerCase();
		for (FlagName flag : flags)
		{
			if (flag.name().toLowerCase().startsWith(lowerName))
			{
				found.add(flag.toString());
			}
		}
		return found.toArray(new String[found.size()]);
	}

	protected final String[] calculateMissingArgumentsBoolean(String bool)
	{
		if (bool.startsWith("t") || bool.startsWith("tr") || bool.startsWith("tru") || bool.startsWith("true"))
		{
			return new String[]
			{
				"true"
			};
		}
		if (bool.startsWith("f") || bool.startsWith("fa") || bool.startsWith("fal") || bool.startsWith("fals") || bool.startsWith("false"))
		{
			return new String[]
			{
				"false"
			};
		}
		if (bool.isEmpty())
		{
			return new String[]
			{
				"false", "true"
			};
		}
		return EMPTY_STRING_ARRAY;
	}
	protected static final int MAX_COORD = 30000000;
	protected static final int MIN_COORD_MINUS_ONE = -30000001;
	protected static final int MIN_COORD = -30000000;

	protected static double getCoordinate(CommandSender sender, double current, String input)
	{
		return getCoordinate(sender, current, input, MIN_COORD, MAX_COORD);
	}

	protected static double getCoordinate(CommandSender sender, double current, String input, int min, int max)
	{
		boolean relative = input.startsWith("~");
		double result = relative ? current : 0;

		if (!relative || input.length() > 1)
		{
			boolean exact = input.contains(".");
			if (relative)
			{
				input = input.substring(1);
			}

			double testResult = getDouble(sender, input);
			if (testResult == MIN_COORD_MINUS_ONE)
			{
				return MIN_COORD_MINUS_ONE;
			}
			result += testResult;

			if (!exact && !relative)
			{
				result += 0.5f;
			}
		}
		if (min != 0 || max != 0)
		{
			if (result < min)
			{
				result = MIN_COORD_MINUS_ONE;
			}

			if (result > max)
			{
				result = MIN_COORD_MINUS_ONE;
			}
		}

		return result;
	}

	protected static double getRelativeDouble(double original, CommandSender sender, String input)
	{
		if (input.startsWith("~"))
		{
			double value = getDouble(sender, input.substring(1));
			if (value == MIN_COORD_MINUS_ONE)
			{
				return MIN_COORD_MINUS_ONE;
			}
			return original + value;
		}
		else
		{
			return getDouble(sender, input);
		}
	}

	protected static double getDouble(CommandSender sender, String input)
	{
		try
		{
			return Double.parseDouble(input);
		}
		catch (NumberFormatException ex)
		{
			return MIN_COORD_MINUS_ONE;
		}
	}

	protected static double getDouble(CommandSender sender, String input, double min, double max)
	{
		double result = getDouble(sender, input);

		// TODO: This should throw an exception instead.
		if (result < min)
		{
			result = min;
		}
		else if (result > max)
		{
			result = max;
		}

		return result;
	}

	protected static String createString(String[] args, int start)
	{
		return createString(args, start, " ");
	}

	protected static String createString(String[] args, int start, String glue)
	{
		StringBuilder string = new StringBuilder();
		for (int x = start; x < args.length; x++)
		{
			string.append(args[x]);
			if (x != args.length - 1)
			{
				string.append(glue);
			}
		}
		return string.toString();
	}

	public abstract void runCommand(CommandSender s, String[] arguments) throws CommandException;
}
