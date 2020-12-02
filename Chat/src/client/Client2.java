package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import network.Connection;
import network.ConnectionListener;
import room.Room;
import server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Client2 extends Application implements ConnectionListener {
   public javafx.scene.control.TextField txtName;
   public javafx.scene.control.TextField txtInput;
    javafx.scene.control.ScrollPane scrollPane;
   public javafx.scene.control.TextField roomField;
    public TextArea txtAreaDisplay;
    private Room currentRoom;

    private final int port = 8181;


    private Connection connection;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onConnectionReady(Connection connection) {
        printMessage("Connection ready..", currentRoom);
    }

    @Override
    public void onReceiveString(Connection connection, String value) {
        printMessage(value,currentRoom);
    }

    @Override
    public void onDisconnect(Connection connection) {
        printMessage("Connection close: ",currentRoom);

    }

    @Override
    public void onException(Connection connection, Exception e) {
        printMessage("Connection exception: " + e, currentRoom);
    }

    private synchronized void printMessage(String message, Room room){
        Platform.runLater(() -> {
            txtAreaDisplay.appendText(message + "\n");
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox();

        scrollPane = new javafx.scene.control.ScrollPane();
        HBox hBox = new HBox();

        txtAreaDisplay = new TextArea();
        txtAreaDisplay.setEditable(false);
        scrollPane.setContent(txtAreaDisplay);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        txtName = new javafx.scene.control.TextField();
        txtName.setPromptText("Name");
        txtName.setTooltip(new Tooltip("Write your name. "));
        txtInput = new javafx.scene.control.TextField();
        txtInput.setPromptText("New message");
        txtInput.setTooltip(new Tooltip("Write your message. "));
        roomField = new javafx.scene.control.TextField();
        roomField.setPromptText("Room");
        roomField.setTooltip(new Tooltip("Choose room. "));
        javafx.scene.control.Button btnSend = new Button("Send");
        btnSend.setOnAction(new ButtonListener());

        hBox.getChildren().addAll(txtName, txtInput, roomField, btnSend);
        HBox.setHgrow(txtInput, Priority.ALWAYS);

        vBox.getChildren().addAll(scrollPane, hBox);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        Scene scene = new Scene(vBox, 450, 500);
        primaryStage.setTitle("Client: JavaFx Text Chat App");
        primaryStage.setScene(scene);
        primaryStage.show();

        try {
            connection = new Connection(this, "localhost", port);
            txtAreaDisplay.appendText("Connected. \n");
        } catch (IOException e) {
            printMessage("Connection exception: " + e, currentRoom);
        }
    }
    private class ButtonListener implements EventHandler<javafx.event.ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent e) {
            String username = txtName.getText().trim();
            String message = txtInput.getText().trim();
            String room = roomField.getText().trim();

            if (username.length() == 0) {
                username = "Unknown";
            }
            if (message.length() == 0) {
                return;
            }
            if(room.length() == 0){
                return;
            }

            connection.sendString("[" + username + "]: " + message + "");

            txtInput.clear();

        }
    }
}
