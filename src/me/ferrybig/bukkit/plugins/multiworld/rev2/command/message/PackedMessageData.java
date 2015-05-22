/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.command.message;

/**
 *
 * @author ferrybig
 */
public interface PackedMessageData
{
public static final PackedMessageData NO_CONSOLE_MESSAGE = new PackedMessageData()
{

	@Override
	public String getBinary()
	{
		return "No-Console";
	}

	@Override
	public String transformMessage(String prevFormat)
	{
		return prevFormat;
	}
};
	/**
	 *
	 * @param prevFormat the value of prevFormat
	 * @return
	 */
	public abstract String transformMessage(String prevFormat);

	public abstract String getBinary();

}
