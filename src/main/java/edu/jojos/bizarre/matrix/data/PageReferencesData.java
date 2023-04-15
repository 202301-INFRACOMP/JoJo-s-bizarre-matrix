package edu.jojos.bizarre.matrix.data;

import edu.jojos.bizarre.matrix.paging.reference.PageReference;
import java.util.List;

public record PageReferencesData(
    int pageSize, int rowSize, int columnSize, List<PageReference> pageReferences) {}
