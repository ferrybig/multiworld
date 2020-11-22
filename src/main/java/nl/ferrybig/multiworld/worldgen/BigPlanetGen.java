/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.worldgen;

import java.util.ArrayList;
import java.util.List;
import nl.ferrybig.multiworld.worldgen.populators.Populators;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

/**
 * Make an world whit planets
 *
 * @author Fernando
 */
public class BigPlanetGen extends AbstractPlanetGen implements ChunkGen {


  public BigPlanetGen() {
  }

  @Override
  public List<BlockPopulator> getDefaultPopulators(World world) {
    List<BlockPopulator> list = new ArrayList<BlockPopulator>();
    list.add(Populators.BIG_PLANET.get());
    list.add(Populators.SNOW.get());
    list.add(Populators.MYCELIUM.get());
    return list;
  }
}
