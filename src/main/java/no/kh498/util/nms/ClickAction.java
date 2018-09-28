package no.kh498.util.nms;

public enum ClickAction {
    RUN_COMMAND("run_command"),
    SUGGEST_COMMAND("suggest_command"),
    OPEN_URL("open_url"),
    CHANGE_PAGE("change_page");

    private String str;

    ClickAction(String str) { this.str = str; }

    public String getString() { return str; }
}
