/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.command;

import me.ferrybig.bukkit.plugins.multiworld.rev2.command.message.PackedMessageData;

/**
 *
 * @author Fernando
 */
public interface MessageLogger
{

	/**
	 * Gets the debug level
	 * <p>
	 * @return
	 */
	public DebugLevel getDebugLevel();

	/**
	 * Sends a normal message to an user
	 * <p>
	 * @param type the type of message to pass to the higher layer, may be null
	 * @param message the message to send
	 * @param options
	 */
	public void sendMessage(MessageType type, String message);
	public void sendMessage(MessageType type, PackedMessageData ... message);

	/**
	 * Sends the usage of the command for better processing inside other classes
	 * <p>
	 * @param commandLabel
	 * @param types
	 */
	public void sendMessageUsage(String commandLabel, ArgumentType... types);

	/**
	 * Sends a broadcast message to everone including the user user
	 * <p>
	 * @param type the type of message to pass to the higher layer, may be null
	 * @param message the message to send
	 * @param options
	 */
	public void sendMessageBroadcast(MessageType type, String message);
	public void sendMessageBroadcast(MessageType type, PackedMessageData ... message);

	public void sendMessageDebug(String message, DebugLevel level);
}
