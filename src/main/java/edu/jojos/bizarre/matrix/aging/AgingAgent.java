package edu.jojos.bizarre.matrix.aging;

import edu.jojos.bizarre.matrix.data.EndState;
import edu.jojos.bizarre.matrix.paging.PageEntry;
import java.util.List;

public class AgingAgent implements Runnable {

  private final List<PageEntry> pageDirectory;

  private final EndState agerState;

  public AgingAgent(List<PageEntry> pageDirectory, EndState agerState) {
    this.pageDirectory = pageDirectory;
    this.agerState = agerState;
  }

  @Override
  public void run() {
    while (!agerState.getIsShutdown()) {
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
}
