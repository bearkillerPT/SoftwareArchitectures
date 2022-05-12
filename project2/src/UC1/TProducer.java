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
            
    public TProducer(List sensor_id, List values, Properties prop) {
        this.prop = prop;
        this.sensor_id = sensor_id;
        this.values = values;
        
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