//these are all our imports, the yellow suiggly lines can be ignored since they arent actual errors

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.GradientPaint;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.RadialGradientPaint;
import java.awt.event.MouseAdapter;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.text.NumberFormat;
import java.util.ArrayList;
//import com.formdev.flatlaf.FlatLightLaf;
//import com.formdev.flatlaf.FlatDarkLaf;


//all gui components inside Main class
public class Main {


    private static void styleButton(JButton button) {
        button.setBackground(new Color(0xADD8E6)); // Light blue color
    }

    public static void main(String[] args) throws IOException {
        categoryStorage storage = new categoryStorage();
        String[] columnNames = {"Items", "Quantity", "Price"};
        ArrayList<Item> restock = new ArrayList<Item>();
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };
        JTable table = new JTable(model);
        table.setRowHeight(30);

        UIManager.put("Label.font", new Font("Chaucer", Font.PLAIN, 25));
 


        //Ritvin's attempt to put a gif background
        JFrame frame = new JFrame("PandaBelly");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setSize(1000,700);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setBackground(java.awt.Color.BLUE);
        frame.setIconImage(new ImageIcon("PandaBelly/src/panda.png").getImage());
        
        // ryan: panda image in top-right corner 
        ImageIcon pandaIcon = new ImageIcon("PandaBelly/src/panda.png");
        Image scaledPanda = pandaIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel pandaLabel = new JLabel(new ImageIcon(scaledPanda));
        pandaLabel.setBounds(890, 10, 50, 50);
        frame.add(pandaLabel);
        


        String[] options = {};
        JComboBox<String> dropdown = new JComboBox<>(options);
        dropdown.setPreferredSize(new Dimension(180, 30));
        
        DataManager.loadData(storage, dropdown);
        updateTableForSelectedCategory(dropdown, storage, model);
        
        // Load restock items from storage (items marked with restockNeeded=true)
        for(Storage storageUnit : storage.getMainStorage()) {
            for(Item item : storageUnit.getCategory()) {
                if(item.isRestockNeeded()) {
                    restock.add(item);
                }
            }
        }
        
        JPanel panel = new JPanel();
        panel.add(dropdown);
        panel.setBounds(150,55,200,45);

        panel.setOpaque(true);

        //these are the 3 lil pannels at the top

        JPanel itemPanel = new JPanel();
        itemPanel.setBorder(BorderFactory.createEtchedBorder());
        itemPanel.setBounds(150, 100, 700/3, 100);

        itemPanel.setLayout(new BorderLayout());
        JLabel itemLabel = new JLabel("Items", SwingConstants.CENTER);
        itemLabel.setSize(100, 80);
        itemPanel.add(itemLabel, BorderLayout.CENTER);

        JPanel pricePanel = new JPanel();
        pricePanel.setBorder(BorderFactory.createEtchedBorder());
        pricePanel.setBounds(150+1400/3, 100, 700/3, 100);

        pricePanel.setLayout(new BorderLayout());
        JLabel priceLabel1 = new JLabel("Price", SwingConstants.CENTER);
        priceLabel1.setSize(100, 80);
        pricePanel.add(priceLabel1, BorderLayout.CENTER);
        pricePanel.setBackground(java.awt.Color.WHITE);

        JPanel quantPanel = new JPanel();
        quantPanel.setBorder(BorderFactory.createEtchedBorder());
        quantPanel.setBounds(150+700/3, 100, 700/3, 100);

        quantPanel.setLayout(new BorderLayout());
        JLabel quantLabel1 = new JLabel("Quantity", SwingConstants.CENTER);
        quantLabel1.setSize(100, 80);
        quantPanel.add(quantLabel1, BorderLayout.CENTER);
        quantPanel.setBackground(java.awt.Color.WHITE);
        //panels end, we got rid of the SKU idea btw


        JPanel bigPanel = new JPanel();
        bigPanel.setBounds(150, 200, 700, 450);

        bigPanel.setBorder(BorderFactory.createEtchedBorder());
        bigPanel.setOpaque(false); // show animated background behind the table
         
         itemPanel.setBackground(java.awt.Color.WHITE);
        // make button-holder panels transparent so the gradient is visible
        // (repeat for other small panels if desired)
        

//Arthur: remove category button, the edges are a little off though.
        JButton removeCategoryButton = new JButton("Remove Category");
        JPanel removeButtonPanel = new JPanel();
        removeButtonPanel.setBounds(700,55,180,45);
        removeButtonPanel.add(removeCategoryButton);

        removeCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sounds.playClick();
                // Create a dropdown with all categories except "None"
                JComboBox<String> removeCatDropdown = new JComboBox<>();
                for (int i = 0; i < dropdown.getItemCount(); i++) {
                    String cat = dropdown.getItemAt(i);
                    if (!cat.equals("None")) {
                        removeCatDropdown.addItem(cat);
                    }
                }
                
                if (removeCatDropdown.getItemCount() == 0) {
                    Sounds.playError();
                    JOptionPane.showMessageDialog(frame, "No categories to remove!");
                    return;
                }
                
                String selectedCategory = (String) JOptionPane.showInputDialog(frame, "Select category to remove:", "Remove Category", JOptionPane.QUESTION_MESSAGE, null, removeCatDropdown.getItemCount() > 0 ? getItems(removeCatDropdown) : null, removeCatDropdown.getItemAt(0));
                
                if (selectedCategory != null) {
                    dropdown.removeItem(selectedCategory);
                    storage.removeCategory(selectedCategory);
                    DataManager.saveData(storage);
                    Sounds.playSuccess();
                    Main.updateTableForSelectedCategory(dropdown, storage, model);
                }
            }
            
            private Object[] getItems(JComboBox<String> combo) {
                Object[] items = new Object[combo.getItemCount()];
                for (int i = 0; i < combo.getItemCount(); i++) {
                    items[i] = combo.getItemAt(i);
                }
                return items;
            }
        });

//Arthur

        JButton addcategory = new JButton("Add Category");
        JPanel addcategoryPanel = new JPanel();
        addcategoryPanel.setBounds(350,55,200,45);
        addcategoryPanel.add(addcategory);


        addcategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sounds.playClick();
                String newCategory = JOptionPane.showInputDialog(frame, "Enter new category name:");
                if (newCategory != null && !newCategory.trim().isEmpty()) {
                    String cat = newCategory.trim();
                    dropdown.addItem(cat);
                    storage.addCategory(new Storage(cat));
                    DataManager.saveData(storage);
                    Sounds.playSuccess();
                    // Select the newly added category and refresh the table
                    dropdown.setSelectedItem(cat);
                    Main.updateTableForSelectedCategory(dropdown, storage, model);
                }
            }
        });

        JButton modifyCategoryButton = new JButton("Modify Category");
        JPanel modifyCategoryPanel = new JPanel();
        modifyCategoryPanel.setBounds(515, 55, 200, 45);
        modifyCategoryPanel.add(modifyCategoryButton);

        modifyCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sounds.playClick();
                String oldCategory = JOptionPane.showInputDialog(frame, "Enter the category name to modify:");
                if (oldCategory != null && !oldCategory.trim().isEmpty()) {
                    String newCategory = JOptionPane.showInputDialog(frame, "Enter the new category name:");
                    if (newCategory != null && !newCategory.trim().isEmpty()) {
                        if(oldCategory.equals("None")){
                            Sounds.playError();
                            JOptionPane.showMessageDialog(frame, "Cannot modify 'None' category.");
                            return;
                            // Exception handling
                        }
                        boolean modified = storage.modifyCategory(oldCategory.trim(), newCategory.trim());
                        if (modified) {
                            dropdown.removeItem(oldCategory.trim());
                            dropdown.addItem(newCategory.trim());
                            DataManager.saveData(storage);
                            Sounds.playSuccess();
                            // Select the modified category and refresh the table
                            dropdown.setSelectedItem(newCategory.trim());
                            Main.updateTableForSelectedCategory(dropdown, storage, model);
                        } else {
                            Sounds.playError();
                            JOptionPane.showMessageDialog(frame, "Category not found: " + oldCategory);
                        }
                    }
                }
            }
        });
//Arthur: the add item frame and its components
        JFrame addItemFrame = new JFrame("Add Item");
        addItemFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addItemFrame.setSize(300, 400);
        addItemFrame.setLayout(new GridLayout(5, 2));
//Arthur: Labels for the add item frame
        JLabel CategoryLabel = new JLabel("Category:");
        JComboBox<String> CategoryField = new JComboBox<>();
        for (int i = 0; i < dropdown.getItemCount(); i++) {
            CategoryField.addItem(dropdown.getItemAt(i));
        }
        addItemFrame.add(CategoryLabel);
        addItemFrame.add(CategoryField);
