package org.app.model;

public enum Grade {
    grade_1 ("CEINTURE NOIR"),
    grade_2 ("CEINTURE MARRON"),
    grade_3 ("CEINTURE VIOLET"),
    grade_4 ("CEINTURE BLEU"),
    grade_5 ("CEINTURE BLANC"),
    grade_6("CEINTURE NOIR - ROUGE"),
    grade_7 ("CEINTURE NOIR - BLANCHE"),
    grade_8 ("CEINTURE ROUGE");

    private final String grade;

    Grade(String s) {
        grade = s;
    }

    public String toString(){
        return this.grade;
    }
}
