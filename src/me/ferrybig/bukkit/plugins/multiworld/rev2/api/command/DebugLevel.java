/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.api.command;

/**
 *
 * @author Fernando
 */
public enum DebugLevel
{
	VVVVVV(-1),
	VVVVV(0),
	VVVV(1),
	VVV(2),
	VV(3),
	V(4),
	NONE(Integer.MAX_VALUE);
	private final int level;
	DebugLevel(int level)
	{
		this.level = level;
	}

	public int getLevel()
	{
		return level;
	}
}
