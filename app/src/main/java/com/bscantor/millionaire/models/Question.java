package com.bscantor.millionaire.models;

import java.util.ArrayList;

public class Question {

    private String name;
    private ArrayList<Answer> answers = new ArrayList<Answer>();

    public Question() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }


}
