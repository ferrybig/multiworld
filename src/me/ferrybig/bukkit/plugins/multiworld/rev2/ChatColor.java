/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2;

/**
 *
 * @author Fernando
 */
public enum ChatColor {

    RESET("\u00A7r"),
    MAGIC("\u00A7k"),
    BOLD("\u00A7l"),
    STRIKETHROUGH("\u00A7m"),
    UNDERLINE("\u00A7n"),
    ITALIC("\u00A7o"),
    BLACK("\u00A70"),
    DARK_BLUE("\u00A71"),
    DARK_GREEN("\u00A72"),
    DARK_AQUA("\u00A73"),
    DARK_RED("\u00A74"),
    DARK_PURPLE("\u00A75"),
    GOLD("\u00A76"),
    GRAY("\u00A77"),
    DARK_GRAY("\u00A78"),
    BLUE("\u00A79"),
    GREEN("\u00A7a"),
    AQUA("\u00A7b"),
    RED("\u00A7c"),
    LIGHT_PURPLE("\u00A7d"),
    YELLOW("\u00A7e"),
    WHITE("\u00A7f");

    private final String data;

    /**
     * Creates a new element of ChatColor
     *
     * @param data the color code of the new element
     */
    private ChatColor(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data;
    }

    public static String getLastColors(String input) {
        int index = input.lastIndexOf('\u00A7');
        if(index > 0) {
            return '\u00A7' + input.substring(index + 1, 1);
        }
        return ChatColor.RESET.toString();
    }
}
