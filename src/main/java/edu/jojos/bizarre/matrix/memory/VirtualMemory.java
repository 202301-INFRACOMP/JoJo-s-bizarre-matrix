package edu.jojos.bizarre.matrix.memory;

import edu.jojos.bizarre.matrix.data.FreeList;
import edu.jojos.bizarre.matrix.paging.reference.PageReference;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class VirtualMemory implements MemorySystem {
  private final int pageSize;

  private final List<PageReference> pageReferences = new ArrayList<>();

  private final FreeList freeList;

  public VirtualMemory(int pageSize, int bitSize) {
    this.pageSize = pageSize;
    this.freeList = new FreeList(bitSize);
  }

  @Override
  public long malloc(long byteSize) {
    return freeList.allocate(byteSize);
  }

  @Override
  public void free(long ptr) {
    freeList.deallocate(ptr);
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
}
