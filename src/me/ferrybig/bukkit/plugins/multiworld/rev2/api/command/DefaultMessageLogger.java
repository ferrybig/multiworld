/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.api.command;

import me.ferrybig.bukkit.plugins.multiworld.rev2.api.ChatColor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import me.ferrybig.bukkit.plugins.multiworld.rev2.api.command.message.PackedMessageData;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.Native;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeCommandBlockCommandSender;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeCommandSender;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativeConsoleCommandSender;
import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.NativePermissionsHolder;

/**
 *
 * @author Fernando
 */
public class DefaultMessageLogger implements MessageLogger {

    private final DebugLevel level;
    private final String prefix;
    private final NativeCommandSender reciever;
    public static final String DEFAULT_PREFIX = ChatColor.GOLD + "[" + ChatColor.GREEN + "MultiWorld" + ChatColor.GOLD + "] " + ChatColor.WHITE;
    private final String errorPrefix;
    private final String succesPrefix;
    private final Native nativez;

    public DefaultMessageLogger(DebugLevel level, NativeCommandSender reciever, String prefix, Native nativez) {
        this(level, reciever, prefix, ChatColor.RED.toString(), ChatColor.GREEN.toString(), nativez);

    }

    public DefaultMessageLogger(DebugLevel level, NativeCommandSender reciever, String prefix, String errorPrefix, String succesPrefix, Native nativez) {
        this.level = level;
        this.reciever = reciever;
        this.prefix = prefix;
        this.errorPrefix = errorPrefix;
        this.succesPrefix = succesPrefix;
        this.nativez = nativez;
    }

    @Override
    public DebugLevel getDebugLevel() {
        return this.level;
    }

    @Override
    public void sendMessage(MessageType type, String message) {
        StringBuilder builder = new StringBuilder();
        if (type != null) {
            switch (type) {
                case SUCCES:
                    builder.append(succesPrefix);
                    break;
                case ERROR:
                    builder.append(errorPrefix);
                    break;
            }
        }
        builder.append(message.replace(ChatColor.RESET.toString(), ChatColor.getLastColors(prefix)));
        sendMessage(reciever, builder.toString(), prefix, true);
    }

    @Override
    public void sendMessage(MessageType type, PackedMessageData... message) {
        this.sendMessage(type, this.transformMessage(message));
    }

    @Override
    public void sendMessageBroadcast(MessageType type, String message) {
        sendMessageBroadcast(type, message, true);
    }

    public void sendMessageBroadcast(MessageType type, String message, boolean sendToConsole) {
        message = message.replace(ChatColor.RESET.toString(), ChatColor.getLastColors(prefix));
        String result = prefix + reciever.getName() + ": " + message;
        if (reciever instanceof NativeCommandBlockCommandSender && ((NativeCommandBlockCommandSender) reciever).getLocation().getWorld().getGameRuleValue("commandBlockOutput").equalsIgnoreCase("false")) {
            reciever.getNative().getConsoleCommandSender().sendMessage(result);
            return;
        }
        Collection<? extends NativePermissionsHolder> users = nativez.getOps();
        String colored = prefix + ChatColor.GRAY + ChatColor.ITALIC + "[" + reciever.getName() + ": " + ChatColor.getLastColors(prefix) + message + ChatColor.GRAY + ChatColor.ITALIC + "]";
        if (!(reciever instanceof NativeConsoleCommandSender)) {
            reciever.sendMessage(prefix + message);
        }
        for (NativePermissionsHolder user : users) {
            if (user instanceof NativeCommandSender) {
                NativeCommandSender target = (NativeCommandSender) user;
                if (target instanceof NativeConsoleCommandSender) {
                    if (!sendToConsole && target.equals(reciever.getNative().getConsoleCommandSender())) {
                        continue;

                    }
                    target.sendMessage(result);
                } else if (target != reciever) {
                    target.sendMessage(colored);
                }
            }
        }
    }

    @Override
    public void sendMessageBroadcast(MessageType type, PackedMessageData... message) {
        this.sendMessageBroadcast(type, this.transformMessage(message), !Arrays.asList(message).contains(PackedMessageData.NO_CONSOLE_MESSAGE));
    }

    @Override
    public void sendMessageDebug(String message, DebugLevel level) {
        if (this.getDebugLevel().getLevel() <= level.getLevel()) {
            // Debugger can log this message
            this.sendMessage((MessageType)null, "[" + level.name() + "] " + message);
        }
    }

