package multiworld;

/**
 * Throwed when a command is used wrong
 * @author Fernando
 */
public class ArgumentException extends CommandException
{
	private static final long serialVersionUID = 22355441L;
	/**
	 * The correct usage of the command
	 */
	private final String usage;

	/**
	 * No argument constructor
	 */
	public ArgumentException()
	{
		super("Illegal arguments specified");
		this.usage = null;
	}

	/**
	 * The contructor were you can specify the correct usage for the command
	 * @param correctUsage
	 */
	public ArgumentException(String correctUsage)
	{
		super("Illegal arguments specified, usage: " + correctUsage);
		this.usage = correctUsage;
	}

	/**
	 * Return the correct usage of this command
	 * @return the correct usage
	 */
	public String correctUsage()
	{
		return this.usage;
	}
}
