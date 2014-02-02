package multiworld.data;

import java.util.EnumMap;
import multiworld.api.flag.FlagName;
import multiworld.flags.FlagValue;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;

/**
 * The class that represents worlds
 * <p>
 * @author Fernando
 */
public class InternalWorld
{
	private String worldName;
	private long worldSeed;
	private World.Environment worldType = World.Environment.NORMAL;
	private ChunkGenerator worldGen;
	private String options;
	private EnumMap<FlagName, FlagValue> flags;
	private String fullGeneratorName;
	private String portalLink;
	private String endLink;
	private Difficulty difficulty = Difficulty.NORMAL;
	private WorldType type = WorldType.NORMAL;

	public InternalWorld()
	{
	}

	public InternalWorld(String name, long seed, World.Environment env, ChunkGenerator gen, String options, EnumMap<FlagName, FlagValue> map, String fullGeneratorName, Difficulty difficulty)
	{
		this(name, seed, env, gen, options, map, fullGeneratorName, "", "", difficulty);
	}

	public InternalWorld(String name, long seed, World.Environment env, ChunkGenerator gen, String options, EnumMap<FlagName, FlagValue> map, String fullGeneratorName, String link, String endLink, Difficulty difficulty)
	{
		this(name, seed, env, gen, options, map, fullGeneratorName, link, endLink, difficulty, WorldType.NORMAL);
	}

	public InternalWorld(String name, long seed, World.Environment env, ChunkGenerator gen, String options, EnumMap<FlagName, FlagValue> map, String fullGeneratorName, String link, String endLink, Difficulty difficulty, WorldType type)
	{
		this.worldName = name;
		this.worldSeed = seed;
		this.worldType = env;
		this.worldGen = gen;
		this.options = options;
		this.flags = map;
		this.fullGeneratorName = fullGeneratorName;
		this.portalLink = link != null ? link : "";
		this.endLink = endLink != null ? endLink : "";
		this.difficulty = difficulty;
	}

	public WorldType getType()
	{
		return type;
	}

	public void setType(WorldType type)
	{
		this.type = type;
	}

	public World getWorld()
	{
		return Bukkit.getWorld(worldName);
	}

	public String getName()
	{
		return this.worldName;
	}

	public World.Environment getEnv()
	{
		return this.worldType;
	}

	public long getSeed()
	{
		return this.worldSeed;
	}

	public String getOptions()
	{
		return this.options;
	}

	public String getPortalWorld()
	{
		return this.portalLink;
	}

	public EnumMap<FlagName, FlagValue> getFlags()
	{
		return flags;
	}

	public ChunkGenerator getGen()
	{
		return this.worldGen;
	}

	public String getWorldType()
	{
		if (this.worldGen != null)
		{
			if (this.getFullGeneratorName().equals("NULLGEN"))
			{
				if (this.worldType == World.Environment.NORMAL)
				{
					return "Normal world with unknown external generator";
				}
				else if (this.worldType == World.Environment.NETHER)
				{
					return "Nether world with unknown external generator";
				}
				else if (this.worldType == World.Environment.THE_END)
				{
					return "End world with unknown external generator";
				}
			}
			else if (this.getFullGeneratorName().startsWith("PLUGIN"))
			{
				if (this.worldType == World.Environment.NORMAL)
				{
					return "Normal world with external generator: " + this.getOptions();
				}
				else if (this.worldType == World.Environment.NETHER)
				{
					return "Nether world with external generator: " + this.getOptions();
				}
				else if (this.worldType == World.Environment.THE_END)
				{
					return "End world with external generator: " + this.getOptions();
				}
			}
			else
			{
				if (this.worldType == World.Environment.NORMAL)
				{
					return "Normal world with internal generator: " + this.getFullGeneratorName() + (this.getOptions().isEmpty() ? "" : ": " + this.getOptions());
				}
				else if (this.worldType == World.Environment.NETHER)
				{
					return "Nether world with internal generator: " + this.getFullGeneratorName() + (this.getOptions().isEmpty() ? "" : ": " + this.getOptions());
				}
				else if (this.worldType == World.Environment.THE_END)
				{
					return "End world with internal generator: " + this.getFullGeneratorName() + (this.getOptions().isEmpty() ? "" : ": " + this.getOptions());
				}
			}
		}
		else
		{
			if (this.worldType == World.Environment.NORMAL)
			{
				return "Normal world";
			}
			else if (this.worldType == World.Environment.NETHER)
			{
				return "Nether world";
			}
			else if (this.worldType == World.Environment.THE_END)
			{
				return "End world";
			}
		}

		return "Unknown world";
	}

