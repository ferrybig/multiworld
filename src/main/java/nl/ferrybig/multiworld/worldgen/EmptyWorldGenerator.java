/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import nl.ferrybig.multiworld.worldgen.util.ChunkMaker;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

/**
 * @author Fernando
 */
public class EmptyWorldGenerator extends SimpleChunkGen {

  @Override
  public List<BlockPopulator> getDefaultPopulators(World world) {
    List<BlockPopulator> list = new ArrayList<BlockPopulator>();
    list.add(new BlockPopulator() {

      @Override
      public void populate(World world, Random random, Chunk chunk) {
        chunk.getBlock(1, 1, 1).setType(Material.SAND);
      }
    });
    return list;
  }

  @Override
  public boolean canSpawn(World world, int x, int z) {
    return true;
  }

  @Override
  protected ChunkMaker makeChunk(World w) {
    return new ChunkMaker(w.getMaxHeight());
  }
}
