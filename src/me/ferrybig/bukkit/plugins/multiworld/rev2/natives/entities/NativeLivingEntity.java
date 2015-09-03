/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.entities;

/**
 *
 * @author Fernando
 */
public interface NativeLivingEntity extends NativeEntity {

    public float getHealth();

    public float getMaxHealth();

    public void setHealth(float health);

    public void setMaxHealth(float health);
}
