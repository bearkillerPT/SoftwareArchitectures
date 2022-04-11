package HCP.Comunication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HCPServer {
    private final int portNumber = 4444;
    private ServerSocket serverSocket;
    private PrintWriter out;
    private BufferedReader in;

    public HCPServer() {
        try {
            this.serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public String getCommand(){
        String res = "";
        try{
            res = in.readLine();
        }catch(Exception e){
            System.out.println(e);
        }
        return res;
    }
}