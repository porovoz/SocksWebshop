package com.bestapp.coursework03sockswebshop.model;

public enum Color {
    BLACK("Black"),
    WHITE("White"),
    GREY("Grey"),
    RED("Red"),
    YELLOW("Yellow");

    private final String text;

    Color(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
