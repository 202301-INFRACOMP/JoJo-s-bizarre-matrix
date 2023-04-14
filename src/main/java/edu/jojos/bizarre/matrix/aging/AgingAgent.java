package edu.jojos.bizarre.matrix.aging;

import edu.jojos.bizarre.matrix.paging.PageEntry;
import java.util.List;

public class AgingAgent implements Runnable {

  private final List<PageEntry> pageDirectory;

  public AgingAgent(List<PageEntry> pageDirectory) {
    this.pageDirectory = pageDirectory;
  }

  @Override
  public void run() {
    synchronized (pageDirectory) {
      for (PageEntry pageEntry : pageDirectory) {
        if (pageEntry.getIsReferenced()) {
          pageEntry.age();
        }
      }
    }
    try {
      Thread.sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
