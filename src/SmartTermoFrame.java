import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class SmartTermoFrame extends JFrame {
    private JLabel lblCurrentTemp;
    private JLabel lblDesiredTemp;
    private JLabel lblCurrentHumidity;
    private JLabel lblDesiredHumidity;
    private JLabel lblStatus;
    private JLabel lblDate;
    private JButton btnIncreaseTemp;
    private JButton btnDecreaseTemp;
    private JButton btnIncreaseHumidity;
    private JButton btnDecreaseHumidity;
    private JButton btnToggleTempUnit;
    private JTextArea logArea;

    private double currentTemp = 20.0;
    private double desiredTemp = 22.0;
    private int currentHumidity = 50;
    private int desiredHumidity = 55;

    public SmartTermoFrame() {
        // Configuração da janela principal
        setTitle("SmartTermo");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicializar componentes
        initComponents();

        // Adicionar os componentes a um painel principal
        JPanel mainPanel = new JPanel(new GridLayout(6, 2));
        mainPanel.add(new JLabel("Current Temp:"));
        mainPanel.add(lblCurrentTemp);
        mainPanel.add(new JLabel("Desired Temp:"));
        mainPanel.add(lblDesiredTemp);
        mainPanel.add(new JLabel("Current Humidity:"));
        mainPanel.add(lblCurrentHumidity);
        mainPanel.add(new JLabel("Desired Humidity:"));
        mainPanel.add(lblDesiredHumidity);
        mainPanel.add(btnIncreaseTemp);
        mainPanel.add(btnDecreaseTemp);
        mainPanel.add(btnIncreaseHumidity);
        mainPanel.add(btnDecreaseHumidity);
        mainPanel.add(btnToggleTempUnit);
        mainPanel.add(lblStatus);

        // Adicionar o painel principal e a área de log à janela
        add(mainPanel, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.SOUTH);

        // Configuração inicial da data
        lblDate.setText("Date: " + new Date().toString());
    }

    private void initComponents() {
        lblCurrentTemp = new JLabel(String.format("%.1f °C", currentTemp));
        lblDesiredTemp = new JLabel(String.format("%.1f °C", desiredTemp));
        lblCurrentHumidity = new JLabel(currentHumidity + "%");
        lblDesiredHumidity = new JLabel(desiredHumidity + "%");
        lblStatus = new JLabel("Heating: OFF");
        lblDate = new JLabel();

        btnIncreaseTemp = new JButton("+ Temp");
        btnDecreaseTemp = new JButton("- Temp");
        btnIncreaseHumidity = new JButton("+ Humidity");
        btnDecreaseHumidity = new JButton("- Humidity");
        btnToggleTempUnit = new JButton("Toggle °C/°F");

        logArea = new JTextArea();
        logArea.setEditable(false);

        // Adicionar os listeners aos botões
        btnIncreaseTemp.addActionListener(e -> adjustTemperature(0.5));
        btnDecreaseTemp.addActionListener(e -> adjustTemperature(-0.5));
        btnIncreaseHumidity.addActionListener(e -> adjustHumidity(5));
        btnDecreaseHumidity.addActionListener(e -> adjustHumidity(-5));
        btnToggleTempUnit.addActionListener(e -> toggleTemperatureUnit());
    }

    private void adjustTemperature(double change) {
        desiredTemp += change;
        lblDesiredTemp.setText(String.format("Desired Temp: %.1f °C", desiredTemp));
        logArea.append(new Date() + ": Desired temperature adjusted to " + String.format("%.1f", desiredTemp) + " °C\n");
        updateHeatingStatus();
    }

    private void simulateTemperatureChange() {
        if (Math.random() < 0.3) { // 30% de chance de diminuir a temperatura
            currentTemp -= 0.5;
        }
        lblCurrentTemp.setText(String.format("Current Temp: %.1f °C", currentTemp));
        logArea.append(new Date() + ": Current temperature changed to " + String.format("%.1f", currentTemp) + " °C\n");
        updateHeatingStatus(); // Atualiza o estado do aquecimento
    }

    private void adjustHumidity(int change) {
        desiredHumidity += change;
        lblDesiredHumidity.setText("Desired Humidity: " + desiredHumidity + "%");
        logArea.append(new Date() + ": Desired humidity adjusted to " + desiredHumidity + "%\n");
        revalidate();
        repaint();
    }

    private void toggleTemperatureUnit() {
        if (lblCurrentTemp.getText().contains("°C")) {
            double fahrenheit = currentTemp * 9 / 5 + 32;
            double desiredFahrenheit = desiredTemp * 9 / 5 + 32;
            lblCurrentTemp.setText(String.format("Current Temp: %.1f °F", fahrenheit));
            lblDesiredTemp.setText(String.format("Desired Temp: %.1f °F", desiredFahrenheit));
            logArea.append(new Date() + ": Temperature display switched to Fahrenheit.\n");
        } else {
            lblCurrentTemp.setText(String.format("Current Temp: %.1f °C", currentTemp));
            lblDesiredTemp.setText(String.format("Desired Temp: %.1f °C", desiredTemp));
            logArea.append(new Date() + ": Temperature display switched to Celsius.\n");
        }
        revalidate();
        repaint();
    }

    private void updateHeatingStatus() {
        if (currentTemp < desiredTemp) {
            lblStatus.setText("Heating: ON");
            logArea.append(new Date() + ": Heating turned ON.\n");
        } else {
            lblStatus.setText("Heating: OFF");
            logArea.append(new Date() + ": Heating turned OFF.\n");
        }
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SmartTermoFrame frame = new SmartTermoFrame();
            frame.setVisible(true);
        });
    }
}
