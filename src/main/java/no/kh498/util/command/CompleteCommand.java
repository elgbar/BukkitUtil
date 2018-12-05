package no.kh498.util.command;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class CompleteCommand extends HostCommand implements TabCompleter {

    protected static final Logger logger = LoggerFactory.getLogger(CompleteCommand.class);

    /**
     * Register this command to the plugin, with a five ticks check delay.
     *
     * @param plugin
     *     The plugin to register this command to
     *
     * @throws NullPointerException
     *     if given {@code plugin} is {@code null}
     * @throws IllegalArgumentException
     *     if {@code checkDelay} is less than {@code 1}
     * @throws NullPointerException
     *     if {@link #getAliases()} is {@code null}
     * @throws IllegalArgumentException
     *     if {@link #getAliases()} contains a {@code null} element
     */
    public CompleteCommand(JavaPlugin plugin) {
        this(plugin, 5L);
    }

    /**
     * Register this command to the plugin.
     * <p>
     * This constructor is not recommended to be used unless you get a timing error when {@link #verifySubcommands}
     *
     * @param checkDelay
     *     How long to delay before verifying subcommands, must be greater than 0
     * @param plugin
     *     The plugin to register this command to
     *
     * @throws NullPointerException
     *     if given {@code plugin} is {@code null}
     * @throws IllegalArgumentException
     *     if {@code checkDelay} is less than {@code 1}
     * @throws NullPointerException
     *     if {@link #getAliases()} is {@code null}
     * @throws IllegalArgumentException
     *     if {@link #getAliases()} contains a {@code null} element or is empty
     * @throws IllegalStateException
     *     if the first argument in {@link #getAliases()} is not registered in the plugins plugin.yml
     */
    public CompleteCommand(JavaPlugin plugin, long checkDelay) {
        super(null);
        Preconditions.checkNotNull(plugin, "A valid java plugin instance is needed to register this command");
        Preconditions.checkNotNull(getAliases(), "getAliases() cannot be null");
        Preconditions.checkArgument(!getAliases().isEmpty(), "There must be at least one alias");
        Preconditions
            .checkArgument(getAliases().stream().noneMatch(Objects::isNull), "None of the aliases can be null");

        String command = getAliases().iterator().next();

        PluginCommand pluginCommand = plugin.getCommand(command);

        if (pluginCommand == null) {
            throw new IllegalStateException(
                "Failed to set executor and tab completer as '" + command + "' is not a recognized command in plugin " +
                plugin.getName() + ". Please define it in plugin.yml");
        }
        pluginCommand.setExecutor(this);
        pluginCommand.setTabCompleter(this);

        //verify all subcommands when they are all registered
        Bukkit.getScheduler().runTaskLater(plugin, this::verifySubcommands, checkDelay);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        logger.trace("tab complete event called");

        SubCommand currSubCommand = this;
        int argsId = 0;

        //get the sub command to tab-complete
        for (String arg : args) {
            if (logger.isTraceEnabled()) {
                logger.trace("---");
                logger.trace("currSubCommand is " + currSubCommand.getClass().getSimpleName());
                logger.trace("next arg is '" + arg + "'");
                logger.trace("currSubCommand.getSubCommands(): " + currSubCommand.getSubCommands());
            }
            if (currSubCommand.getSubCommands() == null || currSubCommand.getSubCommands().size() == 0) {
                //there are no subcommands for this current subcommand
                //the online players will be tabbed through
                return null;
            }
            else if (currSubCommand.getSubFromAlias(arg) == null) {
                //current arg is not in this subcommand, there will be no more final commands
                break;
            }
            argsId++;
            currSubCommand = currSubCommand.getSubFromAlias(arg);
        }

        logger.trace("final sub command is " + currSubCommand.getClass().getSimpleName());

        String toComplete;

        if (args.length == argsId) {
            if (args.length == 0) {
                return getListOfSubcommandAliases(this);
            }
            else {
                currSubCommand = currSubCommand.getParent();
            }
            toComplete = args[args.length - 1];
        }
        else {
            toComplete = args[argsId];
        }

        List<String> suggestions = new ArrayList<>();
        if (currSubCommand.getSubCommands() != null) {
            for (SubCommand subCommand : currSubCommand.getSubCommands()) {
                for (String subAlias : subCommand.getAliases()) {
                    if (subAlias.startsWith(toComplete)) {
                        suggestions.add(subAlias);
                    }
                }
            }
        }
        return suggestions;
    }

    private List<String> getListOfSubcommandAliases(SubCommand subCommand) {
        if (subCommand == null || subCommand.getSubCommands() == null) {
            return null;
        }

        List<String> suggestions = new ArrayList<>();
        for (SubCommand subCmd : subCommand.getSubCommands()) {
            suggestions.addAll(subCmd.getAliases());
        }
        return suggestions;
    }
}
