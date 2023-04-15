package edu.jojos.bizarre.matrix.driver;

import edu.jojos.bizarre.matrix.aging.AgingAgent;
import edu.jojos.bizarre.matrix.data.EndState;
import edu.jojos.bizarre.matrix.data.Result;
import edu.jojos.bizarre.matrix.memory.MemoryAgent;
import edu.jojos.bizarre.matrix.paging.PageEntry;
import edu.jojos.bizarre.matrix.paging.reference.PageReference;
import edu.jojos.bizarre.matrix.paging.reference.PageReferenceIterator;
import java.util.List;

public class MemorySimulation implements Runnable {
  private final List<PageReference> references;

  private final List<Boolean> pageFrames;

  private final List<PageEntry> pageDirectory;

  private final Result result = new Result();

  private final PageReferenceIterator iterator = new PageReferenceIterator();

  private final Thread memoryAgent;

  private final Thread ager;

  private final EndState agerState = new EndState();

  public MemorySimulation(
      List<PageReference> references, List<Boolean> pageFrames, List<PageEntry> pageDirectory) {
    this.references = references;
    this.pageFrames = pageFrames;
    this.pageDirectory = pageDirectory;
    memoryAgent = new Thread(new MemoryAgent(iterator, pageFrames, pageDirectory, result));
    ager = new Thread(new AgingAgent(pageDirectory, agerState));
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
      agerState.shutdown();
      ager.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    System.out.printf("The resulting page faults are: %d", result.getPageFaults());
  }
}
