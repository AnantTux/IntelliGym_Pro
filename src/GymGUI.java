import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import service.WorkoutService;

public class GymGUI {
    // High-Contrast Dark Theme Colors
    private static final Color BG_DARK = new Color(15, 15, 15);
    private static final Color ACCENT_BLUE = new Color(0, 150, 255);
    private static final Color TEXT_WHITE = Color.WHITE;
    private static final Color CARD_BG = new Color(30, 30, 30);

    public static void main(String[] args) {
        // Setup Frame
        JFrame frame = new JFrame("IntelliGym Pro v1.4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 950);
        frame.getContentPane().setBackground(BG_DARK);
        frame.setLayout(new BorderLayout());

        // --- Header Section ---
        JLabel header = new JLabel("INTELLIGYM PRO", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 32));
        header.setForeground(ACCENT_BLUE);
        header.setBorder(new EmptyBorder(25, 0, 25, 0));
        frame.add(header, BorderLayout.NORTH);

        // --- Main Content Panel ---
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(BG_DARK);
        contentPanel.setBorder(new EmptyBorder(10, 50, 10, 50));

        // --- Form Section (Input Fields) ---
        JPanel form = new JPanel(new GridLayout(0, 2, 15, 20));
        form.setBackground(BG_DARK);

        JTextField ageField = createStyledTextField();
        
        // Days per Week Slider
        JSlider daysSlider = new JSlider(3, 6, 5);
        daysSlider.setBackground(BG_DARK);
        daysSlider.setForeground(TEXT_WHITE);
        daysSlider.setMajorTickSpacing(1);
        daysSlider.setPaintTicks(true);
        daysSlider.setPaintLabels(true);

        // Split Dropdown
        String[] splits = {"PPL (Push/Pull/Legs)", "Bro Split (Isolation)"};
        JComboBox<String> splitCombo = createStyledCombo(splits);

        // Duration Dropdown
        String[] hours = {"1.0", "1.5", "2.0"};
        JComboBox<String> hourCombo = createStyledCombo(hours);

        addLabeledComponent(form, "USER AGE:", ageField);
        addLabeledComponent(form, "WORKOUT DAYS / WEEK:", daysSlider);
        addLabeledComponent(form, "ROUTINE STYLE:", splitCombo);
        addLabeledComponent(form, "TIME PER SESSION (HRS):", hourCombo);
        
        contentPanel.add(form);

        // --- Equipment Section ---
        JLabel eqTitle = new JLabel("AVAILABLE EQUIPMENT:");
        eqTitle.setForeground(ACCENT_BLUE);
        eqTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        eqTitle.setBorder(new EmptyBorder(25, 0, 10, 0));
        contentPanel.add(eqTitle);

        JPanel eqPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        eqPanel.setBackground(BG_DARK);
        String[] machines = {"Treadmill", "Barbell", "Dumbbell", "Cable", "Machine", "Smith Machine", "Kettlebell", "Leg Press"};
        List<JCheckBox> checkBoxes = new ArrayList<>();
        for (String m : machines) {
            JCheckBox cb = new JCheckBox(m);
            cb.setForeground(TEXT_WHITE);
            cb.setBackground(BG_DARK);
            cb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            checkBoxes.add(cb);
            eqPanel.add(cb);
        }
        contentPanel.add(eqPanel);

        // --- Result Output Area ---
        JTextArea outputArea = new JTextArea(20, 50);
        outputArea.setBackground(Color.BLACK);
        outputArea.setForeground(new Color(50, 255, 50)); // Terminal Green
        outputArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        outputArea.setEditable(false);
        outputArea.setMargin(new Insets(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(new LineBorder(ACCENT_BLUE, 2));

        // --- Generate Button ---
        JButton genBtn = new JButton("GENERATE OPTIMIZED WORKOUT");
        genBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        genBtn.setBackground(ACCENT_BLUE);
        genBtn.setForeground(Color.WHITE);
        genBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        genBtn.setFocusPainted(false);
        genBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        genBtn.addActionListener(e -> {
            try {
                int age = Integer.parseInt(ageField.getText());
                int selectedDays = daysSlider.getValue();
                double duration = Double.parseDouble((String) hourCombo.getSelectedItem());
                String splitType = (splitCombo.getSelectedIndex() == 0) ? "PPL" : "BroSplit";

                List<String> inventory = new ArrayList<>();
                for (JCheckBox cb : checkBoxes) if (cb.isSelected()) inventory.add(cb.getText());

                WorkoutService ws = new WorkoutService();
                List<String> plan = ws.generatePlan(splitType, age, duration, inventory);

                // Build Visual Schedule
                StringBuilder sb = new StringBuilder();
                sb.append("==================================================\n");
                sb.append("   GENERATED PLAN: ").append(selectedDays).append(" DAYS | ").append(duration).append(" HOURS\n");
                sb.append("   SAFETY FILTER: ACTIVE FOR AGE ").append(age).append("\n");
                sb.append("==================================================\n\n");

                String[] weekDays = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
                for (int i = 0; i < selectedDays; i++) {
                    sb.append("[").append(weekDays[i]).append("]\n");
                    // We cycle through the generated variations to fill the days
                    sb.append(" > ").append(plan.get(i % plan.size())).append("\n");
                    sb.append("--------------------------------------------------\n");
                }
                sb.append("\n[REST & RECOVERY: SUNDAY]");
                outputArea.setText(sb.toString());

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid age (numbers only).", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        contentPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        contentPanel.add(genBtn);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        contentPanel.add(scrollPane);

        frame.add(contentPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // --- Component Styling Helpers ---

    private static void addLabeledComponent(JPanel panel, String labelText, JComponent comp) {
        JLabel label = new JLabel(labelText);
        label.setForeground(TEXT_WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel.add(label);
        panel.add(comp);
    }

    private static JTextField createStyledTextField() {
        JTextField f = new JTextField();
        f.setBackground(CARD_BG);
        f.setForeground(TEXT_WHITE);
        f.setCaretColor(TEXT_WHITE);
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        f.setBorder(new LineBorder(new Color(80, 80, 80), 1));
        return f;
    }

    private static JComboBox<String> createStyledCombo(String[] items) {
        JComboBox<String> b = new JComboBox<>(items);
        b.setBackground(Color.WHITE);
        b.setForeground(Color.BLACK);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return b;
    }
}
