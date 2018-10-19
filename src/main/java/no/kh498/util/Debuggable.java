package no.kh498.util;

import info.ronjenkins.slf4bukkit.ColorString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

public interface Debuggable {

    /**
     * @param sb
     *     The StringBuilding to addend to, will be on an empty line
     *
     * @return the given StringBuilder, {@code sb}, and the last appended item must be a newline
     */
    default ColorString appendDebugInfo(@NotNull ColorString sb) {
        return appendDebugInfo(sb, "");
    }

    /**
     * @param sb
     *     The StringBuilding to addend to, will be on an empty line
     * @param prefix
     *     The prefix that must be added on each newline (excluding the last)
     *
     * @return the given StringBuilder, {@code sb}, and the last appended item must be a newline (without the prefix)
     */
    ColorString appendDebugInfo(@NotNull ColorString sb, @NotNull String prefix);

    default String showDebugInfo() {
        return appendDebugInfo(new ColorString()).toString();
    }


    default void appendSingle(@NotNull ColorString sb, @NotNull String prefix, @NotNull String objectName,
                              boolean bool) {
        sb.none(prefix).yellow(getClass().getSimpleName() + ": " + objectName + " ? ");
        
        if (bool) { sb.green("true"); }
        else { sb.red("false"); }

        sb.none("\n");
    }

    default void appendSingle(@NotNull ColorString sb, @NotNull String prefix, @NotNull String objectName,
                              @Nullable Object object) {
        sb.none(prefix).yellow(getClass().getSimpleName() + ": " + objectName + " = ").aqua(object + "").none("\n");
    }

    default void appendDebuggable(@NotNull ColorString sb, @NotNull String prefix, @NotNull String objectName,
                                  @Nullable Debuggable object) {
        if (object == null) { appendSingle(sb, prefix, objectName, null); }
        else { appendDebuggableList(sb, prefix, objectName, Collections.singleton(object));}
    }

    default void appendDebuggableList(@NotNull ColorString sb, @NotNull String prefix, @NotNull String objectName,
                                      @Nullable Collection<? extends Debuggable> objects) {

        sb.none(prefix).yellow(getClass().getSimpleName() + ": " + objectName + " = \n");
        if (objects == null) {
            sb.none("null");
            return;
        }

        String objPrefix = prefix + "\t";

        for (Debuggable debuggable : objects) {

            sb.gold("\t" + debuggable.getClass().getSimpleName());
            if (debuggable instanceof Nameable) {
                sb.gold(" " + ((Nameable) debuggable).getName());
            }
            sb.none("\n");

            debuggable.appendDebugInfo(sb, objPrefix).none("\n");
        }
    }
}
