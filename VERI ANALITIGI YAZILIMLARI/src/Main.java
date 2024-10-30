import java.text.NumberFormat;
import java.util.Scanner;
public class Main{
    public static void main(String[] args) {
        final byte MONTHS_IN_YEAR = 12;
        final byte PERCENT = 100;
        Scanner scanner = new Scanner(System.in);

        int principal = 0;
        float annualInterestRate = 0;
        int period = 0;


        principal = readPrincipal(scanner);
        annualInterestRate = readInterestRate(scanner);
        period = readPeriod(scanner);


        float monthlyInterestRate = annualInterestRate / PERCENT / MONTHS_IN_YEAR;
        int numberOfPayments = period * MONTHS_IN_YEAR;

        double mortgage = calculateMortgage(principal, monthlyInterestRate, numberOfPayments);


        displayMortgage(mortgage, numberOfPayments, principal);


        printAmortizationScheduleWithAnalysis(principal, monthlyInterestRate, numberOfPayments, mortgage);
    }

    private static int readPrincipal(Scanner scanner) {
        int principal;
        while (true) {
            System.out.print("PRINCIPAL ($1K - $1M): ");
            if (scanner.hasNextInt()) {
                principal = scanner.nextInt();
                if (principal >= 1000 && principal <= 1_000_000)
                    return principal;
                System.out.println("Enter a number between 1,000 and 1,000,000.");
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
        }
    }

    private static float readInterestRate(Scanner scanner) {
        float annualInterestRate;
        while (true) {
            System.out.print("ANNUAL INTEREST RATE (0-30): ");
            if (scanner.hasNextFloat()) {
                annualInterestRate = scanner.nextFloat();
                if (annualInterestRate > 0 && annualInterestRate <= 30)
                    return annualInterestRate;
                System.out.println("Enter a value greater than 0 and up to 30.");
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
        }
    }

    private static int readPeriod(Scanner scanner) {
        int period;
        while (true) {
            System.out.print("PERIOD (Years 1-30): ");
            if (scanner.hasNextInt()) {
                period = scanner.nextInt();
                if (period >= 1 && period <= 30)
                    return period;
                System.out.println("Enter a value between 1 and 30.");
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
        }
    }

    public static double calculateMortgage(int principal, float monthlyInterestRate, int numberOfPayments) {
        return principal * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfPayments)) /
                (Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1);
    }

    public static void displayMortgage(double mortgage, int numberOfPayments, int principal) {
        String mortgageFormatted = NumberFormat.getCurrencyInstance().format(mortgage);
        System.out.println("Monthly Payment: " + mortgageFormatted);

        double totalPayment = mortgage * numberOfPayments;
        String totalPaymentFormatted = NumberFormat.getCurrencyInstance().format(totalPayment);
        System.out.println("Total Payment: " + totalPaymentFormatted);

        double totalInterest = totalPayment - principal;
        String totalInterestFormatted = NumberFormat.getCurrencyInstance().format(totalInterest);
        System.out.println("Total Interest Paid: " + totalInterestFormatted);
    }

    public static void printAmortizationScheduleWithAnalysis(int principal, float monthlyInterestRate, int numberOfPayments, double mortgage) {
        System.out.println("\nPayment Schedule with Data Analysis:");
        System.out.println("Month\tInterest\tPrincipal\tRemaining Balance\tCumulative Interest Paid");

        double balance = principal;
        double cumulativeInterest = 0;

        for (int month = 1; month <= numberOfPayments; month++) {
            double interestPayment = balance * monthlyInterestRate;
            double principalPayment = mortgage - interestPayment;
            balance -= principalPayment;
            cumulativeInterest += interestPayment;


            System.out.printf("%d\t%s\t\t%s\t\t%s\t\t%s\n",
                    month,
                    NumberFormat.getCurrencyInstance().format(interestPayment),
                    NumberFormat.getCurrencyInstance().format(principalPayment),
                    NumberFormat.getCurrencyInstance().format(balance),
                    NumberFormat.getCurrencyInstance().format(cumulativeInterest)
            );

            if (balance < 0) break;
        }
    }
}
