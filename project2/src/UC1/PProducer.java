package UC1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

class PProducer {
    private Socket client;
    private BufferedReader in;

    public String getData() {
        String res = "";
        try {
            this.client = new Socket("127.0.0.1", 3001);
            this.in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            res = in.readLine();
            this.client.close();
            this.in.close();
        } catch (Exception e) {
            return null;
        }
        return res;
    }

    public static void main(String[] args) {
        PProducer prod = new PProducer();
        String data = "";
        while ((data = prod.getData()) != null) {
            System.out.println(data);
        }
    }
}