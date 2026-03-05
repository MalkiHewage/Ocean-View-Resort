package view;

import controller.AuthController;
import controller.ReservationController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Modern tropical beach resort styled main dashboard frame.
 */
public class MainFrame extends JFrame {
    private final AuthController authController;
    private final ReservationController reservationController;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JButton activeButton;

    // Tropical Beach Resort color scheme
    private static final Color OCEAN_DEEP = new Color(0, 119, 182);
    private static final Color OCEAN_LIGHT = new Color(3, 169, 244);
    private static final Color SAND_WARM = new Color(255, 183, 77);
    private static final Color SAND_LIGHT = new Color(255, 224, 178);
    private static final Color CORAL_PINK = new Color(255, 138, 128);
    private static final Color SIDEBAR_BG = new Color(1, 87, 155);
    private static final Color SIDEBAR_HOVER = new Color(2, 136, 209);
    private static final Color SIDEBAR_ACTIVE = new Color(255, 183, 77);
    private static final Color CONTENT_BG = new Color(240, 248, 255);
    private static final Color TEXT_LIGHT = new Color(255, 255, 255);
    private static final Color TEXT_MUTED = new Color(189, 189, 189);

    // Panel names
    private static final String PANEL_ADD = "ADD_RESERVATION";
    private static final String PANEL_DISPLAY = "DISPLAY_RESERVATION";
    private static final String PANEL_BILL = "BILL";
    private static final String PANEL_WELCOME = "WELCOME";

    public MainFrame(AuthController authController) {
        this.authController = authController;
        this.reservationController = new ReservationController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Ocean View Resort - Hotel Reservation System");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1250, 780);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 650));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    MainFrame.this,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        setLayout(new BorderLayout());

        // Create sidebar
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // Create content panel with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(CONTENT_BG);

        // Add panels
        contentPanel.add(createWelcomePanel(), PANEL_WELCOME);
        contentPanel.add(new AddReservationPanel(reservationController), PANEL_ADD);
        contentPanel.add(new DisplayReservationPanel(reservationController), PANEL_DISPLAY);
        contentPanel.add(new BillPanel(reservationController), PANEL_BILL);

        add(contentPanel, BorderLayout.CENTER);

        cardLayout.show(contentPanel, PANEL_WELCOME);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(290, 0));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // Header panel with branding
        JPanel headerPanel = createHeaderPanel();
        sidebar.add(headerPanel);

        // Navigation section
        JPanel navSection = new JPanel();
        navSection.setOpaque(false);
        navSection.setLayout(new BoxLayout(navSection, BoxLayout.Y_AXIS));
        navSection.setBorder(new EmptyBorder(25, 18, 25, 18));

        JLabel navLabel = new JLabel("MAIN MENU");
        navLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        navLabel.setForeground(SAND_LIGHT);
        navLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        navLabel.setBorder(new EmptyBorder(0, 12, 12, 0));
        navSection.add(navLabel);

        // Menu buttons with new icons
        JButton addButton = createMenuButton("New Booking", "\uD83C\uDFD6", PANEL_ADD);
        JButton displayButton = createMenuButton("View Booking", "\uD83D\uDCCB", PANEL_DISPLAY);
        JButton billButton = createMenuButton("Generate Invoice", "\uD83D\uDCB5", PANEL_BILL);

        navSection.add(addButton);
        navSection.add(Box.createRigidArea(new Dimension(0, 10)));
        navSection.add(displayButton);
        navSection.add(Box.createRigidArea(new Dimension(0, 10)));
        navSection.add(billButton);

        sidebar.add(navSection);
        sidebar.add(Box.createVerticalGlue());

        // User section
        JPanel userSection = createUserSection();
        sidebar.add(userSection);

