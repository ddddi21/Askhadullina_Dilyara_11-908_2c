package network;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Connection {
    ConnectionListener connectionListener;
    private Socket socket;
    private Thread thread;
    private BufferedReader in;
    private BufferedWriter out;

    public Connection(ConnectionListener connectionListener, String ip, Integer port) throws IOException{
        this(connectionListener, new Socket(ip,port));
    }

    public Connection(ConnectionListener connectionListener, Socket socket) throws IOException {
        this.connectionListener = connectionListener;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connectionListener.onConnectionReady(Connection.this);
                    while (!thread.isInterrupted()){
                        connectionListener.onReceiveString(Connection.this, in.readLine());
                    }
                } catch (IOException e) {
                    connectionListener.onException(Connection.this,e);
                }
                finally {
                    connectionListener.onDisconnect(Connection.this);
                }
            }
        });
        thread.start();
    }

    public synchronized void sendString(String string){
        try {
            out.write(string + "\r\n");
            out.flush();
        } catch (IOException e) {
            connectionListener.onException(Connection.this, e);
            disconnect();
        }
    }

    public synchronized void disconnect(){
        thread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            connectionListener.onException(Connection.this, e);
        }
    }

    @Override
    public String toString(){
        return "Connection" + socket.getInetAddress() + ": " + socket.getPort();
    }
}
