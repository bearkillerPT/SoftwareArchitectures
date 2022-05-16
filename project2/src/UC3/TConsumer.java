/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UC3;

import static java.lang.Integer.parseInt;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

/**
 *
 * @author junio
 */
public class TConsumer extends Thread{
    
    Properties prop;
    ConsumerRecord<String, String> record;
    KafkaConsumer<String, String> consumer;
    TopicPartition partition1;
    int num;

    public TConsumer(Properties prop, int num) {
        this.prop = prop;
        this.num = num;
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
                System.out.println("Number of records:" + record_counter);
                System.out.println("Recieved new Record-" + "Key: " + record.key() + ", " + "Value: " + record.value()
                +"TConsumer:"+num);
            }
        }
    }
}

