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
        String time_fields[];
        int present_record_time = 0;
        int previous_record_time = -1;

        while ((data = prod.getData()) != null) {
//            System.out.println(prod.getData());
            fields = data.split(";");
            time_fields = fields[2].split(":");
            
            if(previous_record_time > Integer.valueOf(time_fields[1])){
                    System.err.println("ORDER MIXED!!");
                }
            previous_record_time = present_record_time;
            sensors.add(fields[0]);
            values.add(fields[1] + ";" + fields[2]);
        }
        System.err.println("Records recieved from PSource: " + sensors.size());
        
        Properties prop = new Properties();
        prop.setProperty("bootstrap.servers", "127.0.0.1:9092");
        prop.setProperty("key.serializer", StringSerializer.class.getName());
        prop.setProperty("value.serializer", StringSerializer.class.getName());

        TProducer tp = new TProducer(sensors, values, prop);
        tp.start();
        
    }
}