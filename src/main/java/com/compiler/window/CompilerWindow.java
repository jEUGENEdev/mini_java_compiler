package com.compiler.window;

import javax.swing.*;
import java.awt.*;

public class CompilerWindow {
    public CompilerWindow(String text) {
        JFrame jFrame = new JFrame("Output");
        jFrame.setSize(300, 250);
        jFrame.setLayout(new BorderLayout());
        JTextPane textPane = new JTextPane();
        textPane.setText(text);
        JScrollPane scrollPane = new JScrollPane(textPane);
        jFrame.add(scrollPane);
        jFrame.setVisible(true);
    }
}
