package UC1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PServer {

    private final int portNumber = 4445;
    private ServerSocket serverSocket;
    private Socket[] clients_queue = { null, null };
    private TServer[] servers;

    public PServer(int workers_count) {
        try {
            this.serverSocket = new ServerSocket(portNumber);
            this.workers_count = workers_count;
            this.servers = new TServer[workers_count];
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /*
     * Process client request.
     * Returns true if the there is space available in the queue.
     */
    public boolean processClient() {
        if (this.queueFull())
            return false;

    }

    private boolean allServersBusy() {
        
    }

    private boolean queueEmpty() {
        return this.clients_queue[0] == null && this.clients_queue[1] == null;
    }

    private boolean queueFull() {
        return this.clients_queue[0] != null && this.clients_queue[1] != null;
    }

    public void main(String[] args) {
        int server_threads = 3;
        PServer server = new PServer(server_threads);

    }
}
