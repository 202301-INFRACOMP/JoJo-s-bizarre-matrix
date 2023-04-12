package edu.xname;

import java.util.Random;

public class Simulator {
    private int rowSize;
    private int colSize;
    private int wordSize;
    private MemorySystem memorySystem;


    Random random = new Random();
    public Simulator(int pRowSize, int pColSize,int integerSize, MemorySystem pMemorySystem) {
        rowSize = pRowSize;
        colSize = pColSize;
        memorySystem = pMemorySystem;
        wordSize = integerSize;
    }
    int[][] mat1 = new int[rowSize][colSize];
    int[][] mat2 = new int[rowSize][colSize];
    int[][] mat3 = new int[rowSize][colSize];

    public void run() {

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                mat1[i][j] = random.nextInt(wordSize);
                mat2[i][j] = random.nextInt(wordSize);
            }
        }
    }



}
