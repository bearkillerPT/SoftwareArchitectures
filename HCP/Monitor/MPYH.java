package HCP.Monitor;

import HCP.Entities.IDoctor_Pyh;
import HCP.Entities.ICashier_Pyh;
import HCP.Entities.TAdult;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MPYH implements IDoctor_Pyh, ICashier_Pyh{
    
    private final List<TAdult> PYH;
    private final ReentrantLock rl;
    private final Condition cNotFull;
    private final Condition cNotEmpty;
    
    TAdult adultRemoved;
    
    public MPYH() {
        PYH = new ArrayList<>();
        rl = new ReentrantLock();
        cNotEmpty = rl.newCondition();
        cNotFull = rl.newCondition();
    }
    
    @Override
    public void putPyh(TAdult adult) {
        try {
            rl.lock();
            while (isFull()){
                cNotFull.await();
            }
            PYH.add(adult);
            System.out.println("\nAdult: "+ adult.getIdAdult()+" is in the Payment Hall");
            cNotEmpty.signal();
        } catch ( InterruptedException ex ) {}
        finally {
            rl.unlock();
        }
    }
    
    @Override
    public TAdult getPayment() {
        try{
            rl.lock();
            try {
                while ( PYH.isEmpty() ){
                    cNotEmpty.await();   
                }
            } catch( InterruptedException ex ) {}
            adultRemoved = PYH.get(0);
            PYH.remove(0);
            System.out.println("\nAdult: "+ adultRemoved.getIdAdult()+" payed his bill and left");
            cNotFull.signal();
        }
        finally {
            rl.unlock();
        }
        return adultRemoved;
    }
    
    private boolean isFull() {
        return PYH.size()==1;
    }
    
    private boolean isEmpty(){
        return PYH.isEmpty();
    }
}
