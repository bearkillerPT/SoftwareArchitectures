package HCP.Monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.List;
import HCP.Entities.ICallCentre_Eth;
import HCP.Entities.ICallCentre_Evh;
import HCP.Entities.TAdult;

public class METH implements ICallCentre_Evh, ICallCentre_Eth {

    private final List<TAdult> ETR1;
    private int count = 0;

    private final int size;
    private final ReentrantLock rl;
    private final Condition cNotFull;
    private final Condition cNotEmpty;
    
    TAdult adultRemoved;

    public METH(int size) {
        this.size = size;
        ETR1 = new ArrayList<>();
        rl = new ReentrantLock();
        cNotEmpty = rl.newCondition();
        cNotFull = rl.newCondition();
    }

    @Override
    public void putEtr1(TAdult adult) {
        try {
            rl.lock();
            try {
                while (isFull()) {
                    cNotFull.await();
                }
            } catch (InterruptedException ex) {
            }
            ETR1.add(adult);
            System.out.print("\nAdult: " + adult.getIdAdult() + " is in the Entrance Hall");
            count++;
            cNotEmpty.signal();
        } finally {
            rl.unlock();
        }
    }

    @Override
    public TAdult getAdult() {
        try {
            rl.lock();
            try {
                while (isEmpty()) {
                    cNotEmpty.await();
                }
            } catch (InterruptedException ex) {
            }

            adultRemoved = ETR1.get(0);
            ETR1.remove(0);
            System.out.println("\nAdult: " + adultRemoved.getIdAdult() + " removed from Entrance Hall");
            count--;
            cNotFull.signal();
        } finally {
            rl.unlock();
        }
        return adultRemoved;
    }

    private boolean isFull() {
        return count == size;
    }
    
    private boolean isEmpty() {
        return ETR1.isEmpty();
    }
}