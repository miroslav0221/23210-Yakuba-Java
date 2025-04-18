package org.example.View;

import org.example.Model.Figure;
import org.example.Model.Model;
import org.example.Model.MyField;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class View {
    private Figure figure;
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel startPanel;
    private JPanel gamePanel;
    private JPanel scorePanel;
    private JLabel scoreLabel;
    private JTextArea tableScoresArea;
    private JLabel timerLabel;
    private GameListener listener;
    private MyField field;
    private Model model;
    private DefaultTableModel tableModel;
    private DefaultTableModel tableModelGlobal;
    private TetrisPanel tetrisPanel;
    private JPanel sidePanel;
    // Таблица для топ-5 очков
    private JTable scoreTable;
    private DefaultListModel<String> scoreListModel;

    public View(MyField field, Model model, GameListener listener) {
        this.field = field;
        this.model = model;
        this.listener = listener;
        initView();
    }

    public void showWindow() {
        frame.setVisible(true);
    }

    public void initView() {
        frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 820);
        frame.setLocationRelativeTo(null);

        mainPanel = new JPanel(new CardLayout());

        initStartPanel();
        initGamePanel();
        initScorePanel();

        mainPanel.add(startPanel, "start");
        mainPanel.add(gamePanel, "game");
        mainPanel.add(scorePanel, "scores");

        frame.add(mainPanel);
        //frame.pack();
        showStartScreen();
    }

    public void updateGlobalTableRecords(String[] records) {
        for (int i = 0; i < 20; i++) {
            tableModelGlobal.setValueAt(records[i], i, 1);
        }
    }

    private void initStartPanel() {
        startPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
                g2.setColor(new Color(180, 126, 222));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };

        startPanel.setOpaque(false);
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        startPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        startPanel.setBackground(new Color(254, 209, 189, 50));

        int heightButton = 80;
        int widthButton = 400;

        // Заголовок
        JLabel titleLabel = new JLabel("TETRIS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 64));
        titleLabel.setForeground(new Color(60, 0, 90));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startPanel.add(titleLabel);
        startPanel.add(Box.createVerticalStrut(60));

        // Кнопки
        JButton newGameBtn = createStyledButton("New Game", widthButton, heightButton, 24);
        JButton tableScoresBtn = createStyledButton("Table Scores", widthButton, heightButton, 24);
        JButton exitBtn = createStyledButton("Exit", widthButton, heightButton, 24);

        newGameBtn.addActionListener(e -> {
            showGameScreen();
            new Thread(() -> {
                try {
                    listener.play();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();
        });

        tableScoresBtn.addActionListener(e -> showScoreScreen());
        exitBtn.addActionListener(e -> listener.exit());

        startPanel.add(newGameBtn);
        startPanel.add(Box.createVerticalStrut(20));
        startPanel.add(tableScoresBtn);
        startPanel.add(Box.createVerticalStrut(20));
        startPanel.add(exitBtn);
    }

    // Метод для создания стильных кнопок
    private JButton createStyledButton(String text, int width, int height, int size) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, height));
        button.setMaximumSize(new Dimension(width, height));
        button.setMinimumSize(new Dimension(width, height));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI()); // убираем системный стиль
        button.setBackground(new Color(160, 110, 220));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, size));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        return button;
    }


    private void initGamePanel() {
        gamePanel = new JPanel(new BorderLayout());

        // Центр — игровое поле
        tetrisPanel = new TetrisPanel();
        tetrisPanel.setFocusable(true);
        tetrisPanel.addKeyListener(new KeysAdapter());
        gamePanel.add(tetrisPanel, BorderLayout.CENTER);

        // Правая боковая панель
        JPanel sidePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
                g2.setColor(new Color(180, 126, 222));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        sidePanel.setOpaque(false);
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        sidePanel.setPreferredSize(new Dimension(170, 600));
        sidePanel.setBackground(new Color(180, 126, 222, 50));

        // Очки
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        timerLabel = new JLabel("Time: 00:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timerLabel.setForeground(new Color(40, 0, 60));

        // Кнопка Паузы
        JButton pauseButton = createStyledButton("Pause", 150, 60, 21);
        pauseButton.addActionListener(e -> {
            listener.pause();
            tetrisPanel.requestFocusInWindow();
        });

        // Кнопка в Главное меню
        JButton mainMenuButton = createStyledButton("Main Menu", 150, 60, 21);
        mainMenuButton.addActionListener(e -> {
            listener.stop();
            field.doEmpty();
            listener.resetScores();
            showStartScreen();
        });

        // Таблица топ-5 очков (в стиле initScorePanel)
        String[] columnNames = {"Number", "Scores"};
        String[][] data = {
                {"1", "0"}, {"2", "0"}, {"3", "0"}, {"4", "0"}, {"5", "0"}
        };
        tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setForeground(new Color(40, 0, 60));
        table.setBackground(new Color(180, 126, 222, 50));
        table.setOpaque(true);

        // Центрирование содержимого ячеек
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Настройка заголовка таблицы
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(180, 126, 222, 50));
        header.setForeground(new Color(40, 0, 60));
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setOpaque(true);
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(100, -177));
        tableScrollPane.getViewport().setBackground(new Color(180, 126, 222, 50));
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Добавление компонентов
        sidePanel.add(scoreLabel);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(pauseButton);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(mainMenuButton);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(tableScrollPane);
        sidePanel.add(Box.createVerticalGlue());
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(timerLabel);

        gamePanel.add(sidePanel, BorderLayout.EAST);
    }




    public void updateTimer(String timeText) {
        SwingUtilities.invokeLater(() -> timerLabel.setText("Time: " + timeText));
    }

    private void initScorePanel() {
        scorePanel = new JPanel(new BorderLayout());

        String[] columnNames = {"Number", "Scores"};
        String[][] data = {
                {"1", "0"}, {"2", "0"}, {"3", "0"}, {"4", "0"}, {"5", "0"},
                {"6", "0"}, {"7", "0"}, {"8", "0"}, {"9", "0"}, {"10", "0"},
                {"11", "0"}, {"12", "0"}, {"13", "0"}, {"14", "0"}, {"15", "0"},
                {"16", "0"}, {"17", "0"}, {"18", "0"}, {"19", "0"}, {"20", "0"},
        };
        tableModelGlobal = new DefaultTableModel(data, columnNames);

        JTable tableGlobal = new JTable(tableModelGlobal) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(224, 192, 255) : new Color(210, 175, 240));
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(new Color(160, 110, 220));
                    c.setForeground(Color.WHITE);
                }
                return c;
            }
        };

        // Настройка таблицы
        tableGlobal.setRowHeight(30);
        tableGlobal.setFont(new Font("Arial", Font.PLAIN, 16));
        tableGlobal.setForeground(Color.BLACK);
        tableGlobal.setShowGrid(false);
        tableGlobal.setFillsViewportHeight(true);

        // Центровка текста по ячейкам
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tableGlobal.getColumnCount(); i++) {
            tableGlobal.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Заголовок таблицы
        JTableHeader header = tableGlobal.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 18));
        header.setBackground(new Color(150, 100, 200));
        header.setForeground(Color.WHITE);
        header.setOpaque(true);

        // Обёртка с прокруткой
        JScrollPane tableScrollPane = new JScrollPane(tableGlobal);
        tableScrollPane.setPreferredSize(new Dimension(100, 400));
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tableScrollPane.getViewport().setBackground(new Color(224, 192, 255));

        // Кнопка "Back"
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(500, 50));
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setUI(new javax.swing.plaf.basic.BasicButtonUI()); // снимаем стиль
        backButton.setBackground(new Color(160, 110, 220)); // твой цвет
        backButton.setForeground(Color.WHITE);             // цвет текста
        backButton.setFocusPainted(false);                 // без рамки фокуса
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.addActionListener(e -> showStartScreen());

        // Добавляем в панель
        scorePanel.add(tableScrollPane, BorderLayout.CENTER);
        scorePanel.add(backButton, BorderLayout.SOUTH);
    }


    public void updateFigure(Figure figure_) {
        this.figure = figure_;
    }

    public void updateScore(int score) {
        SwingUtilities.invokeLater(() -> scoreLabel.setText("Score: " + score));
    }

    public void updateTable(String[] values) {
        for(int i = 0; i < 5; i++) {
            tableModel.setValueAt(values[i], i, 1);
        }
    }

    public void GameOver() {
        JOptionPane.showMessageDialog(frame, "Game Over!");
        field.doEmpty();

        showStartScreen();
    }

    public void printGame(Figure figure_) {
        figure = figure_;
        MyField copy = new MyField();
        copy.copy(field);
        //model.mergeFigureToField(figure, copy);
        Figure shadowFigure = new Figure();
        shadowFigure.copy(figure);
        shadowFigure.changeColour("black");
        model.offsetLimitPosition(shadowFigure, copy);
        model.mergeFigureToField(shadowFigure, copy);
        model.mergeFigureToField(figure, copy);
        tetrisPanel.updateData(copy, figure);
        tetrisPanel.repaint();
    }

    private void showStartScreen() {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, "start");
    }

    private void showGameScreen() {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, "game");
        SwingUtilities.invokeLater(() -> tetrisPanel.requestFocusInWindow());
    }

    private void showScoreScreen() {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, "scores");
    }

    public void setListener(GameListener listener) {
        this.listener = listener;
    }

    class KeysAdapter implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if (figure != null) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> Model.left(figure, field);
                    case KeyEvent.VK_RIGHT -> Model.right(figure, field);
                    case KeyEvent.VK_DOWN -> Model.down(figure, field);
                    case KeyEvent.VK_UP -> Model.rotate(figure, field);
                }
                printGame(figure);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }
}