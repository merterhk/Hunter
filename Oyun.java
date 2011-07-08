
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JApplet;

public class Oyun extends JApplet {

    final int gorus = 2;
    final int en = 8, boy = 8;
    final int genislik = 48, yukseklik = 48; // pixel
    double alfa = 0.5;
    int speed = 100;
    Hunter[] hunters;
    Prey[] preys;
    BufferedImage screen = new BufferedImage(en * genislik, boy * yukseklik, BufferedImage.TYPE_INT_RGB);
    Hareket[][] bolge = new Hareket[en][boy];
    SQLite durumlar;
    boolean bitti = false;
    int hunterCount, preyCount;
    //BufferedImage hunterBim, preyBim;
    String hunterImage, preyImage;

    public Oyun() {
        hunters = new Hunter[2];
        preys = new Prey[1];
        hunterCount = 2;
        preyCount = 1;
        hunterImage = "lion";
        preyImage = "elephant";
        try {
            durumlar = new SQLite("durumTablosu.mhkDB");
        } catch (Exception e) {
            System.out.println("Hunter and prey initialize error.." + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public Oyun(int hunterCount, int preyCount, String hunterImage, String preyImage) {
        hunters = new Hunter[hunterCount];
        preys = new Prey[preyCount];
        this.hunterCount = hunterCount;
        this.preyCount = preyCount;
        this.hunterImage = hunterImage;
        this.preyImage = preyImage;
        try {
            durumlar = new SQLite("durumTablosu.mhkDB");
        } catch (Exception e) {
            System.out.println("Hunter and prey initialize error.." + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        setSize(en * genislik, boy * yukseklik);
    }

    public void newGame() {
        hunters = new Hunter[hunterCount];
        preys = new Prey[preyCount];
        bitti = false;
        try {
            for (int i = 0; i < hunters.length; i++) {
                int x = (int) (Math.random() * 8);
                int y = (int) (Math.random() * 8);
                hunters[i] = new Hunter(ImageIO.read(getClass().getResource("img/" + hunterImage + ".png")), durumlar, x, y, en, boy);
            }
            for (int i = 0; i < preys.length; i++) {
                int x = (int) (Math.random() * 8);
                int y = (int) (Math.random() * 8);
                preys[i] = new Prey(ImageIO.read(getClass().getResource("img/" + preyImage + ".png")), x, y, en, boy);
            }
        } catch (Exception e) {
            System.out.println("Hata var lo..");
        }
    }

    public void imha() {
        durumlar.close();
    }

    @Override
    public void paint(Graphics grphcs) {
        Graphics g = screen.getGraphics();
        drawLines(g);
        drawActors(g);
        drawGorus(g);
        grphcs.drawImage(screen, 0, 0, this);
    }

    public void chasescape() {
        if (!bitti) {
            bitti = true;
            for (Prey prey : preys) {
                prey.escape(hunters, en, boy);
                if (!prey.oldu) {
                    bitti = false;
                    break;
                }
            }

            for (Hunter hunter : hunters) {
                hunter.chase(hunters, preys, gorus);
            }

        } else {
            durumlar.close();
        }
    }

    public void drawLines(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, en * genislik, boy * yukseklik);

        for (int i = 0; i < en; i++) {
            for (int j = 0; j < boy; j++) {
                g.setColor((i + j) % 2 == 1 ? Color.LIGHT_GRAY : Color.WHITE);
                int x = i * genislik;
                int y = j * yukseklik;
                g.fillRect(x, y, genislik, yukseklik);
            }
        }
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, en * genislik, boy * yukseklik);
    }

    public void drawActors(Graphics g) {
        for (Prey prey : preys) {
//            g.setColor(Color.blue);
//            g.drawRect((prey.x - gorus) * genislik, (prey.y - gorus) * yukseklik, (prey.x + gorus) * genislik, (prey.y + gorus) * yukseklik);
            g.drawImage(prey.imgPrey, prey.x * genislik, prey.y * yukseklik, this);
        }
        for (Hunter hunter : hunters) {
//            g.setColor(Color.red);
//            g.drawRect((hunter.x - gorus) * genislik, (hunter.y - gorus) * yukseklik, (hunter.x + gorus) * genislik, (hunter.y + gorus) * yukseklik);
            g.drawImage(hunter.imgHunter, hunter.x * genislik, hunter.y * yukseklik, this);
        }
    }

    public void drawGorus(Graphics g) {
    }

    public void setHunterCount(int hunterCount) {
        this.hunterCount = hunterCount;
    }

    public void setPreyCount(int preyCount) {
        this.preyCount = preyCount;
    }

    public void setPreyImage(String preyImage) {
        this.preyImage = preyImage;
    }

    public void setHunterImage(String hunterImage) {
        this.hunterImage = hunterImage;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

   
}
