/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.command.world;

import multiworld.ArgumentException;
import multiworld.CommandException;
import multiworld.command.Command;
import multiworld.data.InternalWorld;
import multiworld.data.WorldHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Fernando
 */
public class InfoCommand extends Command
{
	private final WorldHandler w;
	public InfoCommand(WorldHandler w)
	{
		super("info");
		this.w = w;
	}

	@Override
	public void runCommand(CommandSender s, String[] arg) throws CommandException
	{
		String worldName = null;
		if (s instanceof Player)
		{
			worldName = ((Player) s).getWorld().getName();
		}

		if (arg.length != 0)
		{
			worldName = arg[0];
		}
		if (worldName == null)
		{
			throw new ArgumentException("/mw info <world>");
		}
		InternalWorld worldObj = this.w.getWorld(worldName,false);
		s.sendMessage("Name: "+worldName);
		s.sendMessage("Seed: "+worldObj.getSeed());
		s.sendMessage("Generator: "+worldObj.getMainGen());
		s.sendMessage("Generator options: "+worldObj.getOptions());
		s.sendMessage("Type: "+worldObj.getEnv().toString());
		s.sendMessage("Chunks Loaded: "+worldObj.getWorld().getLoadedChunks().length);
		s.sendMessage("Entities Loaded: "+worldObj.getWorld().getEntities().size());
	}
}
