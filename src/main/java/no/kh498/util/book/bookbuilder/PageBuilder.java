package no.kh498.util.book.bookbuilder;

import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public class PageBuilder {

    String page;
    boolean first;
    private BookBuilder book;

    PageBuilder(BookBuilder book) {
        this.book = book;
        first = true;
        page = "{text:\"\", extra:[";
    }

    @NotNull
    public TextBuilder add() { return new TextBuilder(this); }

    public PageBuilder newPage() {
        add("\n").build();
        return book.createPageBuilder();
    }

    public TextBuilder add(String text) { return new TextBuilder(this).setText(text); }

    public BookBuilder build() {
        book.addPage(page += "]}");
        return book;
    }

}
