package Project3;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class TServer extends Thread {
    private Message client_message;
    private Socket client_socket;
    private PrintWriter out;
    public TServer(Message msg) {
        this.client_message = msg;
        try {
            this.client_socket = new Socket("127.0.0.1", 3030 + msg.client_id);
            this.out = new PrintWriter(client_socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    @Override
    public void run() {
        for(int i = 0; i < this.client_message.number_iteractions; i++)
            this.calculatePI();
        double result = Math.round(Math.PI * Math.pow(10, this.client_message.number_iteractions))/ Math.pow(10, this.client_message.number_iteractions);
        try {
            this.out.print(result);
            this.out.flush();
            this.out.close();
            this.client_socket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private double calculatePI() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Math.PI;
    }
}
