package Project3;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PCliente extends Thread {

    private ServerSocketChannel serverSocketChannel;
    private Socket client_socket;
    private DataOutputStream out;
    private int client_id;
    private int request_id = 0;
    private final GUIClient GClient;
    Map<String, Object> map = new HashMap<String, Object>();

    public PCliente(int client_id) {
        this.client_id = client_id;
        this.GClient = new GUIClient("Client");
        try {
            this.serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 3020 + this.client_id));
            serverSocketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(String DeadLine, String n_iterations) {
        try {
            this.client_socket = new Socket("127.0.0.1", 3000);
            this.out = new DataOutputStream(client_socket.getOutputStream());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (this.client_socket != null) {
            System.out.println("send");
            String request_msg = this.client_id + "|";
            request_msg += request_id + "|";
            request_msg += "00" + "|";
            request_msg += n_iterations + "|";
            request_msg += "01" + "|";
            request_msg += "00" + "|";
            request_msg += DeadLine;
            try {
                this.out.writeUTF(request_msg);
                this.out.close();
                this.client_socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.request_id++;
        }
    }

    public double getResult() {
        SocketChannel socketChannel = null;
        try {
            socketChannel = serverSocketChannel.accept();
            if (socketChannel == null) {
                return -1;
            }
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            socketChannel.read(buffer);
            String data = new String(buffer.array()).trim();
            setExecutedRequests("Client Id: " + String.valueOf(client_id) + " - Request Id:" + String.valueOf(data.split(":")[1]) + " - Result: " + String.valueOf(data.split(":")[0]) + "\n");
            map.remove(String.valueOf(data.split(":")[1]));
            setPendingRequests(map);
            return Double.parseDouble(data.split(":")[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void run() {
        while (true) {
            String text = null;
            String n_iterations;
            String deadline;
            while ((text = this.getVal()) != null) {
                n_iterations = text.split(":")[0];
                deadline = text.split(":")[1];
                map.put(String.valueOf(request_id), deadline + ":" + n_iterations);
                setPendingRequests(map);
                this.sendRequest(deadline, n_iterations);
                this.getResult();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            this.getResult();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    synchronized String getVal() {
        return this.GClient.getData();
    }

    synchronized void setPendingRequests(Map my_dict) {
        String pending_requests = "";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            pending_requests += "Id client: " + String.valueOf(client_id)+ " - Request Id: " + key + " - Id deadline: " + value.split(":")[0] + " - NI: " + value.split(":")[1] + "\n";
        }
        GClient.setPendingRequests(pending_requests);
    }

    synchronized void setExecutedRequests(String request) {
        GClient.setExecutedRequests(request);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Integer clientId = 1;
        String file_name = "Project3/src/Project3/info.txt";
        int line_counter = 0;
        String Content = "";

        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = null;
        
        while ((line = br.readLine()) != null) {
            if (line_counter == 0) {
                clientId = Integer.valueOf(line.split(":")[1]);
                Content = Content + (line.split(":")[0] + ":" + String.valueOf(clientId + 1)) + System.lineSeparator();
            } else {
                Content = Content + line + System.lineSeparator();
            }
            line_counter++;
        }
        
        FileWriter writer = new FileWriter(file_name);
        writer.write(Content);
        br.close();
        writer.close();
        PCliente client = new PCliente(clientId);
        client.run();
    }
}
