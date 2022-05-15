package UC2;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;


public class TProducer extends Thread{
    
    ProducerRecord<String, String> record;
    KafkaProducer<String, String> producer;
    ITReadData_TProducer iproducer_Tproducer;
    int num;
            
    public TProducer(ITReadData_TProducer iproducer_Tproducer, int num) {
        this.iproducer_Tproducer = iproducer_Tproducer;
        this.num = num;
    }
    
    @Override
    public void run(){
        while(true){
            iproducer_Tproducer.putData(num);
        }
    }
}
