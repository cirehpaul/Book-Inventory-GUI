/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bookinventorygui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

public class BookInventoryGUI extends JFrame {


    static class Book {
        String title, author, isbn;
        int quantity;

        public Book(String title, String author, String isbn, int quantity) {
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.quantity = quantity;
        }

        public Object[] toRow() {
            return new Object[]{title, author, isbn, quantity};
        }
    }

    private ArrayList<Book> books = new ArrayList<>();
    private DefaultTableModel tableModel;


    private JTextField titleField, authorField, isbnField, quantityField, searchField;

    public BookInventoryGUI() {
        setTitle("📚 Book Inventory System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        setLayout(new BorderLayout());

   
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Book"));

        titleField = new JTextField();
        authorField = new JTextField();
        isbnField = new JTextField();
        quantityField = new JTextField();

        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(new JLabel("Author:"));
        inputPanel.add(new JLabel("ISBN:"));
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(titleField);
        inputPanel.add(authorField);
        inputPanel.add(isbnField);
        inputPanel.add(quantityField);

        JButton addButton = new JButton("➕ Add Book");
        addButton.addActionListener(e -> addBook());

     
        String[] columns = {"Title", "Author", "ISBN", "Quantity"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

   
        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Book"));
        searchField = new JTextField(15);
        JButton searchButton = new JButton("🔍 Search");
        JButton showAllButton = new JButton("📄 Show All");

        searchButton.addActionListener(e -> searchBook());
        showAllButton.addActionListener(e -> refreshTable());

        searchPanel.add(new JLabel("Enter Title: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);


        add(inputPanel, BorderLayout.NORTH);
        add(addButton, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.SOUTH);
    }


    private void addBook() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String isbn = isbnField.getText().trim();
        int quantity;

        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || quantityField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ Quantity must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        books.add(new Book(title, author, isbn, quantity));
        refreshTable();
        clearFields();
        JOptionPane.showMessageDialog(this, "✅ Book added successfully!");
    }


    private void searchBook() {
        String query = searchField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        for (Book b : books) {
            if (b.title.toLowerCase().contains(query)) {
                tableModel.addRow(b.toRow());
            }
        }
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "❌ No book found.");
        }
    }


    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Book b : books) {
            tableModel.addRow(b.toRow());
        }
    }


    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
        quantityField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BookInventoryGUI().setVisible(true);
        });
    }
}
