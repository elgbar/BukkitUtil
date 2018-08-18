package no.kh498.util.command;

import java.util.Map;

public abstract class ChildCommand extends SubCommand {

    public ChildCommand(SubCommand parent) {
        super(parent);
    }

    @Override
    public Map<String, SubCommand> getSubCommands() {
        return null;
    }
}
