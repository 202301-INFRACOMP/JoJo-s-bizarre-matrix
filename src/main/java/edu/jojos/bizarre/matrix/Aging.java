package edu.jojos.bizarre.matrix;

import edu.jojos.bizarre.matrix.paging.PageEntry;
import edu.jojos.bizarre.matrix.paging.reference.PageReference;

import java.util.List;

public class Aging implements Runnable {

    private List<PageEntry> pageDirectory;

    public Aging(List<PageEntry> pageDirectory) {
        this.pageDirectory = pageDirectory;
    }

    private void ageDirectory() {
        int positionOldest = 0;
        for (PageEntry pageEntry : pageDirectory) {
            //gets the position of the oldest page
            if (pageEntry.getCounter() > pageDirectory.get(positionOldest).getCounter()) {
                positionOldest = pageDirectory.indexOf(pageEntry);
            }
        }
        //the oldest page goes out
        pageDirectory.get(positionOldest).isPresent = false;
        //ages all pages after execution
        for (PageEntry pageEntry : pageDirectory) {
            pageEntry.age();
        }
    }

    @Override
    public void run() {
        ageDirectory();
    }


}
