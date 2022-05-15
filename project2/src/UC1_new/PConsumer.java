package UC1_new;

import java.util.Properties;
import org.apache.kafka.common.serialization.StringDeserializer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gil-t
 */



public class PConsumer {
    public static void main(String[] args) {
        
        String topicName = "Sensor";
        String groupName = "sensor-group1";
        Properties prop = new Properties();
        prop.setProperty("bootstrap.servers", "127.0.0.1:9092");
        prop.setProperty("key.deserializer", StringDeserializer.class.getName());
        prop.setProperty("value.deserializer", StringDeserializer.class.getName());
        prop.setProperty("group.id", groupName);
        TConsumer tc = new TConsumer(prop);
        tc.start();
    }
}
