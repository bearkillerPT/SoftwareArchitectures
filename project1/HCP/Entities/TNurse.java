package HCP.Entities;

import java.util.Random;

public class TNurse extends Thread {
    
    private final Random random = new Random();
    
    private final INurse_Evh nurse;
    private final INurse_Wth nurse_wth;
    private TAdult adult;
    private final int evt;
    
    public TNurse(INurse_Evh nurse, INurse_Wth nurse_wth, int evt) {
        this.nurse = nurse;
        this.nurse_wth = nurse_wth;
        this.evt = evt;
    }
    
    @Override
    public void run() {
        while(true){
            try {
                if(evt!=0){
                    Thread.sleep(random.nextInt(evt));
                }
                adult = nurse.evaluate_dos();
                nurse_wth.putWtr2(adult);
            } catch (Exception e) { }
        }
    }
}
