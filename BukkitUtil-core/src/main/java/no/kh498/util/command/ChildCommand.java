package no.kh498.util.command;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ChildCommand extends SubCommand {

    public ChildCommand(@NotNull SubCommand parent) {
        super(parent);
    }

    @Nullable
    @Override
    public List<@NotNull SubCommand> getSubCommands() {
        return null;
    }

}
