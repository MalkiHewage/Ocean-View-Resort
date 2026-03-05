package view;

import controller.AuthController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Premium styled login window for user authentication.
 */
public class LoginFrame extends JFrame {
    private final AuthController authController;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;

    // Tropical Beach Resort color scheme
    private static final Color OCEAN_DEEP = new Color(0, 119, 182);
    private static final Color OCEAN_LIGHT = new Color(3, 169, 244);
    private static final Color SAND_WARM = new Color(255, 183, 77);
    private static final Color SAND_LIGHT = new Color(255, 224, 178);
    private static final Color CORAL_PINK = new Color(255, 138, 128);
    private static final Color TEXT_LIGHT = new Color(255, 255, 255);
    private static final Color TEXT_MUTED = new Color(189, 189, 189);
    private static final Color CARD_BG = new Color(255, 255, 255);
    private static final Color INPUT_BG = new Color(250, 250, 250);
    private static final Color INPUT_BORDER = new Color(224, 224, 224);

    public LoginFrame(AuthController authController) {
        this.authController = authController;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Ocean View Resort - Hotel Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 620);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(false);

        // Main container with split layout
        JPanel mainContainer = new JPanel(new GridLayout(1, 2));

        // Left panel - branding
        JPanel brandPanel = createBrandPanel();
        mainContainer.add(brandPanel);

        // Right panel - login form
        JPanel loginPanel = createLoginPanel();
        mainContainer.add(loginPanel);

