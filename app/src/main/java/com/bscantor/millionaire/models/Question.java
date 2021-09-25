package com.bscantor.millionaire.models;

import java.util.ArrayList;
import java.util.List;

public class Question {
    public String name;
    public List<String> answers;
    public int correct;

    @Override
    public String toString() {
        return "Question{" +
                ", name='" + name + '\'' +
                ", answers=" + answers +
                ", correct=" + correct +
                '}';
    }
}
