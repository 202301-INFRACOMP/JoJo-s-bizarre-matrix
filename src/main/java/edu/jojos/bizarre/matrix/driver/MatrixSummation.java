package edu.jojos.bizarre.matrix.driver;

import edu.jojos.bizarre.matrix.data.Matrix;
import edu.jojos.bizarre.matrix.memory.MemorySystem;

public class MatrixSummation {
  private final MemorySystem memorySystem;

  public MatrixSummation(MemorySystem memorySystem) {
    this.memorySystem = memorySystem;
  }

  public void run(int rowSize, int columnSize, int elementSize) {
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
        memorySystem.access(String.format("[A-%d-%d]", i, j), Aij);
        final var Bij = B.get1DAddress(i, j);
        memorySystem.access(String.format("[B-%d-%d]", i, j), Bij);
        final var Cij = C.get1DAddress(i, j);
        memorySystem.access(String.format("[C-%d-%d]", i, j), Cij);
      }
    }
    A.free();
    B.free();
    C.free();
  }
}
