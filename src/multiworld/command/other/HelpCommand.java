/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.other;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import multiworld.command.Command;
import multiworld.command.CommandStack;
import multiworld.command.MessageType;
import multiworld.data.DataHandler;
import multiworld.translation.Translation;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicComparator;
import org.bukkit.help.IndexHelpTopic;
import org.bukkit.util.ChatPaginator;

/**
 *
 * @author Fernando
 */
public class HelpCommand extends Command
{
	private final DataHandler data;

	public HelpCommand(DataHandler data)
	{
		super("help", "Gets help for multiworld");
		this.data = data;
	}

	@Override
	public void runCommand(CommandStack stack)
	{
		if (this.data.getPlugin().getServer().getHelpMap().getIgnoredPlugins().contains(this.data.getPlugin().getDescription().getName()))
		{
			stack.sendMessage(MessageType.ERROR, Translation.COMMAND_HELP_TURNED_OFF);
			return;
		}
		String[] args = stack.getArguments();
		int pageNumber;
		String command;
		if (args.length == 0)
		{
			command = "";
			pageNumber = 1;
		}
		else if (NumberUtils.isDigits(args[(args.length - 1)]))
		{
			command = StringUtils.join(ArrayUtils.subarray(args, 0, args.length - 1), " ");
			try
			{
				pageNumber = NumberUtils.createInteger(args[(args.length - 1)]).intValue();
			}
			catch (NumberFormatException exception)
			{
				pageNumber = 1;
			}
			if (pageNumber <= 0)
			{
				pageNumber = 1;
			}
		}
		else
		{
			command = StringUtils.join(args, " ");
			pageNumber = 1;
		}
		HelpTopic topic = findPossibleMatches(command, stack);
		int pageWidth;
		int pageHeight;
		if ((stack.getSender() instanceof ConsoleCommandSender))
		{
			pageHeight = Integer.MAX_VALUE;
			pageWidth = Integer.MAX_VALUE;
		}
		else
		{
			pageHeight = 9;
			pageWidth = 45 - 7;
		}

		if ((topic == null))
		{
			stack.sendMessage(MessageType.ERROR, "No help for " + command);
			return;
		}

		ChatPaginator.ChatPage page = ChatPaginator.paginate(topic.getFullText(stack.getSender()), pageNumber, pageWidth, pageHeight);

		StringBuilder header = new StringBuilder();
		header.append(ChatColor.YELLOW);
		header.append("---------");
		header.append(ChatColor.WHITE);
		header.append(" Help: ");
		header.append(topic.getName());
		header.append(" ");
		if (page.getTotalPages() > 1)
		{
			header.append("(");
			header.append(page.getPageNumber());
			header.append("/");
			header.append(page.getTotalPages());
			header.append(") ");
		}
		header.append(ChatColor.YELLOW);
		header.append("---------");
		stack.sendMessage(MessageType.HIDDEN_SUCCES, header.toString());
		for (String line : page.getLines())
		{
			stack.sendMessage(MessageType.HIDDEN_SUCCES, line);
		}
	}

	protected HelpTopic findPossibleMatches(String searchString, CommandStack stack)
	{
		int maxDistance = searchString.length() / 5 + 3;
		Set<HelpTopic> possibleMatches = new TreeSet<HelpTopic>(HelpTopicComparator.helpTopicComparatorInstance());

		if (searchString.startsWith("/"))
		{
			searchString = searchString.substring(1);
		}
		if (searchString.isEmpty())
		{
			for (Map.Entry<String, Command> command : this.data.getPlugin().getCommandHandler().m.entrySet())
			{
				String trimmedTopic = command.getKey();
				if (!trimmedTopic.equals("snowman"))
				{
					possibleMatches.add(command.getValue().generateHelpTopic(stack.getCommandLabel() + " " + trimmedTopic + " .."));
				}
			}
		}
		else
		{
			for (Map.Entry<String, Command> command : this.data.getPlugin().getCommandHandler().m.entrySet())
			{
				String trimmedTopic = command.getKey();
				if (!trimmedTopic.equals("snowman"))
				{
					if ((trimmedTopic.length() >= searchString.length()) && (Character.toLowerCase(trimmedTopic.charAt(0)) == Character.toLowerCase(searchString.charAt(0))))
					{
						if (damerauLevenshteinDistance(searchString, trimmedTopic.substring(0, searchString.length())) < maxDistance)
						{
							possibleMatches.add(command.getValue().generateHelpTopic("/" + stack.getCommandLabel() + " " + trimmedTopic + " .."));
						}
					}
				}
			}
		}
		if (possibleMatches.size() == 1)
		{
			return possibleMatches.iterator().next();
		}
		if (!possibleMatches.isEmpty())
		{
			return new IndexHelpTopic("Search", null, null, possibleMatches, "Search for: " + searchString);
		}
		return null;
	}

	protected static int damerauLevenshteinDistance(String s1, String s2)
	{
		if ((s1 == null) && (s2 == null))
		{
			return 0;
		}
		if ((s1 != null) && (s2 == null))
		{
			return s1.length();
		}
		if ((s1 == null) && (s2 != null))
		{
			return s2.length();
		}
		int s1Len = s1.length();
		int s2Len = s2.length();
		int[][] H = new int[s1Len + 2][s2Len + 2];
		int INF = s1Len + s2Len;
		H[0][0] = INF;
		for (int i = 0; i <= s1Len; i++)
		{
			H[(i + 1)][1] = i;
			H[(i + 1)][0] = INF;
		}
		for (int j = 0; j <= s2Len; j++)
		{
			H[1][(j + 1)] = j;
			H[0][(j + 1)] = INF;
		}
		Map<Character, Integer> sd = new HashMap<Character, Integer>();
		for (char Letter : (s1 + s2).toCharArray())
		{
			if (!sd.containsKey(Character.valueOf(Letter)))
			{
				sd.put(Character.valueOf(Letter), Integer.valueOf(0));
			}
		}
		for (int i = 1; i <= s1Len; i++)
		{
			int DB = 0;
			for (int j = 1; j <= s2Len; j++)
			{
				int i1 = sd.get(Character.valueOf(s2.charAt(j - 1))).intValue();
				int j1 = DB;
				if (s1.charAt(i - 1) == s2.charAt(j - 1))
				{
					H[(i + 1)][(j + 1)] = H[i][j];
					DB = j;
				}
				else
				{
					H[(i + 1)][(j + 1)] = (Math.min(H[i][j], Math.min(H[(i + 1)][j], H[i][(j + 1)])) + 1);
				}

				H[(i + 1)][(j + 1)] = Math.min(H[(i + 1)][(j + 1)], H[i1][j1] + (i - i1 - 1) + 1 + (j - j1 - 1));
			}
			sd.put(Character.valueOf(s1.charAt(i - 1)), Integer.valueOf(i));
		}
		return H[(s1Len + 1)][(s2Len + 1)];
	}
}
