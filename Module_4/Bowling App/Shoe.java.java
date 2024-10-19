public class Shoe extends Product {
    private double size;

    public Shoe() {
        super();
        this.size = 0.0;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSize: " + size;
    }
}
