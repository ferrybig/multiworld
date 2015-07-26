/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.schedular;

/**
 *
 * @author Fernando
 */
public interface NativeTaskManager {
    public void scheduleTask(NativeTask task);

    public static interface NativeTaskDefinition {

        public void cancel();
    }

    public static interface NativeTask {

        public void run(NativeTaskDefinition def);
    }
}
