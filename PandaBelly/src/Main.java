import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main {
    public static void main(String[] args) {
        categoryStorage storage = new categoryStorage();

        UIManager.put("Label.font", new Font("Chaucer", Font.PLAIN, 25));

        UIManager.put("OptionPane.background", Color.LIGHT_GRAY);
        UIManager.put("Panel.background", Color.LIGHT_GRAY);

        JFrame frame = new JFrame("PandaBelly");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setSize(1000,700);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setBackground(java.awt.Color.BLUE);
        
        String[] options = {"None"};
        JComboBox<String> dropdown = new JComboBox<>(options);
        
        JPanel panel = new JPanel();
        panel.add(dropdown);
        panel.setBounds(175,50,200,50);
        frame.add(panel);

        

        JPanel itemPanel = new JPanel();
        itemPanel.setBorder(BorderFactory.createEtchedBorder());
        itemPanel.setBounds(150, 100, 700/3, 100);
        frame.add(itemPanel);
        itemPanel.setLayout(new BorderLayout());
        JLabel itemLabel = new JLabel("Items", SwingConstants.CENTER);
        itemLabel.setSize(100, 80);
        itemPanel.add(itemLabel, BorderLayout.CENTER);

        JPanel pricePanel = new JPanel();
        pricePanel.setBorder(BorderFactory.createEtchedBorder());
        pricePanel.setBounds(150+1400/3, 100, 700/3, 100);
        frame.add(pricePanel);
        pricePanel.setLayout(new BorderLayout());
        JLabel priceLabel1 = new JLabel("Price", SwingConstants.CENTER);
        priceLabel1.setSize(100, 80);
        pricePanel.add(priceLabel1, BorderLayout.CENTER);
        pricePanel.setBackground(java.awt.Color.WHITE);

        JPanel quantPanel = new JPanel();
        quantPanel.setBorder(BorderFactory.createEtchedBorder());
        quantPanel.setBounds(150+700/3, 100, 700/3, 100);
        frame.add(quantPanel);
        quantPanel.setLayout(new BorderLayout());
        JLabel quantLabel1 = new JLabel("Quantity", SwingConstants.CENTER);
        quantLabel1.setSize(100, 80);
        quantPanel.add(quantLabel1, BorderLayout.CENTER);
        quantPanel.setBackground(java.awt.Color.WHITE);
/* 
        JPanel SKUPanel = new JPanel();
        SKUPanel.setBorder(BorderFactory.createEtchedBorder());
        SKUPanel.setBounds(, 100, 175, 100);
        frame.add(SKUPanel);
        SKUPanel.setLayout(new BorderLayout());
        JLabel SKULabel1 = new JLabel("SKU", SwingConstants.CENTER);
        SKULabel1.setSize(100, 80);
        SKUPanel.add(SKULabel1, BorderLayout.CENTER);
        SKUPanel.setBackground(java.awt.Color.WHITE);
*/


        JPanel bigPanel = new JPanel();
        bigPanel.setBounds(150, 200, 700, 450);
        frame.add(bigPanel);
        bigPanel.setBorder(BorderFactory.createEtchedBorder());
        bigPanel.setBackground(java.awt.Color.PINK);
        
        itemPanel.setBackground(java.awt.Color.WHITE);


        /* 
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        table.setRowHeight(30);

        // Wrap the table in a JScrollPane to display headers and enable scrolling
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame's content pane
        frame.add(scrollPane, BorderLayout.CENTER);
*/

        JButton removeCategoryButton = new JButton("Remove Category");
        JPanel removeButtonPanel = new JPanel();
        removeButtonPanel.setBounds(575,50,200,50);
        removeButtonPanel.add(removeCategoryButton);
        removeCategoryButton.setBorder(BorderFactory.createEtchedBorder());
        frame.add(removeButtonPanel);
        removeCategoryButton.setContentAreaFilled(false);
        removeCategoryButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                removeCategoryButton.setContentAreaFilled(true);
                removeCategoryButton.setBackground(new Color(255, 100, 100));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                removeCategoryButton.setContentAreaFilled(false);
            }
        });
        removeCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = JOptionPane.showInputDialog(frame, "Enter category name to remove:");
                if(selectedCategory.equals("None")){
                    JOptionPane.showMessageDialog(frame, "Cannot remove 'None' category.");
                    return;
                }
                if (selectedCategory != null && !selectedCategory.trim().isEmpty()) {
                    dropdown.removeItem(selectedCategory.trim());
                    storage.removeCategory(selectedCategory.trim());
                    // Here you would also add code to save the removed category to a file
                }
            }
        });



        JButton addcategory = new JButton("Add Category");
        JPanel addcategoryPanel = new JPanel();
        addcategoryPanel.setBounds(400,50,200,50);
        addcategoryPanel.add(addcategory);
        addcategory.setBorder(BorderFactory.createEtchedBorder());
        frame.add(addcategoryPanel);
        addcategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newCategory = JOptionPane.showInputDialog(frame, "Enter new category name:");
                if (newCategory != null && !newCategory.trim().isEmpty()) {
                    dropdown.addItem(newCategory.trim());
                    storage.addCategory(new Storage(newCategory.trim()));
                    // Here you would also add code to save the new category to a file
                }
            }
        });
        addcategory.setContentAreaFilled(false);
        addcategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addcategory.setContentAreaFilled(true);
                addcategory.setBackground(new Color(255, 100, 100));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                addcategory.setContentAreaFilled(false);
            }
        });

        JFrame addItemFrame = new JFrame("Add Item");
        addItemFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addItemFrame.setSize(300, 400);
        addItemFrame.setLayout(new GridLayout(5, 2));

        JLabel CategoryLabel = new JLabel("Category:");
        JTextField CategoryField = new JTextField();
        addItemFrame.add(CategoryLabel);
        addItemFrame.add(CategoryField);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        addItemFrame.add(nameLabel);
        addItemFrame.add(nameField);

        JLabel priceLabel = new JLabel("Price:         $");
        JTextField priceField = new JTextField();
        addItemFrame.add(priceLabel);
        addItemFrame.add(priceField);

        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField();
        addItemFrame.add(quantityLabel);
        addItemFrame.add(quantityField);

        JButton submitButton = new JButton("Submit");
        addItemFrame.add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CategoryField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "This field is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        CategoryField.requestFocusInWindow();
                    }
                else if (nameField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "This field is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    nameField.requestFocusInWindow();
                }
                else if (priceField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "This field is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    priceField.requestFocusInWindow();
                }
                else if (quantityField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "This field is required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    quantityField.requestFocusInWindow();
                }
                //exceptions
                try {
                    Double.parseDouble(priceField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter valid price!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    priceField.requestFocusInWindow();
                    return;
                }
                if(Double.parseDouble(priceField.getText()) < 0){
                    JOptionPane.showMessageDialog(null, "Price cannot be negative!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    priceField.requestFocusInWindow();
                    return;
                }
                if(priceField.getText().contains(".")){
                    String[] parts = priceField.getText().split("\\.");
                    if(parts.length == 2 && parts[1].length() > 2){
                        JOptionPane.showMessageDialog(null, "Price can only have two decimal places!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        priceField.requestFocusInWindow();
                        return;
                    }
                }
                try {
                    Integer.parseInt(quantityField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter valid quantity!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    quantityField.requestFocusInWindow();
                    return;
                }
                if(Integer.parseInt(quantityField.getText()) < 0){
                    JOptionPane.showMessageDialog(null, "Quantity cannot be negative!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    quantityField.requestFocusInWindow();
                    return;
                }
                
                if (!storage.categoryExists(CategoryField.getText())) {
                    JOptionPane.showMessageDialog(null, "Category does not exist!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    CategoryField.requestFocusInWindow();
                    return;
                }

                else {
                    Storage selectedStorage = null;
                    for (int i = 0; i < storage.getMainStorage().size(); i++) {
                        if (storage.getMainStorage().get(i).getCName().equals(CategoryField.getText())) {
                            selectedStorage = storage.getMainStorage().get(i);
                            break;
                        }
                    }
                    selectedStorage.addItem(nameField.getText(), Double.parseDouble(priceField.getText()), Integer.parseInt(quantityField.getText()));
                    JOptionPane.showMessageDialog(null, "Item added successfully!");
                    CategoryField.setText("");
                    nameField.setText("");
                    priceField.setText("");
                    quantityField.setText("");
                    addItemFrame.setVisible(false);
                }
            }
        });
        submitButton.setContentAreaFilled(false);
        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                submitButton.setContentAreaFilled(true);
                submitButton.setBackground(new Color(255, 100, 100));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                submitButton.setContentAreaFilled(false);
            }
        });



        JButton addItemButton = new JButton("Add Item");
        JPanel addItemPanel = new JPanel();
        addItemPanel.setBounds(175, 10, 200, 50);
        addItemPanel.add(addItemButton);
        addItemButton.setBorder(BorderFactory.createEtchedBorder());
        frame.add(addItemPanel);
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to add item goes here
                addItemFrame.setVisible(true);
            }   
        });
        addItemButton.setContentAreaFilled(false);
        addItemButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addItemButton.setContentAreaFilled(true);
                addItemButton.setBackground(new Color(255, 100, 100));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                addItemButton.setContentAreaFilled(false);
            }
        });

        JButton removeItemButton = new JButton("Remove Item");
        JPanel removeItemPanel = new JPanel();
        removeItemPanel.setBounds(550, 10, 200, 50);
        removeItemPanel.add(removeItemButton);
        frame.add(removeItemPanel);
        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to remove item goes here
                String itemName = JOptionPane.showInputDialog(frame, "Enter item name to remove:");
                if (itemName != null && !itemName.trim().isEmpty()) {
                    boolean itemFound = false;
                    for (Storage storageUnit : storage.getMainStorage()) {
                        if (storageUnit.removeItem(itemName.trim())) {
                            itemFound = true;
                            break;
                        }
                    }
                    if (itemFound) {
                        JOptionPane.showMessageDialog(frame, "Item '" + itemName + "' removed successfully.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Item '" + itemName + "' not found.");
                    }
                }
            }   
        });
        removeItemButton.setContentAreaFilled(false);
        removeItemButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                removeItemButton.setContentAreaFilled(true);
                removeItemButton.setBackground(new Color(255, 100, 100));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                removeItemButton.setContentAreaFilled(false);
            }
        });

       
       





        
        String[] columnNames = {"Items", "Quantity", "Price"};

        // Create a DefaultTableModel and JTable
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        // Add example data
        model.addRow(new Object[]{"Bamboo", 5, "$12.99"});
        model.addRow(new Object[]{"Panda Food", 10, "$8.50"});
        model.addRow(new Object[]{"Bamboo Shoots", 8, "$15.00"});
        model.addRow(new Object[]{"Leaves", 20, "$3.25"});
        
        JTable table = new JTable(model);
        table.setRowHeight(30);

        // Wrap the table in a JScrollPane to display headers and enable scrolling
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Add the scroll pane to the bigPanel instead
        bigPanel.setLayout(new BorderLayout());
        bigPanel.add(scrollPane, BorderLayout.CENTER);
        
        frame.setVisible(true);
    }
    /*Arthur: For the file stuff, we can use special characters to act as keys to separate different items and stuff
    for example, we can use %% or something to separate items, and categories by &&
    so maybe the first line of the txt is just a list of categories like balls&&cubes&&blahblah
    and then the next lines each individual item with the first part being the category it belongs to
    like balls%%red ball%%5%%$10.00%%SKUBLAHBLAHBLAH
     */

    // public void addToFile(String fileName, String textToAdd) {
    //     BufferedWriter writer = null;
    //     FileWriter fileWriter = new FileWriter(fileName, true);
    //     writer = new BufferedWriter(fileWriter);
    //     writer.write(textToAdd);
    //     writer.newLine();
        
    // }

}