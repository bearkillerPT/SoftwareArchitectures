package UC1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;

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
        String fields[];

        while ((data = prod.getData()) != null) {
            fields = data.split(";");
            String sensor_field[] = fields[0].split(":");
            sensors.add(sensor_field[1]);
            values.add(fields[1] + ";" + fields[2]);
        }
        Properties prop = new Properties();
        prop.setProperty("bootstrap.servers", "127.0.0.1:9092");
        prop.setProperty("key.serializer", StringSerializer.class.getName());
        prop.setProperty("value.serializer", StringSerializer.class.getName());

        TProducer tp = new TProducer(sensors, values, prop);
        tp.start();
        
    }
}