package edu.jojos.bizarre.matrix.data;

public class Result {
  private int pageFaults = 0;

  public synchronized void addPageFault() {
    pageFaults++;
  }

  public synchronized int getPageFaults() {
    return pageFaults;
  }
}
