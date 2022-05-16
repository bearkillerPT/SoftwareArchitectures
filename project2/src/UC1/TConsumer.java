package UC1;

import static java.lang.Integer.parseInt;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class TConsumer extends Thread{
    
    Properties prop;
    ConsumerRecord<String, String> record;
    KafkaConsumer<String, String> consumer;
    private final PGUI gui;

    public TConsumer(Properties prop) {
        this.prop = prop;
        this.gui = new PGUI("Consumer");
    }

    @Override
    public void run() {
        consumer = new KafkaConsumer<>(prop);
        consumer.subscribe(Arrays.asList("Sensor"));
        int previous_record_time = -1;
        String fields [];
        String time_fields[];
        int present_record_time = 0;
        int record_counter = 0;
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10));
            for (ConsumerRecord record : records) {
                record_counter ++;
                fields = (record.value().toString()).split(";");
                time_fields = fields[1].split(":");
                if(previous_record_time > parseInt(time_fields[1])){
                    System.err.println("ORDER MIXED!!");
                }
                previous_record_time = present_record_time;
                this.gui.addRecordsBySensorId(record.key().toString(), record.value().toString());
                System.out.println("Number of records:" + record_counter);
//                System.out.println("Recieved new Record: \n" + "Key: " + record.key() + ", " + "Value: " + record.value());
            }
        }
    }
}

