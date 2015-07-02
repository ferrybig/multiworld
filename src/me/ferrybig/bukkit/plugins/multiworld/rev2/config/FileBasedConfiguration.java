/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.config;

import java.util.Set;

/**
 *
 * @author Fernando
 */
public interface FileBasedConfiguration {
    public Object get(String path);
    
    public void set(String path, Object opject);
    
    public String getString(String path);
    
    public int getInt(String path);
    
    public FileBasedConfiguration getSection(String path);
    
    public Set<String> getKeys(String path);
}
