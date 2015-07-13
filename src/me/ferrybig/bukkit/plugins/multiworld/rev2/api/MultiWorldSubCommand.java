/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.api;

import java.util.HashSet;
import java.util.Set;
import me.ferrybig.bukkit.plugins.multiworld.rev2.api.command.CommandStack;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.Native;

/**
 *
 * @author Fernando
 */
public abstract class MultiWorldSubCommand {

    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    private final String perm;
    private final String description;
    private final Native natives;
    public final static String RESET = new String(new char[]{
        org.bukkit.ChatColor.COLOR_CHAR, 'z'
    });

    /**
     * The 1 arg contructor
     * <p>
     * @param perm The permission that this command needs
     * @param description
     */
    public MultiWorldSubCommand(String perm, String description, Native natives) {
        this.perm = perm;
        this.description = description;
        this.natives = natives;
    }

    public void excute(CommandStack stack) {
        if (this.getPermissions() != null) {
            if (!stack.hasPermission(perm)) {
                return;
            }
        }
        this.runCommand(stack);
    }

    public String getPermissions() {
        return this.perm;
    }

    public String[] calculateMissingArguments(CommandStack sender, String commandName, String[] split) {
        return EMPTY_STRING_ARRAY;
    }

    //TODO: edit this
    protected final String[] calculateMissingArgumentsPlayer(String playerName, CommandStack executer) {
        //Collection<? extends Player> players = natives.getOnlinePlayers();

        Set<String> found = new HashSet<>(0);
        String lowerName = playerName.toLowerCase();
//        for (Player player : players) {
//            if (executer != null) {
//                if (!executer.canSee(player)) {
//                    continue;
//                }
//            }
//            if (player.getName().toLowerCase().startsWith(lowerName)) {
//                found.add(player.getName());
//            }
//        }
        return found.toArray(new String[found.size()]);
    }

    protected final String[] calculateMissingArgumentsBoolean(String bool) {
        if (bool.startsWith("t")) {
            return new String[]{
                "true"
            };
        }
        if (bool.startsWith("f")) {
            return new String[]{
                "false"
            };
        }
        if (bool.isEmpty()) {
            return new String[]{
                "false", "true"
            };
        }
        return EMPTY_STRING_ARRAY;
    }
    protected static final int MAX_COORD = 30000000;
    protected static final int MIN_COORD_MINUS_ONE = -30000001;
    protected static final int MIN_COORD = -30000000;

    protected static double getCoordinate(double current, String input) {
        return getCoordinate(current, input, MIN_COORD, MAX_COORD);
    }

    protected static double getCoordinate(double current, String input, int min, int max) {
        boolean relative = input.startsWith("~");
        double result = relative ? current : 0;

        if (!relative || input.length() > 1) {
            boolean exact = input.contains(".");
            if (relative) {
                input = input.substring(1);
            }
            double testResult = getDouble(input);
            if (testResult == MIN_COORD_MINUS_ONE) {
                return MIN_COORD_MINUS_ONE;
            }
            result += testResult;

            if (!exact && !relative) {
                result += 0.5f;
            }
        }
        if (min != 0 || max != 0) {
            if (result < min) {
                result = MIN_COORD_MINUS_ONE;
            }

            if (result > max) {
                result = MIN_COORD_MINUS_ONE;
            }
        }

        return result;
    }

    protected static double getRelativeDouble(double original, String input) {
        if (input.startsWith("~")) {
            double value = getDouble(input.substring(1));
            if (value == MIN_COORD_MINUS_ONE) {
                return MIN_COORD_MINUS_ONE;
            }
            return original + value;
        } else {
            return getDouble(input);
        }
    }

    protected static double getDouble(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException ex) {
            return MIN_COORD_MINUS_ONE;
        }
    }

    protected static double getDouble(String input, double min, double max) {
        double result = getDouble(input);

        // TODO: This should throw an exception instead.
        if (result < min) {
            result = min;
        } else if (result > max) {
            result = max;
        }

        return result;
    }

    protected static String createString(String[] args, int start) {
        return createString(args, start, " ");
    }

    protected static String createString(String[] args, int start, String glue) {
        StringBuilder string = new StringBuilder();
        for (int x = start; x < args.length; x++) {
            string.append(args[x]);
            if (x != args.length - 1) {
                string.append(glue);
            }
        }
        return string.toString();
    }

    public abstract void runCommand(CommandStack stack);
}
