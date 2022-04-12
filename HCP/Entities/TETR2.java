package HCP.Entities;

    public class TETR2 extends Thread {

    private final  IETH eh;
    
    public TETR2(IETH eh) {
        this.eh = eh;
    }
    @Override
    public void run() {
            try {
                Thread.sleep(2000);
                eh.getAdult();
            } catch (Exception e) { }
    }
    }
