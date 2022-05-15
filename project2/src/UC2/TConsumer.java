package UC2;

import static java.lang.Integer.parseInt;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 *
 * @author junio
 */
public class TConsumer extends Thread{
    
    Properties prop;
    ConsumerRecord<String, String> record;
    KafkaConsumer<String, String> consumer;
    TopicPartition partition1;
    int num_partition;

    public TConsumer(Properties prop, int num_partition) {
        this.prop = prop;
        this.num_partition = num_partition;
    }
    @Override
    public void run() {
        partition1 = new TopicPartition("Sensor", num_partition);
        consumer = new KafkaConsumer<>(prop);
        consumer.assign(Arrays.asList(partition1));
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
                System.out.println("Number of records:" + record_counter);
//                System.out.println("Recieved new Record: \n" + "Key: " + record.key() + ", " + "Value: " + record.value()
//                +"Partition:"+num_partition+"->" + record.partition());
            }
        }
    }
}