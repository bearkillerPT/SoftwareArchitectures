package Project3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class PLoadBalancer {
    private ServerSocket serverSocket;
    private Socket monitor_socket;
    private DataInputStream in;

    public PLoadBalancer() {
        try {
            this.serverSocket = new ServerSocket(3000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {

            Message msg = null;
            try {
                Socket client = this.serverSocket.accept();
                this.in = new DataInputStream(client.getInputStream());
                String msg_text = in.readUTF();
                msg = Message.parseMessage(msg_text);
                System.out.println(msg.toString());
                this.in.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (msg != null) {
                    balanceAndSend(msg);
                }
            }
        }
    }

    // Returns the port of the choosen server
    private int chooseServer() {
        int[] available_spots = new int[10];
        for (int i = 0; i < 10; i++)
            available_spots[i] = -1;
        for (int port = 3010; port < 3020; port++) {
            try {
                Socket server_con = new Socket("127.0.0.1", port);
                DataOutputStream server_out = new DataOutputStream(server_con.getOutputStream());
                DataInputStream server_in = new DataInputStream(server_con.getInputStream());
                server_out.writeUTF("getAvailability");
                available_spots[port - 3010] = Integer.parseInt(server_in.readUTF());
                server_in.close();
                server_out.close();
                server_con.close();

            } catch (Exception e) {
                // Printing the exception here makes no sense as this is a test to all possible
                // 10 servers
                // e.printStackTrace();
            }
        }
        int max_spots = -1;
        int max_spots_i = -1;
        for (int i = 0; i < 10; i++) {
            System.out.println("server_" + i + " -> " + available_spots[i]);
            if (available_spots[i] > max_spots) {
                max_spots = available_spots[i];
                max_spots_i = i;
            }
        }

        if (max_spots_i == -1)
            return 3010;
        return 3010 + max_spots_i;
    }

    private void balanceAndSend(Message msg) {
        int server_port = chooseServer();
        try {
            Socket server_conn = new Socket("127.0.0.1", server_port);
            DataOutputStream server_out = new DataOutputStream(server_conn.getOutputStream());
            server_out.writeUTF(msg.toString());
            server_out.close();
            server_conn.close();
            this.monitor_socket = new Socket("127.0.0.1", 3030);
            DataOutputStream monitor_out = new DataOutputStream(this.monitor_socket.getOutputStream());
            monitor_out.writeUTF("LB: Request handled C" + msg.client_id + " -> S" + (3010 - server_port));
            monitor_out.close();
            this.monitor_socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(msg.toString());
    }

    public static void main(String[] args) {
        PLoadBalancer lb = new PLoadBalancer();
        lb.run();
    }
}
