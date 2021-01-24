package no.kh498.util;

import info.ronjenkins.slf4bukkit.ColorString;
import java.util.Collections;
import kotlin.Deprecated;
import kotlin.ReplaceWith;
import static no.kh498.util.DebuggableUtils.appendSinglePrefix;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Debuggable {

  @NotNull
  default String showDebugInfo() {
    return appendDebugInfo(new ColorString()).toString();
  }

  /**
   * @param sb
   *   The StringBuilding to addend to, will be on an empty line
   *
   * @return the given StringBuilder, {@code sb}, and the last appended item must be a newline
   */
  @NotNull
  default ColorString appendDebugInfo(@NotNull ColorString sb) {
    return appendDebugInfo(sb, "");
  }

  /**
   * @param sb
   *   The StringBuilding to addend to, will be on an empty line
   * @param prefix
   *   The prefix that must be added on each newline (excluding the last)
   *
   * @return the given StringBuilder, {@code sb}, and the last appended item must be a newline (without the prefix)
   */
  @NotNull ColorString appendDebugInfo(@NotNull ColorString sb, @NotNull String prefix);

  default void appendSingle(@NotNull ColorString sb, @NotNull String prefix, @NotNull String objectName, boolean bool) {
    appendSinglePrefix(sb, prefix, objectName, '?');

    if (bool) { sb.darkGreen("true"); }
    else { sb.darkRed("false"); }

    sb.none("\n");
  }

  /**
   * @deprecated Too specific
   */
  @Deprecated(message = "Too specific",
              replaceWith = @ReplaceWith(expression = "appendSingle(sb, prefix, objectName, object)", imports = {}))
  default void appendDebuggable(@NotNull ColorString sb, @NotNull String prefix, @NotNull String objectName,
                                @Nullable Object object) {
    appendSingle(sb, prefix, objectName, object);
  }

  default void appendSingle(@NotNull ColorString sb, @NotNull String prefix, @NotNull String objectName,
                            @Nullable Object object) {
    if (object == null) {
      appendSinglePrefix(sb, prefix, objectName).none("null\n");
    }
    else if (object instanceof Debuggable) {
      appendDebuggableList(sb, prefix, objectName, Collections.singleton((Debuggable) object));
    }
    else if (object instanceof CharSequence) {
      appendSinglePrefix(sb, prefix, objectName).green("\"" + object).green(ChatColor.GREEN + "\"\n");
    }
    else if (object instanceof Number) {
      appendSinglePrefix(sb, prefix, objectName).purple(object + "\n");
    }
    else {
      appendSinglePrefix(sb, prefix, objectName).aqua(object + "\n");
    }
  }

  default void appendSingle(@NotNull ColorString sb, @NotNull String prefix, @NotNull String objectName, float object) {
    appendSinglePrefix(sb, prefix, objectName).purple(object + "\n");
  }

  default void appendSingle(@NotNull ColorString sb, @NotNull String prefix, @NotNull String objectName,
                            double object) {
    appendSinglePrefix(sb, prefix, objectName).purple(object + "\n");
  }

  default void appendSingle(@NotNull ColorString sb, @NotNull String prefix, @NotNull String objectName, byte object) {
    appendSinglePrefix(sb, prefix, objectName).purple(object + "\n");
  }

  default void appendSingle(@NotNull ColorString sb, @NotNull String prefix, @NotNull String objectName, short object) {
    appendSinglePrefix(sb, prefix, objectName).purple(object + "\n");
  }

  default void appendSingle(@NotNull ColorString sb, @NotNull String prefix, @NotNull String objectName, int object) {
    appendSinglePrefix(sb, prefix, objectName).purple(object + "\n");
  }

  default void appendSingle(@NotNull ColorString sb, @NotNull String prefix, @NotNull String objectName, long object) {
    appendSinglePrefix(sb, prefix, objectName).purple(object + "\n");
  }

  default void appendDebuggableList(@NotNull ColorString sb, @NotNull String prefix, @NotNull String objectName,
                                    @Nullable Iterable<? extends Debuggable> objects) {

    sb.yellow(prefix + objectName + " = ");
    if (objects == null) {
      sb.none("null\n");
      return;
    }
    else if (!objects.iterator().hasNext()) {
      sb.none("(empty)\n");
      return;
    }
    sb.yellow("[\n");

    String nextPrefix = prefix + "  ";

    for (Debuggable debuggable : objects) {
      sb.gold(nextPrefix + debuggable.getClass().getSimpleName());
      if (debuggable instanceof Nameable) {
        String name = ((Nameable) debuggable).getName();
        sb.gold(" \"" + name + "\"");
      }
      sb.none("\n");
      debuggable.appendDebugInfo(sb, nextPrefix + " ");
    }
    sb.yellow(prefix + "]\n");
  }
}
