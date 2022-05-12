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

/**
 *
 * @author junio
 */
public class TConsumer extends Thread{
    
    Properties prop;
    ConsumerRecord<String, String> record;
    KafkaConsumer<String, String> consumer;

    public TConsumer(Properties prop) {
        this.prop = prop;
    }
    
    @Override
    public void run(){
        consumer = new KafkaConsumer<>(prop);
        consumer.subscribe(Arrays.asList("sensor"));
        while(true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for(ConsumerRecord record: records){
                System.out.println("Record id : " + String.valueOf(record.key()));
                System.out.println("Record values : " + String.valueOf(record.value()));
            }
        }
    }
}