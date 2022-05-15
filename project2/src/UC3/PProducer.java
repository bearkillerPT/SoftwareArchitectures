package UC3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

class PProducer {

    private Socket client;
    private BufferedReader in;

    public String getData() {
        String res = "";
        try {
            this.client = new Socket("127.0.0.1", 3022);
            this.in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            res = in.readLine();
            this.client.close();
            this.in.close();
        } catch (Exception e) {
            return null;
        }
        return res;
    }

    public static void main(String[] args) {
        PProducer prod = new PProducer();
        String data = "";
        List<String> sensors = new ArrayList<>();
        List<String> values = new ArrayList<>();
        int previous_record_time = -1;
        String fields[];
        String time_fields[];
        int present_record_time = 0;
        String sensorId[];

        while ((data = prod.getData()) != null) {
//            System.out.println(prod.getData());
            fields = data.split(";");
            time_fields = fields[2].split(":");
            sensorId = fields[0].split(":");

            if (previous_record_time > Integer.valueOf(time_fields[1])) {
                System.err.println("ORDER MIXED!!");
            }
            previous_record_time = present_record_time;
            sensors.add(sensorId[1]);
            values.add(fields[1] + ";" + fields[2]);
        }
        
        int sublist_size = sensors.size()/3;
        
        List <String> sublist_sensor1 = sensors.subList(0,sublist_size);
        List <String> sublist_values1 = values.subList(0, sublist_size);
        
        List <String> sublist_sensor2 = sensors.subList(sublist_size,sublist_size*2);
        List <String> sublist_values2 = values.subList(sublist_size,sublist_size*2);
        
        List <String> sublist_sensor3 = sensors.subList(sublist_size*2,sensors.size());
        List <String> sublist_values3 = values.subList(sublist_size*2,sensors.size());
        
        TProducer tp1;
        TProducer tp2;
        TProducer tp3;
        
        tp1 = new TProducer(sublist_sensor1, sublist_values1);
        tp2 = new TProducer(sublist_sensor2, sublist_values2);
        tp3 = new TProducer(sublist_sensor3, sublist_values3);
        
        tp1.start();
        tp2.start();
        tp3.start();
        
    }
}
