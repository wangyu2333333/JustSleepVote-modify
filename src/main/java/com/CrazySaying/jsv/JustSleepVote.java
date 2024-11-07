package com.CrazySaying.jsv;

import com.CrazySaying.jsv.commands.ChangePercentageCommand;
import com.CrazySaying.jsv.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.File;

@Mod(modid = JustSleepVote.MODID,
        name = JustSleepVote.NAME,
        version = JustSleepVote.VERSION,
        acceptableRemoteVersions = "*"
)
public class JustSleepVote
{
    public static final String NAME = "JustSleepVote";
    public static final String VERSION = "1.4";
    public static final String MODID = "jsv";

    // Config instance
    public static Configuration config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        File directory = evt.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "jsv.cfg"));
        Config.readConfig();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent evt) {
        if (config.hasChanged()) {
            config.save();
        }
    }

    @Mod.EventHandler
    public static void onServerStart(FMLServerStartingEvent evt) {
        evt.registerServerCommand(new ChangePercentageCommand());
    }
}
