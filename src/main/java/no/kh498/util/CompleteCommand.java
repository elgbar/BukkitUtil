package no.kh498.util;

import com.google.common.base.Preconditions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class CompleteCommand implements CommandExecutor, TabCompleter {

    /**
     * Register this command to the plugin
     *
     * @param plugin
     *     The plugin to register this command to
     * @param command
     *     The command to register
     */
    public void register(JavaPlugin plugin, String command) {
        Preconditions.checkNotNull(plugin, "A valid java plugin instance is needed to register this command");
        plugin.getCommand(command).setExecutor(this);
        plugin.getCommand(command).setTabCompleter(this);
    }

    /**
     * @return Highest level sub commands (eg 'load' if the command is '/cmd load all')
     */
    public abstract List<String> getSubCommands();

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> subCommands = getSubCommands();
        if (args.length == 0) {
            return subCommands;
        }
        List<String> suggestions = new ArrayList<>();
        char[] array = args[0].toCharArray();
        for (String subCommand : subCommands) {
            if (subCommand.startsWith(args[0])) {
                suggestions.add(subCommand);
            }
        }
        return suggestions;
    }
}
