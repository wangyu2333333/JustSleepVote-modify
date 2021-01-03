package com.justAm0dd3r.jsv.event;

import com.justAm0dd3r.jsv.JustSleepVote;
import com.justAm0dd3r.jsv.config.Config;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
public class EventHandler {
    // Logger
    private static final Logger LOGGER = LogManager.getLogger(JustSleepVote.MODID);

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent evt) {
        EntityPlayer player = evt.getEntityPlayer();
        World world = player.getEntityWorld();

        // Client/Server check
        // -> if on client, the mod will just do nothing
        if (world.isRemote) return;

        int sleepingPlayers = 1;
        sleepingPlayers += world.playerEntities.stream().filter(EntityPlayer::isPlayerSleeping).count();
        int playerCount = world.countEntities(EntityPlayer.class);
        float percentage = Config.percentage;
        int requiredSleepingCount = Math.round(playerCount*percentage);
        if (!player.getEntityWorld().isDaytime()) {
            final TextComponentTranslation message = new TextComponentTranslation(player.getName() + " wants to sleep. (" + sleepingPlayers + "/" + requiredSleepingCount + ")");
            LOGGER.info(message);
            world.playerEntities.forEach((player1)  -> player1.sendStatusMessage(message, true));
            if (sleepingPlayers >= requiredSleepingCount) {
                LOGGER.info("Skipping night.");
                TextComponentTranslation messageSkipped = new TextComponentTranslation
                        ("Skipping night! (" + sleepingPlayers + "/" + requiredSleepingCount + ")");
                world.playerEntities.forEach((player1)  -> player1.sendStatusMessage(messageSkipped, true));
                world.setWorldTime(0);
            }
        }
    }

}
