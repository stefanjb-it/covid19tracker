package server;

import data.Shared;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread{

    private Socket socket;
    private Shared shared;
    private boolean active = true;

    public ServerThread(Socket socket, Shared shared){
        this.socket = socket;
        this.shared = shared;
    }

    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter (socket.getOutputStream());

            shared.add_out(out);

            work(in,out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void work(BufferedReader in, PrintWriter out) throws IOException {
        String line;
        while((line = in.readLine()) != null || active){
            System.out.println(line);
            String[] arguments = line.split(" ");
            switch (arguments[0]) {
                case "NEW":
                    out.println(shared.add_infected(arguments[1],arguments[2]));
                    out.flush();
                    break;
                case "RECOVER":
                    out.println(shared.recover_infected(arguments[1],arguments[2]));
                    out.flush();
                    break;
                case "LIST":
                    out.println(shared.list());
                    out.flush();
                    break;
                case "EXIT":
                    active = false;
                    socket.close();
                    break;
                default:
                    out.println("UNKNOWN COMMAND");
                    out.flush();
                    break;
            }
        }
    }
}
