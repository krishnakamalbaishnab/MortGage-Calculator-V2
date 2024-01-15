import java.text.NumberFormat;
import java.util.Scanner;

public class Main {
    // Constants for clarity
    final static byte MONTHS_IN_YEAR = 12;
    final static byte PERCENT = 100;

    public static void main(String[] args) {
        // Get input from the user
        int principal = (int) readNumber("Principal: ", 1000, 1_000_000);
        float annualInterest = (float) readNumber("Annual Interest Rate: ", 1, 30);
        byte years = (byte) readNumber("Period (Years): ", 1, 30);

        // Calculate and display mortgage details
        printMortgage(principal, annualInterest, years);
        // Display payment schedule
        printPaymentSchedule(principal, annualInterest, years);
    }

    // Method to print mortgage details
    private static void printMortgage(int principal, float annualInterest, byte years) {
        double mortgage = calculateMortgage(principal, annualInterest, years);
        String mortgageFormatted = NumberFormat.getCurrencyInstance().format(mortgage);
        System.out.println();
        System.out.println("MORTGAGE");
        System.out.println("--------");
        System.out.println("Monthly Payments: " + mortgageFormatted);
    }

    // Method to print payment schedule
    private static void printPaymentSchedule(int principal, float annualInterest, byte years) {
        System.out.println();
        System.out.println("PAYMENT SCHEDULE");
        System.out.println("----------------");

        // Display monthly balance for each payment
        for (short month = 1; month <= years * MONTHS_IN_YEAR; month++) {
            double balance = calculateBalance(principal, annualInterest, years, month);
            System.out.println(NumberFormat.getCurrencyInstance().format(balance));
        }
    }

    // Method to read a number within a specified range from the user
    public static double readNumber(String prompt, double min, double max) {
        Scanner scanner = new Scanner(System.in);
        double value;
        while (true) {
            System.out.print(prompt);
            value = scanner.nextFloat();
            if (value >= min && value <= max)
                break;
            System.out.println("Enter a value between " + min + " and " + max);
        }
        return value;
    }

    // Method to calculate the remaining balance of the mortgage at a specific point in time
    public static double calculateBalance(
            int principal,
            float annualInterest,
            byte years,
            short numberOfPaymentsMade
    ) {
        // Calculate monthly interest rate and total number of payments
        float monthlyInterest = annualInterest / PERCENT / MONTHS_IN_YEAR;
        float numberOfPayments = years * MONTHS_IN_YEAR;

        // Calculate the remaining balance using the mortgage formula
        double balance = principal
                * (Math.pow(1 + monthlyInterest, numberOfPayments) - Math.pow(1 + monthlyInterest, numberOfPaymentsMade))
                / (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);

        return balance;
    }

    // Method to calculate the monthly mortgage payment
    public static double calculateMortgage(
            int principal,
            float annualInterest,
            byte years) {
        // Calculate monthly interest rate and total number of payments
        float monthlyInterest = annualInterest / PERCENT / MONTHS_IN_YEAR;
        float numberOfPayments = years * MONTHS_IN_YEAR;

        // Calculate the monthly mortgage payment using the mortgage formula
        double mortgage = principal
                * (monthlyInterest * Math.pow(1 + monthlyInterest, numberOfPayments))
                / (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);

        return mortgage;
    }
}
