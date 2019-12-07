package no.kh498.util.command;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ChildCommand extends SubCommand {

    public ChildCommand(SubCommand parent) {
        super(parent);
    }

    @Nullable
    @Override
    public List<SubCommand> getSubCommands() {
        return null;
    }
}
