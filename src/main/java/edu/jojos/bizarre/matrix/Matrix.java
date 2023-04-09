package edu.jojos.bizarre.matrix;

import edu.jojos.bizarre.matrix.memory.MemorySystem;

public class Matrix {
  final long rowSize;
  final long columnSize;

  final long elementSize;

  final long ptr;

  final MemorySystem memorySystem;

  public Matrix(long rowSize, long columnSize, long elementSize, MemorySystem memorySystem) {
    this.rowSize = rowSize;
    this.columnSize = columnSize;
    this.elementSize = elementSize;
    this.memorySystem = memorySystem;
    this.ptr = memorySystem.malloc(rowSize * columnSize * elementSize);
  }

  public void free() {
    memorySystem.free(ptr);
  }

  public long get1DAddress(long i, long j) {
    return elementSize * ((i * columnSize) + j) + ptr;
  }
}
