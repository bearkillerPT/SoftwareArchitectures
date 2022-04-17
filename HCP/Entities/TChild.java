package HCP.Entities;


public class TChild extends Thread {

    private final ICallCentre_Evh child;
    private final int id;
    private final int dos;
    int counter=0;
    
    public TChild(ICallCentre_Evh child, int id, int dos) {
        this.child = child;
        this.id = id;
        this.dos = dos;
    }
    
    public int getIdChild() {
        return id;
    }
    
    @Override
    public void run() {
            try {
                child.putEtr(new TChild(child, id, dos));
                Thread.sleep(2000);
            } catch (Exception e) { }
    }
}
