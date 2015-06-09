package com.justinrmiller.pojocsv.pojo;

public class User {
    private String firstName;
    private String lastName;
    private Integer age;
    private Float score;

    public User(String firstName, String lastName, Integer age, Float score) {
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

    public int getAge() {
        return age;
    }

    public float getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }
}
