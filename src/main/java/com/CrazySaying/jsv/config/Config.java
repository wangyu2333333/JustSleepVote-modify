package com.CrazySaying.jsv.config;

import com.CrazySaying.jsv.JustSleepVote;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class Config {
    // Logger
    private static final Logger LOGGER = LogManager.getLogger(JustSleepVote.MODID);

    private static final String CATEGORY_GENERAL = "general";
    private static final String PERCENTAGE_KEY = "percentage";

    // Config Values
    public static float percentage = 0.5f;
    public static Property percentageProperty;

    public static void readConfig() {
        Configuration cfg = JustSleepVote.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
        } catch (Exception e1) {
            LOGGER.error("Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    // Update the config file when the value was changed ingame by a command.
    public static void updateConfigFile() {
        percentageProperty.set(percentage);
        JustSleepVote.config.save();
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
        percentage = cfg.getFloat(PERCENTAGE_KEY, CATEGORY_GENERAL, percentage, 0f, 1f, "The percentage of players needing to sleep at the same time.");
        percentageProperty = cfg.get(CATEGORY_GENERAL, PERCENTAGE_KEY, percentage, "The percentage of players needing to sleep at the same time.");
    }
}