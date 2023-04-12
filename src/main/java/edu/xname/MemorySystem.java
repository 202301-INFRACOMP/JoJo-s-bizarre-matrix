package edu.xname;

public interface MemorySystem {
    public long malloc(long size);
    public void free(long ptr);
    public void access(long ptr);
}
