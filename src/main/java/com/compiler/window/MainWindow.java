package com.compiler.window;

import com.compiler.build.Builder;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainWindow {
    private static MainWindow instance;
    private JFrame jFrame;
    private JTextArea textArea;
    private JButton button;

    public static MainWindow getInstance(String title, int sizeX, int sizeY) {
        if(instance == null) {
            instance = new MainWindow(title, sizeX, sizeY);
        }
        return instance;
    }

    private MainWindow() {}

    private MainWindow(String title, int sizeX, int sizeY) {
        jFrame = new JFrame(title);
        jFrame.setSize(sizeX, sizeY);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());
        init();
    }

    public void start() {
        jFrame.setVisible(true);
    }

    public void stop() {
        jFrame.setVisible(false);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JButton getButton() {
        return button;
    }

    private void init() {
        textArea = new JTextArea("""
                public class Main {
                    public static void main(String[] args) {
                        System.out.println("Hello World!");   
                    }
                }""");
        textArea.setCaretColor(Color.WHITE);
        textArea.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        textArea.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        textArea.setBackground(Color.darkGray);
        textArea.setForeground(Color.WHITE);
        JScrollPane scrollBar = new JScrollPane(textArea);
        jFrame.add(scrollBar, BorderLayout.CENTER);
        button = new JButton("Compile");
        jFrame.add(button, BorderLayout.SOUTH);
        initActionButton(button);
    }

    private void initActionButton(JButton button) {
        button.addActionListener(v -> {
            String text = textArea.getText();
            Matcher matcher = Pattern.compile("public class (\\w+)").matcher(text);
            String className = null;
            if(matcher.find()) {
                className = matcher.group(1);
            }
            if(className != null) {
                try {
                    Builder builder = new Builder(className, text);
                    builder.compileToClass();
                    String resp = builder.startClass();
                    new CompilerWindow(resp);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
