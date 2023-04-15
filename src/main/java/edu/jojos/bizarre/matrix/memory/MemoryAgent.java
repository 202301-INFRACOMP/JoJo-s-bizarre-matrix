package edu.jojos.bizarre.matrix.memory;

import edu.jojos.bizarre.matrix.data.Result;
import edu.jojos.bizarre.matrix.paging.PageAccess;
import edu.jojos.bizarre.matrix.paging.PageEntry;
import edu.jojos.bizarre.matrix.paging.reference.PageReferenceIterator;
import java.util.ArrayList;
import java.util.List;

public class MemoryAgent implements Runnable {
  private final PageReferenceIterator iterator;
  private final List<PageEntry> pageDirectory;

  private int maximumSize;

  private final List<Boolean> pageFrames = new ArrayList<>();

  private Result result;

  public MemoryAgent(PageReferenceIterator iterator, List<PageEntry> pageDirectory, Result result) {
    this.iterator = iterator;
    this.pageDirectory = pageDirectory;
    this.result = result;
  }

  private void pageFault(PageEntry newPage) {
    var minimum = Byte.MAX_VALUE;
    PageEntry oldestPage = null;
    for (Boolean pageFrame : pageFrames) {
      if (Boolean.TRUE.equals(pageFrame)){
        pageFrame = false;
        newPage.mapTo(pageFrames.indexOf(pageFrame));
        newPage.access(PageAccess.WRITE);
        return;
      }
    }

    for (PageEntry pageEntry : pageDirectory) {
      if (pageEntry.getCounter() < minimum) {
        oldestPage = pageEntry;
      }
    }
    assert oldestPage != null;

    int index = pageFrames.indexOf(oldestPage.getPageFrame());
    oldestPage.evict();
    newPage.mapTo(index);
    newPage.access(PageAccess.NONE);

    result.pageFaults++;
      // hay que llevar un index para saber quien está libre en memoria fisica
      // la dirección que se le asigna a la página es el index

    }




  public void run() {
    while (iterator.hasNext()) {
      var pageReference = iterator.next();
      var pageNumber = pageReference.pageNumber();
      var currentPage = pageDirectory.get((int) pageNumber);

      if (!currentPage.getIsPresent()) {
        pageFault(currentPage);
      }

      try {
        Thread.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
