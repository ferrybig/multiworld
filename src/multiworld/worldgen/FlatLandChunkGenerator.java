package multiworld.worldgen;

import java.util.HashMap;
import java.util.Map;
import multiworld.InvalidWorldGenOptionsException;
import multiworld.data.InternalWorld;
import multiworld.worldgen.util.ChunkMaker;
import org.bukkit.World;

/**
 * This worldgen makes chunks of the type flat
 * @author Fernando
 */
public class FlatLandChunkGenerator extends SimpleChunkGen
{
	private final Map<String, Byte> heightMap = new HashMap<String, Byte>();

	@Override
	public org.bukkit.Location getFixedSpawnLocation(World world, java.util.Random random)
	{
		return null;
	}

	/**
	 * makes the internal chunk that wil be copied to the new chunks made
	 *
	 * @param w
	 * @return
	 */
	@Override
	protected short[][] makeChunk(World w)
	{
		ChunkMaker chunk = new ChunkMaker(w.getMaxHeight());
		int seeLevel = w.getSeaLevel();
		int lowestDirt = seeLevel - 3;
		chunk.cuboid(0, 0,0, 15,0,15,(byte)BEDROCK);
		chunk.cuboid(0, 1,0, 15,lowestDirt-1,15,STONE);
		chunk.cuboid(0, lowestDirt,0, 15,seeLevel-1,15,(byte)DIRT);
		chunk.cuboid(0, seeLevel, 0, 15, seeLevel, 15, (byte)GRASS);
		return chunk.getRawChunk();
	}

	/**
	 * Makes the world and save the height
	 * @param world the o<code>InternalWorld</code> obj to modify
	 * @throws InvalidWorldGenOptionsException  If the world dont give valid options
	 */
	@Override
	public void makeWorld(InternalWorld world) throws InvalidWorldGenOptionsException
	{
		super.makeWorld(world);
		this.heightMap.put(world.getName(), parseOptions(world.getOptions()));

	}

	private byte parseOptions(String options) throws InvalidWorldGenOptionsException
	{
		if (options.isEmpty())
		{
			return 64;
		}
		try
		{
			byte number = Byte.parseByte(options);
			if (number > 127 || number < 0)
			{
				throw new InvalidWorldGenOptionsException("Argument must be lower than 128");
			}
			return number;
		}
		catch (NumberFormatException e)
		{
			throw new InvalidWorldGenOptionsException(e.getLocalizedMessage());
		}
	}

	protected final byte getHeightByWorldName(String name)
	{
		return this.heightMap.get(name);
	}
}