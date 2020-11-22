package nl.ferrybig.multiworld.worldgen;

import java.util.Random;
import nl.ferrybig.multiworld.WorldGenException;
import nl.ferrybig.multiworld.data.InternalWorld;
import nl.ferrybig.multiworld.worldgen.util.ChunkMaker;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.Material;

/**
 * makes world whit big flat walls where players can make pixelart
 * @author Fernando
 */
public class PixelArtRoomChunkGenerator extends MultiWorldChunkGen
{

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
	public ChunkData generateChunkData(World world,
                                          Random random,
                                          int x,
                                          int z,
                                          ChunkGenerator.BiomeGrid biomes)
	{
		ChunkMaker tmpChunk = new ChunkMaker(world);
		tmpChunk.cuboid(0,0, 0, 15, 0, 15, Material.BEDROCK);
		tmpChunk.cuboid(0,1, 0, 15, 1, 15, Material.WHITE_WOOL);
		if (Integer.numberOfTrailingZeros(x) >= 3)
		{
			tmpChunk.cuboid(0, 1, 0, 0, world.getMaxHeight()-1, 15, Material.WHITE_WOOL);
		}
		else if (Integer.numberOfTrailingZeros(x + 1) >= 3)
		{
			tmpChunk.cuboid(15, 1, 0, 15, world.getMaxHeight()-1, 15, Material.WHITE_WOOL);
		}
		if (Integer.numberOfTrailingZeros(z) >= 3)
		{
			tmpChunk.cuboid(0, 1, 0, 15, world.getMaxHeight()-1, 0, Material.WHITE_WOOL);
		}
		else if (Integer.numberOfTrailingZeros(z + 1) >= 3)
		{
			tmpChunk.cuboid(0, 1, 15, 15, world.getMaxHeight()-1, 15, Material.WHITE_WOOL);
		}
		return tmpChunk.toChunkData(this.createChunkData(world));
	}

	@Override
	public void makeWorld(InternalWorld options) throws WorldGenException {
		options.setWorldGen(this);
	}
}
