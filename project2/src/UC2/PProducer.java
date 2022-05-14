package UC2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
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
        Hashtable<Integer, List<String>> dict = new Hashtable<>();
        List<String> sensor_list;
        
        for (int i = 1; i < 7; i++) {
            sensor_list = new ArrayList<>();
            for (int j = 0; j < sensors.size(); j++) {
                if(i == Integer.valueOf(sensors.get(j))){
                    sensor_list.add(values.get(j));
                }
            }
            dict.put(i, sensor_list);
        }
        System.err.println("Records recieved from PSource: " + sensors.size());
        TProducer tp;
        for(int i = 1; i < 7; i++){
            tp = new TProducer(sensors, dict.get(i), i);
            tp.start();
        }
    }
}