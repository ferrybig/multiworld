/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.command.spawn;

import nl.ferrybig.multiworld.command.Command;
import nl.ferrybig.multiworld.command.CommandStack;
import nl.ferrybig.multiworld.command.MessageType;
import nl.ferrybig.multiworld.data.DataHandler;
import nl.ferrybig.multiworld.data.PlayerHandler;
import nl.ferrybig.multiworld.data.WorldHandler;
import nl.ferrybig.multiworld.translation.Translation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Fernando
 */
public class SpawnCommand extends Command {

  private final DataHandler d;
  private final PlayerHandler p;
  private final WorldHandler w;

  public SpawnCommand(DataHandler data, WorldHandler worlds, PlayerHandler player) {
    super("spawn", "Teleports yourself to spawn");
    this.d = data;
    this.w = worlds;
    this.p = player;
  }

  @Override
  public void runCommand(CommandStack stack) {
    CommandSender sender = stack.getSender();
    if (sender instanceof Player) {
      Player player = (Player) sender;
      player.teleport(player.getWorld().getSpawnLocation());
      stack.sendMessage(MessageType.SUCCES, Translation.COMMAND_SPAWN_SUCCES);
    } else {
      stack.sendMessage(MessageType.ERROR, Translation.COMMAND_SPAWN_FAIL_CONSOLE);
    }
  }
}