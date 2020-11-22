/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.command.config;

import nl.ferrybig.multiworld.command.Command;
import nl.ferrybig.multiworld.command.CommandStack;
import nl.ferrybig.multiworld.command.MessageType;
import nl.ferrybig.multiworld.data.DataHandler;
import nl.ferrybig.multiworld.data.ReloadHandler;
import nl.ferrybig.multiworld.translation.Translation;

/**
 * @author Fernando
 */
public class LoadCommand extends Command {

  private final ReloadHandler r;
  private final DataHandler d;

  public LoadCommand(DataHandler d, ReloadHandler r) {
    super("load", "Reloads the nl.ferrybig.multiworld configuration file");
    this.d = d;
    this.r = r;
  }

  @Override
  public void runCommand(CommandStack stack) {
    if (this.reloadCommand()) {
      stack.sendMessage(MessageType.SUCCES, Translation.COMMAND_RELOAD_SUCCES);
    } else {
      stack.sendMessage(MessageType.ERROR, Translation.COMMAND_RELOAD_FAIL);
    }
  }

  private boolean reloadCommand() {
    return r.reload();
  }
}
