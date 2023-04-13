package edu.jojos.bizarre.matrix;

import edu.jojos.bizarre.matrix.paging.PageEntry;
import edu.jojos.bizarre.matrix.paging.reference.PageReference;

import java.util.List;
import java.util.PriorityQueue;

public class Aging implements Runnable {

    private final PriorityQueue<PageEntry> pageDirectory;


    public Aging(PriorityQueue<PageEntry> pageDirectory) {
        this.pageDirectory = pageDirectory;
    }

    private synchronized int ageDirectory() {

        PageEntry oldest = pageDirectory.peek();
        for (PageEntry pageEntry : pageDirectory) {
            //gets the position of the oldest page
            if (Byte.compareUnsigned(pageEntry.getCounter(), oldest.getCounter())<0) {
                oldest = pageEntry;
            }
        }

        //ages all pages after execution
        for (PageEntry pageEntry : pageDirectory) {
            pageEntry.age();
        }
        return oldest.pageNumber;
    }

    @Override
    public void run() {
        ageDirectory();
    }


}
