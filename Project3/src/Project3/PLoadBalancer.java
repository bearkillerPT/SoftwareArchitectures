package Project3;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class PLoadBalancer {
    private ServerSocket serverSocket;
    private Socket monitor_socket;
    private DataInputStream in;
    private int lb_id;
    private GUILb GLb;
    private Socket client_socket;
    private PrintWriter out;

    public PLoadBalancer(int lb_id) {
        this.GLb = new GUILb("Load Balancer " + lb_id);
        this.lb_id = lb_id;
        try {
            this.serverSocket = new ServerSocket(3000 + lb_id);
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
            return -1;
        return 3010 + max_spots_i;
    }

    private void balanceAndSend(Message msg) {
        int server_port = chooseServer();
        if(server_port != -1){
        try {
            Socket server_conn = new Socket("127.0.0.1", server_port);
            DataOutputStream server_out = new DataOutputStream(server_conn.getOutputStream());
            msg.server_id = server_port - 3010;
            server_out.writeUTF(msg.toString());
            server_out.writeUTF(msg.toString());
            server_out.close();
            server_conn.close();
            this.monitor_socket = new Socket("127.0.0.1", 3030);
            DataOutputStream monitor_out = new DataOutputStream(this.monitor_socket.getOutputStream());
            monitor_out.writeUTF("LB: Request handled C" + msg.client_id + " -> S" + (3010 - server_port));
            setPendingRequests("LB: Request handled C" + msg.client_id + " -> S" + (3010-server_port)+"\n");
            monitor_out.close();
            this.monitor_socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }else{
            try {
            this.monitor_socket = new Socket("127.0.0.1", 3030);
            DataOutputStream monitor_out = new DataOutputStream(this.monitor_socket.getOutputStream());
            monitor_out.writeUTF("LB: Request rejected C" + msg.client_id);
            setPendingRequests("LB: Request rejected C" + msg.client_id+"\n");
            this.client_socket = new Socket("127.0.0.1", 3020 + msg.client_id);
            this.out = new PrintWriter(client_socket.getOutputStream(), true);
            this.out.print("00"+":"+String.valueOf(msg.request_id));
            this.out.close();
            this.client_socket.close();
            monitor_out.close();
            this.monitor_socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        System.out.println(msg.toString());
    }
    
    synchronized void setPendingRequests(String request) {
        GLb.setPendingRequests(request);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String file_name = "Project3/info.txt";
        int line_counter = 0;
        String Content = "";
        int lb_id = 0;

        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = null;
        
        while ((line = br.readLine()) != null) {
            if (line_counter == 2) {
                lb_id = Integer.valueOf(line.split(":")[1]);
                Content = Content + (line.split(":")[0] + ":" + String.valueOf(lb_id + 1)) + System.lineSeparator();
            } else {
                Content = Content + line + System.lineSeparator();
            }
            line_counter++;
        }
        
        FileWriter writer = new FileWriter(file_name);
        writer.write(Content);
        br.close();
        writer.close();
        PLoadBalancer lb = new PLoadBalancer(lb_id);
        lb.run();
    }
}
