package model;

public class User {
    private int age;
    private double weight; // in kg
    private double height; // in cm
    private String gender; // "male" or "female"
    private String goal;   // "Strength" or "Cardio"

    public User(int age, double weight, double height, String gender, String goal) {
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.goal = goal;
    }

    // Getters
    public int getAge() { return age; }
    public double getWeight() { return weight; }
    public double getHeight() { return height; }
    public String getGender() { return gender; }
    public String getGoal() { return goal; }
}
