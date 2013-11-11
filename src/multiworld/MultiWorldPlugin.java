package multiworld;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import multiworld.addons.AddonHandler;
import multiworld.api.CommandStackBuilder;
import multiworld.api.MultiWorldAPI;
import multiworld.command.CommandHandler;
import multiworld.command.CommandStack;
import multiworld.command.DebugLevel;
import multiworld.command.DefaultCommandStack;
import multiworld.command.DefaultMessageLogger;
import multiworld.data.DataHandler;
import multiworld.data.InternalWorld;
import multiworld.data.MyLogger;
import multiworld.data.PlayerHandler;
import multiworld.data.ReloadHandler;
import multiworld.data.WorldContainer;
import multiworld.data.WorldHandler;
import multiworld.metrics.Metrics;
import multiworld.metrics.Metrics.Graph;
import multiworld.worldgen.SimpleChunkGen;
import multiworld.worldgen.WorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main class of the project
 * <p>
 * @author Fernando
 */
public class MultiWorldPlugin extends JavaPlugin implements CommandStackBuilder
{

	private static MultiWorldPlugin instance;

	public static MultiWorldPlugin getInstance()
	{
		return MultiWorldPlugin.instance;
	}
	public CommandStackBuilder builder = this;
	private CommandHandler commandHandler;
	/**
	 * The configuration
	 */
	DataHandler data = null;
	/**
	 * was there anny critical error?
	 */
	boolean errorStatus = false;
	/**
	 * The logger
	 */
	MyLogger log;
	private PlayerHandler playerHandler;
	/**
	 * The plugin directory
	 */
	File pluginDir;
	private AddonHandler pluginHandler;
	private ReloadHandler reloadHandler;
	/**
	 * The version of the plugin
	 */
	private String version;
	private WorldHandler worldHandler;

	@Override
	public CommandStack build(CommandSender sender, DebugLevel level)
	{
		Location loc;
		if (sender instanceof Player)
		{
			loc = ((Player) sender).getLocation();
		}
		else if (sender instanceof BlockCommandSender)
		{
			loc = ((BlockCommandSender) sender).getBlock().getLocation();
		}
		else
		{
			loc = null;
		}
		return DefaultCommandStack.builder(new DefaultMessageLogger(level, sender, ChatColor.translateAlternateColorCodes('&', "&9[&4MultiWorld&9] &3"))).setSender(sender).setLocation(loc).setPermissible(sender).build();
	}

	public void gc()
	{
		WorldGenerator[] list = WorldGenerator.values();
		for (WorldGenerator w : list)
		{
			ChunkGenerator gen = WorldGenerator.getGen(w.name());
			if (gen == null)
			{
				continue;
			}
			if (gen instanceof SimpleChunkGen)
			{
				((SimpleChunkGen) gen).gc();
			}
		}
	}

	/**
	 * Gets the multiworld api interface Notice that its better to cache the result of this
	 * <p>
	 * @return the api interface
	 */
	public MultiWorldAPI getApi()
	{
		if (this.isEnabled())
		{
			return new MultiWorldAPI(this);
		}
		return null;
	}

	public CommandHandler getCommandHandler()
	{
		return commandHandler;
	}

	public DataHandler getDataManager()
	{
		return this.data;
	}

	/**
	 * Gets a chunk gen by name
	 * <p>
	 * @param worldName The name of the world
	 * @param id the id of the gen to get
	 * @return The chunk gen of succces, or null on error
	 */
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
	{
		ChunkGenerator gen = WorldGenerator.getGen(id);
		if (gen == null)
		{
			return null;
		}
		return gen;
	}

	/**
	 * @return the pluginHandler
	 */
	public AddonHandler getPluginHandler()
	{
		return pluginHandler;
	}

	protected InternalWorld getWorld(String name, boolean mustBeLoaded) throws UnknownWorldException
	{
		return Utils.getWorld(name, data, mustBeLoaded);
	}

	public void log(String msg)
	{
		this.log.info(msg);
	}

	/**
	 * Called when there a command for this plugin
	 * <p>
	 * @param sender The sender of the command
	 * @param cmd The command itself
	 * @param cmdLine The raw command
	 * @param split The parameters for the command
	 * @return if the command was ecuted succesfiully
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] split)
	{
		split = Utils.parseArguments(split);
		if (cmd.getName().equals("mw") || cmd.getName().equals("multiworld"))
		{
			String[] verbose = cmdLine.split("-", 2);
			DebugLevel level;
			if (verbose.length == 2)
			{
				try
				{
					level = DebugLevel.valueOf(verbose[1].toUpperCase());
				}
				catch (Exception ex)
				{
					level = DebugLevel.NONE;
				}
			}
			else
			{
				level = DebugLevel.NONE;
			}
			this.pushCommandStack(this.builder.build(sender, level).editStack().setArguments(split).setCommandLabel(verbose[0]).build());
		}
		else if (cmd.getName().equals("multiworld-shortcut"))
		{
			String[] arguments = new String[split.length + 1];
			arguments[0] = cmdLine;
			System.arraycopy(split, 0, arguments, 1, split.length);
			CommandStack stack = this.builder.build(sender, DebugLevel.NONE).editStack().setArguments(arguments).setCommandLabel("multiworld").build();
			this.pushCommandStack(stack);
		}
		return true;
	}

	public void pushCommandStack(CommandStack stack)
	{
		this.commandHandler.excute(stack);
	}

	/**
	 * this is called when the plugin is deactivated
	 */
	@Override
	public void onDisable()
	{
		if (!this.errorStatus)
		{
			this.log.info("Disabled."); //NOI18N
			this.data.onShutdown();
			this.getPluginHandler().disableAll();
		}
		else
		{
			this.getServer().getLogger().severe("[MultiWorld] !!!     CRITICAL MALL FUNCTION     !!!"); //NOI18N
			this.getServer().getLogger().severe("[MultiWorld] !!!          SHUTTING DOWN         !!!"); //NOI18N
			this.getServer().getLogger().severe("[MultiWorld] !!!               :(               !!!"); //NOI18N
		}
		this.commandHandler = null;
		this.data = null;
		Bukkit.getScheduler().cancelTasks(this);
		MultiWorldPlugin.instance = null;
	}

