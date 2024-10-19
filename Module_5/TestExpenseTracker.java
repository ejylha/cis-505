import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Transaction {
    private String date;
    private String description;
    private double amount;


    public Transaction() {
        this.date = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
        this.description = "";
        this.amount = 0.0;
    }

    // Argument constructor
    public Transaction(String date, String description, double amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Date: " + date + "\nDescription: " + description + "\nAmount: $" + String.format("%.2f", amount);
    }
}

class ValidatorIO {
    public static int getInt(Scanner sc, String prompt) {
        int input = 0;
        boolean isValid = false;

        while (!isValid) {
            System.out.println(prompt);
            if (sc.hasNextInt()) {
                input = sc.nextInt();
                isValid = true;
            } else {
                System.out.println("Error! Invalid integer value.");
                sc.next(); 
            }
        }
        return input;
    }

    public static double getDouble(Scanner sc, String prompt) {
        double input = 0;
        boolean isValid = false;

        while (!isValid) {
            System.out.println(prompt);
            if (sc.hasNextDouble()) {
                input = sc.nextDouble();
                isValid = true;
            } else {
                System.out.println("Error! Invalid double value.");
                sc.next(); 
            }
        }
        return input;
    }

    public static String getString(Scanner sc, String prompt) {
        System.out.println(prompt);
        return sc.next();
    }
}

class TransactionIO {
    private static final String FILE_NAME = "expenses.txt";
    private File file = new File(FILE_NAME);

    public static void bulkInsert(ArrayList<Transaction> transactions) throws IOException {
        PrintWriter output = null;
        try {
            output = new PrintWriter(new FileWriter(FILE_NAME, true)); // append mode
            for (Transaction transaction : transactions) {
                output.println(transaction.getDate() + "," + transaction.getDescription() + "," + transaction.getAmount());
            }
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

    public static ArrayList<Transaction> findAll() throws IOException {
        ArrayList<Transaction> transactions = new ArrayList<>();
        File file = new File(FILE_NAME);
        Scanner input = new Scanner(file);

        while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] parts = line.split(",");
            if (parts.length == 3) {
                Transaction transaction = new Transaction(parts[0], parts[1], Double.parseDouble(parts[2]));
                transactions.add(transaction);
            }
        }
        input.close();
        return transactions;
    }
}

public class TestExpenseTracker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        ArrayList<Transaction> transactions = new ArrayList<>();

        do {
            System.out.println("1. View All Transactions");
            System.out.println("2. Add a Transaction");
            System.out.println("3. View Total Expenses");
            System.out.println("4. Exit");
            choice = ValidatorIO.getInt(sc, "Choose an option:");

            switch (choice) {
                case 1:
                    try {
                        transactions = TransactionIO.findAll();
                        for (Transaction t : transactions) {
                            System.out.println(t);
                        }
                    } catch (IOException e) {
                        System.out.println("Error reading transactions: " + e.getMessage());
                    }
                    break;

                case 2:
                    boolean addMore = true;
                    while (addMore) {
                        String date = ValidatorIO.getString(sc, "Enter the date (MM-dd-yyyy):");
                        String description = ValidatorIO.getString(sc, "Enter a description:");
                        double amount = ValidatorIO.getDouble(sc, "Enter the amount:");

                        Transaction transaction = new Transaction(date, description, amount);
                        transactions.add(transaction);

                        System.out.println("Add another transaction? (yes/no)");
                        addMore = sc.next().equalsIgnoreCase("yes");
                    }
                    try {
                        TransactionIO.bulkInsert(transactions);
                    } catch (IOException e) {
                        System.out.println("Error writing transactions: " + e.getMessage());
                    }
                    break;

                case 3:
                    double total = 0;
                    try {
                        transactions = TransactionIO.findAll();
                        for (Transaction t : transactions) {
                            total += t.getAmount();
                        }
                        System.out.printf("Total Expenses: $%,.2f\n", total);
                    } catch (IOException e) {
                        System.out.println("Error reading transactions: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        } while (choice != 4);

        sc.close();
    }
}
