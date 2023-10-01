package org.example;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingWindow extends JFrame {
    private static final int WIDTH = 230;
    private static final int HEIGHT = 350;
    String currentWinSizeValue = "Установленная длина: ";
    String currentFieldValue = "Установленный размер поля: ";

    JRadioButton human, ai;
    ButtonGroup buttonGroup;
    JLabel choiceHA, fieldSize, currentFieldSize, winSize, currentWinSize;
    JPanel mainPanel;
    JSlider sliderWinSize, sliderFieldSize;
    JButton btnStart;
    final int MINSIZE = 3;
    final int MAXSIZE = 10;
    GameWindow gameWindow;

    SettingWindow(GameWindow gameWindow){
        this.gameWindow = gameWindow;

        int centerGameWindowX = gameWindow.getX() + gameWindow.getWidth()/2;
        int centerGameWindowY = gameWindow.getY() + gameWindow.getHeight()/2;
        setLocation(centerGameWindowX - WIDTH/2, centerGameWindowY - HEIGHT/2);
        setSize(WIDTH, HEIGHT);

        human = new JRadioButton("PvP");
        ai = new JRadioButton("PvM");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(human);
        buttonGroup.add(ai);
        btnStart = new JButton("Start new game");
        mainPanel = new JPanel(new GridLayout(10,1));
        fieldSize = new JLabel("Выбор размера поля");
        choiceHA = new JLabel("Режим игры");
        currentFieldSize = new JLabel(currentFieldValue + MINSIZE);
        sliderFieldSize = new JSlider(MINSIZE, MAXSIZE, MINSIZE);
        winSize = new JLabel("Выберите количество победных клеток");
        currentWinSize = new JLabel(currentWinSizeValue + MINSIZE);
        sliderWinSize = new JSlider(MINSIZE, MAXSIZE, MINSIZE);

        mainPanel.add(choiceHA);


        setLocationRelativeTo(gameWindow);
        setSize(WIDTH, HEIGHT);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                startGame();
            }
        });

        sliderWinSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                currentWinSize.setText(currentWinSizeValue + sliderWinSize.getValue());
                sliderWinSize.setMaximum(sliderFieldSize.getValue()); // ограничили максимум чтобы количество
                // выигрышных клеток не можго быть больше размера поля
            }
        });

        sliderFieldSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                currentFieldSize.setText(currentFieldValue + sliderFieldSize.getValue());
            }
        });



        ai.setSelected(true);
        mainPanel.add(ai);
        mainPanel.add(human);
        mainPanel.add(fieldSize);
        mainPanel.add(currentFieldSize);
        mainPanel.add(sliderFieldSize);
        mainPanel.add(currentWinSize);
        mainPanel.add(winSize);
        mainPanel.add(sliderWinSize);
        add(btnStart, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.NORTH);
    }

    public void startGame(){
        int mode;
        if (human.isSelected()) {
            mode = 1;
        } else if (ai.isSelected()) {
            mode = 0;
        } else {
            throw new RuntimeException("Не выбран режим игры");
        }
        int size = sliderFieldSize.getValue();
        int winLength = sliderWinSize.getValue();
        gameWindow.startNewGame(mode, size, size, winLength);

    }
}