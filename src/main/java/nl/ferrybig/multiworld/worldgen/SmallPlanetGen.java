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
 * @author Fernando
 */
public class SmallPlanetGen extends AbstractPlanetGen implements ChunkGen {



	public SmallPlanetGen() {
	}

	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		List<BlockPopulator> list = new ArrayList<BlockPopulator>();
		list.add(Populators.PLANET.get());
		list.add(Populators.SNOW.get());
		list.add(Populators.MYCELIUM.get());
		return list;
	}
}
