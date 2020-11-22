/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.ferrybig.multiworld.command.world;

import nl.ferrybig.multiworld.command.Command;
import nl.ferrybig.multiworld.command.CommandStack;

/**
 *
 * @author ferrybig
 */
public class DifficultyCommand  extends Command {

	private final boolean setter;
	public DifficultyCommand(boolean setter)
	{
		super("difficulty."+(setter?"set":"get"),(setter?"Set":"Get")+ "s the difficulty of a world");
		this.setter = setter;
	}

	@Override
	public void runCommand(CommandStack stack)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
