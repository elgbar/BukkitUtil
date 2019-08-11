package no.kh498.util.book.bookbuilder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Elg
 */
public class TextBuilder {

    @Nullable
    private String text;
    @Nullable
    private ClickAction click;
    @Nullable
    private HoverAction hover;

    private boolean underlined;
    private boolean bold;
    private boolean italic;
    private boolean strikethrough;
    private boolean obfuscated;

    @NotNull
    private StringBuilder hoverLines = new StringBuilder();
    private String clickValue;

    @NotNull
    public TextBuilder setText(String text) {
        this.text = text;
        return this;
    }

    @NotNull
    public TextBuilder clickEvent(ClickAction action) {
        click = action;
        return this;
    }

    @NotNull
    public TextBuilder hoverEvent(HoverAction action) {
        hover = action;
        return this;
    }

    public void clearHoverLines() {
        hoverLines.setLength(0);
    }

    @NotNull
    public TextBuilder clickEvent(ClickAction action, String value) {
        click = action;
        clickValue = value;
        return this;
    }

    @NotNull
    public TextBuilder addHoverLine(String value) {
        hoverLines.append(", \"").append(value).append("\"");
        return this;
    }

    public String build() {
        StringBuilder extra = new StringBuilder("{text:\"" + text + "\"");

        if (click != null) {
            extra.append(", clickEvent:{action:").append(click.getString()).append(", value:\"").append(clickValue)
                 .append("\"}");
        }
        if (hover != null) {
            extra.append(", hoverEvent:{action:").append(hover.getString()).append(", value:[\"\"").append(
                hoverLines.toString()).append("]}");
        }

        if (bold) {
            extra.append(",bold:true");
        }
        if (underlined) {
            extra.append(",underlined:true");
        }
        if (italic) {
            extra.append(",italic:true");
        }
        if (strikethrough) {
            extra.append(",strikethrough:true");
        }
        if (obfuscated) {
            extra.append(",obfuscated:true");
        }
        return extra.append("}").toString();
    }

    public boolean isUnderlined() {
        return underlined;
    }

    @NotNull
    public TextBuilder setUnderlined(boolean underlined) {
        this.underlined = underlined;
        return this;
    }

    public boolean isBold() {
        return bold;
    }

    @NotNull
    public TextBuilder setBold(boolean bold) {
        this.bold = bold;
        return this;
    }

    public boolean isItalic() {
        return italic;
    }

    @NotNull
    public TextBuilder setItalic(boolean italic) {
        this.italic = italic;
        return this;
    }

    public boolean isStrikethrough() {
        return strikethrough;
    }

    @NotNull
    public TextBuilder setStrikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }

    public boolean isObfuscated() {
        return obfuscated;
    }

    @NotNull
    public TextBuilder setObfuscated(boolean obfuscated) {
        this.obfuscated = obfuscated;
        return this;
    }
}

