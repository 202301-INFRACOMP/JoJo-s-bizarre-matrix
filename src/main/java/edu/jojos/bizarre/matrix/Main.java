package edu.jojos.bizarre.matrix;

import edu.jojos.bizarre.matrix.phase.PhaseI;
import edu.jojos.bizarre.matrix.phase.PhaseII;
import java.util.Scanner;

public class Main {
  public static Scanner stdin = new Scanner(System.in);

  public static int bitSize = 16;

  public static void main(String[] args) {
    System.out.print(
        """
                        Welcome to
                              __        __  .  __      __    __       __   __   ___\s
                           | /  \\    | /  \\ ' /__`    |__) |  /  /\\  |__) |__) |__ \s
                        \\__/ \\__/ \\__/ \\__/   .__/    |__) | /_ /~~\\ |  \\ |  \\ |___\s
                                                                                   \s
                                   ___  __                                         \s
                         |\\/|  /\\   |  |__) | \\_/                                  \s
                         |  | /~~\\  |  |  \\ | / \\                                  \s
                                                                                   \s
                        """);
    System.out.println("Available options");
    System.out.println("1 - Phase I");
    System.out.println("2 - Phase II");
    System.out.print("Enter the phase option: ");

    long a = (1L << 64L) - 1L;

    long b = 0L;

    var phase = stdin.nextInt();
    switch (phase) {
      case 1:
        var p1 = new PhaseI();
        p1.run();
        break;
      case 2:
        var p2 = new PhaseII(bitSize);
        p2.run();
        break;
      default:
        throw new RuntimeException("Invalid option");
    }
  }
}
