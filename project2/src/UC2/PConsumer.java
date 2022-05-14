package UC2;

import java.util.Properties;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.clients.consumer.Consumer;


public class PConsumer {
    public static void main(String[] args) {
        
        String topicName = "Sensor";
        String groupName = "sensor-group1";
        Properties prop = new Properties();
        prop.setProperty("bootstrap.servers", "127.0.0.1:9092");
        prop.setProperty("key.deserializer", StringDeserializer.class.getName());
        prop.setProperty("value.deserializer", StringDeserializer.class.getName());
        prop.setProperty("group.id", groupName);
        TConsumer tc;
        for(int i = 1; i < 7; i++){
            tc = new TConsumer(prop, i);
            tc.start();
        }
    }
}
