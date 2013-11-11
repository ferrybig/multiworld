package multiworld.data;

import multiworld.ConfigException;
import multiworld.MultiWorldPlugin;
import multiworld.command.DebugLevel;
import multiworld.command.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 *
 * @author Fernando
 */
public final class DataHandler
{
	private final WorldManager worlds = new WorldManager();
	private FileConfiguration config;
	private final MultiWorldPlugin plugin;
	private MyLogger logger;
	private Difficulty difficulty;
	private LangStrings lang;
	private boolean autoLoadWorld = false;
	private boolean unloadWorldsOnDisable = false;
	private SpawnWorldControl spawn;
	public final static ConfigNode<ConfigurationSection> OPTIONS_NODE = new ConfigNode<ConfigurationSection>("options", null, ConfigurationSection.class);
	public final static ConfigNode<Boolean> OPTIONS_BLOCK_ENDER_CHESTS = new ConfigNode<Boolean>(OPTIONS_NODE, "blockEnderChestInCrea", false, Boolean.class);
	public final static ConfigNode<Boolean> OPTIONS_LINK_NETHER = new ConfigNode<Boolean>(OPTIONS_NODE, "useportalhandler", false, Boolean.class);
	public final static ConfigNode<Boolean> OPTIONS_LINK_END = new ConfigNode<Boolean>(OPTIONS_NODE, "useEndPortalHandler", false, Boolean.class);
	public final static ConfigNode<Boolean> OPTIONS_WORLD_CHAT = new ConfigNode<Boolean>(OPTIONS_NODE, "useWorldChatSeperator", false, Boolean.class);
	public final static ConfigNode<Boolean> OPTIONS_GAMEMODE = new ConfigNode<Boolean>(OPTIONS_NODE, "usecreativemode", false, Boolean.class);
	public final static ConfigNode<Boolean> OPTIONS_GAMEMODE_INV = new ConfigNode<Boolean>(OPTIONS_NODE, "usecreativemodeinv", true, Boolean.class);
	public final static ConfigNode<Boolean> OPTIONS_WORLD_SPAWN = new ConfigNode<Boolean>(OPTIONS_NODE, "useWorldSpawnHandler", false, Boolean.class);
	public final static ConfigNode<Boolean> OPTIONS_DEBUG = new ConfigNode<Boolean>(OPTIONS_NODE, "debug", false, Boolean.class);
	public final static ConfigNode<Integer> OPTIONS_DIFFICULTY = new ConfigNode<Integer>(OPTIONS_NODE, "difficulty", 2, Integer.class);
	public final static ConfigNode<String> OPTIONS_LOCALE = new ConfigNode<String>(OPTIONS_NODE, "locale", "en_US", String.class);

	/**
	 * Makes the object
	 * <p>
	 * @param server The server whits runs the plugin
	 * @param config
	 * @param plugin The main plugin running this
	 * @throws ConfigException When there was an error
	 */
	public DataHandler(Server server, FileConfiguration config, MultiWorldPlugin plugin) throws ConfigException
	{
		this.config = config;
		this.plugin = plugin;
		this.load(true);
	}
	private BukkitTask saveTask = null;

	public void scheduleSave()
	{
		if (this.saveTask == null)
		{
			this.saveTask = new BukkitRunnable()
			{

				@Override
				public void run()
				{
					try
					{
						save();
						getPlugin().builder.build(Bukkit.getConsoleSender(), DebugLevel.NONE).
							sendMessageBroadcast(MessageType.SUCCES, "Saved automaticly!");
					}
					catch (ConfigException ex)
					{
						getPlugin().builder.build(Bukkit.getConsoleSender(), DebugLevel.NONE).
							sendMessageBroadcast(MessageType.ERROR, "Saved automaticly FAILED! Check console for details!");
						ex.printStackTrace();
					}
				}
			}.runTaskLater(plugin, 1200);
		}
	}

	public WorldManager getWorldManager()
	{
		return this.worlds;
	}

	public MultiWorldPlugin getPlugin()
	{
		return this.plugin;
	}

