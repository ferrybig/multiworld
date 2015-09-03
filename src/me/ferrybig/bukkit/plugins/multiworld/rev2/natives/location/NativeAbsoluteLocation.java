/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.location;

/**
 *
 * @author Fernando
 */
public interface NativeAbsoluteLocation extends NativeBlockLocation, NativeAbsoluteLocationReadonly {

    public void setX(double x);

    public void setY(double y);

    public void setZ(double z);
}
