package edu.jojos.bizarre.matrix.driver;

import java.util.ArrayList;
import java.util.List;

import edu.jojos.bizarre.matrix.paging.PageEntry;
import edu.jojos.bizarre.matrix.paging.reference.PageReferenceIterator;

public class MemoryAgent {
    private PageReferenceIterator iterator;
    private List<PageEntry> pageDirectory;


    private int maximumSize;

    private List<PageEntry> freeFrames = new ArrayList<>();

    private void pageFault(PageEntry newPage) {
        var minimum = Byte.MAX_VALUE;
        PageEntry oldestPage = null;
        for (PageEntry pageEntry : pageDirectory) {
            if (pageEntry.getCounter() < minimum) {
                oldestPage = pageEntry;
            }
        }
        assert oldestPage != null;
        oldestPage.isPresent = false;
		freeFrames.remove(oldestPage);
		freeFrames.add(newPage);
        newPage.isPresent = true;
    }

    public MemoryAgent(PageReferenceIterator iterator, List<PageEntry> pageDirectory) {
        this.iterator = iterator;
        this.pageDirectory = pageDirectory;
    }


    public void run() {
        while (iterator.hasNext()) {
            var pageReference = iterator.next();
            var pageNumber = pageReference.pageNumber();
            var currentPage = pageDirectory.get((int) pageNumber);

            if (!freeFrames.contains(currentPage)) {
                if (freeFrames.size() < maximumSize) {
                    freeFrames.add(currentPage);
                    currentPage.setIsReferenced(true);
                }
				else{
					pageFault(currentPage);
				}
            }

            currentPage.age();

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
