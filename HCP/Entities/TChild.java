package HCP.Entities;

public class TChild extends Thread {

    private final IPatient_Eth child;
    private final int id;
    int counter=0;
    
    public TChild(IPatient_Eth child, int id) {
        this.child = child;
        this.id = id;
    }
    
    public int getIdChild() {
        return id;
    }
    
    @Override
    public void run() {
            try {
                Thread.sleep(2000);
                System.out.printf("\nChild[%d]: Analyzed", id);
                child.putEtr(new TChild(child, id));
            } catch (Exception e) { }
    }
}
