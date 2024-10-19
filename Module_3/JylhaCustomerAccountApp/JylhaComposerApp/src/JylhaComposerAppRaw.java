import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Composer class
class Composer {
    private int id;
    private String name;
    private String genre;

    // No-argument constructor
    public Composer() {
        this.id = 0;
        this.name = "";
        this.genre = "";
    }

    // Argument constructor
    public Composer(int id, String name, String genre) {
        this.id = id;
        this.name = name;
        this.genre = genre;
    }

    // Accessor methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    // Override toString method
    @Override
    public String toString() {
        return "ID: " + id + "\nName: " + name + "\nGenre: " + genre;
    }
}

// GenericDao interface
interface GenericDao<T, ID> {
    List<T> findAll();
    T findById(ID id);
    void insert(T entity);
}

// ComposerDao interface extending GenericDao
interface ComposerDao extends GenericDao<Composer, Integer> {}

// MemComposerDao class implementing ComposerDao
class MemComposerDao implements ComposerDao {
    private List<Composer> composers;

    // No-argument constructor initializing with five composers
    public MemComposerDao() {
        composers = new ArrayList<>();
        composers.add(new Composer(1, "Ludwig van Beethoven", "Classical"));
        composers.add(new Composer(2, "Johann Sebastian Bach", "Baroque"));
        composers.add(new Composer(3, "Wolfgang Amadeus Mozart", "Classical"));
        composers.add(new Composer(4, "Frédéric Chopin", "Romantic"));
        composers.add(new Composer(5, "Igor Stravinsky", "20th Century"));
    }

    // Override findAll method
    @Override
    public List<Composer> findAll() {
        return composers;
    }

    // Override findById method
    @Override
    public Composer findById(Integer id) {
        for (Composer composer : composers) {
            if (composer.getId() == id) {
                return composer;
            }
        }
        return null;
    }

    // Override insert method
    @Override
    public void insert(Composer composer) {
        composers.add(composer);
    }
}

// TestComposerApp class
public class TestComposerApp {
    public static void main(String[] args) {
        MemComposerDao composerDao = new MemComposerDao();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Display menu
            System.out.println("\nComposer Database Menu:");
            System.out.println("1. View all composers");
            System.out.println("2. Find composer by ID");
            System.out.println("3. Add a new composer");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (choice) {
                case 1:
                    // Display all composers
                    System.out.println("\nList of composers:");
                    for (Composer composer : composerDao.findAll()) {
                        System.out.println(composer + "\n");
                    }
                    break;

                case 2:
                    // Find composer by ID
                    System.out.print("Enter composer ID: ");
                    int id = scanner.nextInt();
                    Composer composer = composerDao.findById(id);
                    if (composer != null) {
                        System.out.println("\nComposer found:\n" + composer);
                    } else {
                        System.out.println("Composer not found.");
                    }
                    break;

                case 3:
                    // Add a new composer
                    System.out.print("Enter composer ID: ");
                    int newId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    System.out.print("Enter composer name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter composer genre: ");
                    String genre = scanner.nextLine();

                    composerDao.insert(new Composer(newId, name, genre));
                    System.out.println("New composer added.");
                    break;

                case 4:
                    // Exit
                    System.out.println("Exiting program.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        scanner.close();
    }
}