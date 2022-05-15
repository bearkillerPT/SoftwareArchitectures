package UC2;

import UC1.*;
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
    private final List<String> values1 = new ArrayList<>();
    private final List<String> sensor_id1 = new ArrayList<>();

    private final List<String> values2 = new ArrayList<>();
    private final List<String> sensor_id2 = new ArrayList<>();

    private final List<String> values3 = new ArrayList<>();
    private final List<String> sensor_id3 = new ArrayList<>();

    private final List<String> values4 = new ArrayList<>();
    private final List<String> sensor_id4 = new ArrayList<>();

    private final List<String> values5 = new ArrayList<>();
    private final List<String> sensor_id5 = new ArrayList<>();

    private final List<String> values6 = new ArrayList<>();
    private final List<String> sensor_id6 = new ArrayList<>();

    private final ReentrantLock r1;
    private final Condition cond_resEmpty1;
    private final Condition cond_resNotEmpty1;
    private final Condition cond_resEmpty2;
    private final Condition cond_resNotEmpty2;
    private final Condition cond_resEmpty3;
    private final Condition cond_resNotEmpty3;
    private final Condition cond_resEmpty4;
    private final Condition cond_resNotEmpty4;
    private final Condition cond_resEmpty5;
    private final Condition cond_resNotEmpty5;
    private final Condition cond_resEmpty6;
    private final Condition cond_resNotEmpty6;

    public MReadData_Producer() {
        r1 = new ReentrantLock();
        cond_resEmpty1 = r1.newCondition();
        cond_resNotEmpty1 = r1.newCondition();
        cond_resEmpty2 = r1.newCondition();
        cond_resNotEmpty2 = r1.newCondition();
        cond_resEmpty3 = r1.newCondition();
        cond_resNotEmpty3 = r1.newCondition();
        cond_resEmpty4 = r1.newCondition();
        cond_resNotEmpty4 = r1.newCondition();
        cond_resEmpty5 = r1.newCondition();
        cond_resNotEmpty5 = r1.newCondition();
        cond_resEmpty6 = r1.newCondition();
        cond_resNotEmpty6 = r1.newCondition();

        prop.setProperty("bootstrap.servers", "127.0.0.1:9092");
        prop.setProperty("key.serializer", StringSerializer.class.getName());
        prop.setProperty("value.serializer", StringSerializer.class.getName());
        prop.setProperty("acks", "1");//default value
        prop.setProperty("min.insync.replicas", "2");
    }

    @Override
    public String getData() {
        try {
            r1.lock();
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
                String[] sensor_val = fields[0].split(":");
                switch (Integer.valueOf(sensor_val[1])) {
                    case 1:
                        sensor_id1.add(fields[0]);
                        values1.add(fields[1] + ";" + fields[2]);
                        cond_resNotEmpty1.signal();
                    case 2:
                        sensor_id2.add(fields[0]);
                        values2.add(fields[1] + ";" + fields[2]);
                        cond_resNotEmpty2.signal();
                    case 3:
                        sensor_id3.add(fields[0]);
                        values3.add(fields[1] + ";" + fields[2]);
                        cond_resNotEmpty3.signal();
                    case 4:
                        sensor_id4.add(fields[0]);
                        values4.add(fields[1] + ";" + fields[2]);
                        cond_resNotEmpty4.signal();
                    case 5:
                        sensor_id5.add(fields[0]);
                        values5.add(fields[1] + ";" + fields[2]);
                        cond_resNotEmpty5.signal();
                    case 6:
                        sensor_id6.add(fields[0]);
                        values6.add(fields[1] + ";" + fields[2]);
                        cond_resNotEmpty6.signal();
                }
            } catch (Exception e) {
            }
        } finally {
            r1.unlock();
        }
        return res;
    }

    @Override
    public void putData(int thread_id) {

        try {
            r1.lock();
            try {
                switch (thread_id) {
                    case 1:
                        while (sensor_id1.isEmpty()) {
                            cond_resNotEmpty1.await();
                        }
                        producer = new KafkaProducer<>(prop);
                        for (int i = 0; i < sensor_id1.size(); i++) {
                            record = new ProducerRecord<>("Sensor", thread_id, sensor_id1.get(i), values1.get(i));
                            producer.send(record);
//                            System.out.println("Record sent! By Partition-" + record.partition() + ":Values - " + values1.get(i));
                            sensor_id1.remove(i);
                            values1.remove(i);
                        }
                        producer.flush();
                        producer.close();
                        cond_resEmpty1.signal();
                    case 2:
                        while (sensor_id2.isEmpty()) {
                            cond_resNotEmpty2.await();
                        }
                        producer = new KafkaProducer<>(prop);
                        for (int i = 0; i < sensor_id2.size(); i++) {
                            record = new ProducerRecord<>("Sensor", thread_id, sensor_id2.get(i), values2.get(i));
                            producer.send(record);
//                            System.out.println("Record sent! By Partition-" + record.partition() + ":Values - " + values2.get(i));
                            sensor_id2.remove(i);
                            values2.remove(i);
                        }
                        producer.flush();
                        producer.close();
                        cond_resEmpty2.signal();
                    case 3:
                        while (sensor_id3.isEmpty()) {
                            cond_resNotEmpty3.await();
                        }
                        producer = new KafkaProducer<>(prop);
                        for (int i = 0; i < sensor_id3.size(); i++) {
                            record = new ProducerRecord<>("Sensor", thread_id, sensor_id3.get(i), values3.get(i));
                            producer.send(record);
//                            System.out.println("Record sent! By Partition-" + record.partition() + ":Values - " + values3.get(i));
                            sensor_id3.remove(i);
                            values3.remove(i);
                        }
                        producer.flush();
                        producer.close();
                        cond_resEmpty3.signal();
                    case 4:
                        while (sensor_id4.isEmpty()) {
                            cond_resNotEmpty4.await();
                        }
                        producer = new KafkaProducer<>(prop);
                        for (int i = 0; i < sensor_id4.size(); i++) {
                            record = new ProducerRecord<>("Sensor", thread_id, sensor_id4.get(i), values4.get(i));
                            producer.send(record);
//                            System.out.println("Record sent! By Partition-" + record.partition() + ":Values - " + values4.get(i));
                            sensor_id4.remove(i);
                            values4.remove(i);
                        }
                        producer.flush();
                        producer.close();
                        cond_resEmpty4.signal();
                    case 5:
                        while (sensor_id5.isEmpty()) {
                            cond_resNotEmpty5.await();
                        }
                        producer = new KafkaProducer<>(prop);
                        for (int i = 0; i < sensor_id5.size(); i++) {
                            record = new ProducerRecord<>("Sensor", thread_id, sensor_id5.get(i), values5.get(i));
                            producer.send(record);
//                            System.out.println("Record sent! By Partition-" + record.partition() + ":Values - " + values5.get(i));
                            sensor_id5.remove(i);
                            values5.remove(i);
                        }
                        producer.flush();
                        producer.close();
                        cond_resEmpty5.signal();
                    case 6:
                        while (sensor_id6.isEmpty()) {
                            cond_resNotEmpty6.await();
                        }
                        producer = new KafkaProducer<>(prop);
                        for (int i = 0; i < sensor_id6.size(); i++) {
                            record = new ProducerRecord<>("Sensor", thread_id, sensor_id6.get(i), values6.get(i));
                            producer.send(record);
//                            System.out.println("Record sent! By Partition-" + record.partition() + ":Values - " + values6.get(i));
                            sensor_id6.remove(i);
                            values6.remove(i);
                        }
                        producer.flush();
                        producer.close();
                        cond_resEmpty6.signal();
                }
            } catch (InterruptedException ex) {
            }
        } finally {
            r1.unlock();
        }
    }
}
