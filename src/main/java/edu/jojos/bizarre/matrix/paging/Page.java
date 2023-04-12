package edu.jojos.bizarre.matrix.paging;

public class Page {
    private long pageNumber;
    private int lastAccessed;
    private int accesed;
    private boolean inMemory;

    public Page(long pageNumber, int lastAccessed, int accesed, boolean inMemory) {
        this.pageNumber = pageNumber;
        this.lastAccessed = lastAccessed;
        this.accesed = accesed;
        this.inMemory = inMemory;
    }

    public int getLastAccessed() {
        return lastAccessed;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public void setLastAccessed(int i) {
        lastAccessed = i;
    }
}
