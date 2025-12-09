import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JComboBox;




//ryan's file, i added functions to write and read data to a text file


public class DataManager {
    private static final String DATA_FILE = "PandaBelly/src/data.txt";

    // Load: each line is category,name,price,quantity
    public static void loadData(categoryStorage storage, JComboBox<String> dropdown) throws IOException {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            // No data file yet, add default "None" category
            storage.addCategory(new Storage("None"));
            dropdown.addItem("None");
            return;
        }
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String[] parts = scanner.nextLine().split(",");
            if (!storage.categoryExists(parts[0])) {
                storage.addCategory(new Storage(parts[0]));
                dropdown.addItem(parts[0]);
            }
            if (parts.length == 4) {
                for (Storage s : storage.getMainStorage())
                    if (s.getCName().equals(parts[0]))
                        s.addItem(parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]));
            }
        }
        scanner.close();
    }

    // Save: each line is category,name,price,quantity
    public static void saveData(categoryStorage storage) {
        try { 
            PrintWriter writer = new PrintWriter(DATA_FILE);
            for (Storage s : storage.getMainStorage()) {
                if (s.getCategory().isEmpty()) writer.println(s.getCName());
                for (Item item : s.getCategory()) {
                    writer.println(s.getCName() + "," + item.getName() + "," + item.getPrice() + "," + item.getQuantity());
                }
            }
            writer.close();
        } catch (Exception e) {}
    }
}
