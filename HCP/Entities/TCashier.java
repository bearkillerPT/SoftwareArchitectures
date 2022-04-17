package HCP.Entities;

import java.util.Random;

public class TCashier extends Thread{
    
    private final Random random = new Random();
    
    private final ICashier_Pyh cashier;
    private TAdult adult;
    private final int pyt;
    
    public TCashier(ICashier_Pyh cashier, int pyt) {
        this.cashier = cashier;
        this.pyt = pyt;
    }
    
    @Override
    public void run() {
        while(true){
            try {
                if(pyt!=0){
                    Thread.sleep(random.nextInt(pyt));
                }
                adult = cashier.getPayment();
            } catch (Exception e) { }
        }
    }
}
