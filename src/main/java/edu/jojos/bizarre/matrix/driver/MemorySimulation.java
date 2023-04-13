package edu.jojos.bizarre.matrix.driver;

import edu.jojos.bizarre.matrix.paging.reference.PageReference;
import edu.jojos.bizarre.matrix.paging.reference.PageReferenceIterator;
import java.util.List;

public class MemorySimulation implements Runnable {
  private final List<PageReference> references;

  private final int pageCount;
  private final PageReferenceIterator iterator = new PageReferenceIterator();

  private final Thread memoryAgent = new Thread();

  private final Thread ager = new Thread();

  public MemorySimulation(List<PageReference> references, int pageCount) {
    this.references = references;
    this.pageCount = pageCount;
  }

  @Override
  public void run() {
    memoryAgent.start();
    ager.start();

    for (int i = 0; i < references.size(); i++) {
      iterator.feed(references.get(i));
    }

    iterator.terminate();

    try {
      memoryAgent.join();
      ager.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
