import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Locale;

public class GuiMain {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {e.printStackTrace();}

        StringBuilder errorLogs = new StringBuilder();
        PrintStream errorCatcherStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {errorLogs.append((char) b);}
            @Override
            public void write(byte[] b, int off, int len) {
                try {errorLogs.append(new String(b, off, len, "UTF-8"));
                } catch (Exception ex) {errorLogs.append(new String(b, off, len));}
            }
        }, true);

        System.setOut(errorCatcherStream);

        RecipeBook book = new RecipeBook();
        book.loadFromFile("recipes.txt");

        JFrame frame = new JFrame("Recipe Book Loader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 550);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(new Color(245, 245, 245));

        JPanel headerPanel = new JPanel(new GridLayout(1, 3));
        headerPanel.setBackground(new Color(245, 245, 245));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(245, 245, 245));

        JLabel titleLabel = new JLabel("Recipe Book", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(40, 40, 40));

        JButton btnAlerts = new JButton("Alerts");
        btnAlerts.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAlerts.setFocusPainted(false);
        btnAlerts.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAlerts.setBackground(new Color(255, 235, 205));

        btnAlerts.addActionListener(e -> {
            String errors = errorLogs.toString().trim();
            if (errors.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No errors found. All recipes are valid.", "System Status", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int skippedCount = errors.split("\\n").length;
                String displayMessage = "Total skipped recipes: " + skippedCount + "\n\n" + errors;
                JOptionPane.showMessageDialog(frame, displayMessage, "Skipped Recipes During Loading", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(new Color(245, 245, 245));
        rightPanel.add(btnAlerts);

        headerPanel.add(leftPanel);
        headerPanel.add(titleLabel);
        headerPanel.add(rightPanel);
        frame.add(headerPanel, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 15));
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(new Color(30, 30, 30));
        textArea.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        frame.add(centerPanel, BorderLayout.CENTER);

        PrintStream mainStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {textArea.append(String.valueOf((char) b)); textArea.setCaretPosition(textArea.getDocument().getLength());}
            @Override
            public void write(byte[] b, int off, int len) {
                try {textArea.append(new String(b, off, len, "UTF-8"));
                } catch (Exception ex) {textArea.append(new String(b, off, len));}
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }
        }, true);
        System.setOut(mainStream);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4, 15, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 25, 20));
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton btnListAll = createStyledButton("List All Recipes");
        JButton btnFilter = createStyledButton("Filter Category");
        JButton btnTotalTime = createStyledButton("Total Time");
        JButton btnExit = createStyledButton("Exit");

        buttonPanel.add(btnListAll);
        buttonPanel.add(btnFilter);
        buttonPanel.add(btnTotalTime);
        buttonPanel.add(btnExit);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        btnListAll.addActionListener(e -> {
            textArea.setText("");
            System.out.println("=== ALL RECIPES ===\n");
            book.listAllRecipes();
        });

        btnFilter.addActionListener(e -> {
            String category = JOptionPane.showInputDialog(frame, "Enter category (appetizer/main/dessert):");
            if (category != null && !category.trim().isEmpty()) {
                textArea.setText("");
                System.out.println("=== FILTERED BY: " + category.toUpperCase(Locale.ENGLISH) + " ===\n");
                book.filterByCategory(category);
            }
        });

        btnTotalTime.addActionListener(e -> {
            textArea.setText("");
            System.out.println("=== TOTAL COOKING TIME ===\n");
            System.out.println("Total cooking time across all recipes: " + book.getTotalCookingTime() + " minutes");
        });

        btnExit.addActionListener(e -> System.exit(0));

        System.out.println("Loaded " + book.getRecipeCount() + " recipes from recipes.txt");
        System.out.println("Welcome! Click a button below to interact with the Recipe Book.");

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}