/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.bukkit.plugins.multiworld.rev2.natives.schedular;

import java.util.Collection;

/**
 *
 * @author Fernando
 */
public interface NativeTaskManager {

    public NativeTaskDefinition scheduleTask(NativeTask task);

    public NativeTaskDefinition scheduleAsyncTask(NativeTask task);

    public NativeTaskDefinition scheduleTaskRepeating(NativeTask task, int repeat);

    public NativeTaskDefinition scheduleAsyncTaskRepeating(NativeTask task, int repeat);

    public NativeTaskDefinition scheduleTaskRepeatingDelay(NativeTask task, int repeat, int delay);

    public NativeTaskDefinition scheduleAsyncTaskRepeatingDelay(NativeTask task, int repeat, int delay);

    public NativeTaskDefinition scheduleTaskDelay(NativeTask task, int delay);

    public NativeTaskDefinition scheduleAsyncTaskDelay(NativeTask task, int delay);
    
    public Collection<? extends NativeTaskDefinition> getTasks();

    public static interface NativeTaskDefinition {

        public boolean isAsyn();
        
        public boolean isRunning();
        
        public NativeTask getTask();

        public int getInterval();

        public int getInitialDelay();

        public boolean isRepeating();

        public void cancel();
    }

    public static interface NativeTask {

        public void run(NativeTaskDefinition def);
    }
}
