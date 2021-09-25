package com.bscantor.millionaire.models;

import java.util.ArrayList;

public class Level {

    private int id;
    private ArrayList<Question> questions = new ArrayList<Question>();

    public Level() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
