/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld.translation.message;

/**
 *
 * @author ferrybig
 */
public interface PackedMessageData
{

	/**
	 *
	 * @param prevFormat the value of prevFormat
	 * @return
	 */
	public abstract String transformMessage(String prevFormat);

	public abstract String getBinary();

}
