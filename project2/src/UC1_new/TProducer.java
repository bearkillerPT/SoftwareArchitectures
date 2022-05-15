package UC1_new;

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
    ITReadData_TProducer iproducer_Tproducer;
            
    public TProducer(ITReadData_TProducer iproducer_Tproducer) {
        this.iproducer_Tproducer = iproducer_Tproducer;
    }
    
    @Override
    public void run(){
        while(true){
            iproducer_Tproducer.putData();
        }
    }
}
