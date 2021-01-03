package com.justAm0dd3r.jsv.config;

import com.justAm0dd3r.jsv.JustSleepVote;
import com.justAm0dd3r.jsv.event.EventHandler;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Config {
    // Logger
    private static final Logger LOGGER = LogManager.getLogger(JustSleepVote.MODID);

    private static final String CATEGORY_GENERAL = "general";

    // Config Values
    public static float percentage = 0.5f;

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

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
        percentage = cfg.getFloat("percentage", CATEGORY_GENERAL, percentage, 0f, 1f, "The percentage of players needing to sleep at the same time.");
    }
}