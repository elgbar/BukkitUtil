package no.kh498.util.command;

import java.util.List;

public abstract class ChildCommand extends SubCommand {

    public ChildCommand(SubCommand parent) {
        super(parent);
    }

    @Override
    public List<SubCommand> getSubCommands() {
        return null;
    }
}
