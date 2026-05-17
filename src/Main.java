import model.User;
import model.WorkoutPlan;
import service.CalorieService;
import service.WorkoutService;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> machineMenu = Arrays.asList("Treadmill", "Barbell", "Dumbbell", "Cycle", "Leg Press");

        System.out.println("--- Advanced Gym Planner ---");
        
        
        System.out.print("Age: "); int age = scanner.nextInt();
        System.out.print("Weight (kg): "); double weight = scanner.nextDouble();
        System.out.print("Height (cm): "); double height = scanner.nextDouble();
        System.out.print("Gender: "); String gender = scanner.next();

        
        System.out.print("\nHow many days can you workout per week? (3-6): ");
        int days = scanner.nextInt();
        
        System.out.println("How many hours can you spare per day?");
        System.out.println("1. 1 Hour\n2. 1.5 Hours\n3. 2 Hours");
        int timeChoice = scanner.nextInt();
        double hours = (timeChoice == 3) ? 2.0 : (timeChoice == 2) ? 1.5 : 1.0;

        System.out.println("\nChoose your Split Style:");
        System.out.println("1. PPL (Push, Pull, Legs - Compound Focus)");
        System.out.println("2. Bro Split (Individual Muscle Groups)");
        int splitChoice = scanner.nextInt();
        String splitType = (splitChoice == 1) ? "PPL" : "BroSplit";

       
        System.out.println("\nSelect machines (e.g., 0,1,2):");
        for (int i = 0; i < machineMenu.size(); i++) System.out.println(i + ". " + machineMenu.get(i));
        System.out.print("Selection: ");
        String selection = scanner.next();
        
        List<String> gymInventory = new ArrayList<>();
        for (String s : selection.split(",")) {
            int idx = Integer.parseInt(s.trim());
            if (idx >= 0 && idx < machineMenu.size()) gymInventory.add(machineMenu.get(idx));
        }


        User user = new User(age, weight, height, gender, splitType);
        CalorieService calorieService = new CalorieService();
        WorkoutService workoutService = new WorkoutService();

        double bmr = calorieService.calculateBMR(user);
        List<String> routine = workoutService.generatePlan(splitType, days, hours, gymInventory);


        WorkoutPlan finalPlan = new WorkoutPlan(bmr, routine);
        finalPlan.displayPlan();
        System.out.println("Session Duration: " + hours + " hours (includes breaks)");
        
        scanner.close();
    }
}
