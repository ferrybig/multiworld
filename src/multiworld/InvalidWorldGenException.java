package multiworld;

public class InvalidWorldGenException extends WorldGenException
{
	private static final long serialVersionUID = 22395837628743L;
	protected final String wrongName;

	public InvalidWorldGenException()
	{
		super("Cannot find world gen");
		this.wrongName = null;
	}

	public InvalidWorldGenException(String wrongName)
	{
		super("Cannot find world gen " + wrongName);
		this.wrongName = wrongName;
	}

	public String getWrongGen()
	{
		return this.wrongName;
	}
}