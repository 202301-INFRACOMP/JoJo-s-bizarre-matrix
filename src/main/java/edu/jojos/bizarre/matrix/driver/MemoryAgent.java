package edu.jojos.bizarre.matrix.driver;

import edu.jojos.bizarre.matrix.Result;
import edu.jojos.bizarre.matrix.paging.PageAccess;
import edu.jojos.bizarre.matrix.paging.PageEntry;
import edu.jojos.bizarre.matrix.paging.reference.PageReferenceIterator;
import java.util.ArrayList;
import java.util.List;

public class MemoryAgent implements Runnable {
  private final PageReferenceIterator iterator;
  private final List<PageEntry> pageDirectory;

  private int maximumSize;

  private final List<PageEntry> freeFrames = new ArrayList<>();

  private Result result;

  public MemoryAgent(PageReferenceIterator iterator, List<PageEntry> pageDirectory,Result result) {
    this.iterator = iterator;
    this.pageDirectory = pageDirectory;
    this.result = result;
  }

  private void pageFault(PageEntry newPage) {
    var minimum = Byte.MAX_VALUE;
    PageEntry oldestPage = null;
    for (PageEntry pageEntry : freeFrames) {
      //hay que llevar un index para saber quien está libre en memoria fisica
      //la dirección que se le asigna a la página es el index
      System.out.println(pageEntry.FrameNumber);
      if (pageEntry.getCounter() < minimum) {
        oldestPage = pageEntry;
      }
    }
    assert oldestPage != null;
    freeFrames.remove(oldestPage);
    oldestPage.evict();
    freeFrames.add(newPage);
    newPage.access(PageAccess.WRITE);
    newPage.mapTo(oldestPage.FrameNumber);
    result.pageFaults++;
  }

  public void run() {
    while (iterator.hasNext()) {
      var pageReference = iterator.next();
      var pageNumber = pageReference.pageNumber();
      var currentPage = pageDirectory.get((int) pageNumber);

      if (!freeFrames.contains(currentPage)) {
          pageFault(currentPage);
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
