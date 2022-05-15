package UC2;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author junio
 */
public class TReadData extends Thread {

    private Socket client;
    private BufferedReader in;
    Boolean read = true;
    String res;
    ITReadData_TProducer iproducer_tproducer;

    public TReadData(Socket client, BufferedReader in, Boolean read, ITReadData_TProducer iproducer_tproducer) {
        this.iproducer_tproducer = iproducer_tproducer;
        this.client = client;
        this.in = in;
        this.read = read;
    }

    @Override
    public void run() {
        while (true) {
            res = iproducer_tproducer.getData();
        }

    }

}
