package multiworld;
/**
 * Throws when it was unable to parse the flag 
 * ie, non existing
 * @author Fernando
 */
public class InvalidFlagException extends CommandException
{
	private static final long serialVersionUID = 224457743535694L;
/**
	 * No argument constructor
	 */
	public InvalidFlagException()
	{	super("Unknown flag specified");
	}
}