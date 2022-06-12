package Project3;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class PCliente extends Thread{
    private Socket client_socket;
    private DataOutputStream out;
    private int client_id;
    private int request_id = 0;
    private int number_of_iterations;

    public PCliente(){
        this.client_id = 1;
        this.number_of_iterations = 1;
        try {
            client_socket = new Socket("127.0.0.1", 3030 + client_id);
            this.out = new DataOutputStream(client_socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void sendRequest() {
        if(this.client_socket != null) {
            
            long request_deadline=new Random().nextLong(200,1000);
            String request_msg = client_id + "|";
            request_msg += request_id + "|";
            request_msg += "00" + "|";
            request_msg += "01" + "|";
            request_msg += this.number_of_iterations + "|";
            request_msg += "00" + "|";
            request_msg += request_deadline;
            try {
                this.out.writeUTF(request_msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.request_id++;
        }
        while(true);
    }
    public static void main(String[] args) {
        PCliente client = new PCliente();

        client.sendRequest();
        
    }
}
