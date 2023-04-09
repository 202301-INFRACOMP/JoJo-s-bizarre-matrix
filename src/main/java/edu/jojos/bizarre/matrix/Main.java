package edu.jojos.bizarre.matrix;

import edu.jojos.bizarre.matrix.memory.FreeListMemory;
import edu.jojos.bizarre.matrix.paging.PageReferenceGenerator;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws IOException {
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

    System.out.print("Enter an input file path: ");
    var stdin = new Scanner(System.in);
    var input = stdin.next();
    var inputPath = Paths.get(input);

    var scFile = new Scanner(inputPath);
    var rowSize = scFile.nextInt();
    var columnSize = scFile.nextInt();
    var elementSize = scFile.nextInt();
    var pageSize = scFile.nextInt();
    var executionFrames = scFile.nextInt();

    var generator = new PageReferenceGenerator(new FreeListMemory());
    var references = generator.generate(rowSize, columnSize, elementSize, pageSize);

    System.out.print("Enter an intermediate file path: ");
    var intermediate = stdin.next();
    var intermediatePath = Paths.get(intermediate);
    generator.save(intermediatePath, rowSize, columnSize, pageSize, references);
  }
}
