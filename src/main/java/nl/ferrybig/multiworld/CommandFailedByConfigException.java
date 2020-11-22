/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld;

/**
 *
 * @author Fernando
 */
public class CommandFailedByConfigException extends CommandException
{
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance of <code>CommandFailedByConfigException</code> without detail message.
	 */
	public CommandFailedByConfigException()
	{
	}

	/**
	 * Constructs an instance of <code>CommandFailedByConfigException</code> with the specified detail message.
	 * @param msg the detail message.
	 */
	public CommandFailedByConfigException(String msg)
	{
		super(msg);
	}

	/**
	 * Creates a new instance of <code>CommandFailedByConfigException</code> without detail message.
	 * 
	 * @param ex 
	 */
	public CommandFailedByConfigException(Throwable ex)
	{
		super(ex);
	}

	/**
	 * Constructs an instance of <code>CommandFailedByConfigException</code> with the specified detail message.
	 * @param msg the detail message.
	 * @param ex  
	 */
	public CommandFailedByConfigException(String msg, Throwable ex)
	{
		super(msg, ex);
	}
}
