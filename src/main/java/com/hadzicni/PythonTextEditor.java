package com.hadzicni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PythonTextEditor extends JFrame {
    private JTextArea textArea;
    private JButton runButton;
    private JTextArea outputArea;

    public PythonTextEditor() {
        setTitle("Python Text Editor");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea = new JTextArea();
        JScrollPane inputScrollPane = new JScrollPane(textArea);
        getContentPane().add(inputScrollPane, BorderLayout.CENTER);

        runButton = new JButton("Run Python Code");
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executePythonCode();
            }
        });
        getContentPane().add(runButton, BorderLayout.SOUTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        getContentPane().add(outputScrollPane, BorderLayout.EAST);
    }

    private void executePythonCode() {
        try {
            String pythonCode = textArea.getText();
            Process process = Runtime.getRuntime().exec("python");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            writer.write(pythonCode);
            writer.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            reader.close();
            outputArea.setText(output.toString());

            int exitCode = process.waitFor();
            System.out.println("Python-Programm beendet mit Exit-Code: " + exitCode);
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PythonTextEditor editor = new PythonTextEditor();
                editor.setVisible(true);
            }
        });
    }
}
