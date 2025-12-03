import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.BorderFactory;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main {
    public static void main(String[] args) {
        categoryStorage storage = new categoryStorage();

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
        itemPanel.setBounds(150, 100, 175, 100);
        frame.add(itemPanel);

        JPanel bigPanel = new JPanel();
        bigPanel.setBounds(150, 100, 700, 550);
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
        frame.add(removeButtonPanel);
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

        JFrame addItemFrame = new JFrame("Add Item");
        addItemFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addItemFrame.setSize(300, 400);
        addItemFrame.setLayout(new GridLayout(4, 2));

        JLabel CategoryLabel = new JLabel("Category:");
        JTextField CategoryField = new JTextField();
        addItemFrame.add(CategoryLabel);
        addItemFrame.add(CategoryField);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        addItemFrame.add(nameLabel);
        addItemFrame.add(nameField);

        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField();
        addItemFrame.add(priceLabel);
        addItemFrame.add(priceField);

        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField();
        addItemFrame.add(quantityLabel);
        addItemFrame.add(quantityField);



        JButton addItemButton = new JButton("Add Item");
        JPanel addItemPanel = new JPanel();
        addItemPanel.setBounds(175, 10, 200, 50);
        addItemPanel.add(addItemButton);
        frame.add(addItemPanel);
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to add item goes here
                addItemFrame.setVisible(true);
            }
        });

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