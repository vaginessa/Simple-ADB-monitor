package com.gradesscompany;

import javax.swing.*;
import java.io.*;

class Monitor {
    private JTextArea logField;
    private Thread monitorThread;

    Monitor(JTextArea logField) {
        this.logField = logField;
        monitorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                start();
            }
        });
        monitorThread.start();
    }

    private void start() {
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "adb devices -l");
        builder.redirectErrorStream(true);
        try {
            while (true) {
                Process process = builder.start();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                if (logField.getLineCount() > 100) {
                    logField.setText("");
                }
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    logField.append(line + "\n");
                }
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            stop();
        }
    }

    void stop() {
        if (monitorThread != null) {
            monitorThread.interrupt();
        }
    }
}
