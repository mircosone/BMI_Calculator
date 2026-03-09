package BMI_Master_MircoElong;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.PrintStream;

public class Report {
    private static final PrintStream p = System.out;
    private static final String AUTHOR = "by Mirco Elong";
    private static final String BORDER = "_".repeat(94);

    public static void choiceMessage() {
        System.out.printf(
                "My CSC 215 BMI Calculator Projects:" +
                        "%n   1. BMI, English" +
                        "%n   2. BMI, Metric" +
                        "%n%n[ USER MANUAL ] Enter an exclamation mark ! to end."
        );
    }

    public static void welcomeMessage(Data user) {
        String welcomeNote = "Welcome to:";
        String projectTitle = "BODY MASS INDEX (BMI) Computation, CSC 215,";

        p.printf(
                "%s" +
                        "%n-- %10s" +
                        "%n-- %54s %s" +
                        "%n-- %80s" +
                        "%n%s%n%n",
                BORDER, welcomeNote, projectTitle, user.getVersion(), AUTHOR, BORDER
        );
    }

    public static void makeReport(Data user) {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm:ss a");

        String title_01 = "SUMMARY REPORT for";
        String title_02 = "Date and Time:";
        String title_03 = "BMI:";
        String title_04 = "Weight Status:";

        p.printf(
                "%n-- %s %s%n" +
                        "-- %-20s %s%n" +
                        "-- %-20s %.6f (or %.1f if rounded)%n" +
                        "-- %-20s %s%n%n",
                title_01, user.getName().toUpperCase(),
                title_02, date.format(format),
                title_03, user.getBMI(), user.getBMI(),
                title_04, user.getStatus()
        );
    }

    public static void makeTable(Data user) {
        ArrayList<ArrayList<String>> tables = Calculation.makeArray(user);

        int iteration = tables.get(1).size();
        int lenWeight = tables.get(0).get(0).length();
        int lenBMI = tables.get(0).get(1).length();
        int lenStatus = tables.get(0).get(2).length();
        int lenTag = 6;

        final String YELLOW = "\u001B[43m";
        final String BLACK = "\u001B[30m";
        final String RESET = "\u001B[0m";

        for (String weight : tables.get(1)) {
            if (weight.length() > lenWeight) {
                lenWeight = weight.length();
            }
        }

        for (String bmi : tables.get(2)) {
            if (bmi.length() > lenBMI) {
                lenBMI = bmi.length();
            }
        }

        for (String status : tables.get(3)) {
            if (status.length() > lenStatus) {
                lenStatus = status.length();
            }
        }

        lenWeight += 4;
        lenBMI += 2;

        String lines = "_".repeat(2 + lenWeight + 3 + lenBMI + 3 + lenStatus + 3 + lenTag + 3);
        String midLines = "_".repeat(2 + lenWeight + 3 + lenBMI + 3 + lenStatus + 3 + lenTag + 1);

        p.println("\n" + lines);
        p.printf(
                "|  %-" + lenWeight + "s |  %-" + lenBMI + "s |  %-" + lenStatus + "s  %-" + lenTag + "s|%n",
                tables.get(0).get(0), tables.get(0).get(1), tables.get(0).get(2), ""
        );
        p.println(" " + midLines);

        for (int row = 0; row < iteration; row++) {
            String plainTag = (row == 0) ? "(LOW)" : (row == iteration - 1) ? "(HIGH)" : "";
            String tag = plainTag.isEmpty() ? "" : YELLOW + BLACK + plainTag + RESET;

            p.printf(
                    "|  %-" + lenWeight + "s |  %-" + lenBMI + "s |  %-" + lenStatus + "s ",
                    tables.get(1).get(row),
                    tables.get(2).get(row),
                    tables.get(3).get(row)
            );

            if (plainTag.isEmpty()) {
                p.printf("%-" + lenTag + "s", "");
            } else {
                p.print(tag);
                p.print(" ".repeat(lenTag - plainTag.length()));
            }

            p.println(" |");
        }

        p.println(lines);
    }

    public static void exitMessage(Data user) {
        String slogan = switch (user.getName()) {

            case "Otto.Minion" -> "Popaye";
            case "Minnie Mouse" -> "Ear-esistible";
            case "Baymax Hamada" -> "Sayonara";
            case "Goofy Dog" -> "Woof Woof";
            default -> "See ya";
        };

        p.printf("%n%n%nThe SFSU Mashouf Wellness Center is at 755 Font Blvd.");
        p.printf("%n%n%s", BORDER);
        p.printf("%n-- Thank you for using my program, %s!", user.getName());
        p.printf("%n-- %s!!!", slogan);
        p.printf("%n%s%n", BORDER);
    }
}
