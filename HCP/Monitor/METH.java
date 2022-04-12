package HCP.Monitor;

import HCP.Entities.IETH;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import HCP.Entities.IPatient_Eth;
import HCP.Entities.TAdult;
import HCP.Entities.TChild;

public class METH implements IPatient_Eth, IETH {

    private final LinkedList<TAdult> ETR1;
    private final LinkedList<TChild> ETR2;
    private int count = 0;
    private int count2 = 0;
    
    private final int size;
    private final ReentrantLock rl;
    private final ReentrantLock r2;
    private final Condition cNotFull;
    private final Condition cNotEmpty;
    private final Condition cNotFull2;
    private final Condition cNotEmpty2;
    
    public METH(int size) {
        this.size = size;
        ETR1 = new LinkedList<>();
        ETR2 = new LinkedList<>();
        rl = new ReentrantLock();
        r2 = new ReentrantLock();
        cNotEmpty = rl.newCondition();
        cNotFull = rl.newCondition();
        cNotEmpty2 = r2.newCondition();
        cNotFull2 = r2.newCondition();
    }

    @Override
    public void putEtr1(TAdult adult) {
        try {
            rl.lock();
            while ( isFull() ){
                System.out.println("\nAdult list is Full!");
                cNotFull.await();
            }
            ETR1.add(adult);
            System.out.print("\nAdded adult to the list->"+ adult.getIdAdult());
            count++;
            cNotEmpty.signal();
        } catch ( InterruptedException ex ) {}
        finally {
            rl.unlock();
        }
    }
    
    @Override
    public void putEtr2(TChild child) {
        try {
            r2.lock();
            while ( isFull2() ){
                System.out.println("\nChild list is Full!");
                cNotFull2.await();
            }
            ETR2.add(child);
            System.out.print("\nAdded child to the list->"+ child.getIdChild());
            count2++;
            cNotEmpty2.signal();
        } catch ( InterruptedException ex ) {}
        finally {
            r2.unlock();
        }
    }

    @Override
    public void getAdult() {
        try{
            rl.lock();
            try {
                while ( ETR1.isEmpty() ){
                    System.out.println("\nAdult list is Empty!");
                    cNotEmpty.await();   
                }
            } catch( InterruptedException ex ) {}
            System.err.print("\nAdult list size is: " + ETR1.size());
            TAdult adultRemoved = ETR1.removeFirst();
            System.out.println("\n Adult Removed-> "+ adultRemoved.getIdAdult());
            count--;
            cNotFull.signal();
        }
        finally {
            rl.unlock();
        }
    }
    
    @Override
    public void getChild() {
        try{
            r2.lock();
            try {
                while ( ETR2.isEmpty() ){
                    System.out.println("\nChild list is Empty!");
                    cNotEmpty2.await();   
                }
            } catch( InterruptedException ex ) {}
            System.err.print("\nChild list size is: " + ETR2.size());
            TChild childRemoved = ETR2.removeFirst();
            System.out.println("\n Child removed-> "+ childRemoved.getIdChild());
            count2--;
            cNotFull2.signal();
        }
        finally {
            r2.unlock();
        }
    }
    
    private boolean isFull() {
        return count == size;
    }
    
    private boolean isFull2() {
        return count2 == size;
    }
}
