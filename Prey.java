
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Prey {

    BufferedImage imgPrey;
    int x, y;
    int en, boy;
    boolean oldu = false;

    public Prey() {
        try {
            imgPrey = ImageIO.read(getClass().getResource("img/bird.png"));
            x = 0;
            y = 0;
        } catch (Exception ex) {
        }
    }

    public Prey(BufferedImage imgPrey, int x, int y) {
        this.imgPrey = imgPrey;
        this.x = x;
        this.y = y;
    }

    public Prey(BufferedImage imgPrey, int x, int y, int en, int boy) {
        this.imgPrey = imgPrey;
        this.x = x;
        this.y = y;
        this.en = en;
        this.boy = boy;
    }

    public void escape(Hunter[] hunters, int en, int boy) {
        if (!oldu) {

            for (Hunter hunter : hunters) {
                if (x == hunter.x && y == hunter.y) {
                    oldu = true;
                    x = -100;
                    y = -100;
                    return;
                }
            }


            int yon = 0;
            int max = 0;
            int temp = 0;

            if (x > 0) {
                temp = 0;
                for (Hunter hunter : hunters) {
                    if (Math.abs(x - hunter.x) < 3 && Math.abs(y - hunter.y) < 3) {
                        temp += (int) Math.abs((x - 1) - hunter.x) + (int) Math.abs(y - hunter.y);
                    }
                }
//                System.out.println("Temp : " + temp + " Max : " + max + " Yon : " + yon);
                if (temp > max) {
                    max = temp;
                    yon = 4;
                }
            }

            if (x < en - 1) {
                temp = 0;
                for (Hunter hunter : hunters) {
                    if (Math.abs(x - hunter.x) < 3 && Math.abs(y - hunter.y) < 3) {
                        temp += (int) Math.abs((x + 1) - hunter.x) + (int) Math.abs(y - hunter.y);
                    }
                }

//                System.out.println("Temp : " + temp + " Max : " + max + " Yon : " + yon);
                if (temp > max) {
                    max = temp;
                    yon = 2;
                }
            }

            if (y > 0) {
                temp = 0;
                for (Hunter hunter : hunters) {
                    if (Math.abs(x - hunter.x) < 3 && Math.abs(y - hunter.y) < 3) {
                        temp += (int) Math.abs(x - hunter.x) + (int) Math.abs((y - 1) - hunter.y);
                    }

                }

//                System.out.println("Temp : " + temp + " Max : " + max + " Yon : " + yon);
                if (temp > max) {
                    max = temp;
                    yon = 1;
                }
            }

            if (y < boy - 1) {
                temp = 0;
                for (Hunter hunter : hunters) {
                    if (Math.abs(x - hunter.x) < 3 && Math.abs(y - hunter.y) < 3) {
                        temp += (int) Math.abs(x - hunter.x) + (int) Math.abs((y + 1) - hunter.y);
                    }

                }
//                System.out.println("Temp : " + temp + " Max : " + max + " Yon : " + yon);
                if (temp > max) {
                    max = temp;
                    yon = 3;
                }
            }

            temp = 0;
            for (Hunter hunter : hunters) {
                if (Math.abs(x - hunter.x) < 3 && Math.abs(y - hunter.y) < 3) {
                    temp += (int) Math.abs(x - hunter.x) + (int) Math.abs((y) - hunter.y);
                }
            }
            if (temp > max) {
                max = temp;
                yon = 0;
            }


            switch (yon) {
                case 1:
                    y--;
//                moveUp();
                    break;
                case 2:
                    x++;
//                moveRight();
                    break;
                case 3:
                    y++;
//                moveDown();
                    break;
                case 4:
                    x--;
//                moveLeft();
                    break;
                default:
                    break;
            }

//            System.out.println("Av yön : " + yon);
//            System.out.println("sonraki : x:" + x + "y:" + y);
        }

    }

    public void oldur() {
        oldu = true;
    }

    public void moveRight() {
        x = (x < en) ? x++ : x;
    }

    public void moveLeft() {
//        System.out.println("Sola git laann!!");
        x = (x > 0) ? x-- : x;
        System.out.println("x:" + x);
    }

    public void moveUp() {
        y = (y > 0) ? y-- : y;
    }

    public void moveDown() {
        y = (y < boy) ? y++ : y;
    }

    public void moveNot() {
    }
}
