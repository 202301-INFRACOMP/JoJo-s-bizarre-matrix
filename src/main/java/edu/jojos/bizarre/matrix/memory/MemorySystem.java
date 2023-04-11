package edu.jojos.bizarre.matrix.memory;

import java.nio.file.Path;

public interface MemorySystem {
  long malloc(long byteSize);

  void free(long ptr);

  void access(String metadata, long ptr);

  void save(Path p, String header);
}
