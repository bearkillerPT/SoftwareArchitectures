package UC1;

import java.io.File;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class PSource {
    private final int sourcePort = 3022;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;

    public PSource() {
        try {
            this.serverSocket = new ServerSocket(sourcePort);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    public void send(String message) { //Accepts a connection and sends data
        try {
            this.clientSocket = this.serverSocket.accept();
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.println("Accepted PProducer connection!");
            out.println(message);
            this.clientSocket.close();
            this.out.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        PSource sourceServer = new PSource();
        try{
            File fp = new File("sensor.txt");
//            for(String fileNames : fp.list()) System.out.println(fileNames);
            Scanner sc = new Scanner(fp);
            while(sc.hasNextLine()){
                String sensor_id = sc.nextLine();
                double temp = Double.parseDouble(sc.nextLine());
                int timestamp = Integer.parseInt(sc.nextLine());
                sourceServer.send("sensor_id:" + sensor_id + ";temperature:" + temp + ";timestamp:" + timestamp);
            }
            sc.close();
        }catch(Exception e) {
            System.out.println(e);
        }
        
    }
}