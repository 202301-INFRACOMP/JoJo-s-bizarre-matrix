package edu.jojos.bizarre.matrix.paging.reference;

import edu.jojos.bizarre.matrix.iterator.LiveIterator;
import java.util.LinkedList;
import java.util.Queue;

public class PageReferenceIterator implements LiveIterator<PageReference> {
  private final Queue<PageReference> buffer = new LinkedList<>();

  private boolean isAlive;

  public PageReferenceIterator() {}

  @Override
  public synchronized boolean hasNext() {
    if (!buffer.isEmpty())
    {
      return true;
    }

    if (isAlive) {
      try {
        wait();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    return isAlive;
  }

  @Override
  public synchronized PageReference next() {
    return buffer.poll();
  }

  @Override
  public synchronized void feed(PageReference value) {
    buffer.offer(value);
    notify();
  }

  public synchronized void terminate() {
    isAlive = false;
  }
}
