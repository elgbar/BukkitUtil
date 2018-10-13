package no.kh498.util.book.bookbuilder;

public class TextBuilder {

    private String text = null;
    private ClickAction click = null;
    private HoverAction hover = null;

    private boolean underlined;
    private boolean bold;
    private boolean italic;
    private boolean strikethrough;
    private boolean obfuscated;

    private StringBuilder hoverLines = new StringBuilder();
    private String clickValue;

    private PageBuilder builder;

    TextBuilder(PageBuilder builder) { this.builder = builder; }

    public TextBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public TextBuilder clickEvent(ClickAction action) {
        click = action;
        return this;
    }

    public TextBuilder hoverEvent(HoverAction action) {
        hover = action;
        return this;
    }

    public void clearHoverLines() {
        hoverLines.setLength(0);
    }

    public TextBuilder clickEvent(ClickAction action, String value) {
        click = action;
        clickValue = value;
        return this;
    }

    public TextBuilder addHoverLine(String value) {
        hoverLines.append(", \"").append(value).append("\"");
        return this;
    }

    public PageBuilder build() {
        String extra = "{text:\"" + text + "\"";

        if (click != null) {
            extra += ", clickEvent:{action:" + click.getString() + ", value:\"" + clickValue + "\"}";
        }
        if (hover != null) {
            extra += ", hoverEvent:{action:" + hover.getString() + ", value:[\"\"" + hoverLines.toString() + "]}";
        }

        if (bold) {
            extra += ",bold:true";
        }
        if (underlined) {
            extra += ",underlined:true";
        }
        if (italic) {
            extra += ",italic:true";
        }
        if (strikethrough) {
            extra += ",strikethrough:true";
        }
        if (obfuscated) {
            extra += ",obfuscated:true";
        }

        extra += "}";

        if (builder.first) { builder.first = false; }
        else { extra = ", " + extra; }

        builder.page += extra;
        return builder;
    }

    public boolean isUnderlined() {
        return underlined;
    }

    public TextBuilder setUnderlined(boolean underlined) {
        this.underlined = underlined;
        return this;
    }

    public boolean isBold() {
        return bold;
    }

    public TextBuilder setBold(boolean bold) {
        this.bold = bold;
        return this;
    }

    public boolean isItalic() {
        return italic;
    }

    public TextBuilder setItalic(boolean italic) {
        this.italic = italic;
        return this;
    }

    public boolean isStrikethrough() {
        return strikethrough;
    }

    public TextBuilder setStrikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }

    public boolean isObfuscated() {
        return obfuscated;
    }

    public TextBuilder setObfuscated(boolean obfuscated) {
        this.obfuscated = obfuscated;
        return this;
    }
}

