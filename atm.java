import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class atm extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private Map<String, JPanel> panels = new HashMap<>();
    private String currentAccount;
    private double balance = 1000.0;

    private JLabel balanceLabel; // added

    private final Color BACKGROUND_COLOR = Color.BLACK;
    private final Color TEXT_COLOR = new Color(50, 255, 50);
    private final Font TERMINAL_FONT = new Font("Monospace", Font.BOLD, 14);
    private final Font HEADER_FONT = new Font("Monospace", Font.BOLD, 18);
    private final Dimension BUTTON_SIZE = new Dimension(150, 40);
    private final Dimension FIELD_SIZE = new Dimension(200, 30);

    public atm() {
        setTitle("Delin's ATM");
        setSize(480, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        centerFrame();
        setResizable(false);

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        createPanels();
        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }

    private void centerFrame() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }

    private void createPanels() {
        createLoginPanel();
        createMainMenuPanel();
        createBalancePanel();
        createWithdrawPanel();
        createDepositPanel();
    }

    private void createLoginPanel() {
    JPanel panel = new JPanel(new GridBagLayout()); // Main panel with center layout
    panel.setBackground(BACKGROUND_COLOR);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(20, 20, 20, 20);
    gbc.anchor = GridBagConstraints.CENTER;

    // Sub-panel para sa login form
    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(BACKGROUND_COLOR);
    formPanel.setBorder(BorderFactory.createLineBorder(TEXT_COLOR, 1));

    GridBagConstraints fgbc = new GridBagConstraints();
    fgbc.insets = new Insets(10, 10, 10, 10);
    fgbc.gridx = 0;
    fgbc.fill = GridBagConstraints.HORIZONTAL;

    int row = 0;

    // Header
    JLabel header = new JLabel("DELIN'S ATM");
    header.setForeground(TEXT_COLOR);
    header.setFont(new Font("Arial", Font.BOLD, 24));
    fgbc.gridy = row++;
    header.setHorizontalAlignment(SwingConstants.CENTER);
    formPanel.add(header, fgbc);

    // Account Number Label
    JLabel accLabel = createLabel("ACCOUNT NUM:");
    fgbc.gridy = row++;
    formPanel.add(accLabel, fgbc);

    // Account Number Field
    JTextField accField = createTextField();
    accField.setPreferredSize(FIELD_SIZE);
    fgbc.gridy = row++;
    formPanel.add(accField, fgbc);

    // PIN Label
    JLabel pinLabel = createLabel("PIN:");
    fgbc.gridy = row++;
    formPanel.add(pinLabel, fgbc);

    // PIN Field
    JPasswordField pinField = createPasswordField();
    pinField.setPreferredSize(FIELD_SIZE);
    fgbc.gridy = row++;
    formPanel.add(pinField, fgbc);

    // Login Button
    JButton loginBtn = createButton("LOGIN");
    loginBtn.setPreferredSize(BUTTON_SIZE);
    fgbc.gridy = row++;
    formPanel.add(loginBtn, fgbc);

    // Button listener
    loginBtn.addActionListener(e -> {
        if (validateLogin(accField.getText(), new String(pinField.getPassword()))) {
            currentAccount = accField.getText();
            cardLayout.show(mainPanel, "MAIN_MENU");
        } else {
            showError("Invalid credentials");
        }
    });

    // Add form panel to main panel
    panel.add(formPanel, gbc);

    panels.put("LOGIN", panel);
    mainPanel.add(panel, "LOGIN");
}

    private void createMainMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(createHeader(), BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setBackground(BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 50, 15, 50);
        gbc.weightx = 1.0;

        String[] buttons = {"BALANCE", "WITHDRAW", "DEPOSIT", "EXIT"};
        for (String btnText : buttons) {
            JButton btn = createMenuButton(btnText);
            btn.setPreferredSize(BUTTON_SIZE);
            btn.addActionListener(new MenuButtonListener());
            menuPanel.add(btn, gbc);
        }

        panel.add(menuPanel, BorderLayout.CENTER);
        panels.put("MAIN_MENU", panel);
        mainPanel.add(panel, "MAIN_MENU");
    }

    private void createBalancePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(createHeader(), BorderLayout.NORTH);

        JPanel balancePanel = new JPanel(new GridBagLayout());
        balancePanel.setBackground(BACKGROUND_COLOR);
        balancePanel.setBorder(BorderFactory.createLineBorder(TEXT_COLOR, 1));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 50, 30, 50);

        balanceLabel = createLabel(String.format("BALANCE: $%.2f", balance)); // updated
        balanceLabel.setFont(TERMINAL_FONT.deriveFont(Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        balancePanel.add(balanceLabel, gbc);

        panel.add(balancePanel, BorderLayout.CENTER);

        JButton backBtn = createButton("BACK");
        backBtn.setPreferredSize(BUTTON_SIZE);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "MAIN_MENU"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(backBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        panels.put("BALANCE", panel);
        mainPanel.add(panel, "BALANCE");
    }

    private void createWithdrawPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(createHeader(), BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createLineBorder(TEXT_COLOR, 1));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 50, 15, 50);
        gbc.gridy = 0;

        JLabel label = createLabel("WITHDRAW AMOUNT:");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(label, gbc);
        gbc.gridy++;

        JTextField amountField = createTextField();
        amountField.setPreferredSize(FIELD_SIZE);
        contentPanel.add(amountField, gbc);

        panel.add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton confirmBtn = createButton("CONFIRM");
        confirmBtn.setPreferredSize(BUTTON_SIZE);
        JButton backBtn = createButton("CANCEL");
        backBtn.setPreferredSize(BUTTON_SIZE);

        confirmBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount <= 0) {
                    showError("Invalid amount");
                } else if (amount > balance) {
                    showError("Insufficient funds");
                } else {
                    balance -= amount;
                    showSuccess(String.format("$%.2f withdrawn", amount));
                    cardLayout.show(mainPanel, "MAIN_MENU");
                }
            } catch (NumberFormatException ex) {
                showError("Invalid amount");
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "MAIN_MENU"));

        buttonPanel.add(confirmBtn);
        buttonPanel.add(backBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        panels.put("WITHDRAW", panel);
        mainPanel.add(panel, "WITHDRAW");
    }

    private void createDepositPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(createHeader(), BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createLineBorder(TEXT_COLOR, 1));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 50, 15, 50);
        gbc.gridy = 0;

        JLabel label = createLabel("DEPOSIT AMOUNT:");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(label, gbc);
        gbc.gridy++;

        JTextField amountField = createTextField();
        amountField.setPreferredSize(FIELD_SIZE);
        contentPanel.add(amountField, gbc);

        panel.add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton confirmBtn = createButton("CONFIRM");
        confirmBtn.setPreferredSize(BUTTON_SIZE);
        JButton backBtn = createButton("CANCEL");
        backBtn.setPreferredSize(BUTTON_SIZE);

        confirmBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount <= 0) {
                    showError("Invalid amount");
                } else {
                    balance += amount;
                    showSuccess(String.format("$%.2f deposited", amount));
                    cardLayout.show(mainPanel, "MAIN_MENU");
                }
            } catch (NumberFormatException ex) {
                showError("Invalid amount");
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "MAIN_MENU"));

        buttonPanel.add(confirmBtn);
        buttonPanel.add(backBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        panels.put("DEPOSIT", panel);
        mainPanel.add(panel, "DEPOSIT");
    }

    private JLabel createHeader() {
        JLabel header = new JLabel("DELIN'S ATM", SwingConstants.CENTER);
        header.setForeground(TEXT_COLOR);
        header.setFont(HEADER_FONT);
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        return header;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(TERMINAL_FONT);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(TERMINAL_FONT);
        field.setForeground(TEXT_COLOR);
        field.setBackground(new Color(20, 20, 20));
        field.setCaretColor(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEXT_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        field.setHorizontalAlignment(SwingConstants.CENTER);
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(TERMINAL_FONT);
        field.setForeground(TEXT_COLOR);
        field.setBackground(new Color(20, 20, 20));
        field.setCaretColor(TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEXT_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        field.setHorizontalAlignment(SwingConstants.CENTER);
        return field;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(TERMINAL_FONT);
        button.setForeground(Color.BLACK);
        button.setBackground(TEXT_COLOR);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEXT_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        button.setFocusPainted(false);
        return button;
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(TERMINAL_FONT.deriveFont(Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.setBackground(TEXT_COLOR);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEXT_COLOR, 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setFocusPainted(false);
        return button;
    }

    private boolean validateLogin(String account, String pin) {
        return account.equals("123456") && pin.equals("1234");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
    }

    private class MenuButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = ((JButton) e.getSource()).getText();
            switch (command) {
                case "BALANCE":
                    balanceLabel.setText(String.format("BALANCE: $%.2f", balance));
                    cardLayout.show(mainPanel, "BALANCE");
                    break;
                case "WITHDRAW":
                    cardLayout.show(mainPanel, "WITHDRAW");
                    break;
                case "DEPOSIT":
                    cardLayout.show(mainPanel, "DEPOSIT");
                    break;
                case "EXIT":
                    System.exit(0);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new atm().setVisible(true);
        });
    }
}
