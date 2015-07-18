/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives;

import java.net.URL;
import java.util.Collection;

/**
 *
 * @author Fernando
 */
public interface NativePluginProperties {

    public Collection<String> getCreators();

    public URL getOrigin();

    public String getPluginName();

    public String getVersion();

}
