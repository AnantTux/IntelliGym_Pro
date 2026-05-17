package service;
import java.io.*;
import java.util.*;

public class WorkoutService {
    private List<Exercise> database = new ArrayList<>();

    // Internal class to map your new CSV columns
    class Exercise {
        String muscle, machine, name, impact, skill;
        int minAge, maxAge;

        Exercise(String[] row) {
            this.muscle = row[0];
            this.machine = row[1];
            this.name = row[2];
            this.minAge = Integer.parseInt(row[3]);
            this.maxAge = Integer.parseInt(row[4]);
            this.impact = row[5];
            this.skill = row[6];
        }
    }

    public WorkoutService() { loadDatabase(); }

    private void loadDatabase() {
        try (BufferedReader br = new BufferedReader(new FileReader("exercises.csv"))) {
            String line; br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 7) database.add(new Exercise(data));
            }
        } catch (Exception e) { System.err.println("Database load error: " + e.getMessage()); }
    }

    public List<String> generatePlan(String split, int userAge, double hours, List<String> userMachines) {
        List<String> routine = new ArrayList<>();
        
        // Define number of exercises (variations) based on time
        // 1hr = 4 exercises, 1.5hr = 6 exercises, 2hr = 8 exercises
        int exerciseCount = (hours >= 2.0) ? 8 : (hours >= 1.5) ? 6 : 4;

        // Muscles to target based on split
        List<String> targetMuscles = new ArrayList<>();
        if (split.equals("PPL")) {
            targetMuscles.addAll(Arrays.asList("Chest", "Shoulders", "Triceps", "Back", "Biceps", "Legs", "Abs"));
        } else {
            targetMuscles.addAll(Arrays.asList("Chest", "Back", "Legs", "Shoulders", "Biceps", "Triceps", "Abs", "Forearms"));
        }

        // Filter valid exercises based on age and equipment
        List<Exercise> validPool = new ArrayList<>();
        for (Exercise ex : database) {
            if (userAge >= ex.minAge && userAge <= ex.maxAge) {
                if (userMachines.contains(ex.machine) || ex.machine.equals("Bodyweight")) {
                    validPool.add(ex);
                }
            }
        }

        // Pick unique variations
        Collections.shuffle(validPool);
        Set<String> addedExercises = new HashSet<>();
        
        for (Exercise ex : validPool) {
            if (routine.size() >= exerciseCount) break;
            if (!addedExercises.contains(ex.name)) {
                // Fixed sets: focusing on variation, not volume increase
                routine.add(ex.muscle + ": " + ex.name + " (3 Sets)");
                addedExercises.add(ex.name);
            }
        }

        return routine;
    }
}
