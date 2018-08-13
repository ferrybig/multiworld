/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.worldgen.populators;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;

/**
 * The class for 1.9 that handles the grass at Mycelium biomess
 * @author Fernando
 */
public class MyceliumPopulator extends SurfacePopulator
{

	@Override
	public void chanceBlock(int x, int z, Block block)
	{
		
		 if ((block.getBiome() == Biome.MUSHROOM_FIELDS) || (block.getBiome() == Biome.MUSHROOM_FIELD_SHORE))
		 {
			if ((block.getType() == Material.GRASS))
			{
				block.setType(Material.MYCELIUM);
			}
		}
		 
	}
	
}