        setContentPane(mainContainer);
    }

    private JPanel createBrandPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Ocean gradient background
                GradientPaint gp = new GradientPaint(0, 0, OCEAN_DEEP, 0, getHeight(), OCEAN_LIGHT);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Wave decorations
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
                g2d.setColor(Color.WHITE);
                // Top wave
                g2d.fillArc(-50, -50, 300, 200, 0, 180);
                g2d.fillArc(150, 50, 250, 180, 0, 180);
                // Bottom wave
                g2d.fillArc(getWidth() - 250, getHeight() - 150, 300, 200, 180, 180);
                g2d.fillArc(getWidth() - 400, getHeight() - 100, 250, 150, 180, 180);
            }
        };
        panel.setLayout(new GridBagLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // New tropical logo with palm tree and waves
        JLabel iconLabel = new JLabel("\uD83C\uDF34");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Ocean View Resort");
        titleLabel.setFont(new Font("Brush Script MT", Font.BOLD, 44));
        titleLabel.setForeground(TEXT_LIGHT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("~ Hotel Reservation System ~");
        subtitleLabel.setFont(new Font("Georgia", Font.ITALIC, 18));
        subtitleLabel.setForeground(SAND_WARM);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Wave decorative line
        JPanel line = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(SAND_WARM);
                g2d.setStroke(new BasicStroke(3));
                // Draw wave pattern
                int y = getHeight() / 2;
                for (int x = 0; x < getWidth(); x += 20) {
                    g2d.drawArc(x - 10, y - 5, 20, 10, 0, 180);
                }
            }
        };
        line.setOpaque(false);
        line.setPreferredSize(new Dimension(220, 30));
        line.setMaximumSize(new Dimension(220, 30));
        line.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel locationLabel = new JLabel("\uD83C\uDFD6 Galle, Sri Lanka");
        locationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        locationLabel.setForeground(TEXT_LIGHT);
        locationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel taglineLabel = new JLabel("<html><center>Where Waves Meet<br>Luxury & Comfort</center></html>");
        taglineLabel.setFont(new Font("Georgia", Font.ITALIC, 15));
        taglineLabel.setForeground(SAND_LIGHT);
        taglineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(iconLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(subtitleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(line);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPanel.add(locationLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(taglineLabel);

        panel.add(contentPanel);
        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD_BG);
        panel.setLayout(new GridBagLayout());

        JPanel formContainer = new JPanel();
        formContainer.setOpaque(false);
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setBorder(new EmptyBorder(40, 60, 40, 60));

        // Welcome text
        JLabel welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 34));
        welcomeLabel.setForeground(OCEAN_DEEP);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel instructionLabel = new JLabel("Sign in to access the resort management portal");
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        instructionLabel.setForeground(new Color(120, 120, 120));
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        formContainer.add(welcomeLabel);
        formContainer.add(Box.createRigidArea(new Dimension(0, 8)));
        formContainer.add(instructionLabel);
        formContainer.add(Box.createRigidArea(new Dimension(0, 40)));

        // Username field
        formContainer.add(createLabel("USERNAME"));
        formContainer.add(Box.createRigidArea(new Dimension(0, 8)));
        usernameField = createStyledTextField();
        formContainer.add(usernameField);
        formContainer.add(Box.createRigidArea(new Dimension(0, 20)));

        // Password field
        formContainer.add(createLabel("PASSWORD"));
        formContainer.add(Box.createRigidArea(new Dimension(0, 8)));
        passwordField = createStyledPasswordField();
        formContainer.add(passwordField);
        formContainer.add(Box.createRigidArea(new Dimension(0, 30)));

        // Login button
        loginButton = createStyledButton("Sign In");
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        formContainer.add(loginButton);
        formContainer.add(Box.createRigidArea(new Dimension(0, 15)));

        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusLabel.setForeground(CORAL_PINK);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formContainer.add(statusLabel);

        formContainer.add(Box.createVerticalGlue());

        // Footer hint
        JLabel hintLabel = new JLabel("<html><center>Default credentials: <b>admin</b> / <b>admin123</b></center></html>");
        hintLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        hintLabel.setForeground(TEXT_MUTED);
        hintLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formContainer.add(hintLabel);

        panel.add(formContainer);

        // Action listeners
        loginButton.addActionListener(e -> performLogin());

        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        };
        usernameField.addKeyListener(enterKeyListener);
        passwordField.addKeyListener(enterKeyListener);

        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(new Color(108, 117, 125));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        field.setOpaque(false);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBackground(INPUT_BG);
        field.setForeground(OCEAN_DEEP);
        field.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(10, INPUT_BORDER),
            new EmptyBorder(12, 15, 12, 15)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        field.setOpaque(false);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBackground(INPUT_BG);
        field.setForeground(OCEAN_DEEP);
        field.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(10, INPUT_BORDER),
            new EmptyBorder(12, 15, 12, 15)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2d.setColor(OCEAN_DEEP.darker());
                } else if (getModel().isRollover()) {
                    GradientPaint gp = new GradientPaint(0, 0, OCEAN_LIGHT, 0, getHeight(), OCEAN_DEEP);
                    g2d.setPaint(gp);
                } else {
                    GradientPaint gp = new GradientPaint(0, 0, OCEAN_DEEP, 0, getHeight(), OCEAN_LIGHT);
                    g2d.setPaint(gp);
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), x, y);
            }
        };
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void performLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both username and password");
            statusLabel.setForeground(CORAL_PINK);
            return;
        }

        loginButton.setEnabled(false);
        statusLabel.setText("Authenticating...");
        statusLabel.setForeground(OCEAN_LIGHT);

        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                return authController.login(username, password);
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        dispose();
                        MainFrame mainFrame = new MainFrame(authController);
                        mainFrame.setVisible(true);
                    } else {
                        statusLabel.setText("Invalid username or password");
                        statusLabel.setForeground(CORAL_PINK);
                        passwordField.setText("");
                        loginButton.setEnabled(true);
                    }
                } catch (Exception ex) {
                    statusLabel.setText("Login error: " + ex.getMessage());
                    statusLabel.setForeground(CORAL_PINK);
                    loginButton.setEnabled(true);
                }
            }
        };
        worker.execute();
    }

    // Custom rounded border class
    private static class RoundedBorder extends javax.swing.border.AbstractBorder {
        private final int radius;
        private final Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 8, 4, 8);
        }
    }
}
