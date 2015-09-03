/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.materials;

import java.util.Set;

/**
 *
 * @author Fernando
 */
public interface NativeMaterial {

    /**
     * Get the id of this block as it appears on disk when the world is saved
     *
     * @return
     */
    public short id();

    /**
     * Gets the vanila name of this block
     *
     * @return the name
     */
    public String name();

    /**
     * Gets the underlying object that represents this material. This may be
     * null if the underlying api doesn't use sutch object. This may be an array
     * if the underlying api uses multiple objects.
     *
     * Note: the return result of this method is undefined in the api, it may
     * always return null, but never throws an exception
     *
     * @return the underlying object(s)
     */
    public Object getUnderlying();

    /**
     * Get the highest damage number allowed for this item
     *
     * @return
     */
    public short getMaxDamage();

    /**
     * Can this be placed in the world in a block form?
     *
     * @return true if its a block
     */
    public boolean isBlock();

    /**
     * Is this a full block?
     *
     * Note: may be undefined for items
     *
     * @return true if its a full block
     */
    public boolean isFullBlock();

    /**
     * Gets the ligth reduction for this block.
     *
     * Note: may be undefined for items
     *
     * @return
     */
    public byte getLightReduction();

    /**
     * Is this block breakable by liquids?
     *
     * Note: may be undefined for items
     *
     * @return true if its breakable by a liquid
     */
    public boolean isBreakableByLiquid();

    /**
     * What is the friction of this block?
     *
     * Note: may be undefined for items
     *
     * @return the friction
     */
    public double getFriction();

    /**
     * What is the hardness of this block?
     *
     * Note: may be undefined for items
     *
     * @return the hardness
     */
    public double getHardness();

    /**
     * What are the item categories of this block/item?
     * returns all item types that are on this item.
     *
     * @return the item types
     */
    public Set<ItemType> getItemTypes();
}
