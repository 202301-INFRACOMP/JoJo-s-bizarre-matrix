package edu.jojos.bizarre.matrix.driver;

import edu.jojos.bizarre.matrix.Result;
import edu.jojos.bizarre.matrix.aging.AgingAgent;
import edu.jojos.bizarre.matrix.paging.PageEntry;
import edu.jojos.bizarre.matrix.paging.reference.PageReference;
import edu.jojos.bizarre.matrix.paging.reference.PageReferenceIterator;

import java.util.ArrayList;
import java.util.List;

public class MemorySimulation implements Runnable {
  private final List<PageReference> references;

  private final int pageCount;

  private static final Result result = new Result();
  private final PageReferenceIterator iterator = new PageReferenceIterator();

  private List<PageEntry> pageDirectory = new ArrayList<>();

  private final Thread memoryAgent;

  private final Thread ager;

  private int dirSize;

  public MemorySimulation(List<PageReference> references, int pageCount,int dirSize) {
    this.references = references;
    this.pageCount = pageCount;
    this.dirSize = dirSize;

    for(int i=0;i<dirSize;i++){
      assert false;
      pageDirectory.add(new PageEntry());
    }

    memoryAgent = new Thread(new MemoryAgent(iterator,pageDirectory,result));
    ager = new Thread(new AgingAgent(pageDirectory));
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
      System.out.println(result.pageFaults);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
