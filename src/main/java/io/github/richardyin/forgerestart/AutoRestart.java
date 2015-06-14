package io.github.richardyin.forgerestart;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.transformers.ForgeAccessTransformer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = AutoRestart.MODID, version = AutoRestart.VERSION)
public class AutoRestart {
    public static final String MODID = "ForgeRestart";
    public static final String VERSION = "0.1";
    
    private long delay = 0;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	//Calculate delay based on config file input
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	config.load();
    	int hrs = config.getInt("Hours", "Time", -1, -1, 23, "Hour of the day; 0-23; -1 to disable");
    	int mins = config.getInt("Minutes", "Time", 0, 0, 59, "Minute of the day; 0-59");
    	Calendar stopTime = Calendar.getInstance();
    	stopTime.set(Calendar.MINUTE, mins);
    	stopTime.set(Calendar.HOUR, hrs);
    	Calendar nowTime = Calendar.getInstance();
    	if(nowTime.getTimeInMillis() > stopTime.getTimeInMillis())
    		stopTime.add(Calendar.DATE, 1); //stop tomorrow if past stop time
    	delay = stopTime.getTimeInMillis() - nowTime.getTimeInMillis();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	//Schedule event
        Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
			@Override
			public void run() {
				MinecraftServer.getServer().stopServer();
			}
        }, delay, TimeUnit.MILLISECONDS);
    }
}
