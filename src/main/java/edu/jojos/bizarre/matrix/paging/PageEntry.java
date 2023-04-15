package edu.jojos.bizarre.matrix.paging;

public class PageEntry {

  private int pageFrame;

  private boolean isPresent = false;

  private boolean isDirty = false;

  private boolean isReferenced = false;

  private byte counter = 0;

  public PageEntry() {}

  public int getPageFrame() {
    return pageFrame;
  }

  public boolean getIsPresent() {
    return isPresent;
  }

  public boolean getIsDirty() {
    return isDirty;
  }

  public boolean getIsReferenced() {
    return isReferenced;
  }

  public void setIsReferenced(boolean state) {
    isReferenced = state;
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

  public void evict() {
    isPresent = false;
    isDirty = false;
    isReferenced = false;
  }

  public void mapTo(int pageFrame) {
    this.pageFrame = pageFrame;
    isPresent = true;
  }

  public byte getCounter() {
    return counter;
  }

  public void age() {
    counter >>= 2;
    counter |= 1 << (Byte.SIZE - 1);
  }
}
