package com.example.test;
import java.io.Serializable;



public class PersonList implements Serializable {
    private String name = "Unknown";
    private double number = 0;
    private double amount = 0;
    private int mode = 0;

    public PersonList(String name, double number) {
        this.name = name;
        this.number = number;
    }

    public PersonList(double amount, int mode) {
        this.amount = amount;
        this.mode = mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public PersonList(double amount) {
        this.amount = amount;
    }

    public double getNumber() {
        return number;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getMode() {
        return mode;
    }

    @Override
    public String toString() {
        switch (mode) {
            case 0:
                return "Amount : RM " + String.format("%.2f",amount) + " PER PERSON";
            case 1:
                return "Name: " + name
                        + "\nPercentage : " + number + "%"
                        + "\nAmount: RM " + String.format("%.2f",amount)
                        + "\n-------------------\n";
            case 2:
                return "Name: " + name
                        + "\nRatio : " + (int) number
                        + "\nAmount: RM " + String.format("%.2f", amount)
                        + "\n-------------------\n";
            case 3:
                return "Name: " + name
                        + "\nOriginal amount : RM " + number
                        + "\nAdd / Deduct : RM " + String.format("%.2f", amount)
                        + "\n-------------------\n";
        }
        return " ";
    }
}
