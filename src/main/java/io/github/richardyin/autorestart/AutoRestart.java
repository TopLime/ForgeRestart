package io.github.richardyin.autorestart;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = AutoRestart.MODID, version = AutoRestart.VERSION)
public class AutoRestart {
    public static final String MODID = "ForgeRestart";
    public static final String VERSION = "0.1";
    
    private long delay = 0;
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	//TODO Calculate delay based on config file input
    	
    	//Schedule event
        Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
			@Override
			public void run() {
				//something here
			}
        }, delay, TimeUnit.SECONDS);
    }
}
