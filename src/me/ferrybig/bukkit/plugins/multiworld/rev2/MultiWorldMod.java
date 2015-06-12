/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2;

import me.ferrybig.bukkit.plugins.multiworld.rev2.natives.Native;

/**
 *
 * @author Fernando
 */
public interface MultiWorldMod {
    public Native getNative();
    
    public String getVersion();
    
    public String getLastGitCommit();
    
    public boolean isBeta();
}
