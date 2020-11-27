package client;

import network.Connection;
import network.ConnectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;


public class Client extends JFrame implements ActionListener, ConnectionListener {

    private final String ip = "91.245.42.57";
    private final int port = 8181;


    private Connection connection;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Client();
            }
        });
    }
    private final JTextArea textArea = new JTextArea();
    private final JTextField nameField = new JTextField();
    private final JTextField inputField = new JTextField();

    public Client(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600,400);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        textArea.setEnabled(false);
        textArea.setLineWrap(true);
        add(textArea, BorderLayout.CENTER);

        inputField.addActionListener(this);
        add(nameField, BorderLayout.NORTH);
        add(inputField, BorderLayout.SOUTH);
        setVisible(true);

        try {
            connection = new Connection(this, ip, port);
        } catch (IOException e) {
            printMessage("Connection exception: " + e);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String message = inputField.getText();
        if (message.equals("")) return;
        inputField.setText(null);
        connection.sendString(nameField.getText() + ": " + message);
    }

    @Override
    public void onConnectionReady(Connection connection) {
        printMessage("Connection ready..");
    }

    @Override
    public void onReceiveString(Connection connection, String value) {
        printMessage(value);
    }

    @Override
    public void onDisconnect(Connection connection) {
        printMessage("Connection close: ");

    }

    @Override
    public void onException(Connection connection, Exception e) {
        printMessage("Connection exception: " + e);
    }

    private synchronized void printMessage(String message){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textArea.append(message + "\n");
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }
        });
    }
}
