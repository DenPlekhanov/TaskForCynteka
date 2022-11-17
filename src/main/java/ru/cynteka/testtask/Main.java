package ru.cynteka.testtask;

import ru.cynteka.testtask.datalayer.InputDao;
import ru.cynteka.testtask.datalayer.TxtDao;
import ru.cynteka.testtask.domain.StringsSimilarityFinder;
import ru.cynteka.testtask.presentation.Output;
import ru.cynteka.testtask.presentation.OutputToTxtFileFormatter;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        InputDao inputDao = new TxtDao("input.txt");
        StringsSimilarityFinder similarityChecker = new StringsSimilarityFinder();
        Output outputToFile = new OutputToTxtFileFormatter("output.txt");

        outputToFile.publicResults(similarityChecker.findBestStringsSimilarity(inputDao.getParsedListsFromSource()));
    }
}
