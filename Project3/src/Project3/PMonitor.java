package Project3;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class PMonitor {

    private ServerSocketChannel serverSocketChannel;
    private ArrayList<SocketChannel> components;
    private GUIMonitor GMonitor;

    public PMonitor() {
        this.GMonitor = new GUIMonitor("Monitor");
        this.components = new ArrayList<SocketChannel>();
        try {
            this.serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 3030));
            serverSocketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptComponent() {
        SocketChannel client = null;
        try {
            client = this.serverSocketChannel.accept();
            if (client != null) {
                this.components.add(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {

            this.acceptComponent();
            for (SocketChannel client : this.components) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                try {
                    client.read(buffer);
                    String data = new String(buffer.array()).trim();
                    if ((!data.equals(""))) {
                        if("HB_LB".equals(data.split(":")[0])){
                           setLbStatus(data.split(":")[1]);
                        }else if("HB_S".equals(data.split(":")[0])){
                            setServerStatus(data.split(":")[1]);
                        }else if("LB".equals(data.split(":")[0])){
                            setLBRequests(data.split(":")[1]);
                        }else{
                            setServerRequests(data.split(":")[1]);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    synchronized void setLBRequests(String request) {
        GMonitor.setLBRequests(request);
    }
    
    synchronized void setServerRequests(String request) {
        GMonitor.setServerRequests(request);
    }

    synchronized void setServerStatus(String request) {
        GMonitor.setServerStatus(request);
    }

    synchronized void setLbStatus(String request) {
        GMonitor.setLbStatus(request);
    }
    public static void main(String[] args) throws IOException {
        String file_name = "Project3/info.txt";
        String Content = "";
        int set_ids_to_zero = 0;
        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String line = null;
        while ((line = br.readLine()) != null) {
            Content = Content + (line.split(":")[0] + ":" + String.valueOf(set_ids_to_zero)) + System.lineSeparator();
        }
        FileWriter writer = new FileWriter(file_name);
        writer.write(Content);
        br.close();
        writer.close();
        PMonitor monitor = new PMonitor();
        monitor.run();
    }
}
