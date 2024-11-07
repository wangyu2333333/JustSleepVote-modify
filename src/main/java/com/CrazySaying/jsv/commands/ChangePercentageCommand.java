package com.CrazySaying.jsv.commands;

import com.google.common.collect.Lists;
import com.CrazySaying.jsv.JustSleepVote;
import com.CrazySaying.jsv.config.Config;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.util.List;

public class ChangePercentageCommand extends CommandBase {
    private final List<String> aliases = Lists.newArrayList("jsvp", "justsleepvotepercentage");

    @Nonnull
    @Override
    public String getName() {
        return "jsvpercentage";
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return "jsvpercentage <percentage>";
    }

    @Nonnull
    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
        if (args.length < 1) {
            sender.sendMessage(new TextComponentString(TextFormatting.BLUE + "Usage: " + TextFormatting.RESET + "jsvpercentage " + TextFormatting.ITALIC + "<percentage>"));
            return;
        }

        String arg = args[0];
        float percentage;
        try {
            percentage = Float.parseFloat(arg);
        }
        catch (NumberFormatException e) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Invalid percentage."));
            return;
        }
        if (percentage < 0.0f || percentage > 1.0f) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Invalid percentage (must be between 0.0 and 1.0)."));
            return;
        }
        if (Config.percentage == percentage) {
            sender.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Percentage is already set to " + TextFormatting.RESET + percentage + TextFormatting.GREEN + "."));
            return;
        }
        Config.percentage = percentage;
        Config.updateConfigFile();
        sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Set percentage to " + TextFormatting.RESET + percentage + TextFormatting.GREEN + "."));
    }
}
