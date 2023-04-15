package edu.jojos.bizarre.matrix.data;

public class EndState {
  private boolean isShutdown = false;

  public synchronized boolean getIsShutdown() {
    return isShutdown;
  }

  public synchronized void shutdown() {
    isShutdown = true;
  }
}
