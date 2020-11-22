/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld;

/**
 *
 * @author Fernando
 */
public class InvalidWorldGenOptionsException extends WorldGenException
{
	private static final long serialVersionUID = 1120334705430567335L;

	/**
	 * Creates a new instance of <code>InvalidWorldGenOptionsException</code> without detail message.
	 */
	public InvalidWorldGenOptionsException()
	{	super("Unknown options specified to world gen");
	}

	/**
	 * Constructs an instance of <code>InvalidWorldGenOptionsException</code> with the specified detail message.
	 * @param msg the detail message.
	 */
	public InvalidWorldGenOptionsException(String msg)
	{
		super(msg);
	}
}