//Arthur: Labels for the add item frame
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        addItemFrame.add(nameLabel);
        addItemFrame.add(nameField);
//Arthur: Labels for the add item frame
        JLabel priceLabel = new JLabel("Price:        $");
        JTextField priceField = new JTextField();
        addItemFrame.add(priceLabel);
        addItemFrame.add(priceField);
//Arthur:Labels for the add item frame
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField();
        addItemFrame.add(quantityLabel);
        addItemFrame.add(quantityField);


        //Arthur: different cases and exceptions for adding an item. Pressing the submit button runs through all the checks, if everything is valid, it adds the item.
        JButton submitButton = new JButton("Submit");
        addItemFrame.add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sounds.playClick();
                if (CategoryField.getSelectedItem() == null) {
                        Sounds.playError();
                        JOptionPane.showMessageDialog(null, "This field is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        CategoryField.requestFocusInWindow();
                    }
                else if (nameField.getText().isEmpty()) {
                    Sounds.playError();
                    JOptionPane.showMessageDialog(null, "This field is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    nameField.requestFocusInWindow();
                }
                else if (priceField.getText().isEmpty()) {
                    Sounds.playError();
                    JOptionPane.showMessageDialog(null, "This field is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    priceField.requestFocusInWindow();
                }
                else if (quantityField.getText().isEmpty()) {
                    Sounds.playError();
                    JOptionPane.showMessageDialog(null, "This field is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    quantityField.requestFocusInWindow();
                }
                //exceptions
                try {
                    Double.parseDouble(priceField.getText());
                } catch (NumberFormatException ex) {
                    Sounds.playError();
                    JOptionPane.showMessageDialog(null, "Enter valid price!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    priceField.requestFocusInWindow();
                    return;
                }
                if(Double.parseDouble(priceField.getText()) < 0){
                    Sounds.playError();
                    JOptionPane.showMessageDialog(null, "Price cannot be negative!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    priceField.requestFocusInWindow();
                    return;
                }
                if(priceField.getText().contains(".")){
                    String[] parts = priceField.getText().split("\\.");
                    if(parts.length == 2 && parts[1].length() > 2){
                        Sounds.playError();
                        JOptionPane.showMessageDialog(null, "Price can only have two decimal places!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        priceField.requestFocusInWindow();
                        return;
                    }
                }
                try {
                    Integer.parseInt(quantityField.getText());
                } catch (NumberFormatException ex) {
                    Sounds.playError();
                    JOptionPane.showMessageDialog(null, "Enter valid quantity!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    quantityField.requestFocusInWindow();
                    return;
                }
                if(Integer.parseInt(quantityField.getText()) < 0){
                    Sounds.playError();
                    JOptionPane.showMessageDialog(null, "Quantity cannot be negative!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    quantityField.requestFocusInWindow();
                    return;
                }
                
                if (!storage.categoryExists((String) CategoryField.getSelectedItem())) {
                    Sounds.playError();
                    JOptionPane.showMessageDialog(null, "Category does not exist!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    CategoryField.requestFocusInWindow();
                    return;
                }
                
                // Check if item already exists in the category
                Storage selectedStorage = null;
                for (int i = 0; i < storage.getMainStorage().size(); i++) {
                    if (storage.getMainStorage().get(i).getCName().equals((String) CategoryField.getSelectedItem())) {
                        selectedStorage = storage.getMainStorage().get(i);
                        break;
                    }
                }
                
                if (selectedStorage != null) {
                    for (Item item : selectedStorage.getCategory()) {
                        if (item.getName().equalsIgnoreCase(nameField.getText())) {
                            Sounds.playError();
                            JOptionPane.showMessageDialog(null, "Item already exists in this category!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                            nameField.requestFocusInWindow();
                            return;
                        }
                    }
                }

                if (!nameField.getText().isEmpty()) {
                    for (int i = 0; i < storage.getMainStorage().size(); i++) {
                        if (storage.getMainStorage().get(i).getCName().equals((String) CategoryField.getSelectedItem())) {
                            selectedStorage = storage.getMainStorage().get(i);
                            break;
                        }
                    }
                    //Runs if all checks are passed, adds the item to the selected category
                    boolean restockFlag = false;
                    if (quantityField.getText().equals("0")) {
                        int restockChoice = JOptionPane.showConfirmDialog(null, "Quantity is 0. Mark item as needing restock?", "Restock Confirmation", JOptionPane.YES_NO_OPTION);
                        restockFlag = (restockChoice == JOptionPane.YES_OPTION);
                        if(restockFlag) {
                            restock.add(new Item(nameField.getText(), Double.parseDouble(priceField.getText()), Integer.parseInt(quantityField.getText()), true));
                        }
                    }
                    selectedStorage.addItem(nameField.getText(), Double.parseDouble(priceField.getText()), Integer.parseInt(quantityField.getText()), restockFlag);
                    // persist and give feedback
                    DataManager.saveData(storage);
                    Sounds.playSuccess();
                    JOptionPane.showMessageDialog(null, "Item added successfully!");
                    nameField.setText("");
                    priceField.setText("");
                    quantityField.setText("");
                    addItemFrame.setVisible(false);
                    dropdown.setSelectedItem((String) CategoryField.getSelectedItem());
                    Main.updateTableForSelectedCategory(dropdown, storage, model);
                }
            }
        });

//Arthur:Add Item button and its panel
        JButton addItemButton = new JButton("Add Item");
        JPanel addItemPanel = new JPanel();
        addItemPanel.setBounds(175, 10, 200, 50);
        addItemPanel.add(addItemButton);


        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sounds.playClick();
                // Refresh the category dropdown with current categories
                CategoryField.removeAllItems();
                for (int i = 0; i < dropdown.getItemCount(); i++) {
                    CategoryField.addItem(dropdown.getItemAt(i));
                }
                addItemFrame.setVisible(true);
            }   
        });
//Arthur: Remove Item button, frame and its components
        JButton removeItemButton = new JButton("Remove Item");
        JFrame removeItemFrame = new JFrame("Remove Item");
        removeItemFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); 
        removeItemFrame.setSize(350, 200);
        removeItemFrame.setLayout(new GridLayout(3, 2));
        removeItemFrame.setVisible(false);
        JLabel removeCategoryLabel = new JLabel("Category:");
        JComboBox<String> removeCategoryDropdown = new JComboBox<>();
        for (int i = 0; i < dropdown.getItemCount(); i++) {
            removeCategoryDropdown.addItem(dropdown.getItemAt(i));
        }
        removeItemFrame.add(removeCategoryLabel);
        removeItemFrame.add(removeCategoryDropdown);
//Arthur: Labels for remove item frame
        JLabel removeItemLabel = new JLabel("Item Name:");
        JTextField removeItemField = new JTextField();
        removeItemFrame.add(removeItemLabel);
        removeItemFrame.add(removeItemField);
        JButton submitRemoveItemButton = new JButton("Submit");
        removeItemFrame.add(submitRemoveItemButton);
        submitRemoveItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sounds.playClick();
                if (removeCategoryDropdown.getSelectedItem() == null) {
                        Sounds.playError();
                        JOptionPane.showMessageDialog(null, "This field is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        removeCategoryDropdown.requestFocusInWindow();
                    }
                else if (removeItemField.getText().isEmpty()) {
                    Sounds.playError();
                    JOptionPane.showMessageDialog(null, "This field is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    removeItemField.requestFocusInWindow();
                }
                else {
                    String categoryName = (String) removeCategoryDropdown.getSelectedItem();
                    String itemName = removeItemField.getText();
                    Storage selectedStorage = null;
                    for (int i = 0; i < storage.getMainStorage().size(); i++) {
                        if (storage.getMainStorage().get(i).getCName().equals(categoryName)) {
                            selectedStorage = storage.getMainStorage().get(i);
                            break;
                        }
                    }
                    if (selectedStorage != null && selectedStorage.removeItem(itemName)) {
                        DataManager.saveData(storage);
                        Sounds.playSuccess();
                        JOptionPane.showMessageDialog(null, "Item removed successfully!");
                        removeItemField.setText("");
                        removeItemFrame.setVisible(false);
                        dropdown.setSelectedItem(categoryName);
                        Main.updateTableForSelectedCategory(dropdown, storage, model);
                    } else {
                        Sounds.playError();
                        JOptionPane.showMessageDialog(null, "Item not found in the specified category!", "Error", JOptionPane.ERROR_MESSAGE);
                        removeItemField.requestFocusInWindow();
                    }
                }
            }   
        });
//Arthur: Remove Item button
        JPanel removeItemPanel = new JPanel();
        removeItemPanel.setBounds(600, 10, 200, 50);
        removeItemPanel.add(removeItemButton);

        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sounds.playClick();
                // Refresh the category dropdown with current categories
                removeCategoryDropdown.removeAllItems();
                for (int i = 0; i < dropdown.getItemCount(); i++) {
                    removeCategoryDropdown.addItem(dropdown.getItemAt(i));
                }
                removeItemFrame.setVisible(true);
            }   
        });

        JButton modifyItemButton = new JButton("Modify Item");
        JPanel modifyItemPanel = new JPanel();
        JFrame modifyItemFrame = new JFrame("Modify Item");
        modifyItemFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        modifyItemFrame.setSize(400, 400);
        modifyItemFrame.setLayout(new GridLayout(6, 2));
        modifyItemFrame.setVisible(false);
        //Arthur: Labels for modify item frame
        JLabel modifyCategoryLabel = new JLabel("Category:");
        JComboBox<String> modifyCategoryDropdown = new JComboBox<>();
        for (int i = 0; i < dropdown.getItemCount(); i++) {
            modifyCategoryDropdown.addItem(dropdown.getItemAt(i));
        }
        modifyItemFrame.add(modifyCategoryLabel);
        modifyItemFrame.add(modifyCategoryDropdown);
        JLabel modifyItemLabel = new JLabel("Item Name:");
        JTextField modifyItemField = new JTextField();
        modifyItemFrame.add(modifyItemLabel);
        modifyItemFrame.add(modifyItemField);
        JLabel newNameLabel = new JLabel("New Name:");
        JTextField newNameField = new JTextField();
        modifyItemFrame.add(newNameLabel);
        modifyItemFrame.add(newNameField);
        JLabel newPriceLabel = new JLabel("New Price:        $");
        JTextField newPriceField = new JTextField();
        modifyItemFrame.add(newPriceLabel);
        modifyItemFrame.add(newPriceField);
        JLabel newQuantityLabel = new JLabel("New Quantity:");
        JTextField newQuantityField = new JTextField();
        modifyItemFrame.add(newQuantityLabel);
        modifyItemFrame.add(newQuantityField);
        JButton modifyItemSubmitButton = new JButton("Submit");
        modifyItemFrame.add(modifyItemSubmitButton);
        modifyItemSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sounds.playClick();
                if (modifyCategoryDropdown.getSelectedItem() == null) {
                        Sounds.playError();
                        JOptionPane.showMessageDialog(null, "This field is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        modifyCategoryDropdown.requestFocusInWindow();
                    }
                else if (modifyItemField.getText().isEmpty()) {
                    Sounds.playError();
                    JOptionPane.showMessageDialog(null, "This field is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    modifyItemField.requestFocusInWindow();
                }
                else if (newNameField.getText().isEmpty()) {
                    Sounds.playError();
                    JOptionPane.showMessageDialog(null, "This field is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    newNameField.requestFocusInWindow();
                }
                else if (newPriceField.getText().isEmpty()) {
                    Sounds.playError();
                    JOptionPane.showMessageDialog(null, "This field is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    newPriceField.requestFocusInWindow();
                }
                else if (newQuantityField.getText().isEmpty()) {
                    Sounds.playError();
                    JOptionPane.showMessageDialog(null, "This field is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    newQuantityField.requestFocusInWindow();
                }
                //exceptions
                try {
                    Double.parseDouble(newPriceField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter valid price!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    newPriceField.requestFocusInWindow();
                    return;
                }
                if(Double.parseDouble(newPriceField.getText()) < 0){
                    JOptionPane.showMessageDialog(null, "Price cannot be negative!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    newPriceField.requestFocusInWindow();
                    return;
                }
                if(newPriceField.getText().contains(".")){
                    String[] parts = newPriceField.getText().split("\\.");
                    if(parts.length == 2 && parts[1].length() > 2){
                        JOptionPane.showMessageDialog(null, "Price can only have two decimal places!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        newPriceField.requestFocusInWindow();
                        return;
                    }
                }
                try {
                    Integer.parseInt(newQuantityField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter valid quantity!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    newQuantityField.requestFocusInWindow();
                    return;
                }
                if(Integer.parseInt(newQuantityField.getText()) < 0){
                    JOptionPane.showMessageDialog(null, "Quantity cannot be negative!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    newQuantityField.requestFocusInWindow();
                    return;
                }

                else {
                    String categoryName = (String) modifyCategoryDropdown.getSelectedItem();
                    String itemName = modifyItemField.getText();
                    Storage selectedStorage = null;
                    for (int i = 0; i < storage.getMainStorage().size(); i++) {
                        if (storage.getMainStorage().get(i).getCName().equals(categoryName)) {
                            selectedStorage = storage.getMainStorage().get(i);
                            break;
                        }
                    }
                    if (selectedStorage != null) {
                        boolean modified = selectedStorage.modifyItem(itemName, newNameField.getText(), Double.parseDouble(newPriceField.getText()), Integer.parseInt(newQuantityField.getText()));
                        if (modified) {
                            if(Integer.parseInt(newQuantityField.getText()) == 0){
                                int choice = JOptionPane.showConfirmDialog(frame, "Would you like to restock this item? Item Quantity is 0", "Restock Item", JOptionPane.YES_NO_OPTION);
                                if (choice == JOptionPane.YES_OPTION) {
                                    // Find the Item object by name and add to restock list
                                    for (Item item : selectedStorage.getCategory()) {
                                        if (item.getName().equalsIgnoreCase(newNameField.getText())) {
                                            // mark item as needing restock
                                            item.setRestockNeeded(true);
                                            // avoid duplicate entries
                                            boolean already = false;
                                            for (Item r : restock) {
                                                if (r.getName().equalsIgnoreCase(item.getName())) { already = true; break; }
                                            }
                                            if (!already) restock.add(item);
                                            break;
                                        }
                                    }
                                    JOptionPane.showMessageDialog(frame, "Item added to restock list.");
                                }
                            }
                            DataManager.saveData(storage);
                            Sounds.playSuccess();
                            JOptionPane.showMessageDialog(null, "Item modified successfully!");
                            modifyItemField.setText("");
                            newNameField.setText("");
                            newPriceField.setText("");
                            newQuantityField.setText("");
                            modifyItemFrame.setVisible(false);
                            dropdown.setSelectedItem(categoryName);
                            Main.updateTableForSelectedCategory(dropdown, storage, model);
                        } else {
                            Sounds.playError();
                            JOptionPane.showMessageDialog(null, "Item not found in the specified category!", "Error", JOptionPane.ERROR_MESSAGE);
                            modifyItemField.requestFocusInWindow();
                        }
                    }
                }
            }
        });
        modifyItemPanel.setBounds(400, 10, 200, 50);
        modifyItemPanel.add(modifyItemButton);

        modifyItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sounds.playClick();
                // Refresh the category dropdown with current categories
                modifyCategoryDropdown.removeAllItems();
                for (int i = 0; i < dropdown.getItemCount(); i++) {
                    modifyCategoryDropdown.addItem(dropdown.getItemAt(i));
                }
                modifyItemFrame.setVisible(true);
            }   
        });


        JFrame restockFrame = new JFrame("Restock List");
        restockFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        restockFrame.setSize(600, 400);
        String[] restockColumnNames = {"Category", "Item Name"};
        DefaultTableModel restockModel = new DefaultTableModel(restockColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable restockTable = new JTable(restockModel);
        restockTable.setRowHeight(25);
        restockFrame.add(new JScrollPane(restockTable));
        JButton viewRestockButton = new JButton("View Restock List");
        JPanel viewRestockPanel = new JPanel();
        viewRestockPanel.setBounds(800, 10, 200, 50);
        viewRestockPanel.add(viewRestockButton);
        viewRestockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sounds.playClick();
                restockModel.setRowCount(0);
                for(Item item : restock){
                    String category = null;
                    // Find the category for this item
                    for(Storage storageUnit : storage.getMainStorage()) {
                        for(Item storageItem : storageUnit.getCategory()) {
                            if(storageItem.getName().equals(item.getName())) {
                                category = storageUnit.getCName();
                                break;
                            }
                        }
                        if(category != null) break;
                    }
                    // Only display if the item still exists in storage
                    if(category != null) {
                        restockModel.addRow(new Object[]{category, item.getName()});
                    }
                }
                restockFrame.setVisible(true);
            }
        });
        

        JLabel titleLabel = new JLabel("Welcome to PandaBelly!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Chaucer", Font.BOLD, 36));
        titleLabel.setBounds(250, 150, 500, 50);
        frame.add(titleLabel);
        Image mainMenuPanda = new ImageIcon("PandaBelly/src/panda.png").getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel mainMenuPandaLabel = new JLabel(new ImageIcon(mainMenuPanda));
        mainMenuPandaLabel.setBounds(400, 250, 200, 200);
        frame.add(mainMenuPandaLabel);

        JButton returnToMenuButton = new JButton("Back to \nMain Menu");
        JPanel returnToMenuPanel = new JPanel();
        JButton mainMenuButton = new JButton("Enter PandaBelly");
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setBounds(400, 550, 200, 50);
        mainMenuPanel.add(mainMenuButton);
        frame.add(mainMenuPanel); 
        mainMenuButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Sounds.playClick();
                frame.add(addItemPanel);
                frame.add(removeItemPanel);
                frame.add(modifyItemPanel);
                frame.add(addcategoryPanel);
                frame.add(modifyCategoryPanel);
                frame.add(removeButtonPanel);
                frame.add(panel);
                frame.add(itemPanel);
                frame.add(pricePanel);
                frame.add(quantPanel);
                frame.add(bigPanel);
                frame.add(returnToMenuPanel);
                frame.add(viewRestockPanel);
                mainMenuPanel.setVisible(false);
                titleLabel.setVisible(false);
                mainMenuPandaLabel.setVisible(false);
                frame.repaint();
            }
        });



        returnToMenuPanel.setBounds(10, 10, 180, 50);
        returnToMenuPanel.add(returnToMenuButton);
        returnToMenuButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Sounds.playClick();
                frame.remove(addItemPanel);
                frame.remove(removeItemPanel);
                frame.remove(modifyItemPanel);
                frame.remove(addcategoryPanel);
                frame.remove(modifyCategoryPanel);
                frame.remove(removeButtonPanel);
                frame.remove(panel);
                frame.remove(itemPanel);
                frame.remove(pricePanel);
                frame.remove(quantPanel);
                frame.remove(bigPanel);
                frame.remove(returnToMenuPanel);
                frame.remove(viewRestockPanel);
                mainMenuPanel.setVisible(true);
                titleLabel.setVisible(true);
                mainMenuPandaLabel.setVisible(true);
                frame.repaint();
            }
        });

       styleButton(addItemButton);
       styleButton(modifyItemButton);
       styleButton(removeItemButton);
       styleButton(addcategory);
       styleButton(modifyCategoryButton);
       styleButton(removeCategoryButton);
       


        //tools.setBounds(0, 0, frame.getWidth(), 70);
        //frame.add(tools);





        


        
        //ryan - adding Jtable to display our data
        
        //  example data, not stored in any category so we can disregard, its more a proof of concept
        // model.addRow(new Object[]{"Bamboo", 5, "$12.99"});
        // model.addRow(new Object[]{"Panda Food", 10, "$8.50"});
        // model.addRow(new Object[]{"Bamboo Shoots", 8, "$15.00"});
        // model.addRow(new Object[]{"Leaves", 20, "$3.25"});
        
        

        // Wrap the table in a JScrollPane to display headers and enable scrolling
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Add the scroll pane to the bigPanel instead
        bigPanel.setLayout(new BorderLayout());
        bigPanel.add(scrollPane, BorderLayout.CENTER);

        dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.updateTableForSelectedCategory(dropdown, storage, model);
            }
        });
        
        frame.setVisible(true);
    }
    
    //Arthur: Update table method to refresh the table based on selected category
    public static void updateTableForSelectedCategory(JComboBox<String> dropdown, categoryStorage storage, DefaultTableModel model) {
        String selectedCategory = (String) dropdown.getSelectedItem();
                // Update the table based on the selected category
                model.setRowCount(0); // Clear existing rows

                for(Storage storageUnit : storage.getMainStorage()) {
                    if (storageUnit.getCName().equals(selectedCategory)) {
                        for (Item item : storageUnit.getCategory()) {
                            model.addRow(new Object[]{item.getName(), item.getQuantity(), "$" + String.format("%.2f", item.getPrice())});
                        }
                        break;
                    }
                }
                if(model.getRowCount() == 0){
                    model.addRow(new Object[]{"No items in this category", "", ""});
                }
    }

}


