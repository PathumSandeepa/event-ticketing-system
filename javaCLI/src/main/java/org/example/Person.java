package org.example;

//person abstract class (extends: vendor, Customer)
public abstract class Person {
    protected String id;
    protected String name;

    public Person() {}

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}