package nl.ferrybig.multiworld.api.flag;

import java.util.Locale;
import nl.ferrybig.multiworld.InvalidFlagException;

/**
 * the flags to set on a world
 * @author Fernando
 */
public enum FlagName
{
	/**
	 * The flag for monster spawning
	 */
	SPAWNMONSTER("SpawnMonster", true),
	/**
	 * The flag to spawn animals
	 */
	SPAWNANIMAL("SpawnAnimal",true),
	/**
	 * The flag to enable PvP
	 */
	PVP("PvP", true),
	/**
	 * Must the spawn keep loded at the mem
	 */
	REMEMBERSPAWN("RememberSpawn", true),
	/**
	 * Is this a world where players use creative mode
	 */
	CREATIVEWORLD("CreativeWorld", false),
	SAVEON("SaveOn", true),
	RECIEVECHAT("RecieveChat", true),
	SENDCHAT("SendChat", true);
	/**
	 * The user frendly name of this flag
	 */
	private final String userFriendlyName;
	private final boolean defaultState;

	/**
	 * contructs the flag
	 * @param name 
	 */
	private FlagName(String name, boolean defaultState)
	{
		userFriendlyName = name;
		this.defaultState = defaultState;
	}

	/**
	 * gets the user frendly name of this flag
	 * @return the user frendly name
	 */
	@Override
	public String toString()
	{
		return userFriendlyName;
	}

	/**
	 * try to parse the argument as an flag and return the result
	 * @param str the flagname to parse
	 * @return the FlagName when there was no error
	 * @throws InvalidFlagException when the argument was not able to parse as a flag
	 */
	public static FlagName getFlagFromString(String str) throws InvalidFlagException
	{
		try
		{
			return FlagName.valueOf(FlagName.class, str.toUpperCase(Locale.ENGLISH));
		}
		catch (IllegalArgumentException e)
		{
			throw (InvalidFlagException) new InvalidFlagException().initCause(e);
		}
	}

	/**
	 * Make an list of al the flags useable
	 * @return An list for an user containing the flags
	 */
	public static String makeFlagList()
	{
		FlagName[] flags = FlagName.class.getEnumConstants();
		StringBuilder out = new StringBuilder().append("The flags: ");
		boolean first = true;
		for (FlagName flag : flags)
		{
			if (!first)
			{
				out.append(", ");
			}
			out.append(flag.toString());
			first = false;
		}
		return out.toString();
	}

	/**
	 * @return the defaultState
	 */
	public boolean getDefaultState()
	{
		return defaultState;
	}
}