package UC1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class TServer extends Thread {

    private Socket client_socket;
    private PrintWriter out;

    public TServer(Socket client_socket) {
        this.client_socket = client_socket;

        try {
            this.out = new PrintWriter(client_socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        double result = this.calculatePI();

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
        return Math.PI;
    }
}
