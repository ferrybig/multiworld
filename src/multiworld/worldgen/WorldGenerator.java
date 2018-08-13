package multiworld.worldgen;

import multiworld.InvalidWorldGenException;
import multiworld.WorldGenException;
import multiworld.data.InternalWorld;
import org.bukkit.World;
import org.bukkit.WorldType;

/**
 * the main class to get world genarators
 * @author Fernando
 */
public enum WorldGenerator implements ChunkGen
{
	/**
	 * the normal mc world constant
	 */
	NORMAL(World.Environment.NORMAL, "Normal", "Makes a normal minecraft world", SpeedLevel.NORMAL),
	/**
	 * the nether mc world contant
	 */
	NETHER(World.Environment.NETHER, "Nether", "Makes a nether world", SpeedLevel.NORMAL),
	/*
	 *
	 */
	THE_END(World.Environment.THE_END, "The_End", "Makes a end world", SpeedLevel.NORMAL),
	/**
	 * gen for the end
	 */
	END(THE_END, "End", "Alias for 'The_End'", false, SpeedLevel.NORMAL),
	/**
	 * A flat world
	 */
	FLATLAND(new FlatLandChunkGenerator(), "FlatLand", "Makes a flatland for creative buildings", SpeedLevel.FAST),
	PIXELARTROOM(new PixelArtRoomChunkGenerator(), "PixelArtRoom", "Makes a world from wool with big walls for pixelart", SpeedLevel.FAST),
	PLANETS(new SmallPlanetGen(), "Planets", "Makes worlds out of planets", SpeedLevel.NORMAL),
	SOLARSYSTEM(new BigPlanetGen(), "SolarSystem", "Makes a world with a few, big planets", SpeedLevel.NORMAL),
	ISLANDS(new FlyingIslandsGenerator(), "Islands", "Flying islands", SpeedLevel.NORMAL),
	NULLGEN(NullGen.get(), "NullGen", "An dummy gen to says that the world is made by another plugin", false, SpeedLevel.UNKNOWN),
	PLUGIN(new PluginGen(World.Environment.NORMAL), "Plugin", "Makes the world with another plugin.", SpeedLevel.UNKNOWN),
	PLUGIN_NETHER(new PluginGen(World.Environment.NETHER), "Plugin_Nether", "Makes the world with another plugin (nether version).", SpeedLevel.UNKNOWN),
	PLUGIN_END(new PluginGen(World.Environment.THE_END), "Plugin_End", "Makes the world with another plugin (end version).", SpeedLevel.UNKNOWN),
	OCEAN(new OceanGen(), "Ocean", "Makes a world that is 1 big ocean.", SpeedLevel.FAST),
	DESERT(new DesertGen(), "Desert", "Makes a world that is 1 big desert.", SpeedLevel.FAST),
	EPICCAVES(new ChunkGeneratorEpicCaves(), "EpicCaves", "just realy complex (and laggy) caves", SpeedLevel.SLOW),
	EMPTY(new EmptyWorldGenerator(), "Empty", "an empty world", SpeedLevel.FAST),
	AMPLIFIED(new WorldTypeBasedGenerator(WorldType.AMPLIFIED),"Amplified","Uses another generator to generate a amplified world"),
	LARGEBIOMES(new WorldTypeBasedGenerator(WorldType.LARGE_BIOMES),"LargeBiomes","Uses another generator to generate a Large Biomes world"),
	SUPERFLAT(new WorldTypeBasedGenerator(WorldType.FLAT),"SuperFlat","Uses minecraft superflat methode to create a world"),
	;
	private final ChunkGen generator;
	private final String name;
	private final String destr;
	private final Boolean mayShowUP;
	private final SpeedLevel speed;

	private WorldGenerator(ChunkGen gen)
	{
		this(gen, gen.toString(), "INVALID DESTR", true, SpeedLevel.UNKNOWN);
	}

	private WorldGenerator(World.Environment type)
	{
		this(new DefaultGen(type), type.name(), "INVALID DESTR", true, SpeedLevel.UNKNOWN);
	}

	private WorldGenerator(ChunkGen gen, String name, String destr)
	{
		this(gen, name, destr, true, SpeedLevel.UNKNOWN);
	}

	private WorldGenerator(World.Environment type, String name, String destr)
	{
		this(new DefaultGen(type), name, destr, true, SpeedLevel.UNKNOWN);
	}

	private WorldGenerator(ChunkGen gen, String name, String destr, SpeedLevel speed)
	{
		this(gen, name, destr, true, speed);
	}

	private WorldGenerator(World.Environment type, String name, String destr, SpeedLevel speed)
	{
		this(new DefaultGen(type), name, destr, true, speed);
	}

	private WorldGenerator(World.Environment type, String name, String destr, boolean visable)
	{
		this(new DefaultGen(type), name, destr, visable, SpeedLevel.UNKNOWN);
	}

	private WorldGenerator(ChunkGen gen, String name, String destr, boolean visable)
	{
		this(gen, name, destr, visable, SpeedLevel.UNKNOWN);
	}

	private WorldGenerator(World.Environment type, String name, String destr, boolean visable, SpeedLevel speed)
	{
		this(new DefaultGen(type), name, destr, visable, speed);
	}

	private WorldGenerator(ChunkGen gen, String name, String destr, boolean visable, SpeedLevel speed)
	{
		this.generator = gen;
		this.name = name;
		this.destr = destr;
		this.mayShowUP = visable;
		this.speed = speed;
	}

	@Override
	public void makeWorld(InternalWorld options) throws WorldGenException
	{
		this.generator.makeWorld(options);
	}

	public boolean mayInList()
	{
		return this.mayShowUP;
	}

	/**
	 * gets the name of this world gen
	 * @return the name
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * gets the description of this world gen
	 * @return the description
	 */
	public String getDestr()
	{
		return this.destr;
	}

	public static org.bukkit.generator.ChunkGenerator getGen(String id)
	{
		try
		{
			WorldGenerator gen = WorldGenerator.valueOf(WorldGenerator.class, id.toUpperCase());
			if (gen.generator instanceof org.bukkit.generator.ChunkGenerator)
			{
				return (org.bukkit.generator.ChunkGenerator) gen.generator;
			}
		}
		catch (IllegalArgumentException e)
		{
		}
		return null;
	}

	public static WorldGenerator getGenByName(String gen) throws multiworld.InvalidWorldGenException
	{
		try
		{
			return WorldGenerator.valueOf(gen.toUpperCase());
		}
		catch (IllegalArgumentException e)
		{
			throw (InvalidWorldGenException) new InvalidWorldGenException(gen).initCause(e);
		}
	}

	public static String[] getAllGenerators()
	{
		WorldGenerator[] gens = WorldGenerator.class.getEnumConstants();
		StringBuilder out = new StringBuilder();
		for (WorldGenerator gen : gens)
		{
			if (gen.mayInList())
			{
				out.append(gen.getName()).append(" - ").append(gen.getDestr()).append("#");
			}
		}
		return out.toString().split("\\#");
	}

	/**
	 * @return the speed
	 */
	public SpeedLevel getSpeed()
	{
		return speed;
	}
}
