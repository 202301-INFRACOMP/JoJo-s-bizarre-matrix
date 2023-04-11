package edu.jojos.bizarre.matrix.memory;

import edu.jojos.bizarre.matrix.allocation.AllocationNode;
import edu.jojos.bizarre.matrix.allocation.AllocationStatus;
import edu.jojos.bizarre.matrix.paging.PageReference;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FreeListMemory implements MemorySystem {

  private final int pageSize;
  private final List<PageReference> pageReferences = new ArrayList<>();

  private final AllocationNode root =
      new AllocationNode(0L, Long.MAX_VALUE, AllocationStatus.FREE, null, null);

  public FreeListMemory(int pageSize) {
    this.pageSize = pageSize;
  }

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

  @Override
  public void access(String metadata, long ptr) {
    pageReferences.add(new PageReference(metadata, ptr / pageSize, ptr % pageSize));
  }

  @Override
  public void save(Path p, String header) {
    try (var bw = Files.newBufferedWriter(p)) {
      bw.write(header);
      bw.write(String.format("NR=%d\n", pageReferences.size()));
      for (final var r : pageReferences) {
        bw.write(String.format("%s,%d,%d\n", r.metadata(), r.pageNumber(), r.pageOffset()));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
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
