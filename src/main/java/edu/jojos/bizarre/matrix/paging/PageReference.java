package edu.jojos.bizarre.matrix.paging;

public record PageReference(String id, long row, long column, long pageNumber, long pageOffset) {}
