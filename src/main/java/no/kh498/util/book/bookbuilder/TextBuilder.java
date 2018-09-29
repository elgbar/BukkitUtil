package no.kh498.util.book.bookbuilder;

public class TextBuilder {

    private String text = null;
    private ClickAction click = null;
    private HoverAction hover = null;

    StringBuilder hoverLines = new StringBuilder();
    String clickValue;

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

        extra += "}";

        if (builder.first) { builder.first = false; }
        else { extra = ", " + extra; }

        builder.page += extra;
        return builder;
    }
}

