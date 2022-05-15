package UC1;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;


public class TProducer extends Thread{
    
    private final PGUI gui;
    Properties prop;
    private List<String> values = new ArrayList<>();
    private List<String> sensor_id = new ArrayList<>();
    ProducerRecord<String, String> record;
    KafkaProducer<String, String> producer;
            
    public TProducer(List sensor_id, List values, Properties prop) {
        this.gui = new PGUI("Producer");
        this.prop = prop;
        this.sensor_id = sensor_id;
        this.values = values;
    }
    
    @Override
    public void run(){
        producer = new KafkaProducer<>(prop);
        for(int i = 0; i < sensor_id.size();i++){
            this.gui.addRecordsBySensorId(sensor_id.get(i), values.get(i));
            record = new ProducerRecord<>("Sensor", sensor_id.get(i), values.get(i));
            producer.send(record);
            producer.flush();
            System.out.println("Record sent!");
        }
        producer.close();
    }
}
