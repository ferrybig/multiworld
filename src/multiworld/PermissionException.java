/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiworld;

/**
 *
 * @author Fernando
 */
public class PermissionException extends CommandException {
	private static final long serialVersionUID = 498329828736746745L;

	public PermissionException() {
		super("You dont have permissions");
	}

}
