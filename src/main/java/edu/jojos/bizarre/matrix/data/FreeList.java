package edu.jojos.bizarre.matrix.data;

import edu.jojos.bizarre.matrix.allocation.AllocationNode;
import edu.jojos.bizarre.matrix.allocation.AllocationStatus;

public class FreeList {
  private final AllocationNode root;

  public FreeList(int bitSize) {
    var maxAddress = (1L << bitSize) - 1L;
    this.root = new AllocationNode(0L, maxAddress, AllocationStatus.FREE, null, null);
  }

  public long allocate(long byteSize) {
    var currentNode = root;
    var address = -1L;

    while (currentNode != null) {
      if (byteSize <= currentNode.blockSize && currentNode.status == AllocationStatus.FREE) {
        address = split(currentNode, byteSize);
        break;
      }

      currentNode = currentNode.next;
    }

    if (address == -1) {
      throw new RuntimeException("Could not allocate memory");
    }

    return address;
  }

  private long split(AllocationNode node, long byteSize) {
    var oldAddress = node.address;
    var oldBlockSize = node.blockSize;
    var newNode =
        new AllocationNode(
            oldAddress + byteSize, oldBlockSize - byteSize, AllocationStatus.FREE, node, node.next);

    node.blockSize = byteSize;
    node.status = AllocationStatus.ALLOCATED;
    node.next = newNode;
    return node.address;
  }

  public void deallocate(long ptr) {
    var currentNode = root;
    var f = true;

    while (currentNode != null) {
      if (Long.compareUnsigned(ptr, currentNode.address) == 0
          && currentNode.status == AllocationStatus.ALLOCATED) {
        deallocate(currentNode);
        f = false;
      }

      currentNode = currentNode.next;
    }

    if (f) {
      throw new RuntimeException("Segmentation fault");
    }
  }

  private void deallocate(AllocationNode currentNode) {
    currentNode.status = AllocationStatus.FREE;

    var prevNode = currentNode.prev;
    var nextNode = currentNode.next;

    if (prevNode != null && prevNode.status == AllocationStatus.FREE) {
      currentNode = merge(prevNode, currentNode);
    }

    if (nextNode != null && nextNode.status == AllocationStatus.FREE) {
      merge(currentNode, nextNode);
    }
  }

  private AllocationNode merge(AllocationNode lNode, AllocationNode rNode) {
    lNode.blockSize = lNode.blockSize + rNode.blockSize;

    var nextNode = rNode.next;
    lNode.next = nextNode;
    if (nextNode != null) {
      nextNode.prev = lNode;
    }

    return lNode;
  }
}