    @Override
    public void sendMessageUsage(String commandLabel, ArgumentType... types) {
        StringBuilder build = new StringBuilder();
        build.append("Command Usage: /").append(commandLabel);
        for (ArgumentType type : types) {
            build.append(' ').append(type.getMessage());
        }
        this.sendMessage(MessageType.ERROR, build.toString());
    }

    private String transformMessage(PackedMessageData[] options) {
        String process = "";
        if (options.length != 0) {
            for (PackedMessageData option : options) {
                process = option.transformMessage(process);
            }
        }
        return process;
    }

    private static void sendMessage(NativeCommandSender s, String msg) {

        sendMessage(s, msg, 5);
    }

    /**
     * Sends a command sender a message in a friendly way
     * <p>
     * @param s the commandsender to send to
     * @param msg the message
     * @param spaces The amount of spaces before the message if it doesn't fit
     */
    private static void sendMessage(NativeCommandSender s, String msg, int spaces) {
        char[] spaceChars = new char[spaces];
        for (int i = 0; i < spaceChars.length; i++) {
            spaceChars[i] = ' ';
        }
        String spaceString = new String(spaceChars);
        sendMessage(s, msg, spaceString, false);
    }

    /**
     *
     * @param s the value of s
     * @param msg the value of msg
     * @param prefix the value of prefix
     * @param addPrefixToFirstOutput the value of addPrefixToFirstOutput
     */
    private static void sendMessage(NativeCommandSender s, String msg, String prefix, boolean addPrefixToFirstOutput) {
        if (msg.contains("\n")) {
            for (String str : msg.split("\n")) {
                sendMessage0(s, str, prefix, addPrefixToFirstOutput);
                // addded another method to make this call less expensive since .contains on a long string takes long
            }
            return;
        }
        sendMessage0(s, msg, prefix, addPrefixToFirstOutput);
    }

    private static void sendMessage0(NativeCommandSender s, String msg, String prefix, boolean addPrefixToFirstOutput) {
        if (s instanceof NativeConsoleCommandSender) {
            if (addPrefixToFirstOutput) {
                s.sendMessage(prefix + msg);
            } else {
                s.sendMessage(msg);
            }
            return;
        }
        final int prefixSubstract = countOccurrences(prefix, org.bukkit.ChatColor.COLOR_CHAR) * 2;
        final int prefixLength = prefix.length() - prefixSubstract;
        final int maxLineLenght = 60;
        if ((msg.length() + (addPrefixToFirstOutput ? prefixLength : 0)) > maxLineLenght) {
            char color;
            {
                final int lastIndexOf = prefix.lastIndexOf(org.bukkit.ChatColor.COLOR_CHAR);
                if (lastIndexOf != -1) {
                    color = prefix.charAt(lastIndexOf + 1);
                } else {
                    color = 'f';
                }
            }
            int charsLeft = 60;
            String[] parts = msg.split(" ");
            StringBuilder b = new StringBuilder(maxLineLenght);
            if (addPrefixToFirstOutput) {
                b.append(prefix);
                charsLeft -= prefixLength;
            }
            for (String i : parts) {
                if (i.lastIndexOf(0x00A7) != -1) {
                    assert i.lastIndexOf(0x00A7) + 1 < i.length();
                    color = i.charAt(i.lastIndexOf(0x00A7) + 1);
                }
                if ((charsLeft - i.length()) < 1) {
                    s.sendMessage(b.toString());
                    charsLeft = maxLineLenght - prefixLength;
                    b.setLength(0);
                    b = new StringBuilder(maxLineLenght);
                    b.append(prefix);
                    b.append('\u00A7').append(color);
                }
                charsLeft -= i.length() + 1;
                charsLeft += countOccurrences(i, org.bukkit.ChatColor.COLOR_CHAR) * 2;
                b.append(i).append(" ");
            }
            if (b.length() != 0) {
                s.sendMessage(b.toString());
            }
        } else {
            s.sendMessage(addPrefixToFirstOutput ? prefix + msg : msg);
        }
    }

    private static int countOccurrences(String haystack, char needle) {
        int count = 0;
        char[] contents = haystack.toCharArray();
        for (int i = 0; i < contents.length; i++) {
            if (contents[i] == needle) {
                count++;
            }
        }
        return count;
    }

}
