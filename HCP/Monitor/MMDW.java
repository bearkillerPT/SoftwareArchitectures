package HCP.Monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import HCP.Entities.IDoctor_Mdw;
import HCP.Entities.ICallCentre_Mdw;
import HCP.Entities.TAdult;

public class MMDW implements IDoctor_Mdw, ICallCentre_Mdw{
    
    private int count = 0;
    private final List<TAdult> MDW;
    private final ReentrantLock rl;
    private final Condition cNotFull;
    private final Condition cNotEmpty;
    
    private TAdult adultRemoved;
    
    public MMDW() {
        MDW = new ArrayList<>();
        rl = new ReentrantLock();
        cNotEmpty = rl.newCondition();
        cNotFull = rl.newCondition();
    }
    
    @Override
    public void putMdw2(TAdult adult) {
        try {
            rl.lock();
            while (isFull()){
                cNotFull.await();
            }
            MDW.add(adult);
            count++;
            System.out.println("\nAdult: "+ adult.getIdAdult()+" is in the Medical Waiting Hall");
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
                while ( MDW.isEmpty() ){
                    cNotEmpty.await();   
                }
            } catch( InterruptedException ex ) {}
            adultRemoved = MDW.get(0);
            MDW.remove(0);
            count--;
            System.out.println("\nAdult: "+ adultRemoved.getIdAdult()+" left the Medical Waiting Hall");
            cNotFull.signal();
        }
        finally {
            rl.unlock();
        }
        return adultRemoved;
    }
    
    private boolean isFull() {
        return MDW.size()==4;
    }
    
    private boolean isEmpty(){
        return MDW.isEmpty();
    }
}
