package UC2;
class PProducer {

    public static void main(String[] args) {
        TProducer tp;
        for (int i = 0; i < 6; i++) {
            tp = new TProducer();
            tp.start();
        }
    }
}
