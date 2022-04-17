package HCP.Entities;

    public class TCallCentre extends Thread {

    private final ICallCentre_Eth cc;
    private final ICallCentre_Wth wth;
    private final IPatient_Evh evh;
    private final ICallCentre_Mdw mdw;
    
    private TAdult tadult;
    private TAdult tadult_evaluated;
    
    public TCallCentre(ICallCentre_Eth cc, ICallCentre_Wth wth, IPatient_Evh evh, ICallCentre_Mdw mdw) {
        this.cc = cc;
        this.evh = evh;
        this.mdw = mdw;
        this.wth = wth;
    }
    
    @Override
    public void run() {
        while(true){
            try {
                    tadult = cc.getAdult();
                    evh.putEvr1(tadult);
                    Thread.sleep(1000);
                    tadult_evaluated = wth.getPatient();
                    mdw.putMdw2(tadult_evaluated);
            } catch (Exception e) { }
        }
    }
}
