package com.jep.p2p.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.System.out;

public class ClientApp extends JFrame implements ActionListener {

    String userName;
    PrintWriter writer;
    BufferedReader reader;
    JTextArea taMessages;
    JTextField tfInput;
    JButton btnSend, btnExit;
    Socket client;

    public ClientApp(String uName, String serverName) throws IOException {
        super(uName);
        this.userName = uName;
        this.client = new Socket(serverName, 9099);
        this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        this.writer = new PrintWriter(client.getOutputStream(), true);
        writer.println(this.userName);
        buildInterface();
        new MessagesThread().start();
    }

    public void buildInterface() {
        btnSend = new JButton("Send");
        btnExit = new JButton("Exit");
        taMessages = new JTextArea();
        taMessages.setRows(10);
        taMessages.setColumns(50);
        taMessages.setEditable(false);
        tfInput = new JTextField(50);
        JScrollPane sp = new JScrollPane(taMessages, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(sp, "Center");
        JPanel bp = new JPanel(new FlowLayout());
        bp.add(tfInput);
        bp.add(btnSend);
        bp.add(btnExit);
        add(bp, "South");
        btnSend.addActionListener(this);
        btnExit.addActionListener(this);
        setSize(550, 300);
        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == btnExit) {
            writer.println("end");
            System.exit(0);
        } else {
            writer.println(tfInput.getText());
        }
    }

    public static void main(String[] args) {
        String name = JOptionPane.showInputDialog(null, "Enter your name : ", "Username", JOptionPane.PLAIN_MESSAGE);
        String serverName = "localhost";
        try {
            new ClientApp(name, serverName);
        } catch (Exception exc) {
            out.println("Error : " + exc.getMessage());
        }
    }

    class MessagesThread extends Thread {
        @Override
        public void run() {
            String line;
            try {
                while (true) {
                    line = reader.readLine();
                    taMessages.append(line + "\n");
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
