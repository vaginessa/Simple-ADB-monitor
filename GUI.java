package com.gradesscompany;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("FieldCanBeLocal")
public class GUI {
    private static int WIDTH = 500;
    private static int HEIGHT = 200;
    private static int LOCATION_X = 500;
    private static int LOCATION_Y = 300;
    private static boolean IS_VISIBLE = true;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });

    }

    private GUI() {
        JFrame frame = new JFrame("ADB monitor");
        frame.setBounds(LOCATION_X, LOCATION_Y, WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(IS_VISIBLE);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        ((DefaultCaret) (textArea.getCaret())).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane scroll = new JScrollPane(textArea);
        frame.add(scroll);
        frame.setVisible(true);

        final Monitor monitor = new Monitor(textArea);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                monitor.stop();
                super.windowClosing(e);
            }
        });
    }
}