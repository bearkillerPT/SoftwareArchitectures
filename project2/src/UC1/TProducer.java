package UC1;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;


public class TProducer extends Thread{
    
    Properties prop;
    private List<String> values = new ArrayList<>();
    private List<String> sensor_id = new ArrayList<>();
    ProducerRecord<String, String> record;
            
    public TProducer(List sensor_id, List values) {
        this.prop = new Properties();
        this.sensor_id = sensor_id;
        this.values = values;
        prop.setProperty("bootstrap.servers", "127.0.0.1:9092");
        prop.setProperty("key.serializer", StringSerializer.class.getName());
        prop.setProperty("value.serializer", StringSerializer.class.getName());
    }
        KafkaProducer<String, String> producer = new KafkaProducer<>(prop);
    @Override
    public void run(){
        for(int i = 0; i < sensor_id.size();i++){
            record = new ProducerRecord<>("sensor", sensor_id.get(i), values.get(i));
            producer.send(record);
            producer.flush();
            producer.close();
        }
    }
}
