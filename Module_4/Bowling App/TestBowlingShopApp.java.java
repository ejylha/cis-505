import java.util.Scanner;

public class TestBowlingShopApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.println("Enter product type (b: Ball, s: Shoe, a: Bag) or 'x' to exit:");
            input = scanner.nextLine();

            if (!input.equals("x")) {
                GenericQueue<Product> products = ProductDB.getProducts(input);
                while (!products.isEmpty()) {
                    System.out.println(products.dequeue());
                    System.out.println();
                }
            }

        } while (!input.equals("x"));

        scanner.close();
    }
}
