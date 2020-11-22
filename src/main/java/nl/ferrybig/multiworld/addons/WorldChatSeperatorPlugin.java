/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.addons;

import java.util.Iterator;
import java.util.List;
import nl.ferrybig.multiworld.api.flag.FlagName;
import nl.ferrybig.multiworld.data.DataHandler;
import nl.ferrybig.multiworld.data.InternalWorld;
import nl.ferrybig.multiworld.data.MyLogger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 *
 * @author Fernando
 */
public class WorldChatSeperatorPlugin implements Listener, MultiworldAddon
{
	private final DataHandler d;
	private final MyLogger log;

	public WorldChatSeperatorPlugin(DataHandler d)
	{
		this.d = d;
		this.log = d.getLogger();

	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onChat(AsyncPlayerChatEvent evt)
	{
		if(!isEnabled)
		{
			return;
		}
		InternalWorld w = d.getWorldManager().getInternalWorld(evt.getPlayer().getWorld().getName(), true);
		boolean maySendChat = d.getWorldManager().getFlag(w.getName(), FlagName.SENDCHAT).getAsBoolean();
		if (!maySendChat)
		{
			List<Player> worldPlayers = evt.getPlayer().getWorld().getPlayers();
			Iterator<Player> recipients = evt.getRecipients().iterator();
			Player p;
			while (recipients.hasNext())
			{
				p = recipients.next();
				if (!worldPlayers.contains(p))
				{
					recipients.remove();
				}
			}
			return;
		}
		for (InternalWorld world : d.getWorldManager().getLoadedWorlds())
		{
			if (world != w && !d.getWorldManager().getFlag(world.getName(), FlagName.RECIEVECHAT).getAsBoolean())
			{
				evt.getRecipients().removeAll(world.getWorld().getPlayers());
			}
		}
	}
	private boolean isEnabled = false;

	/**
	 * called when this plugin is disabled
	 */
	@Override
	public void onDisable()
	{
		this.isEnabled = false;
	}

	/**
	 * called when this plugin is enabled
	 */
	@Override
	public void onEnable()
	{
		this.isEnabled = true;
	}

	@Override
	public boolean isEnabled()
	{
		return this.isEnabled;
	}
}
