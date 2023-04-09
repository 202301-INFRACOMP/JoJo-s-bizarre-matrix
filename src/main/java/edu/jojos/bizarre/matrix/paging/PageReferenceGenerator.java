package edu.jojos.bizarre.matrix.paging;

import edu.jojos.bizarre.matrix.memory.MemorySystem;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PageReferenceGenerator {
  private final MemorySystem memorySystem;

  public PageReferenceGenerator(MemorySystem memorySystem) {
    this.memorySystem = memorySystem;
  }

  public List<PageReference> generate(int rowSize, int columnSize, int elementSize, int pageSize) {
    final var references = new ArrayList<PageReference>();

    final var matrixSize = rowSize * columnSize * elementSize;
    final var A = memorySystem.malloc(matrixSize);
    final var B = memorySystem.malloc(matrixSize);
    final var C = memorySystem.malloc(matrixSize);

    for (long i = 0; i < rowSize; i++) {
      for (long j = 0; j < columnSize; j++) {
        /*
         * read A
         * read B
         * write C
         *
         * Simulates C = A + B
         * */
        final var Aij = generateAddress(A, elementSize, columnSize, i, j);
        references.add(new PageReference("A", i, j, Aij / pageSize, Aij % pageSize));
        final var Bij = generateAddress(B, elementSize, columnSize, i, j);
        references.add(new PageReference("B", i, j, Bij / pageSize, Bij % pageSize));
        final var Cij = generateAddress(C, elementSize, columnSize, i, j);
        references.add(new PageReference("C", i, j, Cij / pageSize, Cij % pageSize));
      }
    }

    memorySystem.free(A);
    memorySystem.free(B);
    memorySystem.free(C);

    return references;
  }

  private long generateAddress(long ptr, long elementSize, long columnSize, long i, long j) {
    return elementSize * ((i * columnSize) + j) + ptr;
  }

  public void save(
      Path p, int rowSize, int columnSize, int pageSize, List<PageReference> references)
      throws IOException {
    try (var bw = Files.newBufferedWriter(p)) {
      bw.write(String.format("TP=%d\n", pageSize));
      bw.write(String.format("NF=%d\n", rowSize));
      bw.write(String.format("NC=%d\n", columnSize));
      bw.write(String.format("NR=%d\n", references.size()));
      for (final var r : references) {
        bw.write(
            String.format(
                "[%s-%d-%d],%d,%d\n", r.id(), r.row(), r.column(), r.pageNumber(), r.pageOffset()));
      }
    }
  }
}
