package no.kh498.util.command;

import no.kh498.util.ItemListFormat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;

public abstract class SubCommand implements CommandExecutor {

    private SubCommand parent;

    public SubCommand(SubCommand parent) {
        this.parent = parent;
    }

    public SubCommand() {
        this(null);
    }

    /**
     * @return Highest level sub commands (eg 'load' if the command is '/cmd load all') or null if there are no
     * sub commands
     */
    public abstract Map<String, SubCommand> getSubCommands();

    public SubCommand getParent() {
        return parent;
    }

    /**
     * @param sender
     *     the sender of the command
     * @param command
     *     base command
     * @param label
     *     alias of the command used
     * @param args
     *     args from the parent command
     *
     * @return if the command was handled
     */
    public boolean onSubCommand(CommandSender sender, Command command, String label, String[] args) {
//        Preconditions.checkArgument(args.length > 0, "there must be a sub command");

        if (getSubCommands() == null) {
            return false;
        }

        if (args.length == 0 || !getSubCommands().containsKey(args[0])) {
            sender.sendMessage(ItemListFormat
                                   .colorString(ChatColor.RED, ChatColor.DARK_RED, "Invalid sub-command, valid are ",
                                                getSubCommands().keySet().toArray()));
            return true;
        }

        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, args.length - 1);
        return getSubCommands().get(args[0]).onCommand(sender, command, label, subArgs);
    }
}
