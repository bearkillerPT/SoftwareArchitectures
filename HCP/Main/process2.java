package HCP.Main;

import HCP.Comunication.Server;
import HCP.Main.HCP;

public class Process2 {
    private Server comServer;
    private HCP hosp;
    public Process2(){
        this.comServer = new Server();
        String cmd="";
        while((cmd = this.comServer.getCommand())=="");
        System.out.println("CMD Received: " + cmd);
        setConfigurationAndInitializeHCP(this.comServer.getCommand());
    }

    private void setConfigurationAndInitializeHCP(String confCMD) {
        String[] configs = confCMD.split(",");
        int total_adult_patients = 0;
        int total_children_patients = 0;
        int total_seats = 0;
        int evt = 0;
        int mdt = 0;
        int pyt = 0;
        int time_to_move = 0;
        for(String conf : configs){
            String[] confTuple = conf.split(":");
            switch(confTuple[0]) {
                case "total_adult_patients":
                    total_adult_patients = Integer.parseInt(confTuple[1]);
                case "total_children_patients":
                    total_children_patients = Integer.parseInt(confTuple[1]);
                case "total_seats":
                    total_seats = Integer.parseInt(confTuple[1]);
                case "evt":
                    evt = Integer.parseInt(confTuple[1]);
                case "mdt":
                    mdt = Integer.parseInt(confTuple[1]);
                case "pyt":
                    pyt = Integer.parseInt(confTuple[1]);
                case "time_to_move":
                    time_to_move = Integer.parseInt(confTuple[1]);
            }
        }
        this.hosp = new HCP(total_adult_patients, total_children_patients, total_seats, evt, mdt, pyt, time_to_move);
    }
    public static void main(String args[]) {
        Process2 p = new Process2();
    }
}
