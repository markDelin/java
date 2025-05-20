// Libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class schoolFee extends JFrame {
    // Form components
    private JTextField nameField, courseField, feeField;
    private JButton addButton, clearButton;
    private JTable feeTable;
    private JScrollPane scrollPane;
    private javax.swing.table.DefaultTableModel tableModel;

    // Colors
    private final Color BLUE_BACKGROUND = new Color(240, 245, 255);  // Lighter background
    private final Color BLUE_BUTTON = new Color(0, 82, 165);        // Darker blue for buttons
    private final Color BLUE_BUTTON_TEXT = Color.WHITE;
    private final Color BLUE_TABLE_HEADER = new Color(0, 102, 204); // Darker blue for headers
    private final Color TEXT_COLOR = new Color(30, 30, 30);         // Dark text for better contrast
    private final Color BORDER_COLOR = new Color(100, 149, 237);    // Cornflower blue for borders

    public schoolFee() {
        // Set up the window
        setTitle("School Fee System - Group 5");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BLUE_BACKGROUND);

        // Initialize components
        createComponents();

        // Add components to the window
        setupLayout();
    }

    private void createComponents() {
        // Create text fields
        nameField = createTextField();
        courseField = createTextField();
        feeField = createTextField();

        // Create buttons
        addButton = createButton("Add Record");
        clearButton = createButton("Clear");

        // Create table
        String[] columns = {"Student Name", "Course", "Fee Amount"};
        tableModel = new javax.swing.table.DefaultTableModel(columns, 0);
        feeTable = new JTable(tableModel);

        // Style the table
        feeTable.setBackground(BLUE_BACKGROUND);
        feeTable.setForeground(TEXT_COLOR);
        feeTable.setSelectionBackground(BLUE_BUTTON);
        feeTable.setSelectionForeground(Color.WHITE);
        feeTable.getTableHeader().setBackground(BLUE_TABLE_HEADER);
        feeTable.getTableHeader().setForeground(Color.WHITE);
        feeTable.setGridColor(BORDER_COLOR);

        scrollPane = new JScrollPane(feeTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        // Add button actions
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addFeeRecord();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setForeground(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(BLUE_BUTTON);
        button.setForeground(BLUE_BUTTON_TEXT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    private void setupLayout() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BLUE_BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Form panel with blue border
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 10));
        formPanel.setBackground(BLUE_BACKGROUND);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 2),
            "Enter Student Fee Details",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 12),
            BLUE_BUTTON
        ));

        // Add form components with labels
        formPanel.add(createLabel("Student Name:"));
        formPanel.add(nameField);
        formPanel.add(createLabel("Course:"));
        formPanel.add(courseField);
        formPanel.add(createLabel("Fee Amount:"));
        formPanel.add(feeField);
        formPanel.add(addButton);
        formPanel.add(clearButton);

        // Add panels to main window
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        return label;
    }

    private void addFeeRecord() {
        String name = nameField.getText();
        String course = courseField.getText();
        String fee = feeField.getText();

        if (name.isEmpty() || course.isEmpty() || fee.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tableModel.addRow(new Object[]{name, course, fee});
        clearFields();

        JOptionPane.showMessageDialog(this, "Record added successfully!");
    }

    private void clearFields() {
        nameField.setText("");
        courseField.setText("");
        feeField.setText("");
    }

    public static void main(String[] args) {
        // Set look and feel for all components
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("OptionPane.background", new Color(240, 245, 255));
            UIManager.put("Panel.background", new Color(240, 245, 255));
            UIManager.put("OptionPane.messageForeground", new Color(30, 30, 30));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Run the application
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new schoolFee().setVisible(true);
            }
        });
    }
}
