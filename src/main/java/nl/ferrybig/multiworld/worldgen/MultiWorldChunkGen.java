/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.worldgen;

import java.util.List;
import nl.ferrybig.multiworld.worldgen.populators.Populators;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

/**
 * @author Fernando
 */
public abstract class MultiWorldChunkGen extends org.bukkit.generator.ChunkGenerator implements
    ChunkGen, BlockConstants {

  @Override
  public List<BlockPopulator> getDefaultPopulators(World world) {
    List<BlockPopulator> list = super.getDefaultPopulators(world);
    list.add(Populators.MYCELIUM.get());
    list.add(Populators.SNOW.get());
    return list;
  }
}
