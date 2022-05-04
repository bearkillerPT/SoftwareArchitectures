package HCP.Monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.List;
import HCP.Entities.ICallCentre_Eth;
import HCP.Entities.ICallCentre_Evh;
import HCP.Entities.TAdult;
import HCP.Entities.TChild;

public class METH implements ICallCentre_Evh, ICallCentre_Eth {

    private final List<TAdult> ETR1;
    private final List<TChild> ETR2;
    
    private int count = 0;
    private int count2 = 0;

    private final int size;
    private final ReentrantLock rl;
    private final Condition cNotFull;
    private final Condition cNotEmpty;
    private final ReentrantLock r2;
    private final Condition cNotFull2;
    private final Condition cNotEmpty2;

    private TAdult adultRemoved;

    public METH(int size) {
        this.size = size;
        ETR1 = new ArrayList<>();
        rl = new ReentrantLock();
        cNotEmpty = rl.newCondition();
        cNotFull = rl.newCondition();
        ETR2 = new ArrayList<>();
        r2 = new ReentrantLock();
        cNotEmpty2 = r2.newCondition();
        cNotFull2 = r2.newCondition();
    }

    @Override
    public <T> void putEtr(T pacient) {
        if (pacient instanceof TAdult) {
            try {
                rl.lock();
                try {
                    while (isFull()) {
                        cNotFull.await();
                    }
                } catch (InterruptedException ex) {
                }
                ETR1.add((TAdult) pacient);
                System.out.print("\nAdult: " + ((TAdult) pacient).getIdAdult() + " is in the Entrance Hall");
                count++;
                cNotEmpty.signal();
            } finally {
                rl.unlock();
            }
        } else {
            try {
                r2.lock();
                try {
                    while (isFull()) {
                        cNotFull2.await();
                    }
                } catch (InterruptedException ex) {
                }
                ETR2.add((TChild) pacient);
                System.out.print("\nChild: " + ((TChild) pacient).getIdChild()+ " is in the Entrance Hall");
                count2++;
                cNotEmpty2.signal();
            } finally {
                r2.unlock();
            }
        }
    }

    @Override
    public <T> T getAdult() {
        try {
            rl.lock();
            try {
                while (isEmpty()) {
                    cNotEmpty.await();
                }
            } catch (InterruptedException ex) {
            }
            int id = 9999;
            int index = -1;
            for (int i = 0; i < ETR1.size(); i++) {
                if (ETR1.get(i).getIdAdult() < id) {
                    index = i;
                    id = ETR1.get(i).getIdAdult();
                }
            }

            adultRemoved = ETR1.get(index);
            ETR1.remove(index);
            System.out.println("\nAdult: " + adultRemoved.getIdAdult() + " removed from Entrance Hall");
            count--;
            cNotFull.signal();
        } finally {
            rl.unlock();
        }
        return (T)adultRemoved;
    }

    private boolean isFull() {
        return count == size;
    }

    private boolean isEmpty() {
        return ETR1.isEmpty();
    }
}