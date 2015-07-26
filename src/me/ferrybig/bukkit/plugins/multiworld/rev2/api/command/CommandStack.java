/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.api.command;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.entities.NativeCommandSender;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeLocation;


/**
 *
 * @author Fernando
 */
public interface CommandStack extends MessageLogger
{
	public String getCommandLabel();

	public NativeCommandSender getSender();

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
	public NativeLocation getLocation();

	public Builder newStack();

	public Builder editStack();

	public interface Builder
	{
		public Builder setSender(NativeCommandSender sender);

		public Builder setLocation(NativeLocation loc);

		public Builder popArguments(int number);

		public Builder setArguments(String[] args);

		public Builder setCommandLabel(String commandLabel);

		public Builder setMessageLogger(MessageLogger logger);

		public CommandStack build();

	}
}
