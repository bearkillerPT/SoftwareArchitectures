package HCP.Comunication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int portNumber = 4445;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Server() {
        try {
            this.serverSocket = new ServerSocket(portNumber);
            this.clientSocket = serverSocket.accept();
            System.out.println("Accepted CCP Socket connection!");
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public String getCommand(){
        String res = "";
        try{
            res = in.readLine();
        }catch(Exception e){
            System.err.println(e);
        }
        return res;
    }
}