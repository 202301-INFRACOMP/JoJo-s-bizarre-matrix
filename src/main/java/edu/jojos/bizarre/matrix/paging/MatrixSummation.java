package edu.jojos.bizarre.matrix.paging;

import edu.jojos.bizarre.matrix.Matrix;
import edu.jojos.bizarre.matrix.memory.MemorySystem;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MatrixSummation {
  private final MemorySystem memorySystem;

  public MatrixSummation(MemorySystem memorySystem) {
    this.memorySystem = memorySystem;
  }

  public List<PageReference> simulate(int rowSize, int columnSize, int elementSize, int pageSize) {
    final var references = new ArrayList<PageReference>();

    final var A = new Matrix(rowSize, columnSize, elementSize, memorySystem);
    final var B = new Matrix(rowSize, columnSize, elementSize, memorySystem);
    final var C = new Matrix(rowSize, columnSize, elementSize, memorySystem);

    for (long i = 0; i < rowSize; i++) {
      for (long j = 0; j < columnSize; j++) {
        /*
         * read A
         * read B
         * write C
         *
         * Simulates C = A + B
         * */
        final var Aij = A.get1DAddress(i, j);
        references.add(new PageReference("A", i, j, Aij / pageSize, Aij % pageSize));
        final var Bij = B.get1DAddress(i, j);
        references.add(new PageReference("B", i, j, Bij / pageSize, Bij % pageSize));
        final var Cij = C.get1DAddress(i, j);
        references.add(new PageReference("C", i, j, Cij / pageSize, Cij % pageSize));
      }
    }

    A.free();
    B.free();
    C.free();

    return references;
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
