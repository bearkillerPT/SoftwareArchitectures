package HCP.Entities;

import HCP.Monitor.Fifo;
import HCP.Monitor.METH;

public class TCallCenter {
    private Fifo<TPatient> entrance_hall;
    private METH waiting_hall;
    private METH medical_hall;

    public TCallCenter(Fifo<TPatient>entrance_hall,METH waiting_hall, METH medical_hall){
        this.entrance_hall = entrance_hall;
        this.medical_hall = medical_hall;
    }

    public evaluateAndSendToWaitingHall() {
        TPatient patient = this.entrance_hall.get();
        //evaluate priority and set it
        this.waiting_hall.putEtr1(adult); //Devia ser patient.. (A class METH sabe se é um adulto ou criança e trata de tudo) 

    }
}
