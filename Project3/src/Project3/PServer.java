package Project3;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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

public class PServer {
    private ServerSocketChannel serverSocketChannel;
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
        this.GServer = new GUIServer("Server " + id_server);
        try {
            this.serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 3010 + id_server));
            serverSocketChannel.configureBlocking(false);
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
        long epochTime = System.currentTimeMillis() / 1000; // Seconds EPOCHE
        while (true) {
            long newEpochTime = System.currentTimeMillis() / 1000; // Seconds EPOCHE
            this.clearThreads();
            if (newEpochTime - epochTime > 30) {
                try {
                    this.monitor_socket = new Socket("127.0.0.1", 3030);
                    DataOutputStream monitor_out = new DataOutputStream(this.monitor_socket.getOutputStream());
                    monitor_out.writeUTF("Heartbeat : " + "Server_" + this.id_server);
                    monitor_out.close();
                    this.monitor_socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                epochTime = newEpochTime; // Secon
            }
            try {
                SocketChannel client = acceptClient();
                if (client == null)
                    continue;
                ByteBuffer buffer = ByteBuffer.allocate(1024);

                client.read(buffer);
                String data = new String(buffer.array()).trim();
                System.out.println(data);
                if (data.equals("getAvailability")) {
                    buffer.clear();
                    String res = "" + this.totalFreeSlots();
                    buffer.put(res.getBytes());
                    buffer.flip();
                    client.write(buffer);
                    client.close();
                } else {
                    this.moveQueue();
                    this.processClient(Message.parseMessage(data), id_server);
                    map.put(String.valueOf(Message.parseMessage(data).request_id),
                            String.valueOf(Message.parseMessage(data).client_id) + ":"
                                    + String.valueOf(Message.parseMessage(data).number_iteractions));
                    setPendingRequests(map);
                }

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
            pending_requests += "Id server: " + String.valueOf(id_server) + " - Request Id: " + key + " - Id client: "
                    + value.split(":")[0] + " - NI: " + value.split(":")[1] + "\n";
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
            System.out.println("Server_" + id_server + " T_" + next_worker_i + " is working!");
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

    private SocketChannel acceptClient() {
        SocketChannel socketChannel = null;
        try {
            socketChannel = serverSocketChannel.accept();
            if (socketChannel == null) {
                return null;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return socketChannel;
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