	public String getFullGeneratorName()
	{
		return this.fullGeneratorName;
	}

	public String getEndPortalWorld()
	{
		return this.endLink;
	}

	/**
	 * @return the difficulty
	 */
	public Difficulty getDifficulty()
	{
		return difficulty;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final InternalWorld other = (InternalWorld) obj;
		if ((this.worldName == null) ? (other.worldName != null) : !this.worldName.equals(other.worldName))
		{
			return false;
		}
		if (this.worldSeed != other.worldSeed)
		{
			return false;
		}
		if (this.worldType != other.worldType)
		{
			return false;
		}
		if (this.worldGen != other.worldGen && (this.worldGen == null || !this.worldGen.equals(other.worldGen)))
		{
			return false;
		}
		if ((this.options == null) ? (other.options != null) : !this.options.equals(other.options))
		{
			return false;
		}
		if (this.flags != other.flags && (this.flags == null || !this.flags.equals(other.flags)))
		{
			return false;
		}
		if ((this.fullGeneratorName == null) ? (other.fullGeneratorName != null) : !this.fullGeneratorName.equals(other.fullGeneratorName))
		{
			return false;
		}
		if ((this.portalLink == null) ? (other.portalLink != null) : !this.portalLink.equals(other.portalLink))
		{
			return false;
		}
		if ((this.endLink == null) ? (other.endLink != null) : !this.endLink.equals(other.endLink))
		{
			return false;
		}
		if (this.difficulty != other.difficulty)
		{
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		int hash = 0x82746283;
		hash ^= (this.worldName != null ? this.worldName.hashCode() : 0);
		hash ^= (int) (this.worldSeed ^ (this.worldSeed >>> 32));
		hash ^= (this.worldType != null ? this.worldType.hashCode() : 0);
		hash ^= (this.worldGen != null ? this.worldGen.hashCode() : 0);
		hash ^= (this.options != null ? this.options.hashCode() : 0);
		hash ^= (this.flags != null ? this.flags.hashCode() : 0);
		hash ^= (this.fullGeneratorName != null ? this.fullGeneratorName.hashCode() : 0);
		hash ^= (this.portalLink != null ? this.portalLink.hashCode() : 0);
		hash ^= (this.endLink != null ? this.endLink.hashCode() : 0);
		hash ^= this.difficulty.ordinal() * 123456;
		return hash;
	}

	@Override
	public String toString()
	{
		return "InternalWorld{"
			+ "worldName=" + worldName
			+ ", worldSeed=" + worldSeed
			+ ", worldType=" + worldType
			+ ", worldGen=" + worldGen
			+ ", options=" + options
			+ ", flags=" + flags
			+ ", madeBy=" + fullGeneratorName
			+ ", portalLink=" + portalLink
			+ ", endLink=" + endLink
			+ ", difficulty=" + difficulty
			+ '}';
	}

	public void setWorldName(String worldName)
	{
		this.worldName = worldName;
	}

	public void setWorldSeed(long worldSeed)
	{
		this.worldSeed = worldSeed;
	}

	public void setWorldType(World.Environment worldType)
	{
		this.worldType = worldType;
	}

	public void setWorldGen(ChunkGenerator worldGen)
	{
		this.worldGen = worldGen;
	}

	public void setOptions(String options)
	{
		this.options = options;
	}

	public void setFlags(EnumMap<FlagName, FlagValue> flags)
	{
		this.flags = flags;
	}

	public void setFullGeneratorName(String madeBy)
	{
		this.fullGeneratorName = madeBy;
	}

	public void setPortalLink(String portalLink)
	{
		this.portalLink = portalLink;
	}

	public void setEndLink(String endLink)
	{
		this.endLink = endLink;
	}

	public void setDifficulty(Difficulty difficulty)
	{
		this.difficulty = difficulty;
	}
}
