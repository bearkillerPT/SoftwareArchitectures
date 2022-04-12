package HCP.Main;
import HCP.Entities.IPatient_Eth;
import HCP.Entities.IETH;
import HCP.Entities.TAdult;
import HCP.Entities.TChild;
import HCP.Entities.TETR1;
import HCP.Entities.TETR2;
import HCP.Monitor.METH;

public class main {
    
    public static void main(String[] args) {
        
        int NoS = 6;
        int counter = 0;
        
        TAdult adult;
        TChild child;
        TETR1 etr1;
        TETR2 etr2;
        
        METH monitor = new METH(2);
        
        for(int i = 0; i < NoS/2;i++){
            counter++;
            adult = new TAdult((IPatient_Eth)monitor, counter);
            adult.start();
            etr2 = new TETR2((IETH)monitor);
            etr2.start();
        }
        
        for(int i = 0; i < NoS/2;i++){
            counter++;
            child = new TChild((IPatient_Eth)monitor, counter);
            child.start();
            etr1 = new TETR1((IETH)monitor);
            etr1.start();
        }
    }
}
