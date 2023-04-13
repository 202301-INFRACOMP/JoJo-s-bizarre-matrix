package edu.jojos.bizarre.matrix.iterator;

public interface LiveIterator<T> {
  boolean hasNext();

  T next();

  void feed(T value);

  void terminate();
}
