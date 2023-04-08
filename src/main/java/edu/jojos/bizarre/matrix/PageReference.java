package edu.jojos.bizarre.matrix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public record PageReference(String id, int row, int column, int pageNumber, int pageOffset) {

  public static List<PageReference> generate(
      int rowCount, int columnCount, int elementSize, int pageSize) {
    final var references = new ArrayList<PageReference>();
    int currentPage = 0;
    int pageOffset = 0;
    for (int i = 0; i < rowCount; i++) {
      for (int j = 0; j < columnCount; j++) {
        /*
         * read A
         * read B
         * write C
         *
         * Simulates C = A + B
         * */
        var remainingA = elementSize;
        while (remainingA > 0) {
          if (pageOffset >= pageSize) {
            currentPage++;
            pageOffset = 0;
          }
          references.add(new PageReference("A", i, j, currentPage, pageOffset));
          remainingA -= pageSize;
          pageOffset += elementSize;
        }

        var remainingB = elementSize;
        while (remainingB > 0) {
          if (pageOffset >= pageSize) {
            currentPage++;
            pageOffset = 0;
          }
          references.add(new PageReference("B", i, j, currentPage, pageOffset));
          remainingB -= pageSize;
          pageOffset += elementSize;
        }

        var remainingC = elementSize;
        while (remainingC > 0) {
          if (pageOffset >= pageSize) {
            currentPage++;
            pageOffset = 0;
          }
          references.add(new PageReference("C", i, j, currentPage, pageOffset));
          remainingC -= pageSize;
          pageOffset += elementSize;
        }
      }
    }

    return references;
  }

  static void save(
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
