package nl.ferrybig.multiworld;

import java.beans.ConstructorProperties;

/**
 * thrown when it cant find a world
 * @author Fernando
 */
public class UnknownWorldException extends CommandException {
	private static final long serialVersionUID = 2203948382489392L;

	/**
	 * The wrong world string
	 */
	private final String world;

	/**
	 * makes the exception
	 */
	public UnknownWorldException() {
		super("Cannot find the world specified");
		this.world = "";
	}

	/**
	 * makes the exception
	 * @param world The wrong world string
	 */
	@ConstructorProperties("wrongWorld")
	public UnknownWorldException(String world) {
		super("cannot find world: " + world);
		this.world = world;
	}

	/**
	 * Gets the wrong world string
	 * @return The wrong world string
	 */
	public String getWrongWorld() {
		return world;
	}
}