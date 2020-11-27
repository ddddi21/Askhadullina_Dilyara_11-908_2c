package server;

import client.Client;
import network.Connection;
import network.ConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server implements ConnectionListener {
    public static void main(String[] args) {
        new Server();
    }
    private final ArrayList<Connection> connectionArrayList = new ArrayList<>();

    private Server(){
        System.out.println("Server is starting..");
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
        connectionArrayList.add(connection);
        sendToAll("Now connected:" + connection);
    }

    @Override
    public synchronized void onReceiveString(Connection connection, String value) {
        sendToAll(value);
    }

    @Override
    public synchronized void onDisconnect(Connection connection) {
        connectionArrayList.remove(connection);
        sendToAll("Now disconnected =( :" + connection);
    }

    @Override
    public synchronized void onException(Connection connection, Exception e) {
        System.out.println("Connection exception: " + e);
    }

    private void sendToAll(String string){
        System.out.println(string);
        int size= connectionArrayList.size();
        for (int i = 0; i < size ; i++) {
            connectionArrayList.get(i).sendString(string);
        }
    }
}
