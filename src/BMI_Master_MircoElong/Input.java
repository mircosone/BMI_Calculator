package BMI_Master_MircoElong;

import java.util.Scanner;
import java.io.PrintStream;

public class Input {

    private static final PrintStream p = System.out;
    private static final Scanner input = new Scanner(System.in);

    public static String inputValue(Scanner input, String message) {

        p.printf(message);

        String value = input.nextLine().trim();

        if (value.equals("!")) {
            p.println("Program terminated!");
            System.exit(0);
        }

        return value;
    }

    public static double inputDouble(Scanner input, String message) {

        return Double.parseDouble(inputValue(input, message));
    }

    public static void choiceInput() {

        //noinspection WriteOnlyObject
        Data user = new Data();

        String answer = inputValue(input,
                "%nPlease enter the version you want to try: "
                                    ).toLowerCase().replace(
                                                " ", "").replaceAll("(.)\\1+", "$1"
                                                            );

        if (answer.contains("english")) {
            user.setVersion("English version");
            user.setStep(5.5);
            new BMIEnglish().english(user);
        }
        else if (answer.contains("metric")) {
            user.setVersion("Metric version");
            user.setStep(2.5);
            new BMIMetric().metric(user);
        }
    }

    public static void dataInputEnglish(Data user) {

        user.setName(inputValue(input, "Please enter your full name: "));

        user.setHeight(Calculation.englishHeightCalc(input, user));

        user.setWeight(inputDouble(input, String.format("Please enter weight in pounds for %s: ", user.getName())));

        user.setBMI(Calculation.findBMI(user, user.getWeight()));
        user.setStatus(Calculation.findStatus(user.getBMI()));
    }

    public static void dataInputMetric(Data user) {

        user.setName(inputValue(input, "Please enter your full name: "));

        user.setHeight(Calculation.metricHeightCalc(input, user));

        user.setWeight(inputDouble(input, String.format("Please enter weight in kilograms for %s: ", user.getName())));

        user.setBMI(Calculation.findBMI(user, user.getWeight()));
        user.setStatus(Calculation.findStatus(user.getBMI()));
    }

    public static double[] lowHighInput(Data user) {

        String version = ("English version".equals(user.getVersion()))? "pounds" : "kilograms";
        String name = user.getName();

        double lowWeight = inputDouble(input, String.format("Please enter a LOW weight in %s for %s: ", version, name));
        double highWeight = inputDouble(input, String.format("Please enter a HIGH weight in %s for %s: ", version, name));

        return new double[]{lowWeight, highWeight};
    }
}

