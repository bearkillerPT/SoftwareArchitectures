package UC1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class PServer {

    private final int portNumber = 4445;
    private ServerSocket serverSocket;
    private Socket[] clients_queue = {null, null};
    private TServer[] servers;

    public PServer(int workers_count) {
        try {
            this.serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }
        
    }

    public boolean queueFull() {
        return this.clients_queue[0] != null && this.clients_queue[1] != null;
    }
    public void main(String[] args) {
        int server_threads = 3;
        PServer server = new PServer(server_threads);
        

    }
}
