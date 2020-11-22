/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld;

/**
 *
 * @author Fernando
 */
public class InvalidWorldNameException extends UnknownWorldException
{
	private static final long serialVersionUID = 1L;
	public InvalidWorldNameException(String world)
	{
		super(world+ ", Invalid world syntacs");
	}
}
