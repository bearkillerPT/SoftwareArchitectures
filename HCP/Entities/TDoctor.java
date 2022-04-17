package HCP.Entities;

import java.util.Random;

public class TDoctor extends Thread{
    
    private final Random random = new Random();
    
    private final IDoctor_Mdw doctor;
    private final IDoctor_Pyh pyh;
    private TAdult adult;
    private final int mdt;
    
    public TDoctor(IDoctor_Mdw doctor, IDoctor_Pyh pyh, int mdt) {
        this.doctor = doctor;
        this.pyh = pyh;
        this.mdt = mdt;
    }
    
    @Override
    public void run() {
        while(true){
            try {
                if(mdt!=0){
                    Thread.sleep(random.nextInt(mdt));
                }
                adult = doctor.getPatient();
                pyh.putPyh(adult);
            } catch (Exception e) { }
        }
    }
}
