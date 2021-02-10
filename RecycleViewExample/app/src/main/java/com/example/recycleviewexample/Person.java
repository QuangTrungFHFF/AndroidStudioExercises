package com.example.recycleviewexample;

public class Person {
    private String name;
    private boolean isMale;

    public Person(String name, boolean isMale)
    {
        this.name = name;
        this.isMale = isMale;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean getMale()
    {
        return isMale;
    }

    public void setMale(boolean isMale)
    {
        this.isMale = isMale;
    }

    public boolean isMale() {
        return isMale;
    }
}
