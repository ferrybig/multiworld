package nl.ferrybig.multiworld.worldgen;

import nl.ferrybig.multiworld.WorldGenException;
import nl.ferrybig.multiworld.data.InternalWorld;

/**
 * The interface that all the world genarators implement
 * @author Fernando
 */
public interface ChunkGen
{
	/**
	 * return an world whit updates values to make an world whit this gen
	 * @param options The options this world have
	 * @throws WorldGenException  
	 */
	public void makeWorld(InternalWorld options) throws WorldGenException;
}