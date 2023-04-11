package edu.jojos.bizarre.matrix;

import static edu.jojos.bizarre.matrix.Main.stdin;

import edu.jojos.bizarre.matrix.memory.FreeListMemory;
import edu.jojos.bizarre.matrix.paging.MatrixSummation;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class PhaseI implements Runnable {
  public PhaseI() {}

  @Override
  public void run() {
    System.out.print("Enter an input file path: ");
    var input = Paths.get(stdin.next());

    Scanner scFile = null;
    try {
      scFile = new Scanner(input);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    var rowSize = scFile.nextInt();
    var columnSize = scFile.nextInt();
    var elementSize = scFile.nextInt();
    var pageSize = scFile.nextInt();

    var memorySystem = new FreeListMemory(pageSize);
    var summation = new MatrixSummation(memorySystem);
    summation.run(rowSize, columnSize, elementSize);

    System.out.print("Enter an intermediate file path: ");
    var intermediate = Paths.get(stdin.next());

    var header =
        String.format(
            """
            TP=%d
            NF=%d
            NC=%d
            """,
            pageSize, rowSize, columnSize);

    memorySystem.save(intermediate, "");
  }
}
