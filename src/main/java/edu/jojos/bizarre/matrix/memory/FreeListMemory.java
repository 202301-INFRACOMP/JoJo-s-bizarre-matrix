package edu.jojos.bizarre.matrix.memory;

import edu.jojos.bizarre.matrix.allocation.AllocationNode;
import edu.jojos.bizarre.matrix.allocation.AllocationStatus;

public class FreeListMemory implements MemorySystem {

  private final AllocationNode root =
      new AllocationNode(0L, Long.MAX_VALUE, AllocationStatus.FREE, null, null);

  public FreeListMemory() {}

  @Override
  public long malloc(long byteSize) {

    var currentNode = root;
    var address = -1L;

    while (currentNode != null) {
      if (byteSize <= currentNode.getBlockSize()
          && currentNode.getStatus() == AllocationStatus.FREE) {
        address = allocate(currentNode, byteSize);
        break;
      }

      currentNode = currentNode.getNext();
    }

    if (address == -1) {
      throw new RuntimeException("Could not allocate memory");
    }

    return address;
  }

  private long allocate(AllocationNode node, long byteSize) {
    var oldAddress = node.getAddress();
    var oldBlockSize = node.getBlockSize();
    var newNode =
        new AllocationNode(
            oldAddress + byteSize,
            oldBlockSize - byteSize,
            AllocationStatus.FREE,
            node,
            node.getNext());

    node.setBlockSize(byteSize);
    node.setStatus(AllocationStatus.ALLOCATED);
    node.setNext(newNode);

    return node.getAddress();
  }

  @Override
  public void free(long ptr) {
    var currentNode = root;
    var f = true;

    while (currentNode != null) {
      if (ptr == currentNode.getAddress()
          && currentNode.getStatus() == AllocationStatus.ALLOCATED) {
        deallocate(currentNode);
        f = false;
      }

      currentNode = currentNode.getNext();
    }

    if (f) {
      throw new RuntimeException("Segmentation fault");
    }
  }

  private void deallocate(AllocationNode currentNode) {
    currentNode.setStatus(AllocationStatus.FREE);

    var prevNode = currentNode.getPrev();
    var nextNode = currentNode.getNext();

    if (prevNode != null && prevNode.getStatus() == AllocationStatus.FREE) {
      currentNode = merge(prevNode, currentNode);
    }

    if (nextNode != null && nextNode.getStatus() == AllocationStatus.FREE) {
      merge(currentNode, nextNode);
    }
  }

  private AllocationNode merge(AllocationNode lNode, AllocationNode rNode) {
    lNode.setBlockSize(lNode.getBlockSize() + rNode.getBlockSize());

    var nextNode = rNode.getNext();
    lNode.setNext(nextNode);
    if (nextNode != null) {
      nextNode.setPrev(lNode);
    }

    return lNode;
  }
}
