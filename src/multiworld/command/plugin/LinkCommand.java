/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.plugin;

import multiworld.ArgumentException;
import multiworld.CommandException;
import multiworld.ConfigException;
import multiworld.NotEnabledException;
import multiworld.Utils;
import multiworld.command.Command;
import multiworld.data.DataHandler;
import multiworld.addons.AddonHandler;
import multiworld.addons.AddonHolder;
import multiworld.addons.PortalHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Fernando
 */
public class LinkCommand extends Command
{
	private final AddonHandler plugin;
	private final DataHandler d;
	private final boolean isForEndPortals;

	public LinkCommand(DataHandler data, AddonHandler portal, boolean isForEndPortals)
	{
		super(isForEndPortals ? "link.end" : "link.nether");
		this.d = data;
		this.plugin = portal;
		this.isForEndPortals = isForEndPortals;
	}

	@Override
	public void runCommand(CommandSender s, String[] split) throws CommandException
	{
		PortalHandler portal = getPortalHandler();
		if (portal == null)
		{
			throw new NotEnabledException();
		}
		if (split.length == 1)
		{
			Utils.getWorld(split[0], this.d, false);
			portal.add(split[0], null);
			s.sendMessage(ChatColor.GREEN + this.d.getLang().getString("PORTAL CLEAR", new String[]
				{
					split[0]
				}));
		}
		else if (split.length == 2)
		{

			if (Utils.getWorld(split[0], this.d, false) == Utils.getWorld(split[1], this.d, false))
			{
				portal.add(split[0], null);
				s.sendMessage(ChatColor.GREEN + this.d.getLang().getString("PORTAL CLEAR", new String[]
					{
						split[0]
					}));
			}
			else
			{
				portal.add(split[0], split[1]);
				s.sendMessage(ChatColor.GREEN + this.d.getLang().getString("PORTAL SET", new String[]
					{
						split[0], split[1]
					}));
			}
			try
			{
				portal.save();
				s.sendMessage(ChatColor.GREEN + this.d.getLang().getString("PORTAL UPDATE"));
			}
			catch (ConfigException e)
			{
				s.sendMessage(ChatColor.YELLOW + this.d.getLang().getString("PORTAL ERR"));
				this.d.getLogger().throwing("multiworld.MultiWorld", "onCommand", e, "Cannot save portals"); //NOI18N
			}
		}
		else
		{
			throw new ArgumentException(isForEndPortals ? "/mw link-end <target world> [to world]" : "/mw link <target world> [to world]"); //NOI18N
		}
	}

	private PortalHandler getPortalHandler()
	{
		if (isForEndPortals)
		{
			AddonHolder<?> holder = this.plugin.getPlugin("EndPortalHandler");
			return (PortalHandler) holder.getAddon();
		}
		else
		{
			AddonHolder<?> holder = this.plugin.getPlugin("NetherPortalHandler");
			return (PortalHandler) holder.getAddon();
		}
	}
	@Override
	public String[] calculateMissingArguments(CommandSender sender, String commandName, String[] split)
	{
		if (split.length == 0)
		{
			return this.calculateMissingArgumentsWorld("");
		}
		else if (split.length == 1)
		{
			return this.calculateMissingArgumentsWorld(split[0]);
		}else if (split.length == 2)
		{
			return this.calculateMissingArgumentsWorld(split[1]);
		}else
		{
			return EMPTY_STRING_ARRAY;
		}
	}
}
