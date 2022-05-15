package UC1_new;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.PropertyConfigurator;

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
        Socket client1;
        BufferedReader in1;
        TReadData trd;
        
        MReadData_Producer mReadData_Producer = new MReadData_Producer();
        try {
            client1 = new Socket("127.0.0.1", 3022);
            in1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
            trd = new TReadData(client1, in1, true, (ITReadData_TProducer) mReadData_Producer);
            trd.start();

            TProducer tp = new TProducer((ITReadData_TProducer) mReadData_Producer);
            tp.start();
        } catch (IOException ex) {
            Logger.getLogger(PProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
