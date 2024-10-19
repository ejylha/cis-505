public class Product {
    private String code;
    private String description;
    private double price;

    public Product() {
        this.code = "";
        this.description = "";
        this.price = 0.0;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product Code: " + code + "\nDescription: " + description + "\nPrice: $" + price;
    }
}

