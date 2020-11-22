package multiworld.worldgen;

import multiworld.data.InternalWorld;
import multiworld.worldgen.util.ChunkMaker;
import org.bukkit.World;

/**
 * The dummy gen thats used when it find an world created by another plugin than this
 * @author Fernando
 */
public class NullGen extends SimpleChunkGen
{

	private static NullGen INSTANCE = new NullGen();
	public static NullGen get()
	{
		return INSTANCE;
	}

	@Override
	public void makeWorld(InternalWorld options)
	{
		options.setWorldType(null);
	}

	@Override
	protected ChunkMaker makeChunk(World w)
	{
		return new ChunkMaker(w);
	}

}
