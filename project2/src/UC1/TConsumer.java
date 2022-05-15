/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UC1;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class TConsumer extends Thread {

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
        int records_counter = 0;

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord record : records) {
                records_counter ++;
                fields = (record.value().toString()).split(";");
                gui.addRecordsBySensorId(fields[0], fields.toString());
                time_fields = fields[1].split(":");
                if(previous_record_time > Integer.valueOf(time_fields[1])){
                    System.err.println("ORDER MIXED!!");
                }
                previous_record_time = present_record_time;
                // System.out.println("Recieved new Record: " + "Key: " + record.key() + ", " + "Value: " + record.value());
            }
            System.out.println("Number of records:" + records_counter);
        }
    }
}