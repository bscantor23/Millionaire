package com.bscantor.millionaire.models;

import java.util.ArrayList;

public class Answer {

    private String name;
    private Boolean correct;

    public Answer() {
    }

    public Answer(String name, Boolean correct) {
        this.name = name;
        this.correct = correct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
}
