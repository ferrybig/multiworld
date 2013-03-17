/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld;

/**
 *
 * @author Fernando
 */
public class NotAPlayerException extends CommandException
{
	/**
	 * Creates a new instance of <code>NotAPlayerException</code> without detail message.
	 */
	public NotAPlayerException()
	{
	}

	/**
	 * Constructs an instance of <code>NotAPlayerException</code> with the specified detail message.
	 * @param msg the detail message.
	 */
	public NotAPlayerException(String msg)
	{
		super(msg);
	}
}
