/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ferrybig.multiworld.command.move;

import nl.ferrybig.multiworld.Utils;
import nl.ferrybig.multiworld.command.ArgumentType;
import nl.ferrybig.multiworld.command.Command;
import nl.ferrybig.multiworld.command.CommandStack;
import nl.ferrybig.multiworld.command.MessageType;
import nl.ferrybig.multiworld.data.DataHandler;
import nl.ferrybig.multiworld.data.InternalWorld;
import nl.ferrybig.multiworld.data.PlayerHandler;
import nl.ferrybig.multiworld.data.WorldHandler;
import nl.ferrybig.multiworld.translation.Translation;
import nl.ferrybig.multiworld.translation.message.MessageCache;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Fernando
 */
public class GotoCommand extends Command {

  private final DataHandler h;
  private final PlayerHandler p;
  private final WorldHandler w;

  public GotoCommand(DataHandler h, PlayerHandler p, WorldHandler w) {
    super("goto", "Warps yourself to a other world");
    this.h = h;
    this.p = p;
    this.w = w;
  }

  @Override
  public void runCommand(CommandStack stack) {
    String[] args = stack.getArguments();
    if (args.length != 1 && args.length != 4) {
      stack.sendMessageUsage(stack.getCommandLabel(), ArgumentType.valueOf("move"),
          ArgumentType.TARGET_PLAYER, ArgumentType.TARGET_WORLD);
    } else {
      if (!(stack.getSender() instanceof Player)) {
        stack.sendMessage(MessageType.ERROR, Translation.NEED_PLAYER);
        return;
      }
      Player targetPlayer = (Player) stack.getSender();
      InternalWorld worldObj = w.getWorld(args[0], true);
      if (worldObj == null) {
        stack.sendMessage(MessageType.ERROR, Translation.WORLD_NOT_FOUND,
            MessageCache.WORLD.get(args[0]));
        return;
      }
      Location warpLoc = worldObj.getWorld().getSpawnLocation();
      warpLoc.setWorld(worldObj.getWorld());
      if (args.length == 4) {
        if (!Utils.canUseCommand(stack, this.getPermissions() + ".cordinates")) {
          return;
        }
        double x = getCoordinate(warpLoc.getX(), args[args.length - 3]);
        double y = getCoordinate(warpLoc.getY(), args[args.length - 2], 0, 0);
        double z = getCoordinate(warpLoc.getZ(), args[args.length - 1]);
        if (x == MIN_COORD_MINUS_ONE || y == MIN_COORD_MINUS_ONE || z == MIN_COORD_MINUS_ONE) {
          stack.sendMessage(MessageType.ERROR, Translation.INVALID_LOCATION);
          return;
        }
        warpLoc.setX(x);
        warpLoc.setY(y);
        warpLoc.setZ(z);
      }
      p.movePlayer(targetPlayer, warpLoc);
      stack.sendMessageBroadcast(MessageType.SUCCES,
          Translation.COMMAND_MOVE_MESSAGE_SUCCES,
          MessageCache.PLAYER.get(targetPlayer.getName()),
          MessageCache.WORLD.get(warpLoc.getWorld().getName()));
    }
  }

  @Override
  public String[] calculateMissingArguments(CommandSender sender, String commandName,
      String[] split) {
    if (split.length == 0) {
      return this.calculateMissingArgumentsWorld("");
    } else if (split.length == 1) {
      return this.calculateMissingArgumentsWorld(split[0]);
    } else {
      return EMPTY_STRING_ARRAY;
    }
  }
}