	/**
	 * this is called when the plugin is enabled
	 */
	@Override
	public void onEnable()
	{
		try
		{
			MultiWorldPlugin.instance = this;
			PluginDescriptionFile pdfFile = this.getDescription();
			this.version = pdfFile.getVersion();
			this.pluginDir = this.getDataFolder();
			this.pluginDir.mkdir();

			this.data = new DataHandler(this.getServer(), this.getConfig(), this); //NOI18N
			this.log = this.data.getLogger();
			this.playerHandler = new PlayerHandler(this.data);
			this.worldHandler = new WorldHandler(this.data);
			this.pluginHandler = new AddonHandler(this.data, this.version);
			this.reloadHandler = new ReloadHandler(this.data, this.getPluginHandler());
			this.commandHandler = new CommandHandler(this.data, this.playerHandler, this.worldHandler, this.reloadHandler, this.getPluginHandler(), this.getPluginHandler());
			this.pluginHandler.onSettingsChance();
			this.submitStats();
			this.log.info("v" + this.version + " enabled."); //NOI18N
		}
		catch (ConfigException e)
		{
			this.getServer().getLogger().log(Level.SEVERE, "[MultiWorld] error while enabling:".concat(e.toString())); //NOI18N
			this.getServer().getLogger().severe("[MultiWorld] plz check the configuration for any misplaced tabs, full error:"); //NOI18N
			e.printStackTrace(System.err);
			this.errorStatus = true;
			this.setEnabled(false);
		}
		catch (RuntimeException e)
		{
			this.getServer().getLogger().log(Level.SEVERE, "[MultiWorld] error while enabling:".concat(e.toString())); //NOI18N
			this.getServer().getLogger().severe("[MultiWorld] plz report the full error to the author:"); //NOI18N
			e.printStackTrace(System.err);
			this.errorStatus = true;
			this.setEnabled(false);
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] split)
	{
		split = Utils.parseArguments(split);
		List<String> list = Arrays.asList(this.commandHandler.getOptionsForUnfinishedCommands(sender, command.getName(), split));
		return list;
	}

	private void submitStats()
	{
		try
		{
			Metrics metrics = new Metrics(this);
			Graph graph = metrics.createGraph("components used");
			{
				graph.addPlotter(new Metrics.Plotter("GameMode chancer used")
				{
					@Override
					public int getValue()
					{
						return data.getNode(DataHandler.OPTIONS_GAMEMODE) ? 1 : 0;
					}
				});
				graph.addPlotter(new Metrics.Plotter("NetherPortal chancer used")
				{
					@Override
					public int getValue()
					{
						return data.getNode(DataHandler.OPTIONS_LINK_NETHER) ? 1 : 0;
					}
				});
				graph.addPlotter(new Metrics.Plotter("EndPortal chancer used")
				{
					@Override
					public int getValue()
					{
						return data.getNode(DataHandler.OPTIONS_LINK_END) ? 1 : 0;
					}
				});
				graph.addPlotter(new Metrics.Plotter("WorldChatSeperator used")
				{
					@Override
					public int getValue()
					{
						return data.getNode(DataHandler.OPTIONS_WORLD_CHAT) ? 1 : 0;
					}
				});
				graph.addPlotter(new Metrics.Plotter("EnderBlock used")
				{
					@Override
					public int getValue()
					{
						return data.getNode(DataHandler.OPTIONS_BLOCK_ENDER_CHESTS) ? 1 : 0;
					}
				});
				graph.addPlotter(new Metrics.Plotter("WorldSpawnChancer used")
				{
					@Override
					public int getValue()
					{
						return data.getNode(DataHandler.OPTIONS_WORLD_SPAWN) ? 1 : 0;
					}
				});
			}
			graph = metrics.createGraph("Generators used");
			for (final WorldGenerator gen : WorldGenerator.values())
			{
				graph.addPlotter(new Metrics.Plotter(gen.getName())
				{
					@Override
					public int getValue()
					{
						int returnValue = 0;
						for (WorldContainer world : data.getWorldManager().getWorlds())
						{
							if (world.getWorld().getMainGen().equals(gen.name()))
							{
								returnValue++;
							}
						}
						return returnValue;
					}
				});
			}
			metrics.addCustomData(new Metrics.Plotter("Worlds Existing")
			{
				@Override
				public int getValue()
				{
					return data.getWorldManager().getAllWorlds().length;
				}
			});
			metrics.addCustomData(new Metrics.Plotter("Worlds Loaded")
			{
				@Override
				public int getValue()
				{
					return data.getWorldManager().getWorlds(true).length;
				}
			});
			metrics.start();
		}
		catch (IOException e)
		{
			// Failed to submit the stats :-(
		}
	}

	public void warning(String msg)
	{
		this.log.warning(msg);
	}
}
