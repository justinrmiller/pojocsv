package com.justinrmiller.pojocsv.pojo;

import java.math.BigDecimal;

public class User {
    private String firstName;
    private String lastName;
    private BigDecimal age;
    private Float score;

    public User(String firstName, String lastName, BigDecimal age, Float score) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.score = score;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getAge() {
        return age;
    }

    public float getScore() {
        return score;
    }
}
