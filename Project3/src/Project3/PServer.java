package Project3;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class PServer {

    private ServerSocket serverSocket;
    private Message[] msg_queue = { null, null };
    private TServer[] servers;
    private int id_server;
    public int workers_count;
    private DataOutputStream out;
    private DataInputStream in;
    private Socket monitor_socket;
    private final GUIServer GServer;
    Map<String, Object> map = new HashMap<String, Object>();

    public PServer(int workers_count, int id_server) {
        this.GServer = new GUIServer("Server "+id_server);
        try {
            this.serverSocket = new ServerSocket(3010 + id_server);
            this.workers_count = workers_count;
            this.id_server = id_server;
            this.servers = new TServer[workers_count];
            for (int i = 0; i < workers_count; i++) {
                this.servers[i] = null;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void moveQueue() {
        if (!queueEmpty()) {
            Message msg_to_send;
            if (queueFull()) {
                if (msg_queue[0].compareTo(msg_queue[1]) < 0) {
                    msg_to_send = msg_queue[1];
                    msg_queue[1] = null;
                } else {
                    msg_to_send = msg_queue[0];
                    msg_queue[0] = null;
                }
            } else if (msg_queue[0] != null) {
                msg_to_send = msg_queue[0];
                msg_queue[0] = null;
            } else if (msg_queue[1] != null) {
                msg_to_send = msg_queue[1];
                msg_queue[1] = null;
            } else
                return;
            int avail_server_i = getAvailableServer();
            if (avail_server_i == -1)
                return;
            processClient(msg_to_send, id_server);
        }
    }

    /*
     * Return the space available on the server
     */
    private int totalFreeSlots() {
        int available_spots = 0;
        for (TServer server : this.servers)
            if (server == null)
                available_spots++;
        for (Message msg : msg_queue)
            if (msg == null)
                available_spots++;
        return available_spots;
    }

    public void run() {
        while (true) {
            this.clearThreads();
            try {
                Socket client = acceptClient();
                System.out.println("Processing request!");
                this.in = new DataInputStream(client.getInputStream());
                String msg_text = in.readUTF();
                System.out.println(msg_text);
                if (msg_text.equals("getAvailability")) {
                    this.out = new DataOutputStream(client.getOutputStream());
                    this.out.writeUTF("" + this.totalFreeSlots());
                } else {
                    this.moveQueue();
                    this.processClient(Message.parseMessage(msg_text), id_server);
                    map.put(String.valueOf(Message.parseMessage(msg_text).request_id), String.valueOf(Message.parseMessage(msg_text).client_id) + ":" + String.valueOf(Message.parseMessage(msg_text).number_iteractions));
                    setPendingRequests(map);
                }
                this.in.close();
                this.out.close();
                client.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized void setPendingRequests(Map my_dict) {
        String pending_requests = "";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            pending_requests += "Id server: " + String.valueOf(id_server)+ " - Request Id: " + key + " - Id client: " + value.split(":")[0] + " - NI: " + value.split(":")[1] + "\n";
        }
        GServer.setPendingRequests(pending_requests);
    }
    
    /*
     * Process client request.
     * Returns false if no space is available.
     */
    public boolean processClient(Message msg, int id_server) {
        DataOutputStream monitor_out = null;
        try {
            this.monitor_socket = new Socket("127.0.0.1", 3030);
            monitor_out = new DataOutputStream(this.monitor_socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (this.queueFull()) {
            if (monitor_out != null) {
                try {
                    monitor_out.writeUTF("Server is Busy and no space in queue!");
                    monitor_out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
        int next_worker_i = this.getAvailableServer();

        if (next_worker_i != -1) {
            System.out.println("Server_" + id_server + " T_"+next_worker_i+" is working!");
            this.servers[next_worker_i] = new TServer(msg, GServer, map);
            this.servers[next_worker_i].start();
            if (monitor_out != null) {
                try {
                    monitor_out.writeUTF("Server - sending request to Sthread " + next_worker_i);
                    monitor_out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (monitor_out != null) {
                try {
                    monitor_out.writeUTF("Server - Queueing the request!");
                    monitor_out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (this.queueEmpty()) {
                this.msg_queue[0] = msg;
            } else {
                this.msg_queue[1] = msg;
            }
        }
        return true;
    }

    private Socket acceptClient() {
        Socket clientSocket = null;
        try {
            clientSocket = this.serverSocket.accept();
        } catch (IOException e) {
            System.out.println(e);
        }
        return clientSocket;
    }

    private void clearThreads() {
        for (int i = 0; i < this.workers_count; i++)
            if (this.servers[i] != null && !this.servers[i].isAlive()) {
                this.servers[i] = null;
            }
    }

    private int getAvailableServer() {
        for (int i = 0; i < this.workers_count; i++) {
            if (this.servers[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private boolean queueEmpty() {
        return this.msg_queue[0] == null && this.msg_queue[1] == null;
    }

    public boolean queueFull() {
        return this.msg_queue[0] != null && this.msg_queue[1] != null;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        int server_threads = 3;
        Integer id_server = 1;
        String file_name = "Project3/info.txt";
        int line_counter = 0;
        String Content = "";

        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = null;
        
        while ((line = br.readLine()) != null) {
            if (line_counter == 1) {
                id_server = Integer.valueOf(line.split(":")[1]);
                Content = Content + (line.split(":")[0] + ":" + String.valueOf(id_server + 1)) + System.lineSeparator();
            } else {
                Content = Content + line + System.lineSeparator();
            }
            line_counter++;
        }
        
        FileWriter writer = new FileWriter(file_name);
        writer.write(Content);
        br.close();
        writer.close();
        PServer server = new PServer(server_threads, id_server);
        server.run();

    }
}
