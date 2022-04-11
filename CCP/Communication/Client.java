package CCP.Communication;

import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket client;
    private PrintWriter out;

    public Client() {
        try {
            this.client = new Socket("127.0.0.1", 4445);
            out = new PrintWriter(client.getOutputStream(), true);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void sendCommand(String cmd) {
        out.println(cmd);
    }
}
