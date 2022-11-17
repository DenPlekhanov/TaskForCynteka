package ru.cynteka.testtask.presentation;

import ru.cynteka.testtask.domain.DTO;

import java.io.IOException;
import java.util.List;

public interface Output {
    void publicResults(List<DTO> dtoToPublic) throws IOException;
}