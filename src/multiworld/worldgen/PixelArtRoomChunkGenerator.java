package multiworld.worldgen;

import java.util.Random;
import multiworld.worldgen.util.ChunkMaker;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

/**
 * makes world whit big flat walls where players can make pixelart
 * @author Fernando
 */
public class PixelArtRoomChunkGenerator extends SimpleChunkGen
{
	/**
	 * The no argument constructor
	 */
	public PixelArtRoomChunkGenerator()
	{
	}

	/**
	 * Gets the spawn location of the world
	 * @param world the world to getInstance from
	 * @param random the random go getInstance from
	 * @return null, always
	 */
	@Override
	public org.bukkit.Location getFixedSpawnLocation(World world, Random random)
	{
		return null;
	}

	/**
	 * Makes the chunk
	 * @param world the chunk from this world
	 * @param random the random to use
	 * @param x The x-cordinate
	 * @param z the z-cordinate
	 * @return the byte[32768] containing the raw blocks
	 */
	@Override
	public short[][]  generateExtBlockSections(World world,
                                          Random random,
                                          int x,
                                          int z,
                                          ChunkGenerator.BiomeGrid biomes)
	{
		ChunkMaker tmpChunk = new ChunkMaker(super.generateExtBlockSections(world,random,x,z,biomes),world.getMaxHeight());
		if (Integer.numberOfTrailingZeros(x) >= 3)
		{
			tmpChunk.cuboid(0, 1, 0, 0, world.getMaxHeight()-1, 15, WOOL);
		}
		else if (Integer.numberOfTrailingZeros(x + 1) >= 3)
		{
			tmpChunk.cuboid(15, 1, 0, 15, world.getMaxHeight()-1, 15, WOOL);
		}
		if (Integer.numberOfTrailingZeros(z) >= 3)
		{
			tmpChunk.cuboid(0, 1, 0, 15, world.getMaxHeight()-1, 0, WOOL);
		}
		else if (Integer.numberOfTrailingZeros(z + 1) >= 3)
		{
			tmpChunk.cuboid(0, 1, 15, 15, world.getMaxHeight()-1, 15, WOOL);
		}
		return tmpChunk.getRawChunk();
	}

	private void makeWallX1(byte[] chunk1, int height)
	{
		for (int z = 0; z < 16; z++)
		{
			for (int y = 1; y < height; y++)
			{
				chunk1[z * y] = WOOL;
			}
		}
	}

	private void makeWallX2(byte[] chunk1, int height)
	{
		for (int z = 0; z < 16; z++)
		{
			for (int y = 1; y < height; y++)
			{
				chunk1[(240 + z) * height] = WOOL;
			}
		}
	}

	private void makeWallZ1(byte[] chunk1, int height)
	{
		for (int x = 0; x < 16; x++)
		{
			for (int y = 1; y < height; y++)
			{
				chunk1[(x * 16) * y] = WOOL;
			}
		}
	}

	private void makeWallZ2(byte[] chunk1, int height)
	{
		for (int x = 0; x < 16; x++)
		{
			for (int y = 1; y < height; y++)
			{
				chunk1[(((x * 16) + 15)) * y] = WOOL;
			}
		}
	}

	@Override
	protected short[][] makeChunk(World w)
	{
		ChunkMaker chunk = new ChunkMaker(w.getMaxHeight());
		chunk.cuboid(0,0, 0, 15, 0, 15, (byte)BEDROCK);
		chunk.cuboid(0,1, 0, 15, 1, 15, WOOL);
		return chunk.getRawChunk();
	}
}