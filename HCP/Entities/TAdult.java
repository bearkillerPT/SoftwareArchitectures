package HCP.Entities;

public class TAdult extends TPatient {

    private final ICallCentre_Evh adult;
    private int id;
    private int dos;
    
    public TAdult(ICallCentre_Evh adult, int id, int dos) {
        this.adult = adult;
        this.id = id;
        this.dos = dos;
    }

    public int getIdAdult() {
        return id;
    }
    
    public void setDos(int dos){
        this.dos = dos;
    }
    
    public void setPatientId(int id){
        this.id = id;
    }
    
    public int getDos(){
        return dos;
    }
    
    @Override
    public void run() {
            try {
                Thread.sleep(2000);
                adult.putEtr1(new TAdult(adult, id, dos));
            } catch (Exception e) { }
    }
}
