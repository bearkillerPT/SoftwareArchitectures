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
        List<String> sensors = new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        
        while ((data = prod.getData()) != null) {
            data.split(";");
            String sensor_field[] = fields[0].split(":");
            sensors.add(sensor_field[1]);
            String temp[] = fields[1].split(":");
            String timestamp[] = fields[2].split(":");
            values.add(temp[1] + ";" + timestamp[1]);
        }

        TProducer tp = new TProducer(sensors, values);
        tp.start();
        
    }
}