package edu.jojos.bizarre.matrix.paging;

public class PageEntry {

  public int pageNumber;

  public boolean isPresent = false;

  private boolean isDirty = false;
  private boolean isReferenced = false;

  private byte counter = 0;

  public PageEntry() {}

  public boolean getIsDirty() {
    return isDirty;
  }

  public boolean getIsReferenced() {
    return isReferenced;
  }

  public void access(PageAccess pageAccess) {
    switch (pageAccess) {
      case READ -> isReferenced = true;
      case WRITE, NONE -> {
        isDirty = true;
        isReferenced = true;
      }
    }
  }

  public byte getCounter() {
    return counter;
  }

  public void age() {
    counter >>= 2;
    counter |= 1 << (Byte.SIZE - 1);
  }
}
