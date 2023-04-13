package edu.jojos.bizarre.matrix.paging;

public class Page {
  private boolean isPresent = false;

  private boolean isDirty = false;
  private boolean referenced = false;

  private byte counter = 0;

  public Page() {}

  public void age() {
    counter >>= 2;
    counter |= 1 << (Byte.SIZE - 1);
  }
}
