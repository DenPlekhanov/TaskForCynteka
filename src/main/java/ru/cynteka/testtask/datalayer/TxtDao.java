package ru.cynteka.testtask.datalayer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TxtDao implements InputDao {
    private final Path inputFilePath;

    public TxtDao(String inputFileName) throws URISyntaxException {
        inputFilePath = Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource(inputFileName)).toURI());
    }

    @Override
    public List<List<String>> getParsedListsFromSource() {
        List<String> firstListOfWords = new ArrayList<>();
        List<String> secondListOfWords = new ArrayList<>();
        List<List<String>> resultList = new ArrayList<>();

        try (Stream<String> fileLines = Files.lines(inputFilePath)) {
            int firstQuantityOfLines;
            int secondQuantityOfLines;
            List<String> rawLines = new ArrayList<>();
            fileLines.forEach(rawLines::add);
            firstQuantityOfLines = Integer.parseInt(rawLines.get(0));
            secondQuantityOfLines = Integer.parseInt(rawLines.get(firstQuantityOfLines + 1));

            firstListOfWords = rawLines.stream()
                    .skip(1)
                    .limit(firstQuantityOfLines)
                    .collect(Collectors.toList());

            secondListOfWords = rawLines.stream()
                    .skip(firstQuantityOfLines + 2L)
                    .limit(secondQuantityOfLines)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        resultList.add(firstListOfWords);
        resultList.add(secondListOfWords);
        return resultList;
    }
}
