/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * This Monitor contains several errors. Debug it.
 */
package Process1.Monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author OMP
 */
public class Fifo<T>{
    
    private int idxPut = 0;
    private int idxGet = 0;
    private int count = 0;
    
    private final T[] fifo;
    private final int size;
    private final ReentrantLock rl;
    private final Condition cNotFull;
    private final Condition cNotEmpty;
    
    public Fifo(int size) {
        this.size = size;
        fifo = (T[]) new Object[size];
        rl = new ReentrantLock();
        cNotEmpty = rl.newCondition();
        cNotFull = rl.newCondition();
    }
    public void put( T value ) {
        try {
            rl.lock();
            while ( isFull() )
                cNotFull.await();
            fifo[ idxGet ] = value;
            idxPut++;
            count++;
            cNotEmpty.signal();
        } catch ( InterruptedException ex ) {}
        finally {
            rl.unlock();
        }
    }
    public T get() {
        try{
            rl.lock();
            try {
                while ( isEmpty() )
                    cNotEmpty.await();
            } catch( InterruptedException ex ) {}
            cNotEmpty.signal();
            idxGet = (++idxGet) % size;
            return fifo[ idxGet ];
        }
        finally {
            rl.unlock();
        }
    }

    private boolean isFull() {
        return count == size;
    }

    private boolean isEmpty() {
        return count == 0;
    }
}
