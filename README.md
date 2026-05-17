# IntelliGym Pro v1.4

A clean, interactive Java Swing desktop application that dynamically generates personalized workout routines. It utilizes a dynamic 7-column CSV exercise database to suggest age-safe, equipment-tailored fitness plans.

## 🚀 Key Features

- **Age-Based Safety Filtering:** Uses database fields (`MinAge`, `MaxRecommendedAge`) to exclude exercises that are not age-appropriate.
- **Dynamic Database Parsing:** Read directly from `exercises.csv` to scale the workout library without needing to recompile code.
- **Equipment Matching:** Tailors plans based on active selection (e.g. Barbell, Kettlebell, Smith Machine, Cable, Leg Press).
- **Session Duration Customization:** Focuses on increasing physical exercise **variation** (up to 8 moves) as duration increases (1.0, 1.5, or 2.0 hours) instead of simply adding raw sets.
- **Training Splits:** Supports dynamic generation of PPL (Push/Pull/Legs) and Bro Splits across 3 to 6 training days per week.
- **High-Contrast Dark Theme:** Elegant dark-mode UI customized specifically for clear text visibility on Linux.

## 📂 Project Structure

```
gym-workout-planner/
│
├── exercises.csv           # Exercise Database (7 columns)
├── src/
│   ├── GymGUI.java         # Desktop GUI Entry Point
│   ├── model/
│   │   └── User.java       # User Profile Data Structure
│   └── service/
│       └── WorkoutService.java # Selection logic & CSV File Parsing
└── README.md               # Setup Guide


```

## 🛠️ Compilation & Execution

Open your Linux terminal, navigate to the `gym-workout-planner` root directory, and run:

1. **Compile all files:**
  ```
  javac src/model/*.java src/service/*.java src/GymGUI.java


  ```
2. **Run the application:**
  ```
  java -cp src GymGUI


  ```

## 📋 Database Schema

Your `exercises.csv` must follow this structure:

```
Muscle,Machine,ExerciseName,MinAge,MaxRecommendedAge,ImpactLevel,SkillLevel
Chest,Barbell,Barbell Bench Press,16,60,Medium,Intermediate
...


```

*Developed as part of the Gym Web Development Project.*