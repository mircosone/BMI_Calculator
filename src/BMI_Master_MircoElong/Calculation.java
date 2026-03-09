package BMI_Master_MircoElong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Calculation {
    public static ArrayList<ArrayList<String>> makeArray(Data user) {

        ArrayList<ArrayList<String>> tables = new ArrayList<>();
        double[] lowHigh = Input.lowHighInput(user);

        double lowWeight = lowHigh[0];
        double highWeight = lowHigh[1];

        tables.add(new ArrayList<>(Arrays.asList("WEIGHT", "BMI", "WEIGHT STATUS")));
        tables.add(new ArrayList<>(weightCalc(user, lowWeight, highWeight)));
        tables.add(new ArrayList<>(bmiCalc(user, lowWeight, highWeight)));
        tables.add(new ArrayList<>(statusCalc(user, lowWeight, highWeight)));

        return tables;
    }

    public static String findStatus(double bmi) {

        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Healthy Weight";
        } else if (bmi < 30) {
            return "Overweight";
        } else {
            return "Obesity";
        }
    }

    public static double findBMI(Data user, double weight) {

        float height = user.getHeight();

        if ("English version".equals(user.getVersion())) {
            return (weight / (height * height) * 703);
        } else {return (weight / (height * height));}
    }

    public static String formatBMI(double bmi) {

        String status = findStatus(bmi);

        return switch (status) {
            case "Underweight" -> String.format("%.2f", bmi);
            case "Healthy Weight" -> String.format("%.3f", bmi);
            case "Overweight" -> String.format("%.4f", bmi);
            default -> String.format("%.5f", bmi);
        };
    }

    public static ArrayList<String> weightCalc(Data user, double lowWeight, double highWeight) {

        ArrayList<String> weightRange = new ArrayList<>();

        boolean userInside = false;

        double weight = user.getWeight();
        double step = user.getStep();

        for (double low = lowWeight; low <= highWeight + 0.0001; low += step) {
            if (!userInside && weight >= lowWeight && weight <= highWeight && weight < low) {
                weightRange.add(String.format("%.2f", weight));
                userInside = true;
            }

            weightRange.add(String.format("%.2f", low));
        }

        if (!userInside && weight >= lowWeight && weight <= highWeight) {
            weightRange.add(String.format("%.2f", weight));
        }

        double lastStep = weightRange.isEmpty()? -1: Double.parseDouble(weightRange.getLast());

        if (Math.abs(lastStep - highWeight) > 0.0001) {
            weightRange.add(String.format("%.2f", highWeight));
        }

        return weightRange;
    }

    public static ArrayList<String> bmiCalc(Data user, double lowWeight, double highWeight) {

        ArrayList<String> bmiRange = new ArrayList<>();

        boolean userInside = false;

        double weight = user.getWeight();
        double bmi = user.getBMI();
        double step = user.getStep();

        for (double low = lowWeight; low <= highWeight +0.0001; low += step) {

            if (!userInside && weight >= lowWeight && weight <= highWeight && weight < low) {
                bmiRange.add(formatBMI(bmi));
                userInside = true;
            }

            double getBmi = findBMI(user, low);
            bmiRange.add(formatBMI(getBmi));
        }

        if (!userInside && weight >= lowWeight && weight <= highWeight) {
            bmiRange.add(formatBMI(bmi));
        }

        double lastWeight = lowWeight + step * Math.floor((highWeight - lowWeight) / step);
        if (Math.abs(lastWeight - highWeight) > 0.001) {
            bmiRange.add(formatBMI(findBMI(user, highWeight)));
        }

        return bmiRange;
    }

    public static ArrayList<String> statusCalc(Data user, double lowWeight, double highWeight) {

        ArrayList<String> bmiStatus = new ArrayList<>();

        double userWeight = user.getWeight();
        double step = user.getStep();

        boolean userInside = false;

        for (double currWeight = lowWeight; currWeight <= highWeight + 0.0001; currWeight += step) {

            if (!userInside && userWeight >= lowWeight && userWeight <= highWeight && userWeight < currWeight) {
                bmiStatus.add(findStatus(user.getBMI()) + " (this)");
                userInside = true;
            }

            double bmi = findBMI(user, currWeight);
            bmiStatus.add(findStatus(bmi));
        }

        if (!userInside && userWeight >= lowWeight && userWeight <= highWeight) {
            bmiStatus.add(findStatus(user.getBMI()) + " (this)");
        }

        double lastWeight = lowWeight + step * Math.floor((highWeight - lowWeight) / step);

        if (Math.abs(lastWeight - highWeight) > 0.0001) {
            bmiStatus.add(findStatus(findBMI(user, highWeight)));
        }

        return bmiStatus;
    }

    public static float englishHeightCalc(Scanner input, Data user) {
        String rawHeight = Input.inputValue(input,
                String.format("Please enter height in feet and inches for %s: ",
                        user.getName()));

        String[] feetInches = rawHeight.split("[^0-9]+");

        int feet = Integer.parseInt(feetInches[0]);
        int inches = Integer.parseInt(feetInches[1]);

        return (float) (feet * 12 + inches);
    }

    public static float metricHeightCalc(Scanner input, Data user) {
        float rawHeight = Float.parseFloat(
                Input.inputValue(input,
                        String.format("Please enter height in centimeters for %s: ",
                                user.getName())));

        return (float) (rawHeight / 100.0);
    }
}