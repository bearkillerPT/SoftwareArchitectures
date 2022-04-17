package HCP.Monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;

import HCP.Entities.IPatient_Evh;
import java.util.ArrayList;
import java.util.List;
import HCP.Entities.INurse_Evh;
import HCP.Entities.TAdult;

public class MEVH implements INurse_Evh, IPatient_Evh {

    private final Random random = new Random();

    private final List<TAdult> EVR1;
    private final ReentrantLock rl;
    private final Condition cNotFull;
    private final Condition cNotEmpty;
    
    TAdult adultRemoved;

    public MEVH() {
        EVR1 = new ArrayList<>();
        rl = new ReentrantLock();
        cNotEmpty = rl.newCondition();
        cNotFull = rl.newCondition();
    }

    @Override
    public void putEvr1(TAdult adult) {
        try {
            rl.lock();
            try {
                while (isFull()) {
                    cNotFull.await();
                }
            } catch (InterruptedException ex) {
            }
            EVR1.add(adult);
            System.out.println("\nAdult: " + adult.getIdAdult() + " is in the Evaluation Hall");
            cNotEmpty.signal();
        } finally {
            rl.unlock();
        }
    }

    @Override
    public TAdult evaluate_dos() {
        try {
            rl.lock();
            try {
                while (isEmpty()) {
                    cNotEmpty.await();
                }
            } catch (InterruptedException ex) {
            }
            adultRemoved = EVR1.get(0);
            EVR1.remove(0);
            adultRemoved.setDos(random.nextInt(2));
            System.out.println("\nAdult: " + adultRemoved.getIdAdult() + " left the Evaluation Hall");
            cNotFull.signal();
        } finally {
            rl.unlock();
        }
        return adultRemoved;
    }

    private boolean isFull() {
        return EVR1.size() == 1;
    }

    private boolean isEmpty() {
        return EVR1.isEmpty();
    }
}
