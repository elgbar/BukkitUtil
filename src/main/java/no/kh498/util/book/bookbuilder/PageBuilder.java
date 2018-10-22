package no.kh498.util.book.bookbuilder;

/**
 * @author Elg
 */
public class PageBuilder {

    String page;
    boolean first;
    private IBookBuilder book;

    PageBuilder(IBookBuilder book) {
        this.book = book;
        first = true;
        page = "{text:\"\", extra:[";
    }

    public TextBuilder add() { return new TextBuilder(this); }

    public PageBuilder newPage() {
        add("\n").build();
        return book.createPageBuilder();
    }

    public TextBuilder add(String text) { return new TextBuilder(this).setText(text); }

    public IBookBuilder build() {
        book.addPage(page += "]}");
        return book;
    }

}
