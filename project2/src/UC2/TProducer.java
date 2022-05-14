package UC2;

import UC1.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;


public class TProducer extends Thread{
    
    private List<String> values = new ArrayList<>();
    private List<String> sensor_id = new ArrayList<>();
    ProducerRecord<String, String> record;
    KafkaProducer<String, String> producer;
    Properties prop = new Properties();
    int num;
            
    public TProducer(List sensor_id, List values, int num) {
        this.prop = prop;
        this.sensor_id = sensor_id;
        this.values = values;
        this.num = num;
        prop.setProperty("bootstrap.servers", "127.0.0.1:9092");
        prop.setProperty("key.serializer", StringSerializer.class.getName());
        prop.setProperty("value.serializer", StringSerializer.class.getName());
        prop.setProperty("acks","1");//default value
    }
    
    @Override
    public void run(){
        producer = new KafkaProducer<>(prop);
        for(int i = 0; i < sensor_id.size();i++){
            record = new ProducerRecord<String, String>("Sensor", num, sensor_id.get(i), values.get(i));
            producer.send(record);
            System.out.println("Record sent! By Partition-"+record.partition() + ":Values - "+values.get(i));
        }
        producer.flush();
        producer.close();
    }
}
