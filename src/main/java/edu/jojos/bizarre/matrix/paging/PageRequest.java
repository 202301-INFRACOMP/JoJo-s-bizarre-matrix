package edu.jojos.bizarre.matrix.paging;

public class PageRequest {

    private long pageNumber;
    private long pageOffset;


    public PageRequest(long pageNumber, long pageOffset) {
        this.pageNumber = pageNumber;
        this.pageOffset = pageOffset;
    }

    // getters and setters
    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageOffset(long pageOffset) {
        this.pageOffset = pageOffset;
    }

    public long getPageOffset() {
        return pageOffset;
    }

}
