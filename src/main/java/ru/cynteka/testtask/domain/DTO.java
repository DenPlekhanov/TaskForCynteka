package ru.cynteka.testtask.domain;

/**
 * Container for a pair of strings and their similarity value
 */
public class DTO {
    private String firstString;
    private String secondString;
    private int similarityValue;

    public DTO(String firstString, String secondString) {
        this.firstString = firstString;
        this.secondString = secondString;
    }

    public DTO(String firstString, String secondString, int similarityValue) {
        this.firstString = firstString;
        this.secondString = secondString;
        this.similarityValue = similarityValue;
    }

    @Override
    public String toString() {
        return "DTO{" +
                "firstString='" + firstString + '\'' +
                ", secondString='" + secondString + '\'' +
                ", similarityValue=" + similarityValue +
                '}';
    }

    public String getFirstString() {
        return firstString;
    }

    public void setFirstString(String firstString) {
        this.firstString = firstString;
    }

    public String getSecondString() {
        return secondString;
    }

    public void setSecondString(String secondString) {
        this.secondString = secondString;
    }

    public int getSimilarityValue() {
        return similarityValue;
    }

    public void setSimilarityValue(int similarityValue) {
        this.similarityValue = similarityValue;
    }
}
