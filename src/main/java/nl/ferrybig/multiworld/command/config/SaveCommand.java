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
public class SaveCommand extends Command {

  private final ReloadHandler r;
  private final DataHandler d;

  public SaveCommand(DataHandler data, ReloadHandler reload) {
    super("save", "Saves data from nl.ferrybig.multiworld to the disk");
    this.d = data;
    this.r = reload;
  }

  @Override
  public void runCommand(CommandStack stack) {
    if (this.saveCommand()) {
      stack.sendMessageBroadcast(MessageType.SUCCES, Translation.COMMAND_SAVE_SUCCES);
    } else {
      stack.sendMessageBroadcast(MessageType.ERROR, Translation.COMMAND_SAVE_FAIL);
    }
  }

  private boolean saveCommand() {
    return this.r.save();
  }
}
