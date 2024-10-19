import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CustomerAccountApp {
    
    // Customer Class
    public static class Customer {
        private String name;
        private String address;
        private String city;
        private String zip;

        // No-argument constructor
        public Customer() {
            this.name = "Default Name";
            this.address = "Default Address";
            this.city = "Default City";
            this.zip = "00000";
        }

        // Argument constructor
        public Customer(String name, String address, String city, String zip) {
            this.name = name;
            this.address = address;
            this.city = city;
            this.zip = zip;
        }

        // Accessor methods
        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public String getCity() {
            return city;
        }

        public String getZip() {
            return zip;
        }

        // toString method
        @Override
        public String toString() {
            return "Name: " + name + "\n" +
                   "Address: " + address + "\n" +
                   "City: " + city + "\n" +
                   "ZIP: " + zip;
        }
    }

    // CustomerDB Class
    public static class CustomerDB {
        public static Customer getCustomer(int id) {
            if (id == 1007) {
                return new Customer("Alice Smith", "123 Elm St", "Springfield", "12345");
            } else if (id == 1008) {
                return new Customer("Bob Johnson", "456 Oak St", "Shelbyville", "67890");
            } else if (id == 1009) {
                return new Customer("Charlie Brown", "789 Pine St", "Capital City", "10112");
            } else {
                return new Customer(); // Default customer
            }
        }
    }

    // Account Class
    public static class Account {
        private double balance = 200.0;

        public double getBalance() {
            return balance;
        }

        public void deposit(double amt) {
            if (amt > 0) {
                balance += amt;
            }
        }

        public void withdraw(double amt) {
            if (amt > 0 && balance >= amt) {
                balance -= amt;
            }
        }

        public void displayMenu() {
            System.out.println("Account Menu:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");
        }

        public String getTransactionDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            return sdf.format(new Date());
        }
    }

    // Main TestCustomerAccountApp Class
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a customer number (1007-1009): ");
        int customerId = scanner.nextInt();
        
        Customer customer = CustomerDB.getCustomer(customerId);
        Account account = new Account();

        int choice;
        do {
            account.displayMenu();
            System.out.print("Select an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.printf("Current balance: $%,.2f\n", account.getBalance());
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Error: Invalid Option.");
            }
        } while (choice != 4);

        System.out.println("\nCustomer Details:");
        System.out.println(customer);
        System.out.printf("Account Balance: $%,.2f\n", account.getBalance());
        
        scanner.close();
    }
}
