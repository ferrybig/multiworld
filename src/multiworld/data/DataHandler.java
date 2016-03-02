package multiworld.data;

import multiworld.ConfigException;
import multiworld.MultiWorldPlugin;
import multiworld.command.CommandStack;
import multiworld.command.DebugLevel;
import multiworld.command.MessageType;
import multiworld.data.config.ConfigNode;
import multiworld.data.config.ConfigNodeSection;
import multiworld.data.config.DefaultConfigNode;
import multiworld.data.config.DifficultyConfigNode;
import multiworld.translation.Translation;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
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
	private final WorldUtils worlds;
	private FileConfiguration config;
	private final MultiWorldPlugin plugin;
	private MyLogger logger;
	/**
	 * Base difficulty
	 */
	private Difficulty difficulty;
	private boolean unloadWorldsOnDisable = false;
	private SpawnWorldControl spawn;
	public final static ConfigNode<ConfigurationSection> OPTIONS_MAIN_NODE = new ConfigNodeSection("options");
	public final static DefaultConfigNode<Boolean> OPTIONS_BLOCK_ENDER_CHESTS = new DefaultConfigNode<Boolean>(OPTIONS_MAIN_NODE, "blockEnderChestInCrea", false, Boolean.class);
	public final static DefaultConfigNode<Boolean> OPTIONS_LINK_NETHER = new DefaultConfigNode<Boolean>(OPTIONS_MAIN_NODE, "useportalhandler", false, Boolean.class);
	public final static DefaultConfigNode<Boolean> OPTIONS_LINK_END = new DefaultConfigNode<Boolean>(OPTIONS_MAIN_NODE, "useEndPortalHandler", false, Boolean.class);
	public final static DefaultConfigNode<Boolean> OPTIONS_WORLD_CHAT = new DefaultConfigNode<Boolean>(OPTIONS_MAIN_NODE, "useWorldChatSeperator", false, Boolean.class);
	public final static DefaultConfigNode<Boolean> OPTIONS_GAMEMODE = new DefaultConfigNode<Boolean>(OPTIONS_MAIN_NODE, "usecreativemode", false, Boolean.class);
	public final static DefaultConfigNode<Boolean> OPTIONS_GAMEMODE_INV = new DefaultConfigNode<Boolean>(OPTIONS_MAIN_NODE, "usecreativemodeinv", true, Boolean.class);
	public final static DefaultConfigNode<Boolean> OPTIONS_WORLD_SPAWN = new DefaultConfigNode<Boolean>(OPTIONS_MAIN_NODE, "useWorldSpawnHandler", false, Boolean.class);
	public final static DefaultConfigNode<Boolean> OPTIONS_CRAFTBUKKIT_HOOKS = new DefaultConfigNode<Boolean>(OPTIONS_MAIN_NODE, "craftbukkitHooks", true, Boolean.class);
	public final static DefaultConfigNode<Boolean> OPTIONS_DEBUG = new DefaultConfigNode<Boolean>(OPTIONS_MAIN_NODE, "debug", false, Boolean.class);
	public final static ConfigNode<Difficulty> OPTIONS_DIFFICULTY = new DifficultyConfigNode(OPTIONS_MAIN_NODE, "difficulty", Difficulty.NORMAL);
	public final static DefaultConfigNode<String> OPTIONS_LOCALE = new DefaultConfigNode<String>(OPTIONS_MAIN_NODE, "locale", "en_US", String.class);

	/**
	 * Makes the object
	 * <p>
	 * @param config
	 * @param plugin The main plugin running this
	 * @throws ConfigException When there was an error
	 */
	public DataHandler(FileConfiguration config, MultiWorldPlugin plugin) throws ConfigException
	{
		this.config = config;
		this.plugin = plugin;
		this.worlds = new WorldManager();
		this.load(true);
	}

	private BukkitTask saveTask = null;
	private int configSaveFailed = 0;
	private final Runnable saver = new Runnable()
	{
		@Override
		public void run()
		{
			CommandStack console = DataHandler.this.getPlugin().builder.build(Bukkit.getConsoleSender(), DebugLevel.NONE);
			try
			{
				save();
				console.sendMessageBroadcast(MessageType.SUCCES, Translation.MULTIWORLD_SAVE_SUCCES);
				configSaveFailed = 0;
			}
			catch (ConfigException ex)
			{
				configSaveFailed++;
				if (configSaveFailed < 3)
				{
					console.sendMessageBroadcast(MessageType.ERROR, Translation.MULTIWORLD_SAVE_FAIL_RETRY);
					scheduleSave(20 * 10);
				}
				else
				{
					console.sendMessageBroadcast(MessageType.ERROR, Translation.MULTIWORLD_SAVE_FAIL);
				}
				ex.printStackTrace();
			}
		}
	};

	public void scheduleSave()
	{
		scheduleSave(1200);
	}

	private void scheduleSave(int time)
	{
		if (this.saveTask == null)
		{
			this.saveTask = new BukkitRunnable()
			{
				@Override
				public void run()
				{
					saver.run();
				}
			}.runTaskLater(plugin, time);
		}
	}

	public WorldUtils getWorldManager()
	{
		return this.worlds;
	}

	/**
	 * Get the main multiworld plugin
	 * <p>
	 * @return The plugin
	 */
	public MultiWorldPlugin getPlugin()
	{
		return this.plugin;
	}
/**
 * Called when multiworld is shutdown, performs auto saving if needed, 6 tries fail reducanty
 */
	public void onShutdown()
	{
		if (this.saveTask == null)
		{
			return;
		}
		CommandStack console = DataHandler.this.getPlugin().builder.build(Bukkit.getConsoleSender(), DebugLevel.NONE);
		boolean saved = false;
		this.configSaveFailed = 0;
		while (configSaveFailed < 6 && !saved)
		{
			try
			{
				save();
				console.sendMessageBroadcast(MessageType.SUCCES, Translation.MULTIWORLD_SAVE_SUCCES);
				saved = true;
			}
			catch (ConfigException ex)
			{
				console.sendMessageBroadcast(MessageType.ERROR, Translation.MULTIWORLD_SAVE_FAIL_RETRY_DIRECT);
				configSaveFailed++;
				ex.printStackTrace();
			}
		}
		if (!saved)
		{
			console.sendMessageBroadcast(MessageType.ERROR, Translation.MULTIWORLD_SAVE_FAIL_SHUTDOWN);
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
			+ "# spawnGroup: used to set withs worlds have what spawn, difficult to use. see official site for details");
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
		this.logger = new MyLogger(getNode(OPTIONS_DEBUG), "MultiWorld", this.getPlugin().getLogger());
		this.logger.fine("config loaded");
		this.difficulty = getNode(OPTIONS_DIFFICULTY);

		/* locale setting */
		/*{
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
			@SuppressWarnings("deprecation")
			LangStrings lang1 = new LangStrings(tmp1, tmp2, tmp3, this.plugin);
			this.lang = lang1;
		}*/
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
		this.save();
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
			+ ", unloadWorldsOnDisable=" + unloadWorldsOnDisable
			+ '}';
	}

	/**
	 * Sees if a world is known by multiworld
	 * <p>
	 * @param world
	 * @return
	 * @deprecated All calls to this methode should be deglated to the world manager
	 */
	@Deprecated
	public boolean isWorldExisting(String world)
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
