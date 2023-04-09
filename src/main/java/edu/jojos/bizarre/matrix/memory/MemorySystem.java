package edu.jojos.bizarre.matrix.memory;

public interface MemorySystem {
  long malloc(long byteSize);

  void free(long ptr);
}
