package UC1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PServer {

    private final int portNumber = 4445;
    private ServerSocket serverSocket;
    private Socket[] clients_queue = {null, null};
    private TServer[] servers;
    public int workers_count;

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

    /*
     * Process client request.
     * Returns false if no space is available.
     */
    public boolean processClient(int num_iter) {
        if (this.queueFull()) {
            return false;
        }
        TServer next_worker = this.getAvailableServer();
        Socket clientSocket = this.acceptClient();

        if (next_worker != null) {
            if (clientSocket != null) {
                next_worker = new TServer(clientSocket);
                next_worker.start();
            }
        } else {
            if (this.queueEmpty()) {
                this.clients_queue[0] = clientSocket;
            } else {
                this.clients_queue[1] = clientSocket;
            }

        }
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

    
    /*
    * Return the space available on the server
    */
    public int serverSpace() {
        if (this.queueFull()) {
            return 0;
        }
        int space = 0;
        if(this.clients_queue[0] == null) space++;
        if(this.clients_queue[1] == null) space++;
        for (int i = 0; i < this.workers_count; i++) {
            if (this.servers[i] == null) {
                space++;
            }
        }
        return space;
    }

    private TServer getAvailableServer() {
        for (int i = 0; i < this.workers_count; i++) {
            if (this.servers[i] == null) {
                return this.servers[i];
            }
        }
        return null;
    }

    private boolean queueEmpty() {
        return this.clients_queue[0] == null && this.clients_queue[1] == null;
    }

    public boolean queueFull() {
        return this.clients_queue[0] != null && this.clients_queue[1] != null;
    }

    public void main(String[] args) {
        int server_threads = 3;
        PServer server = new PServer(server_threads);

    }
}
