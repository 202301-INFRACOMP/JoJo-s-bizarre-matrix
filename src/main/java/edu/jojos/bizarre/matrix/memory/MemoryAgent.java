package edu.jojos.bizarre.matrix.memory;

import edu.jojos.bizarre.matrix.data.Result;
import edu.jojos.bizarre.matrix.paging.PageAccess;
import edu.jojos.bizarre.matrix.paging.PageEntry;
import edu.jojos.bizarre.matrix.paging.reference.PageReferenceIterator;
import java.util.List;

public class MemoryAgent implements Runnable {
  private final PageReferenceIterator iterator;

  private final List<Boolean> pageFrames;

  private final List<PageEntry> pageDirectory;

  private final Result result;

  public MemoryAgent(
      PageReferenceIterator iterator,
      List<Boolean> pageFrames,
      List<PageEntry> pageDirectory,
      Result result) {
    this.iterator = iterator;
    this.pageFrames = pageFrames;
    this.pageDirectory = pageDirectory;
    this.result = result;
  }

  private void pageFault(PageEntry faultPage) {
    int pageFrameIndex = -1;
    for (int i = 0; i < pageFrames.size(); i++) {
      if (pageFrames.get(i)) {
        pageFrameIndex = i;
      }
    }

    if (pageFrameIndex == 1) {
      var pageFrame = pageFrames.get(pageFrameIndex);
      pageFrame = false;
      faultPage.mapTo(pageFrameIndex);
    } else {
      var minimum = Byte.MAX_VALUE;
      int oldestPageEntryIndex = -1;
      for (int i = 0; i < pageDirectory.size(); i++) {
        var pageEntry = pageDirectory.get(i);
        var counter = pageEntry.getCounter();
        if (Byte.compareUnsigned(counter, minimum) < 0) {
          oldestPageEntryIndex = i;
        }
      }

      var oldestPage = pageDirectory.get(oldestPageEntryIndex);
      faultPage.mapTo(oldestPage.getPageFrame());
    }

    faultPage.access(PageAccess.NONE);
    result.addPageFault();
  }

  public void run() {
    while (iterator.hasNext()) {
      var pageReference = iterator.next();
      var pageNumber = pageReference.pageNumber();
      var currentPage = pageDirectory.get(pageNumber);

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
