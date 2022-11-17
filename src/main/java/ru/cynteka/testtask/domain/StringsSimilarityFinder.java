package ru.cynteka.testtask.domain;

import org.apache.commons.text.similarity.FuzzyScore;

import java.util.*;
import java.util.stream.Collectors;

public class StringsSimilarityFinder {

    public List<DTO> findBestStringsSimilarity(List<List<String>> twoListFromSource) {
        List<String> firstList;
        List<String> secondList;
        List<String> sortedFirstList;
        List<String> sortedSecondList;
        List<DTO> dtoList = new ArrayList<>();
        Map<String, DTO> preResultMap = new HashMap<>();
        List<DTO> resultDtoList = new ArrayList<>();

        if (twoListFromSource.size() != 2) {
            throw new IllegalArgumentException("Source must contain 2 Lists of Strings, but it contains : " + twoListFromSource.size() + " lists");
        }
        firstList = twoListFromSource.get(0);
        secondList = twoListFromSource.get(1);

        if (firstList.isEmpty() && secondList.isEmpty()) {
            return resultDtoList;
        }

        firstList.forEach(str -> preResultMap.put(str, new DTO(str, "")));

        Comparator<String> compareByLength = (a, b) -> b.length() - a.length();
        sortedFirstList = firstList.stream().sorted(compareByLength).collect(Collectors.toList());
        sortedSecondList = secondList.stream().sorted(compareByLength).collect(Collectors.toList());

        for (String stringFromList1 : sortedFirstList) {
            for (String stringFromList2 : sortedSecondList) {
                dtoList.add(new DTO(stringFromList1, stringFromList2, checkTwoStringsSimilarity(stringFromList1, stringFromList2))); // Levenshtein distance
//                dtoList.add(new DTO(stringFromList1, stringFromList2, fuzzyCheckSimilarity(stringFromList1, stringFromList2))); // FuzzyScore
            }

            int maxSimilarity = 0;
            if (!dtoList.isEmpty()) {
                maxSimilarity = dtoList.stream()
                        .map(DTO::getSimilarityValue)
                        .sorted(Collections.reverseOrder())
                        .limit(1)
                        .collect(Collectors.toList())
                        .get(0);
            }

            for (DTO dto : dtoList) {
                if (dto.getSimilarityValue() == maxSimilarity && maxSimilarity != 0) {
                    preResultMap.get(stringFromList1).setSecondString(dto.getSecondString());
                    preResultMap.get(stringFromList1).setSimilarityValue(dto.getSimilarityValue());
                    sortedSecondList.remove(preResultMap.get(stringFromList1).getSecondString());
                }
            }
            dtoList.clear();
        }

        for (String str : firstList) {
            if (!Objects.equals(preResultMap.get(str).getSecondString(), "")) {
                resultDtoList.add(new DTO(str,preResultMap.get(str).getSecondString(), preResultMap.get(str).getSimilarityValue()));
            } else if (Objects.equals(preResultMap.get(str).getSecondString(), "")) {
                resultDtoList.add(new DTO(str, "?"));
            }
        }

        for (DTO dto : resultDtoList) {
            String stringToRemove = "";
            for (String str : secondList) {
                if (Objects.equals(dto.getSecondString(), str)){
                    stringToRemove = str;
                }
            }
            secondList.remove(stringToRemove);
        }
        secondList.forEach(s -> resultDtoList.add(new DTO(s, "?")));
        return resultDtoList;
    }

//Нижние два метода - одна из реализаций метода "Levenshtein distance" без использования библиотек.
    /** Calculates the similarity between two strings and return a float number within 0 and 1000000*/
    public int checkTwoStringsSimilarity(String s1, String s2) {
        String longer = s1;
        String shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1000000;
        }
        return (longerLength - editDistance(longer, shorter)) * 1000000 / longerLength;
    }

    // See http://rosettacode.org/wiki/Levenshtein_distance#Java
    public static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }

//Алгоритм FuzzyScore из библиотеки Apache Commons Text. Также выдаёт не все ответы идентичными примерам в вашей задаче. Вызов метода закомментирован.
    public int fuzzyCheckSimilarity(String s1, String s2){
        FuzzyScore score = new FuzzyScore(Locale.ENGLISH);
        return score.fuzzyScore(s1,s2);
    }
}
