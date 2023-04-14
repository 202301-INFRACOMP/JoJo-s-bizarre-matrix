package edu.jojos.bizarre.matrix.allocation;

public class AllocationNode {
  public long address;

  public long blockSize;

  public AllocationStatus status;

  public AllocationNode prev;

  public AllocationNode next;

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
}
