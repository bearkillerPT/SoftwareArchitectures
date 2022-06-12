package Project3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PServer {

    private final int portNumber = 4445;
    private ServerSocket serverSocket;
    private Message[] msg_queue = { null, null };
    private TServer[] servers;
    public int workers_count;
    private DataOutputStream out;
    private DataInputStream in;

    public PServer(int workers_count) {
        try {
            this.serverSocket = new ServerSocket(portNumber);
            this.workers_count = workers_count;
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
                if (msg_queue[0].compareTo(msg_queue[1]) < 0)
                    {
                        msg_to_send = msg_queue[1];
                        msg_queue[1] = null;
                    }
                else
                    {
                        msg_to_send = msg_queue[0];
                        msg_queue[0] = null;
                    }
            }
            else if(msg_queue[0] != null) {
                msg_to_send = msg_queue[0];
                msg_queue[0] = null;
            }
            else if(msg_queue[1] != null) {
                msg_to_send = msg_queue[1];
                msg_queue[1] = null;
            }
            else return;
            TServer avail_server = getAvailableServer();
            if(avail_server == null) return;
            processClient(msg_to_send);
        }
    }

    /*
     * Return the space available on the server
     */
    private int totalFreeSlots() {
        int available_spots = 0;
        for (TServer server : this.servers)
            if (server != null)
                available_spots++;
        for (Message msg : msg_queue)
            if (msg != null)
                available_spots++;
        return available_spots;
    }

    public void run() {
        while (true) {
            Socket client = acceptClient();
            try {
                this.in = new DataInputStream(client.getInputStream());
                String msg_text = in.readUTF();
                if(msg_text.equals("getAvailability")){
                    this.out = new DataOutputStream(client.getOutputStream());
                    this.out.write(this.totalFreeSlots());
                }else{
                    this.moveQueue();
                    this.processClient(Message.parseMessage(msg_text));
                }
                    
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * Process client request.
     * Returns false if no space is available.
     */
    public boolean processClient(Message msg) {
        if (this.queueFull()) {
            return false;
        }
        TServer next_worker = this.getAvailableServer();
        Socket clientSocket = this.acceptClient();

        if (next_worker != null) {
            if (clientSocket != null) {
                next_worker = new TServer(msg);
                next_worker.start();
            }
        } else {
            if (this.queueEmpty()) {
                this.msg_queue[0] = msg;
            } else {
                this.msg_queue[1] = msg;
            }

        }
        return false;
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

    private TServer getAvailableServer() {
        for (int i = 0; i < this.workers_count; i++) {
            try {
                this.servers[i].join(10);
                this.servers[i] = null;
            } catch (InterruptedException e) {
            }
            if (this.servers[i] == null) {
                return this.servers[i];
            }
        }
        return null;
    }

    private boolean queueEmpty() {
        return this.msg_queue[0] == null && this.msg_queue[1] == null;
    }

    public boolean queueFull() {
        return this.msg_queue[0] != null && this.msg_queue[1] != null;
    }


    public static void main(String[] args) {
        int server_threads = 3;
        PServer server = new PServer(server_threads);
        server.run();

    }
}
