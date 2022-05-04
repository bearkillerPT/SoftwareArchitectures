package HCP.Monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import HCP.Entities.ICallCentre_Wth;
import HCP.Entities.INurse_Wth;
import HCP.Entities.TAdult;

public class MWTH implements INurse_Wth, ICallCentre_Wth{
    
    private final List<TAdult> WTR1;
    private final ReentrantLock rl;
    private final Condition cNotFull;
    private final Condition cNotEmpty;
    private final int size;
    private int count = 0;
    private int WTN = 0;
    
    private TAdult adultRemoved;
    
    public MWTH(int size) {
        this.size = size;
        WTR1 = new ArrayList<>();
        rl = new ReentrantLock();
        cNotEmpty = rl.newCondition();
        cNotFull = rl.newCondition();
    }
    
    @Override
    public void putWtr2(TAdult adult) {
        try {
            rl.lock();
            while (isFull()){
                cNotFull.await();
            }
            WTN++;
            adult.setPatientId(WTN);
            WTR1.add(adult);
            System.out.println("\nAdult: "+ adult.getIdAdult()+" is in the Waiting Hall");
            count++;
            cNotEmpty.signal();
        } catch ( InterruptedException ex ) {}
        finally {
            rl.unlock();
        }
    }
    
    @Override
    public TAdult getPatient() {
        try{
            rl.lock();
            try {
                while ( isEmpty() ){
                    cNotEmpty.await();   
                }
            } catch( InterruptedException ex ) {}
            int dos = 4;
            int id_adult=9999;
            
            for(int i = 0; i<WTR1.size();i++){
                if(WTR1.get(i).getDos()<=dos && WTR1.get(i).getIdAdult() < id_adult){
                    dos=WTR1.get(i).getDos();
                    id_adult =i;
                }
            }
            adultRemoved = WTR1.get(id_adult);
            WTR1.remove(id_adult);
            count--;
            System.out.println("\nAdult: "+ adultRemoved.getIdAdult()+";Dos:"+adultRemoved.getDos()+" left the Waiting Hall");
            cNotFull.signal();
        }
        finally {
            rl.unlock();
        }
        return adultRemoved;
    }
    
    private boolean isFull() {
        return count == size;
    }
    
    private boolean isEmpty(){
        return WTR1.isEmpty();
    }
}
