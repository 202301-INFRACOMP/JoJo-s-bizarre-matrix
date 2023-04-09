package edu.jojos.bizarre.matrix.allocation;

public class AllocationNode {
  private long address;
  private long blockSize;

  private AllocationStatus status;

  private AllocationNode prev;

  private AllocationNode next;

  public AllocationNode(
      long address,
      long blockSize,
      AllocationStatus status,
      AllocationNode prev,
      AllocationNode next) {
    this.address = address;
    this.blockSize = blockSize;
    this.status = status;
    this.prev = prev;
    this.next = next;
  }

  public long getAddress() {
    return address;
  }

  public void setAddress(long address) {
    this.address = address;
  }

  public long getBlockSize() {
    return blockSize;
  }

  public void setBlockSize(long blockSize) {
    this.blockSize = blockSize;
  }

  public AllocationStatus getStatus() {
    return status;
  }

  public void setStatus(AllocationStatus status) {
    this.status = status;
  }

  public AllocationNode getPrev() {
    return prev;
  }

  public void setPrev(AllocationNode prev) {
    this.prev = prev;
  }

  public AllocationNode getNext() {
    return next;
  }

  public void setNext(AllocationNode next) {
    this.next = next;
  }
}
