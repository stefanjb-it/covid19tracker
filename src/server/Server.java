package server;

import data.Shared;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PORT = 15500;
    private int port;
    private Shared shared;

    public static void main(String[] args) {
        new Server(PORT).start();
    }

    public Server(int port){
        this.port = port;
        this.shared = new Shared();
    }

    public void start(){
        try {
            ServerSocket server = new ServerSocket(port);
            while(true){
                System.out.println("Waiting ...");
                Socket socket = server.accept();
                System.out.printf("Connected %s \n",socket.getRemoteSocketAddress());
                new ServerThread(socket, shared).start();
            }
        } catch (IOException e) {
            System.err.printf("ERROR: %s",e.getMessage());
        }
    }
}
