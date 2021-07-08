package no.kh498.util.command;

import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import no.kh498.util.ChatUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SubCommand implements CommandExecutor {

    private static final Logger logger = LoggerFactory.getLogger(SubCommand.class);

    @Nullable
    private final SubCommand parent;

    public SubCommand(@Nullable SubCommand parent) {
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
    @NotNull
    public abstract List<@NotNull String> getAliases();

    protected void verify() {

        logger.trace("Verifying subcommand '{}' (parent '{}')", getCommand(), parent);
        Preconditions.checkState(!getAliases().isEmpty(), "There must be at least one alias given");
        Preconditions.checkState(getAliases().stream().allMatch(alias -> getAliases().stream().filter(alias::equalsIgnoreCase).count() == 1),
                "There cannot be any duplicate aliases within an alias");
        // the conditions below are only relevant if the command has subcommands
        List<SubCommand> commands = getSubCommands();
        if (commands == null) {
            logger.trace("There are no subcommands");
            return;
        }

        for (SubCommand subCommand : commands) {
            for (SubCommand otherSubCmd : commands) {
                if (subCommand != otherSubCmd && subCommand.getAliases().stream().anyMatch(s -> otherSubCmd.getAliases().contains(s))) {
                    throw new IllegalStateException(
                            String.format("Subcommand '%s' and '%s' in command '%s' has the same alias", subCommand.getCommand(), otherSubCmd.getCommand(), getCommand()));
                }
            }

            //verify recursively
            subCommand.verify();
        }
    }

    @NotNull
    public String getCommand() {
        return getAliases().get(0);
    }

    /**
     * @return A subcommand of this subcommand from one of it's aliases
     */
    @Nullable
    public SubCommand getSubFromAlias(@Nullable String alias) {
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
     * @return Highest level sub commands (eg 'load' if the command is '/cmd load all') or null if there are no
     * sub commands
     */
    @Nullable
    public abstract List<@NotNull SubCommand> getSubCommands();

    /**
     * If {@link #getSubCommands()} is not {@code null} or not empty this method will not be used, instead favouring  {@link #getSubCommands()} for tab
     * completion
     *
     * @return List of arguments to tab-complete for this command.
     */
    @NotNull
    public List<@NotNull String> getArguments() {
        return Collections.emptyList();
    }

    /**
     * @return If this command should allow tabbing through the current online players if there are no arguments/subcommands
     */
    public boolean tabThroughPlayers() {
        return true;
    }

    /**
     * @return The parent command, if {@code null} this is the root command
     */
    @Nullable
    public SubCommand getParent() {
        return parent;
    }


    /**
     * @param sender  the sender of the command
     * @param command base command
     * @param label   alias of the command used
     * @param args    args from the parent command
     * @return if the command was handled
     */
    public boolean onSubCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (getSubCommands() == null || getSubCommands().isEmpty()) {
            return false;
        }

        if (args.length == 0 || getSubCommands().stream().noneMatch(sub -> sub.getAliases().contains(args[0]))) {

            sender.sendMessage(ChatUtil.colorString(ChatColor.RED, ChatColor.DARK_RED, "Invalid sub-command, valid are ", getSubCommands().stream().map(
                    subCmd -> subCmd.getAliases().get(0)).distinct().collect(Collectors.toList())));
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
     *   Source of the command
     * @param command
     *   Command which was executed
     * @param label
     *   Alias of the command which was used (includes previous subcommands, if any separated by a space)
     * @param args
     *   Passed command arguments
     *
     * @return true if a valid command, otherwise false
     */
    @Override
    public abstract boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);
}
