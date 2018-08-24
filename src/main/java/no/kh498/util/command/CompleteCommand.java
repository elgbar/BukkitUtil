package no.kh498.util.command;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class CompleteCommand extends HostCommand implements TabCompleter {

    private static final Logger logger = LoggerFactory.getLogger(CompleteCommand.class);

    /**
     * Register this command to the plugin
     *
     * @param plugin
     *     The plugin to register this command to
     */
    public CompleteCommand(JavaPlugin plugin) {
        super(null);
        Preconditions.checkNotNull(plugin, "A valid java plugin instance is needed to register this command");

        String command = getAliases().iterator().next();

        plugin.getCommand(command).setExecutor(this);
        plugin.getCommand(command).setTabCompleter(this);

        //verify all subcommands when they are all registered
        Bukkit.getScheduler().runTaskLater(plugin, this::verifySubcommands, 2L);
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
