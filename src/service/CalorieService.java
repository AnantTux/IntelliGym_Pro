package service;
import model.User;

public class CalorieService {
    public double calculateBMR(User user) {
        if (user.getGender().equalsIgnoreCase("male")) {
            return (10 * user.getWeight()) + (6.25 * user.getHeight()) - (5 * user.getAge()) + 5;
        } else {
            return (10 * user.getWeight()) + (6.25 * user.getHeight()) - (5 * user.getAge()) - 161;
        }
    }
}
