package Project3;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TServer extends Thread {
    private Message client_message;
    private Socket client_socket;
    private PrintWriter out;
    private GUIServer GServer;
    Map<String, Object> map = new HashMap<String, Object>();
    public TServer(Message msg, GUIServer GServer, Map map) {
        this.client_message = msg;
        this.GServer = GServer;
        this.map = map;
    }


    @Override
    public void run() {
        for(int i = 0; i < this.client_message.number_iteractions; i++)
            this.calculatePI();
        double result = Math.floor(Math.PI * Math.pow(10, this.client_message.number_iteractions))/ Math.pow(10, this.client_message.number_iteractions);
        try {
            this.client_socket = new Socket("127.0.0.1", 3020 + this.client_message.client_id);
            this.out = new PrintWriter(client_socket.getOutputStream(), true);
            this.out.print(result+":"+String.valueOf(client_message.request_id));
            this.out.close();
            this.client_socket.close();
            setExecutedRequests("Id server: " + String.valueOf(client_message.server_id)+ " - Request Id: " + String.valueOf(client_message.request_id) + " - Id client: " + String.valueOf(client_message.client_id) + " - NI: " + String.valueOf(client_message.number_iteractions) + "\n");
            map.remove(String.valueOf(client_message.request_id));
            setPendingRequests(map, String.valueOf(client_message.server_id));
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("Server done working!");

    }
    synchronized void setPendingRequests(Map my_dict, String serverId) {
        String pending_requests = "";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            pending_requests += "Id server: " + serverId + " - Request Id: " + key + " - Id client: " + value.split(":")[0] + " - NI: " + value.split(":")[1] + "\n";
        }
        GServer.setPendingRequests(pending_requests);
    }
    synchronized void setExecutedRequests(String request) {
        GServer.setExecutedRequests(request);
    }
    private double calculatePI() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Math.PI;
    }
}
