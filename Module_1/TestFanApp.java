public class TestFanApp {
    public static void main(String[] args) {
        Fan defaultFan = new Fan();
        System.out.println("Default Fan:");
        System.out.println(defaultFan.toString());
        Fan customFan = new Fan(Fan.FAST, true, 8, "blue");
        System.out.println("\nCustom Fan:");
        System.out.println(customFan.toString());
    }
}
