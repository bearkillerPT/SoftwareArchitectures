package Project3;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PLoadBalancer {
    private ServerSocket serverSocket;
    private Socket client_socket;
    private PrintWriter out;
    private DataInputStream in;
    public PLoadBalancer() {
        try {
            this.serverSocket = new ServerSocket(3000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(true){
            
            Message msg = null;
            try {
                Socket client = this.serverSocket.accept();
                this.in = new DataInputStream(client.getInputStream());
                String msg_text = in.readUTF();
                msg = Message.parseMessage(msg_text);
                this.in.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(msg != null){
                balanceAndSend(msg);
            }
        }
    }

    private void balanceAndSend(Message msg) {
        
    }

    public static void main(String[] args) {
        PLoadBalancer lb = new PLoadBalancer();
        lb.run();
    }
}
