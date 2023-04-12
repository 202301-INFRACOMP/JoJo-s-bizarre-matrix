package edu.jojos.bizarre.matrix.paging;

import java.util.List;

public class PageDirectory {
    private Page[] pages;

    public PageDirectory(int size) {
        pages = new Page[size];
    }

    public Page getPage(long pageNumber) {
        return pages[(int) pageNumber];
    }

    public List<Page> getPages() {
        return List.of(pages);
    }

    public void setPage(long pageNumber, Page page) {
        pages[(int) pageNumber] = page;
    }
}
