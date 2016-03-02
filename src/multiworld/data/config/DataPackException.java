/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multiworld.data.config;

import multiworld.MultiWorldException;

/**
 *
 * @author ferrybig
 */
public class DataPackException extends MultiWorldException {
	private static final long serialVersionUID = 1L;

	public DataPackException(String msg)
	{
		super(msg);
	}

	public DataPackException(String msg, Throwable cause)
	{
		super(msg, cause);
	}

}
