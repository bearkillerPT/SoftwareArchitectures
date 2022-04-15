package HCP.Entities;

public class TAdult extends TPatient {

    private final IPatient_Eth adult;
    private final int id;
    
    public TAdult(IPatient_Eth adult, int id) {
        this.adult = adult;
        this.id = id;
    }

    public int getIdAdult() {
        return id;
    }
    
    @Override
    public void run() {
            try {
                Thread.sleep(2000);
                System.out.printf("\nAdult[%d]: Analyzed", id);
                adult.putEtr1(new TAdult(adult, id));
            } catch (Exception e) { }
    }
}
