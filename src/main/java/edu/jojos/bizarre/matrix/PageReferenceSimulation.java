package edu.jojos.bizarre.matrix;

import edu.jojos.bizarre.matrix.paging.Page;
import edu.jojos.bizarre.matrix.paging.PageDirectory;
import edu.jojos.bizarre.matrix.paging.PageRequest;

public class PageReferenceSimulation {
    private PageDirectory pageDirectory;
    private PageRequest[]  pageRequests;

    private int pageFaults=0;

    private int pageSize;
    public PageReferenceSimulation(PageDirectory pageDirectory, PageRequest[] pageRequests,int pageSize) {
        this.pageDirectory = pageDirectory;
        this.pageRequests = pageRequests;
        this.pageSize = pageSize;
    }

    public void simulate() {
        for (PageRequest pageRequest : pageRequests) {
            Page page = pageDirectory.getPage(pageRequest.getPageNumber());
            if (page == null) {
                pageFaults++;
                page = new Page(pageRequest.getPageNumber(), 0, 0, false);
                pageDirectory.setPage(pageRequest.getPageNumber(), page);
            }
            page.setLastAccessed(page.getLastAccessed() + 1);
        }
    }

}
