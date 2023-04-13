package edu.jojos.bizarre.matrix.phase;

import static edu.jojos.bizarre.matrix.Main.stdin;

import edu.jojos.bizarre.matrix.driver.MemorySimulation;
import edu.jojos.bizarre.matrix.paging.reference.PageReferenceLoader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class PhaseII implements Runnable {
  public PhaseII() {}

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
    var pageReferences = loader.load(input);

    System.out.print("Enter execution page count: ");
    var pageCount = stdin.nextInt();

    var simulation = new MemorySimulation(pageReferences, pageCount);
    simulation.run();
  }
}
