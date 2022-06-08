package Project3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class TCliente extends Thread{
    private Socket client_socket;
    private PrintWriter out;
    private BufferedReader in;
    private int client_id;
    private int request_id = 0;
    private int number_of_iterations;

    public TCliente(int client_id, int serverPort, int number_of_iterations){
        this.client_id = client_id;
        this.number_of_iterations = number_of_iterations;
        try {
            client_socket = new Socket("127.0.0.1", serverPort);
            this.out = new PrintWriter(client_socket.getOutputStream(), true);
            this.in = new BufferedReader(
                    new InputStreamReader(client_socket.getInputStream()));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void sendRequest() {
        if(this.client_socket != null) {
            long random_time_increment = new Random().nextLong(200,1000);
            long request_deadline=System.currentTimeMillis() + random_time_increment;
            String request_msg = client_id + "|";
            request_msg += request_id + "|";
            request_msg += "00" + "|";
            request_msg += "01" + "|";
            request_msg += this.number_of_iterations + "|";
            request_msg += "00" + "|";
            request_msg += request_deadline;
            this.out.println(request_msg);
            this.request_id++;
        }
    }
}
