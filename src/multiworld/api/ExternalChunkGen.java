/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.api;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

/**
 *
 * @author Fernando
 */
public interface ExternalChunkGen
{
	public String getDescription();
	public String getName();
	public ChunkGenerator getGen();
	public World.Environment getEnvoiment();
}
