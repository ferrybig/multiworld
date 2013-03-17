package multiworld.command;

import multiworld.command.other.HelpCommand;
import multiworld.command.other.DebugCommand;
import multiworld.command.plugin.LinkCommand;
import multiworld.command.spawn.SpawnCommand;
import multiworld.command.spawn.SetSpawnCommand;
import multiworld.command.config.SaveCommand;
import multiworld.command.config.LoadCommand;
import multiworld.command.world.ListCommand;
import multiworld.command.world.generator.ListWorldGensCommand;
import multiworld.command.flag.FlagListCommand;
import multiworld.command.flag.SetFlagCommand;
import multiworld.command.flag.GetFlagCommand;
import multiworld.command.move.MoveCommand;
import multiworld.command.move.GotoCommand;
import multiworld.command.world.CreateCommand;
import multiworld.command.world.DeleteCommand;
import multiworld.command.world.InfoCommand;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import multiworld.CommandException;
import multiworld.command.world.LoadWorldCommand;
import multiworld.command.world.UnloadWorldCommand;
import multiworld.data.DataHandler;
import multiworld.data.VersionHandler;
import multiworld.data.PlayerHandler;
import multiworld.data.ReloadHandler;
import multiworld.data.WorldHandler;
import multiworld.addons.AddonHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * The class that handle the commands
 * @author Fernando
 */
public class CommandHandler extends CommandMap
{
	public CommandHandler(DataHandler d, PlayerHandler p, WorldHandler w, ReloadHandler r, AddonHandler pm, VersionHandler de)
	{
super(null, CommandHandler.createCommandMap(d, p, w, r, pm, de),getAliassesMap(), ChatColor.RED + "Unknown command! Do \"/mw help\" for a list of commands.");
	}

	private static HashMap<String, Command> createCommandMap(DataHandler data, PlayerHandler player, WorldHandler worlds, ReloadHandler reload, AddonHandler plugin, VersionHandler debugger)
	{
		HashMap<String, Command> h = new HashMap<String, Command>(20, 0.9f);
		h.put("help", new HelpCommand());
		h.put("goto", new GotoCommand(data, player, worlds));
		h.put("create", new CreateCommand(data));
		h.put("load", new LoadWorldCommand(data, worlds));
		h.put("unload", new UnloadWorldCommand(data, worlds));
		h.put("delete", new DeleteCommand(data, worlds));
		h.put("listgens", new ListWorldGensCommand());
		h.put("move", new MoveCommand(data, player, worlds));
		h.put("save", new SaveCommand(data, reload));
		h.put("reload", new LoadCommand(data, reload));
		h.put("debug", new DebugCommand(debugger));
		h.put("list", new ListCommand(data));
		h.put("setflag", new SetFlagCommand(data, plugin));
		h.put("getflag", new GetFlagCommand(data));
		h.put("link", new LinkCommand(data, plugin, false));
		h.put("link-end", new LinkCommand(data, plugin, true));
		h.put("flags", new FlagListCommand());
		h.put("spawn", new SpawnCommand(data, worlds, player));
		h.put("setspawn", new SetSpawnCommand(data, worlds, player));
		h.put("info", new InfoCommand(worlds));
		return h;
	}
	private static Map<String,String> getAliassesMap()
	{
		HashMap<String, String> h = new HashMap<String, String>(20, 0.9f);
		h.put("gens", "listgens");
		h.put("list-gens", "listgens");
		
		h.put("link-nether", "link");
		
		h.put("version", "debug");
		
		h.put("world-info", "info");
		
		h.put("flaglist", "flags");
		h.put("flag-list", "flags");
		
		h.put("worlds", "list");
		
		h.put("store", "save");
		
		h.put("move-player", "move");
		
		h.put("g", "goto");
		h.put("i", "info");
		h.put("d", "debug");
		h.put("s", "spawn");
		h.put("sf", "setflag");
		h.put("gf", "getflag");
		h.put("ss", "setspawn");
		
		h.put("ss", "setspawn");
		return h;
	}

	
}
