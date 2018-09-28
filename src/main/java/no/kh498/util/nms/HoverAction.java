package no.kh498.util.nms;

public enum HoverAction {
    SHOW_TEXT("show_text"),
    SHOW_ITEM("show_item"),
    SHOW_ENTITY("show_entity"),
    SHOW_ACHIEVEMENT("show_achievement");

    private String str;

    HoverAction(String str) { this.str = str; }

    public String getString() { return str; }
}
