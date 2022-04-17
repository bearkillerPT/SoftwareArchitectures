package HCP.Main;
import HCP.Entities.TAdult;
import HCP.Entities.TCallCentre;
import HCP.Entities.TCashier;
import HCP.Entities.TDoctor;
import HCP.Entities.TNurse;
import HCP.Monitor.METH;
import HCP.Monitor.MEVH;
import HCP.Monitor.MMDW;
import HCP.Monitor.MPYH;
import HCP.Monitor.MWTH;
import HCP.Entities.IPatient_Evh;
import HCP.Entities.ICallCentre_Eth;
import HCP.Entities.ICallCentre_Wth;
import HCP.Entities.INurse_Wth;
import HCP.Entities.IDoctor_Pyh;
import HCP.Entities.ICashier_Pyh;
import HCP.Entities.INurse_Evh;
import HCP.Entities.IDoctor_Mdw;
import HCP.Entities.ICallCentre_Evh;
import HCP.Entities.ICallCentre_Mdw;

public class main {
    
    public static void main(String[] args) {
        
        //Data from the CCP
        int NoA = 5;
        int NoC = 0;
        int NoS = 8;
        int EVT = 1;
        int MDT = 1;
        int PYT = 1;
        int ttm = 1;
        
        int etn = 0;
        
        TAdult adult;
        TCallCentre cc;
        TNurse nurse1;
        TDoctor doc;
        TCashier cashier;
        
        METH mEth = new METH(NoS/2);
        MEVH mEvh = new MEVH();
        MWTH mWth = new MWTH(NoS/2);
        MMDW mMdw = new MMDW();
        MPYH mPyh = new MPYH();
        
        for(int i = 0; i < NoA;i++){
            etn++;
            adult = new TAdult((ICallCentre_Evh)mEth, etn, -1);
            adult.start();
        }
        
        cc = new TCallCentre((ICallCentre_Eth)mEth,(ICallCentre_Wth)mWth,(IPatient_Evh)mEvh, (ICallCentre_Mdw)mMdw);
        cc.start();
        
        nurse1 = new TNurse((INurse_Evh)mEvh, (INurse_Wth)mWth, EVT);
        nurse1.start();
        
        doc = new TDoctor((IDoctor_Mdw)mMdw, (IDoctor_Pyh)mPyh, MDT);
        doc.start();
        
        cashier = new TCashier((ICashier_Pyh)mPyh, PYT);
        cashier.start();
        
    }
}
