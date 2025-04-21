package org.example.View;

import org.example.Controller.Controller;
import org.example.Controller.Slider;
import org.example.Factory.Factory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class View extends JPanel {
    private final Factory factory;
    private final Controller controller;
    private JFrame frame;
    private Timer timer;
    private JSlider bodySupplierDelaySlider;
    private JSlider motorSupplierDelaySlider;
    private JSlider accessorySupplierDelaySlider;
    private JSlider dealerDelaySlider;
    private JSlider workersDelaySlider;

    private JTextArea bodyStorageInfo;
    private JTextArea motorStorageInfo;
    private JTextArea accessoryStorageInfo;
    private JTextArea carStorageInfo;

    public View(Factory factory, Controller controller) {
        this.factory = factory;
        this.controller = controller;
        initialize();
        startTimer();
    }

    private void initialize() {
        frame = new JFrame("🚗 Factory Dashboard");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.addWindowListener(controller);

        Color softPink = new Color(255, 230, 240);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(softPink);

        JPanel sliderPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        sliderPanel.setBorder(BorderFactory.createTitledBorder("⏱ Delays (in ticks)"));
        sliderPanel.setBackground(softPink);

        bodySupplierDelaySlider = createSlider("bodySupplierDelay");
        motorSupplierDelaySlider = createSlider("motorSupplierDelay");
        accessorySupplierDelaySlider = createSlider("accessorySupplierDelay");
        dealerDelaySlider = createSlider("dealerDelay");
        workersDelaySlider = createSlider("workersDelay");

        sliderPanel.add(new JLabel("🧍 Body Supplier Delay:"));
        sliderPanel.add(bodySupplierDelaySlider);
        sliderPanel.add(new JLabel("🚗 Motor Supplier Delay:"));
        sliderPanel.add(motorSupplierDelaySlider);
        sliderPanel.add(new JLabel("🎁 Accessory Supplier Delay:"));
        sliderPanel.add(accessorySupplierDelaySlider);
        sliderPanel.add(new JLabel("🧑‍💼 Dealer Delay:"));
        sliderPanel.add(dealerDelaySlider);
        sliderPanel.add(new JLabel("👷 Worker Delay:"));
        sliderPanel.add(workersDelaySlider);

        JPanel infoPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("📦 Storage Info"));
        infoPanel.setBackground(softPink);

        bodyStorageInfo = createTextArea();
        motorStorageInfo = createTextArea();
        accessoryStorageInfo = createTextArea();
        carStorageInfo = createTextArea();

        infoPanel.add(createStoragePanel("🧍 Body Storage", bodyStorageInfo));
        infoPanel.add(createStoragePanel("🚗 Motor Storage", motorStorageInfo));
        infoPanel.add(createStoragePanel("🎁 Accessory Storage", accessoryStorageInfo));
        infoPanel.add(createStoragePanel("🚙 Car Storage", carStorageInfo));

        mainPanel.add(sliderPanel, BorderLayout.NORTH);
        mainPanel.add(infoPanel, BorderLayout.CENTER);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setVisible(true);
    }


    private void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateStorageInfo(
                        factory.getBodyStorageCapacity(),
                        factory.getMotorStorageCapacity(),
                        factory.getAccesoryStorageCapacity(),
                        factory.getCarStorageCapacity()
                );
            }
        }, 0, 500);
    }

    public void updateStorageInfo(int bodyInfo, int motorInfo, int accessoryInfo, int autoInfo) {
        bodyStorageInfo.setText(String.valueOf(bodyInfo));
        motorStorageInfo.setText(String.valueOf(motorInfo));
        accessoryStorageInfo.setText(String.valueOf(accessoryInfo));
        carStorageInfo.setText(String.valueOf(autoInfo));
    }

    private JSlider createSlider(String type) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
        slider.addChangeListener(new Slider(factory, type));
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        return slider;
    }

    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea("0", 5, 10);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.BOLD, 18));
        textArea.setBackground(new Color(245, 245, 245));
        textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        textArea.setHighlighter(null);
        return textArea;
    }

    private JPanel createStoragePanel(String title, JTextArea textArea) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(title, JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(label, BorderLayout.NORTH);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }
}
