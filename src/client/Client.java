package client;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private int port;
    private String address;
    private BufferedReader in;
    private PrintWriter out;

    public Client (String host, int port){
        this.address = host;
        this.port = port;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Client sc = new Client("localhost",15500);
        sc.connect();
        System.out.println(sc.newInfections("4AHEL",5));
        Thread.sleep(1000);
        System.out.println(sc.listInfections());
        Thread.sleep(1000);
        System.out.println(sc.recoverInfections("4AHEL",4));
        Thread.sleep(1000);
        sc.exit();
    }

    public void connect(){
        try {
            socket = new Socket(address,port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            this.in = in;
            this.out = out;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String newInfections(String classname, int count) throws IOException {
        if(socket == null){
            connect();
        }
        out.println("NEW "+classname+" "+Integer.toString(count));
        out.flush();
        return in.readLine();
    }
    public String listInfections() throws IOException {
        if(socket == null){
            connect();
        }
        out.println("LIST");
        out.flush();
        return in.readLine();
    }
    public String recoverInfections(String classname, int count) throws IOException {
        if(socket == null){
            connect();
        }
        out.println("RECOVER "+classname+" "+Integer.toString(count));
        out.flush();
        return in.readLine();
    }
    public void exit() throws IOException {
        out.println("EXIT");
        out.flush();
        out.close();
        in.close();
        socket.close();
    }
}
