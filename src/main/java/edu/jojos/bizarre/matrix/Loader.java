package edu.jojos.bizarre.matrix;

import edu.jojos.bizarre.matrix.paging.PageRequest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Loader {
    private List<PageRequest> requests;
    public Loader(String inputFileName) throws IOException {

        //reads input file and creates a list of PageRequests
        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));

        while (reader.readLine() != null) {
            reader.readLine();
            String[] line = reader.readLine().split(" ");
            long pageNumber = Long.parseLong(line[0]);
            long pageOffset = Long.parseLong(line[1]);
            String metadata = reader.readLine();
            requests.add(new PageRequest(pageNumber, pageOffset));
        }

    }

}
