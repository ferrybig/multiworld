/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.flags;

import multiworld.api.flag.FlagName;
import java.util.EnumMap;

/**
 *
 * @author Fernando
 */
public class FlagMap extends EnumMap<FlagName,FlagValue>
{
	private static final long serialVersionUID = 1L;
	public FlagMap()
	{
		super(FlagName.class);
	}
}
