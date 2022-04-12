package HCP.Entities;

    public class TETR1 extends Thread {

    private final  IETH eh;
    
    public TETR1(IETH eh) {
        this.eh = eh;
    }
    @Override
    public void run() {
            try {
                Thread.sleep(2000);
                eh.getChild();
            } catch (Exception e) { }
    }
    }
