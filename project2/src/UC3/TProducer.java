package UC3;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;


public class TProducer extends Thread{
    
    private List<String> values = new ArrayList<>();
    private List<String> sensor_id = new ArrayList<>();
    ProducerRecord<String, String> record;
    KafkaProducer<String, String> producer;
    Properties prop = new Properties();
            
    public TProducer(List sensor_id, List values) {
        this.sensor_id = sensor_id;
        this.values = values;
        prop.setProperty("bootstrap.servers", "127.0.0.1:9092");
        prop.setProperty("key.serializer", StringSerializer.class.getName());
        prop.setProperty("value.serializer", StringSerializer.class.getName());
    }
    
    @Override
    public void run(){
        producer = new KafkaProducer<>(prop);
        for(int i = 0; i < sensor_id.size();i++){
            record = new ProducerRecord<>("Sensor", sensor_id.get(i), values.get(i));
            producer.send(record);
            // System.out.println("Record sent! By Partition-"+record.partition() + ":Values - "+values.get(i));
        }
        producer.flush();
        producer.close();
    }
}
