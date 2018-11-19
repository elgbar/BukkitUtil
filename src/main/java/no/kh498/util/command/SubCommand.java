package no.kh498.util.command;

import com.google.common.base.Preconditions;
import no.kh498.util.ChatUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public abstract class SubCommand implements CommandExecutor {

    private static final Logger logger = LoggerFactory.getLogger(SubCommand.class);

    private SubCommand parent;

    public SubCommand(SubCommand parent) {

        Preconditions.checkState(getAliases() != null && getAliases().size() > 0, "There must be at least one alias");
        Preconditions
            .checkState(getAliases().stream().noneMatch(Objects::isNull), "no elements in the aliases can be null");
        Preconditions.checkState(
            getAliases().stream().allMatch(alias -> getAliases().stream().filter(alias::equalsIgnoreCase).count() == 1),
            "There cannot be any duplicate aliases within an alias");

        this.parent = parent;
    }

    /**
     * The first element must be defined in the plugin's {@code plugin.yml}
     * <p>
     * There must be at least one element in the alias.
     * <p>
     * None of the elements are {@code null}
     *
     * @return A collection of strings this subcommand is known for
     */
    public abstract List<String> getAliases();

    void verifySubcommands() {

        logger.trace("Verifying subcommand '{}' (parent '{}')", getCommand(), parent);

        // the conditions below are only relevant if the command has subcommands
        if (getSubCommands() == null) {
            logger.trace("There are no subcommands");
            return;
        }

        if (getSubCommands().stream().anyMatch(Objects::isNull)) {
            throw new IllegalStateException("The sub command list must either be null or not contain any nulls");
        }

        for (SubCommand subCommand1 : getSubCommands()) {
            for (SubCommand subCommand2 : getSubCommands()) {
                if (subCommand1 != subCommand2 &&
                    subCommand1.getAliases().stream().anyMatch(s -> subCommand2.getAliases().contains(s))) {
                    throw new IllegalStateException(String.format(
                        "Subcommand '%s' and '%s' in command '%s' has the same alias", subCommand1.getCommand(),
                        subCommand2.getCommand(), getCommand()));
                }
            }

            //verify recursively
            subCommand1.verifySubcommands();
        }
    }

    public String getCommand() {
        return getAliases().get(0);
    }

    /**
     * @return A subcommand of this subcommand from one of it's aliases
     */
    public SubCommand getSubFromAlias(String alias) {
        if (alias == null || getSubCommands() == null) {
            return null;
        }
        for (SubCommand subCommand : getSubCommands()) {
            for (String subAlias : subCommand.getAliases()) {
                if (subAlias.equalsIgnoreCase(alias)) {
                    return subCommand;
                }
            }

        }
        return null;
    }

    /**
     * None of the elements are {@code null}
     *
     * @return Highest level sub commands (eg 'load' if the command is '/cmd load all') or null if there are no
     * sub commands
     */
    public abstract List<SubCommand> getSubCommands();

    /**
     * @return The parent command
     */
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

        if (getSubCommands() == null || getSubCommands().size() == 0) {
            return false;
        }

        if (args.length == 0 || getSubCommands().stream().noneMatch(sub -> sub.getAliases().contains(args[0]))) {

            sender.sendMessage(ChatUtil
                                   .colorString(ChatColor.RED, ChatColor.DARK_RED, "Invalid sub-command, valid are ",
                                                getSubCommands().stream().map(subCmd -> subCmd.getAliases().get(0))
                                                                .distinct().toArray()));
            return true;
        }

        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, args.length - 1);

        for (SubCommand subCommand : getSubCommands()) {
            if (subCommand.getAliases().contains(args[0])) {
                return subCommand.onCommand(sender, command, label + " " + args[0], subArgs);
            }
        }

        return false;
    }

    /**
     * Executes the given command, returning its success
     *
     * @param sender
     *     Source of the command
     * @param command
     *     Command which was executed
     * @param label
     *     Alias of the command which was used (includes previous subcommands, if any separated by a space)
     * @param args
     *     Passed command arguments
     *
     * @return true if a valid command, otherwise false
     */
    @Override
    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);
}
