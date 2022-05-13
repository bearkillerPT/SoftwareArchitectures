package UC1;

import java.util.Properties;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.kafka.clients.consumer.Consumer;

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

        String log4jConfPath = "../kakfa_2.13-3.1.0/config/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        
        final Logger logger = LoggerFactory.getLogger(Consumer.class);

        String topicName = "sensor";
        String groupName = "sensorGroup";
        Properties prop = new Properties();
        prop.setProperty("bootstrap.servers", "127.0.0.1:9092");
        prop.setProperty("key.deserializer", StringDeserializer.class.getName());
        prop.setProperty("value.deserializer", StringDeserializer.class.getName());
        prop.setProperty("group.id", groupName);
        
        TConsumer tc = new TConsumer(prop, logger);
        tc.start();

    }
}
