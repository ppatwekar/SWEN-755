package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

public class Book {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int price;

    public Book(String name, int price) {
        this.name = name;
        this.price = price;
    }

}