        return sidebar;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, OCEAN_DEEP, 0, getHeight(), SIDEBAR_BG);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setMaximumSize(new Dimension(290, 150));
        headerPanel.setPreferredSize(new Dimension(290, 150));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(new EmptyBorder(28, 22, 28, 22));

        // Palm tree icon
        JLabel iconLabel = new JLabel("\uD83C\uDF34");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        iconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel("Ocean View Resort");
        titleLabel.setFont(new Font("Brush Script MT", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_LIGHT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("~ Hotel Reservation ~");
        subtitleLabel.setFont(new Font("Georgia", Font.ITALIC, 13));
        subtitleLabel.setForeground(SAND_WARM);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        headerPanel.add(iconLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        headerPanel.add(subtitleLabel);

        return headerPanel;
    }

    private JPanel createUserSection() {
        JPanel userSection = new JPanel();
        userSection.setBackground(new Color(0, 96, 160));
        userSection.setMaximumSize(new Dimension(290, 140));
        userSection.setPreferredSize(new Dimension(290, 140));
        userSection.setLayout(new BoxLayout(userSection, BoxLayout.Y_AXIS));
        userSection.setBorder(new EmptyBorder(18, 22, 18, 22));

        // User info
        JPanel userInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        userInfo.setOpaque(false);
        userInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Avatar circle with gradient
        JLabel avatar = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, SAND_WARM, 40, 40, CORAL_PINK);
                g2d.setPaint(gp);
                g2d.fillOval(0, 0, 44, 44);
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 18));
                String initial = authController.getCurrentUser().getUsername().substring(0, 1).toUpperCase();
                FontMetrics fm = g2d.getFontMetrics();
                int x = (44 - fm.stringWidth(initial)) / 2;
                int y = (44 + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(initial, x, y);
            }
        };
        avatar.setPreferredSize(new Dimension(44, 44));

        JPanel userText = new JPanel();
        userText.setOpaque(false);
        userText.setLayout(new BoxLayout(userText, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel(authController.getCurrentUser().getUsername());
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        usernameLabel.setForeground(TEXT_LIGHT);

        JLabel roleLabel = new JLabel("Resort Manager");
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        roleLabel.setForeground(SAND_LIGHT);

        userText.add(usernameLabel);
        userText.add(roleLabel);

        userInfo.add(avatar);
        userInfo.add(userText);

        userSection.add(userInfo);
        userSection.add(Box.createRigidArea(new Dimension(0, 18)));

        // Logout button
        JButton logoutButton = new JButton("Sign Out") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isRollover()) {
                    g2d.setColor(new Color(229, 57, 53));
                } else {
                    g2d.setColor(CORAL_PINK);
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
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setMaximumSize(new Dimension(246, 42));
        logoutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> performLogout());

        userSection.add(logoutButton);

        return userSection;
    }

    private JButton createMenuButton(String text, String icon, String panelName) {
        JButton button = new JButton(icon + "  " + text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (this == activeButton) {
                    GradientPaint gp = new GradientPaint(0, 0, SAND_WARM, getWidth(), 0, SAND_LIGHT);
                    g2d.setPaint(gp);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                } else if (getModel().isRollover()) {
                    g2d.setColor(SIDEBAR_HOVER);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                }

                g2d.setColor(this == activeButton ? OCEAN_DEEP : TEXT_LIGHT);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), 18, y);
            }
        };

        button.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        button.setForeground(TEXT_LIGHT);
        button.setMaximumSize(new Dimension(254, 52));
        button.setPreferredSize(new Dimension(254, 52));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.addActionListener(e -> {
            activeButton = button;
            cardLayout.show(contentPanel, panelName);
            getContentPane().repaint();
        });

        return button;
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                // Beach gradient background
                GradientPaint gp = new GradientPaint(0, 0, new Color(224, 247, 250), 0, getHeight(), CONTENT_BG);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridBagLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Welcome header
        JLabel welcomeLabel = new JLabel("Welcome to Ocean View Resort");
        welcomeLabel.setFont(new Font("Brush Script MT", Font.BOLD, 42));
        welcomeLabel.setForeground(OCEAN_DEEP);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel locationLabel = new JLabel("\uD83C\uDFD6 Galle, Sri Lanka");
        locationLabel.setFont(new Font("Georgia", Font.ITALIC, 20));
        locationLabel.setForeground(SAND_WARM);
        locationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Wave decorative line
        JPanel line = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(OCEAN_LIGHT);
                g2d.setStroke(new BasicStroke(3));
                int y = getHeight() / 2;
                for (int x = 0; x < getWidth(); x += 25) {
                    g2d.drawArc(x - 12, y - 6, 25, 12, 0, 180);
                }
            }
        };
        line.setOpaque(false);
        line.setPreferredSize(new Dimension(180, 30));
        line.setMaximumSize(new Dimension(180, 30));
        line.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = new JLabel("Hotel Reservation System");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        descLabel.setForeground(new Color(100, 100, 100));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Room rates card
        JPanel ratesCard = createRatesCard();
        ratesCard.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Quick actions
        JPanel actionsPanel = createQuickActions();
        actionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(locationLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 22)));
        centerPanel.add(line);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 22)));
        centerPanel.add(descLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 45)));
        centerPanel.add(ratesCard);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 35)));
        centerPanel.add(actionsPanel);

        panel.add(centerPanel);
        return panel;
    }

    private JPanel createRatesCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Shadow
                g2d.setColor(new Color(0, 0, 0, 25));
                g2d.fillRoundRect(5, 5, getWidth() - 5, getHeight() - 5, 25, 25);

                // Card background with gradient
                GradientPaint gp = new GradientPaint(0, 0, Color.WHITE, 0, getHeight(), new Color(250, 250, 255));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth() - 5, getHeight() - 5, 25, 25);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(28, 35, 28, 35));
        card.setPreferredSize(new Dimension(550, 240));
        card.setMaximumSize(new Dimension(550, 240));

        // Header
        JLabel headerLabel = new JLabel("\uD83C\uDFE8 Room Rates (per night)");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(OCEAN_DEEP);
        headerLabel.setBorder(new EmptyBorder(0, 0, 18, 0));

        // Rates grid
        JPanel ratesGrid = new JPanel(new GridLayout(2, 2, 25, 18));
        ratesGrid.setOpaque(false);

        String[][] rates = {
            {"Single Room", "LKR 5,000", "\uD83D\uDECF"},
            {"Double Room", "LKR 8,000", "\uD83D\uDECF\uD83D\uDECF"},
            {"Family Suite", "LKR 12,000", "\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67"},
            {"Luxury Suite", "LKR 20,000", "\u2B50"}
        };

        for (String[] rate : rates) {
            JPanel rateItem = new JPanel();
            rateItem.setOpaque(false);
            rateItem.setLayout(new BoxLayout(rateItem, BoxLayout.Y_AXIS));

            JLabel typeLabel = new JLabel(rate[2] + " " + rate[0]);
            typeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            typeLabel.setForeground(new Color(100, 100, 100));

            JLabel priceLabel = new JLabel(rate[1]);
            priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
            priceLabel.setForeground(SAND_WARM);

            rateItem.add(typeLabel);
            rateItem.add(Box.createRigidArea(new Dimension(0, 4)));
            rateItem.add(priceLabel);
            ratesGrid.add(rateItem);
        }

        card.add(headerLabel, BorderLayout.NORTH);
        card.add(ratesGrid, BorderLayout.CENTER);

        return card;
    }

    private JPanel createQuickActions() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        panel.setOpaque(false);

        String[][] actions = {
            {"New Booking", PANEL_ADD},
            {"Find Booking", PANEL_DISPLAY},
            {"Generate Invoice", PANEL_BILL}
        };

        for (String[] action : actions) {
            JButton btn = new JButton(action[0]) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    if (getModel().isRollover()) {
                        GradientPaint gp = new GradientPaint(0, 0, OCEAN_LIGHT, 0, getHeight(), OCEAN_DEEP);
                        g2d.setPaint(gp);
                        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                        g2d.setColor(Color.WHITE);
                    } else {
                        g2d.setColor(Color.WHITE);
                        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                        g2d.setColor(OCEAN_LIGHT);
                        g2d.setStroke(new BasicStroke(2));
                        g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 12, 12);
                    }

                    g2d.setFont(getFont());
                    FontMetrics fm = g2d.getFontMetrics();
                    int x = (getWidth() - fm.stringWidth(getText())) / 2;
                    int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                    g2d.drawString(getText(), x, y);
                }
            };
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setPreferredSize(new Dimension(160, 48));
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            String panelName = action[1];
            btn.addActionListener(e -> cardLayout.show(contentPanel, panelName));

            panel.add(btn);
        }

        return panel;
    }

    private void performLogout() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to sign out?",
            "Confirm Sign Out",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            authController.logout();
            dispose();
            LoginFrame loginFrame = new LoginFrame(authController);
            loginFrame.setVisible(true);
        }
    }
}
