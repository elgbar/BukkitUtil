package no.kh498.util.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * A command that hosts other commands, it does nothing on it's own and will call
 * {@link #onSubCommand(CommandSender, Command, String, String[])} when
 * {@link #onCommand(CommandSender, Command, String, String[])} is called
 */
public abstract class HostCommand extends SubCommand {

    public HostCommand(SubCommand parent) {
        super(parent);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return onSubCommand(sender, command, label, args);
    }
}
