package UC1_new;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class MReadData_Producer implements ITReadData_TProducer {

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
    private final List<String> values = new ArrayList<>();
    private final List<String> sensor_id = new ArrayList<>();
    private final ReentrantLock r1;
    private final Condition cond_resEmpty;
    private final Condition cond_resNotEmpty;

    public MReadData_Producer() {
        r1 = new ReentrantLock();
        cond_resEmpty = r1.newCondition();
        cond_resNotEmpty = r1.newCondition();
        prop.setProperty("bootstrap.servers", "127.0.0.1:9092");
        prop.setProperty("key.serializer", StringSerializer.class.getName());
        prop.setProperty("value.serializer", StringSerializer.class.getName());
        prop.setProperty("acks","1");//default value
        prop.setProperty("min.insync.replicas", "2");
    }

    @Override
    public String getData() {
        try {
            r1.lock();
            try {
                while (!sensor_id.isEmpty()) {
                    cond_resEmpty.await();
                }
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
                sensor_id.add(fields[0]);
                values.add(fields[1] + ";" + fields[2]);
                cond_resNotEmpty.signal();
            } catch (Exception e) {
            }
        } finally {
            r1.unlock();
        }
        return res;
    }

    @Override
    public void putData() {
        try {
            r1.lock();
            try {
                while (sensor_id.isEmpty()) {
                    cond_resNotEmpty.await();
                }
                producer = new KafkaProducer<>(prop);
                for (int i = 0; i < sensor_id.size(); i++) {
                    record = new ProducerRecord<>("Sensor", sensor_id.get(i), values.get(i));
                    producer.send(record);
                    System.out.println("Record sent! By Partition-" + record.partition() + ":Values - " + values.get(i));
                    sensor_id.remove(i);
                    values.remove(i);
                }
                producer.flush();
                producer.close();
                cond_resEmpty.signal();
            } catch (InterruptedException ex) {
            }
        } finally {
            r1.unlock();
        }
    }
}