	public void onShutdown()
	{
		if (this.saveTask == null)
		{
			return;
		}
		try
		{
			save();
		}
		catch (ConfigException ex)
		{
			Command.broadcastCommandMessage(Bukkit.getConsoleSender(), "[Multiworld] Failed saving!!! check console for details!!!", false);
			ex.printStackTrace();
		}
	}

	public void save() throws ConfigException
	{
		if (this.saveTask != null)
		{
			this.saveTask.cancel();
			this.saveTask = null;
		}
		this.config.options().header("# options.debug: must the debug output be printed?\n"
			+ "# options.difficulty: what is the server diffeculty?\n"
			+ "# options.locale: what set of lang files must be used, supported: en_US, nl_NL, de_DE, it_IT\n"
			+ "# spawnGroup: used to set withs worlds have what spawn, difficult to use. see official site for details (try expierementing)");
		ConfigurationSection l1;
		l1 = this.config.createSection("worlds");
		this.worlds.saveWorlds(l1, logger, this.spawn);
		if (this.spawn != null)
		{
			this.spawn.save(config.createSection("spawnGroup"));
		}
		this.plugin.saveConfig();
	}

	public void load() throws ConfigException
	{
		this.load(false);
	}

	private void load(boolean isStartingUp) throws ConfigException
	{
		if (!isStartingUp)
		{
			this.plugin.reloadConfig();
			this.config = this.plugin.getConfig();
		}
		this.logger = new MyLogger(getNode(OPTIONS_DEBUG), "MultiWorld");
		this.logger.fine("config loaded");
		this.difficulty = Difficulty.getByValue(getNode(OPTIONS_DIFFICULTY));

		/* locale setting */
		{
			String tmp1;
			String tmp2 = "";
			String tmp3 = "";
			String[] tmp4 = getNode(OPTIONS_LOCALE).split("_");
			switch (tmp4.length)
			{
				case 3:
					tmp3 = tmp4[2];
				case 2:
					tmp2 = tmp4[1];
				default:
					tmp1 = tmp4[0];
					break;
			}

			this.lang = new LangStrings(tmp1, tmp2, tmp3, this.plugin);
		}
		/* addons settings */
		{
			this.getNode(DataHandler.OPTIONS_DEBUG);
			this.getNode(DataHandler.OPTIONS_GAMEMODE);
			this.getNode(DataHandler.OPTIONS_GAMEMODE_INV);
			this.getNode(DataHandler.OPTIONS_BLOCK_ENDER_CHESTS);
			this.getNode(DataHandler.OPTIONS_LINK_END);
			this.getNode(DataHandler.OPTIONS_LINK_NETHER);
			this.getNode(DataHandler.OPTIONS_WORLD_SPAWN);
		}
		if (this.getNode(DataHandler.OPTIONS_WORLD_SPAWN))
		{
			ConfigurationSection spawnGroup = this.config.getConfigurationSection("spawnGroup");
			if (spawnGroup == null)
			{
				this.config.set("spawnGroup.defaultGroup.world", Bukkit.getWorlds().get(0).getName());
			}
			this.spawn = new SpawnWorldControl(spawnGroup, this);

		}
		ConfigurationSection worldList = this.config.getConfigurationSection("worlds");
		if (worldList != null)
		{
			worlds.loadWorlds(worldList, this.logger, this.difficulty, this.spawn);
		}
	}

	public MyLogger getLogger()
	{
		return this.logger;
	}

	@Override
	public String toString()
	{
		return "DataHandler{"
			+ "worlds=" + worlds
			+ ", config=" + config
			+ ", plugin=" + plugin
			+ ", logger=" + logger
			+ ", difficulty=" + difficulty
			+ ", lang=" + lang
			+ ", unloadWorldsOnDisable=" + unloadWorldsOnDisable
			+ '}';
	}

	public LangStrings getLang()
	{
		return this.lang;
	}

	boolean isWorldExisting(String world)
	{
		return worlds.isWorldExisting(world);
	}

	public <T> T getNode(ConfigNode<T> input)
	{

		return input.get(config);
	}

	public <T> void setNode(ConfigNode<T> input, T value)
	{
		input.set(config, value);
	}

	public SpawnWorldControl getSpawns()
	{
		return this.spawn;
	}
}
