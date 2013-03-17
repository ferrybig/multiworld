/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld;

/**
 * The exception thrown when there was an error whit the config
 * @author Fernando
 */
public class ConfigException extends MultiWorldException
{
	private static final long serialVersionUID = 5397583331684367L;

	/**
	 * Creates a new instance of <code>ConfigException</code> without detail message.
	 */
	public ConfigException()
	{
	}

	/**
	 * Constructs an instance of <code>ConfigException</code> with the specified detail message.
	 * @param msg the detail message.
	 */
	public ConfigException(String msg)
	{
		super(msg);
	}
	/**
	 * Creates a new instance of <code>ConfigException</code> with cause.
	 * @param cause The exception caused this exception
	 */
	public ConfigException(Throwable cause)
	{	super(cause);
	}

	/**
	 * Constructs an instance of <code>ConfigException</code> with the specified detail message and cause.
	 * @param msg the detail message.
	 * @param cause The exception caused this
	 */
	public ConfigException(String msg, Throwable cause)
	{
		super(cause,msg);
	}
}
