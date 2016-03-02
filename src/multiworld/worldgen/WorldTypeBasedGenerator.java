/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.worldgen;

import java.util.Locale;
import multiworld.WorldGenException;
import multiworld.data.InternalWorld;
import org.bukkit.WorldType;

/**
 *
 * @author ferrybig
 */
public class WorldTypeBasedGenerator extends MultiWorldChunkGen
{

	private final WorldType type;

	public WorldTypeBasedGenerator(WorldType type)
	{
		this.type = type;
	}

	@Override
	public void makeWorld(InternalWorld options) throws WorldGenException
	{
		String generator = options.getOptions();
		options.setWorldGen(null);
		String option;
		int index = generator.indexOf(':');
		if (index != -1)
		{
			option = generator.substring(index + 1);
			generator = generator.substring(0, index);
		}
		else
		{
			option = "";
		}
		if ("".equals(generator))
		{
			generator = "NORMAL";
		}
		else
		{
			generator = generator.toUpperCase(Locale.ENGLISH);
		}
		ChunkGen env = WorldGenerator.getGenByName(generator);
		{
			String orginalOptions = options.getOptions();
			options.setOptions(option);
			env.makeWorld(options);
			options.setOptions(orginalOptions);
		}
		options.setType(type);
	}

}
