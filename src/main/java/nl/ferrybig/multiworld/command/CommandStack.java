/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.command;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Fernando
 */
public interface CommandStack extends MessageLogger
{
	public String getCommandLabel();

	public CommandSender getSender();

	public String[] getArguments();

	/**
	 * Gets the commandStack of the parent, used for debugging purposes
	 * @return
	 */
	public CommandStack getParent();

	public boolean hasPermission(String permission);
	/**
	 * Gets the location, null if theres no location
	 * <p>
	 * @return
	 */
	public Location getLocation();

	public Builder newStack();

	public Builder editStack();

	public interface Builder
	{
		public Builder setSender(CommandSender sender);

		public Builder setLocation(Location loc);

		public Builder popArguments(int number);

		public Builder setArguments(String[] args);

		public Builder setCommandLabel(String commandLabel);

		public Builder setMessageLogger(MessageLogger logger);

		public CommandStack build();

	}
}
