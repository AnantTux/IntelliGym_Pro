package model;
import java.util.List;

public class WorkoutPlan {
    private double targetCalories;
    private List<String> exercises;

    public WorkoutPlan(double targetCalories, List<String> exercises) {
        this.targetCalories = targetCalories;
        this.exercises = exercises;
    }

    public void displayPlan() {
        System.out.println("\n========== YOUR WORKOUT PLAN ==========");
        System.out.printf("Estimated Daily BMR: %.2f kcal\n", targetCalories);
        System.out.println("Tailored Exercises:");
        if (exercises.isEmpty()) {
            System.out.println(" - No matching exercises found for available machines.");
        } else {
            for (String exercise : exercises) {
                System.out.println(" - " + exercise);
            }
        }
        System.out.println("=======================================");
    }
}
