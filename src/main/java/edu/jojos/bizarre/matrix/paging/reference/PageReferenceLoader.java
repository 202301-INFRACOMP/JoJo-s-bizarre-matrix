package edu.jojos.bizarre.matrix.paging.reference;

import edu.jojos.bizarre.matrix.data.PageReferencesData;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PageReferenceLoader {

  public PageReferenceLoader() {}

  public PageReferencesData load(Path p) {
    List<PageReference> pageReferences = null;
    try (var br = Files.newBufferedReader(p)) {
      var pageSize = loadHeader("TP", br.readLine());
      var rowSize = loadHeader("NF", br.readLine());
      var columnSize = loadHeader("NC", br.readLine());
      var referenceCount = loadHeader("NR", br.readLine());

      pageReferences = new ArrayList<>(referenceCount);
      for (int i = 0; i < referenceCount; i++) {
        var line = br.readLine();
        pageReferences.add(loadPageReference(line));
      }

      return new PageReferencesData(pageSize, rowSize, columnSize, pageReferences);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private int loadHeader(String header, String line) {
    var equalsIndex = line.indexOf('=');

    if (equalsIndex == -1) {
      throw new RuntimeException(
          "Error reading intermediate file. expected a line in the form <key>=<argument>");
    }

    var key = line.substring(0, equalsIndex);
    if (!header.equals(key)) {
      throw new RuntimeException(
          String.format("Error reading intermediate file. %s expected, found %s", header, key));
    }
    var argument = line.substring(equalsIndex + 1);
    return Integer.parseInt(argument);
  }

  private PageReference loadPageReference(String line) {
    var f = false;

    var fci = line.indexOf(',');
    var sci = line.indexOf(',', fci + 1);

    var metadata = line.substring(0, fci);
    var pageNumber = Integer.parseInt(line.substring(fci + 1, sci));
    var pageOffset = Integer.parseInt(line.substring(sci + 1));

    return new PageReference(metadata, pageNumber, pageOffset);
  }
}
