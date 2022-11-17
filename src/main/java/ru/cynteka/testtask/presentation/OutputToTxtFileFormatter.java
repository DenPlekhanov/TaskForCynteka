package ru.cynteka.testtask.presentation;

import ru.cynteka.testtask.domain.DTO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;



public class OutputToTxtFileFormatter implements Output {
    Path outputPath;

    public OutputToTxtFileFormatter(String outputFileName) {
        outputPath = Path.of(outputFileName);
    }

    @Override
    public void publicResults(List<DTO> dtoListToPublic) throws IOException {
        StringBuilder sb = new StringBuilder();

        for (DTO dto : dtoListToPublic) {
            sb.append(dto.getFirstString());
            sb.append(":");
            sb.append(dto.getSecondString());
            sb.append("\n");
        }

        Files.writeString(outputPath, sb.toString(), StandardCharsets.UTF_8);
    }
}
