
public class Tred implements Runnable {

    Oyun o;

    public Tred() {
        o = new Oyun();
    }

    public Tred(Oyun o) {
        this.o = o;
    }

    public void run() {
        while (!o.bitti) {
            o.chasescape();
            o.repaint();
            try {

                Thread.sleep(o.speed);

            } catch (Exception e) {
                System.out.println("sleep hata");
            }
        }
        System.out.println("Tred ölür........");
    }
}
