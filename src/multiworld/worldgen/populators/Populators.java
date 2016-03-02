/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.worldgen.populators;

import org.bukkit.generator.BlockPopulator;

/**
 * The class to get al the chunk gens
 * @author Fernando
 */
public enum Populators
{
	SNOW(new SnowPopulator()),
	PLANET(new SmallPlanetPopulator()),
	BIG_PLANET(new BigPlanetPopulator()),
	DUNGEON(new DungeonPopulator()),
	MYCELIUM(new MyceliumPopulator());
	protected final BlockPopulator populator;

	private Populators(BlockPopulator pop)
	{
		this.populator = pop;
	}

	public BlockPopulator get()
	{
		return this.populator;
	}
}
