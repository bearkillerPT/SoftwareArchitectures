package UC1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;


public class TProducer extends Thread{
    
    private Socket client;
    private BufferedReader in;
    ProducerRecord<String, String> record;
    KafkaProducer<String, String> producer;
    Properties prop = new Properties();
    String res = "";
    int previous_record_time = -1;
    String fields[];
    String time_fields[];
    int present_record_time = 0;
    private final PGUI gui;

    public TProducer() {
        this.gui = new PGUI("Producer");
        prop.setProperty("bootstrap.servers", "127.0.0.1:9092");
        prop.setProperty("key.serializer", StringSerializer.class.getName());
        prop.setProperty("value.serializer", StringSerializer.class.getName());
        prop.setProperty("acks", "1");//default value
        prop.setProperty("min.insync.replicas", "2");
    }

    @Override
    public void run() {
        while(true){
        try {
            this.client = new Socket("127.0.0.1", 3022);
            this.in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            res = in.readLine();
            this.client.close();
            this.in.close();
            fields = res.split(";");
            time_fields = fields[2].split(":");

            if (previous_record_time > Integer.valueOf(time_fields[1])) {
                System.err.println("ORDER MIXED!!");
            }
            previous_record_time = present_record_time;
            producer = new KafkaProducer<>(prop);

            record = new ProducerRecord<>("Sensor", fields[0], fields[1] + ";" + fields[2]);
            this.gui.addRecordsBySensorId(fields[0], fields[1] + ";" + fields[2]);
            producer.send(record);
            System.out.println("Record sent! By Partition-" + record.partition() + ":Values - " + fields[1] + ";" + fields[2]);
            producer.flush();
            producer.close();
        } catch (Exception e) {
        }
    }
    }
}
