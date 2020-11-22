package nl.ferrybig.multiworld.command;

import java.util.HashMap;
import java.util.Map;
import nl.ferrybig.multiworld.addons.AddonHandler;
import nl.ferrybig.multiworld.command.config.LoadCommand;
import nl.ferrybig.multiworld.command.config.SaveCommand;
import nl.ferrybig.multiworld.command.flag.FlagListCommand;
import nl.ferrybig.multiworld.command.flag.GetFlagCommand;
import nl.ferrybig.multiworld.command.flag.SetFlagCommand;
import nl.ferrybig.multiworld.command.move.GotoCommand;
import nl.ferrybig.multiworld.command.move.MoveCommand;
import nl.ferrybig.multiworld.command.other.DebugCommand;
import nl.ferrybig.multiworld.command.other.EasterEggCommand;
import nl.ferrybig.multiworld.command.other.HelpCommand;
import nl.ferrybig.multiworld.command.plugin.LinkCommand;
import nl.ferrybig.multiworld.command.spawn.SetSpawnCommand;
import nl.ferrybig.multiworld.command.spawn.SpawnCommand;
import nl.ferrybig.multiworld.command.world.CreateCommand;
import nl.ferrybig.multiworld.command.world.DeleteCommand;
import nl.ferrybig.multiworld.command.world.InfoCommand;
import nl.ferrybig.multiworld.command.world.ListCommand;
import nl.ferrybig.multiworld.command.world.LoadWorldCommand;
import nl.ferrybig.multiworld.command.world.UnloadWorldCommand;
import nl.ferrybig.multiworld.command.world.generator.ListWorldGensCommand;
import nl.ferrybig.multiworld.data.DataHandler;
import nl.ferrybig.multiworld.data.PlayerHandler;
import nl.ferrybig.multiworld.data.ReloadHandler;
import nl.ferrybig.multiworld.data.VersionHandler;
import nl.ferrybig.multiworld.data.WorldHandler;

/**
 * The class that handles the commands
 * <p>
 * @author Fernando
 */
public class CommandHandler extends CommandMap
{
	public CommandHandler(DataHandler d, PlayerHandler p, WorldHandler w, ReloadHandler r, AddonHandler pm, VersionHandler de)
	{
		super(null, CommandHandler.createCommandMap(d, p, w, r, pm, de), getAliassesMap());
	}

	private static HashMap<String, Command> createCommandMap(DataHandler data, PlayerHandler player, WorldHandler worlds, ReloadHandler reload, AddonHandler plugin, VersionHandler debugger)
	{
		HashMap<String, Command> h = new HashMap<String, Command>(30,0.9f);
		h.put("help", new HelpCommand(data));
		h.put("goto", new GotoCommand(data, player, worlds));
		h.put("create", new CreateCommand(data));
		h.put("load", new LoadWorldCommand(data, worlds));
		h.put("unload", new UnloadWorldCommand(data, worlds));
		h.put("delete", new DeleteCommand(data, worlds));
		h.put("generators", new ListWorldGensCommand());
		h.put("move", new MoveCommand(data, player, worlds));
		h.put("save", new SaveCommand(data, reload));
		h.put("reload", new LoadCommand(data, reload));
		h.put("debug", new DebugCommand(debugger));
		h.put("list", new ListCommand(data));
		h.put("setflag", new SetFlagCommand(data, plugin, worlds));
		h.put("getflag", new GetFlagCommand(data, worlds));
		h.put("link", new LinkCommand(data, worlds, plugin, false));
		h.put("link-end", new LinkCommand(data, worlds, plugin, true));
		h.put("snowman", new EasterEggCommand());
		h.put("flags", new FlagListCommand());
		h.put("spawn", new SpawnCommand(data, worlds, player));
		h.put("setspawn", new SetSpawnCommand(data, worlds, player));
		h.put("info", new InfoCommand(worlds));
		return h;
	}

	private static Map<String, String> getAliassesMap()
	{
		HashMap<String, String> h = new HashMap<String, String>(22, 0.9f);
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

		h.put("listgens", "generators");
		h.put("list-gens", "generators");

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
