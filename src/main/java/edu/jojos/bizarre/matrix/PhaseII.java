package edu.jojos.bizarre.matrix;

import static edu.jojos.bizarre.matrix.Main.stdin;

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

    // get intermediate information

    System.out.print("Enter execution page count: ");
    var pageCount = stdin.nextInt();

    // ...
  }
}
