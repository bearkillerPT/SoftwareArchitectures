package Project3;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PCliente extends Thread {
    private ServerSocketChannel serverSocketChannel;
    private Socket client_socket;
    private DataOutputStream out;
    private int client_id;
    private int request_id = 0;
    private int number_of_iterations;

    public PCliente() {
        this.client_id = 1;
        this.number_of_iterations = 1;
        try {
            this.serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 3040 + this.client_id));
            serverSocketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest() {
        try {
            this.client_socket = new Socket("127.0.0.1", 3000);
            this.out = new DataOutputStream(client_socket.getOutputStream());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (this.client_socket != null) {
            System.out.println("send");
            long request_deadline = new Random().nextLong(200, 1000);
            String request_msg = client_id + "|";
            request_msg += request_id + "|";
            request_msg += "00" + "|";
            request_msg += "01" + "|";
            request_msg += this.number_of_iterations + "|";
            request_msg += "00" + "|";
            request_msg += request_deadline;
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
            if (socketChannel == null)
                return -1;
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            socketChannel.read(buffer);
            String data = new String(buffer.array()).trim();
            return Double.parseDouble(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void run() {
        while (true) {
            // this.getResult();
            this.sendRequest();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        PCliente client = new PCliente();
        client.run();
    }
}
