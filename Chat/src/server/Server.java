package server;

import client.Client;
import client.Client2;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import network.Connection;
import network.ConnectionListener;
import room.Room;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server extends Application implements ConnectionListener{
    public TextArea txtAreaDisplay;
    private final ArrayList<Connection> connectionArrayList = new ArrayList<>();
    private Client2 client = new Client2();
    private Room currentRoom;
    private final List<Room> rooms = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        txtAreaDisplay = new TextArea();
        txtAreaDisplay.setEditable(false);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(txtAreaDisplay);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 450, 500);
        primaryStage.setTitle("Server: JavaFx Text Chat App");
        primaryStage.setScene(scene);
        primaryStage.show();

        Platform.runLater(()
                -> txtAreaDisplay.appendText("New server started at " + new Date() + '\n'));
        try(ServerSocket socket = new ServerSocket(8181)){
            while(true){
                try {
                    new Connection(this, socket.accept());
                } catch (IOException e){
                    System.out.println("Connection exception: " + e);
                }
            }
        } catch (IOException e){
            throw  new RuntimeException(e);
        }

    }

    @Override
    public synchronized void onConnectionReady(Connection connection) {
        String room = client.roomField.getText().trim();
        if(connection.roomIsCreat(Integer.parseInt(room))){
            currentRoom = connection.connectToRoom(Integer.parseInt(room));
        } else{
            currentRoom = connection.createRoom(Integer.parseInt(room));
        }
        currentRoom.list.add(connection);
        connectionArrayList.add(connection);
        sendToAll("Now connected:" + connection, currentRoom);
    }

    @Override
    public synchronized void onReceiveString(Connection connection, String value) {
        sendToAll(value, currentRoom);
        Platform.runLater(() -> {
            txtAreaDisplay.appendText(value + "\n");
        });
    }

    @Override
    public synchronized void onDisconnect(Connection connection) {
        connectionArrayList.remove(connection);
        sendToAll("Now disconnected =( :" + connection, currentRoom);
    }

    @Override
    public synchronized void onException(Connection connection, Exception e) {
        System.out.println("Connection exception: " + e);
    }

    public void sendToAll(String string, Room room){
        for (Connection connection : room.list) {
            connection.sendString(string);
        }
    }

}
