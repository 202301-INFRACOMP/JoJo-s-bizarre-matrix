package edu.jojos.bizarre.matrix.phase;

import static edu.jojos.bizarre.matrix.Main.stdin;

import edu.jojos.bizarre.matrix.driver.MemorySimulation;
import edu.jojos.bizarre.matrix.paging.PageEntry;
import edu.jojos.bizarre.matrix.paging.reference.PageReferenceLoader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class PhaseII implements Runnable {

  int bitSize;

  public PhaseII(int bitSize) {
    this.bitSize = bitSize;
  }

  @Override
  public void run() {
    System.out.print("Enter an intermediate file path: ");
    var input = Paths.get(stdin.next());

    Scanner scFile = null;
    try {
      scFile = new Scanner(input);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    var loader = new PageReferenceLoader();
    var data = loader.load(input);

    System.out.print("Enter execution page frame count: ");
    var pageFrameCount = stdin.nextInt();

    var pageFrames = new ArrayList<Boolean>(pageFrameCount);
    for (int i = 0; i < pageFrameCount; i++) {
      pageFrames.add(false);
    }

    int pageDirectorySize = (1 << bitSize) / data.pageSize();
    var pageDirectory = new ArrayList<PageEntry>(pageDirectorySize);
    for (int i = 0; i < pageDirectorySize; i++) {
      pageDirectory.add(new PageEntry());
    }

    var simulation = new MemorySimulation(data.pageReferences(), pageFrames, pageDirectory);
    simulation.run();
  }
}
