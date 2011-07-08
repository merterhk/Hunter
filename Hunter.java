
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.ResultSet;
import javax.imageio.ImageIO;

public class Hunter {

    BufferedImage imgHunter;
    SQLite hafiza;
    int x, y;
    int en, boy;

    public Hunter() {
        try {
            imgHunter = ImageIO.read(getClass().getResource("img/lion.png"));
            x = 0;
            y = 0;
        } catch (IOException ex) {
        }
    }

    public Hunter(BufferedImage imgHunter, int x, int y) {
        this.imgHunter = imgHunter;
        this.x = x;
        this.y = y;
    }

    public Hunter(BufferedImage imgHunter, SQLite puanlar, int x, int y) {
        this.imgHunter = imgHunter;
        this.hafiza = puanlar;
        this.x = x;
        this.y = y;
    }

    public Hunter(BufferedImage imgHunter, SQLite puanlar, int x, int y, int en, int boy) {
        this.imgHunter = imgHunter;
        this.hafiza = puanlar;
        this.x = x;
        this.y = y;
        this.en = en;
        this.boy = boy;
    }


    /* Avcı'nın kovalama metodu. */
    public void chase(Hunter[] hunters, Prey[] preys, int gorus) {
        String ortam = ortam(hunters, preys, gorus);
//
        if (preyVarMi(preys, x + 1, y)) {
            moveRight();
            odulGuncelle(ortam, 2, 100);
            return;
        } else if (preyVarMi(preys, x - 1, y)) {
            moveLeft();
            odulGuncelle(ortam, 4, 100);
            return;
        } else if (preyVarMi(preys, x, y + 1)) {
            moveDown();
            odulGuncelle(ortam, 3, 100);
            return;
        } else if (preyVarMi(preys, x, y - 1)) {
            moveUp();
            odulGuncelle(ortam, 1, 100);
            return;
        } else if (preyVarMi(preys, x, y)) {
            moveNot();
            
            System.out.println("duruyor dünya..");
            odulGuncelle(ortam, 0, 100);
            return;
        }


        try {
            boolean goruyor = false;

//            ResultSet rs1 = hareketler(ortam);
//            int tmp = -1;
//            while (rs1.next()) {
//                if (tmp == -1) {
//                    tmp = rs1.getInt("odul");
//                }
//                if (tmp == rs1.getInt("odul")) {
//                    esit = false;
//                }
//            }


            for (Prey prey : preys) {
                if (!prey.oldu && Math.abs(x - prey.x) <= gorus && Math.abs(y - prey.y) <= gorus) {
                    goruyor = true;
                }
            }

            int hareket = -1;
            int maxOdul = 0;

            if (goruyor) {

                hareket = 0;
                if (true) {
                    int tempOdul = maxOdul(ortam(hunters, preys, gorus));
                    if (tempOdul > maxOdul) {
                        hareket = 0;
                    }
                }
                if (x > 0 && hunterVarMi(hunters, x - 1, y)) { // Sola giderse maximum ödül ne?
                    moveLeft();
                    int tempOdul = maxOdul(ortam(hunters, preys, gorus));
                    if (tempOdul > maxOdul) {
                        hareket = 4;
                    }
                    moveRight();
                }

                if (x < en - 1 && hunterVarMi(hunters, x + 1, y)) { // Sağa giderse maximum ödül ne?
                    moveRight();
                    int tempOdul = maxOdul(ortam(hunters, preys, gorus));
                    if (tempOdul > maxOdul) {
                        hareket = 2;
                    }
                    moveLeft();
                }

                if (y > 0 && hunterVarMi(hunters, x, y - 1)) { // Yukarı giderse maximum ödül ne?
                    moveUp();
                    int tempOdul = maxOdul(ortam(hunters, preys, gorus));
                    if (tempOdul > maxOdul) {
                        hareket = 1;
                    }
                    moveDown();
                }

                if (y < boy - 1 && hunterVarMi(hunters, x, y + 1)) { // Yukarı giderse maximum ödül ne?
                    moveDown();
                    int tempOdul = maxOdul(ortam(hunters, preys, gorus));
                    if (tempOdul > maxOdul) {
                        hareket = 3;
                    }
                    moveUp();
                }
                //hareket = maxOdulluHareket(ortam);
                if (hareket == 0) {
                    hareket = (int) (Math.random() * 4) + 1;
                }
            } else {
                hareket = (int) (Math.random() * 4) + 1;
            }



            switch (hareket) {
                case 1:
                    if (!hunterVarMi(hunters, x, y - 1) && y > 0) {
                        moveUp();
                        int yeniOdul = (int) (maxOdul(ortam(hunters, preys, gorus)) * 0.5);
                        if (yeniOdul > 0 && goruyor) {
                            odulGuncelle(ortam, 1, yeniOdul);
                        }
                        return;
                    }
                    break;
                case 2:
                    if (!hunterVarMi(hunters, x + 1, y) && x < en - 1) {
                        moveRight();

                        int yeniOdul = (int) (maxOdul(ortam(hunters, preys, gorus)) * 0.5);
                        if (yeniOdul > 0 && goruyor) {
                            odulGuncelle(ortam, 2, yeniOdul);
                        }

                        return;
                    }
                    break;
                case 3:
                    if (!hunterVarMi(hunters, x, y + 1) && y < boy - 1) {
                        moveDown();

                        int yeniOdul = (int) (maxOdul(ortam(hunters, preys, gorus)) * 0.5);
                        if (yeniOdul > 0 && goruyor) {
                            odulGuncelle(ortam, 3, yeniOdul);
                        }

                        return;
                    }
                    break;
                case 4:
                    if (!hunterVarMi(hunters, x - 1, y) && x > 0) {
                        moveLeft();

                        int yeniOdul = (int) (maxOdul(ortam(hunters, preys, gorus)) * 0.5);
                        if (yeniOdul > 0 && goruyor) {
                            odulGuncelle(ortam, 4, yeniOdul);
                        }

                        return;
                    }
                    break;
                default:
                    break;

            }

        } catch (Exception e) {
            System.out.println("Hafıza sorunu yaşayan avcı var..");
        }

    }

    /* Belirtilen koordinatta avcı var mı? */
    public boolean hunterVarMi(Hunter[] hunters, int x, int y) {
        for (Hunter hunter : hunters) {
            if (hunter.x == x && hunter.y == y) {
                return true;
            }
        }
        return false;
    }

    /* Belirtilen koordinatta av var mı? */
    public boolean preyVarMi(Prey[] preys, int x, int y) {
        for (Prey prey : preys) {
            if (prey.x == x && prey.y == y && !prey.oldu) {
                return true;
            }
        }
        return false;
    }

    /* Ortam dizisini Stringe çevirir. */
    public String ortam(Hunter[] hunters, Prey[] preys, int gorus) {
        byte[] ortam = new byte[(2 * gorus + 1) * (2 * gorus + 1)];

        // Heryer boş
        for (int i = 0; i < ortam.length; i++) {
            ortam[i] = 'N';
        }

        // Tam ortasında avcı var
        ortam[ortam.length / 2] = 'H';

        // Diğer avcıları yerleştir
        for (Hunter hunter : hunters) {
            if (!this.equals(hunter)) {
                if (Math.abs(hunter.x - x) <= gorus && Math.abs(hunter.y - y) <= gorus) {
                    int hX = hunter.x - (x - gorus);
                    int hY = hunter.y - (y - gorus);
                    ortam[hX + (((2 * gorus) + 1) * hY)] = 'H';
                }
            }
        }

        // Avları yerleştir
        for (Prey prey : preys) {
            if (Math.abs(prey.x - x) <= gorus && Math.abs(prey.y - y) <= gorus && !prey.oldu) {
                int pX = prey.x - (x - gorus);
                int pY = prey.y - (y - gorus);
                ortam[pX + (((2 * gorus) + 1) * pY)] = 'P';
            }
        }
        return new String(ortam);

    }

    public void odulGuncelle(String ortam, int hareket, int odul) {
        try {
            ResultSet rs = hafiza.executeQuery("select * from durumlar where ortam like '" + ortam + "' and hareket like " + hareket);
            if (rs.next()) {
                hafiza.executeUpdate("update durumlar set odul = " + odul + " where ortam like '" + ortam + "' and hareket like " + hareket);
                System.out.println("Guncellendi");
            } else {
                hafiza.executeUpdate("insert into durumlar (ortam,hareket,odul) values ('" + ortam + "'," + hareket + "," + odul + ");");
                System.out.println("Eklendi");
            }
            System.out.println("update durumlar set odul = " + odul + " where ortam like '" + ortam + "' and hareket like " + hareket);
        } catch (Exception e) {
            System.out.println("OdulGuncelle hata." + e.getLocalizedMessage());
        }
    }

    public void hareketEkle(String ortam, int hareket, int odul) {
        try {
            ResultSet rs = hafiza.executeQuery("select * from durumlar where ortam='" + ortam + "' and hareket=" + hareket);
            if (!rs.next()) {
                hafiza.executeUpdate("insert into durumlar (ortam,hareket,odul) values ('" + ortam + "'," + hareket + "," + odul + ");");
            } else {
                hafiza.executeUpdate("update durumlar set odul = " + odul + " where ortam like '" + ortam + "' and hareket like " + hareket);
            }
        } catch (Exception e) {
            System.out.println("hareketEkle hata." + e.getLocalizedMessage());
        }
    }

    /* Yapılacak hareketleri ödeül sırasıyla getirir. */
    public ResultSet hareketler(String ortam) {
        try {
            ResultSet rs = hafiza.executeQuery("select * from durumlar where ortam = '" + ortam + "' order by odul desc");
            if (!rs.next()) { // Durum kayıtlı değilse, kayıt et.
                hareketEkle(ortam, 0, 0); // 0 : yerinde kal
                hareketEkle(ortam, 1, 0); // 1 : yukarı
                hareketEkle(ortam, 2, 0); // 2 : sağa
                hareketEkle(ortam, 3, 0); // 3 : aşağı
                hareketEkle(ortam, 4, 0); // 4 : sola
            }
            rs = hafiza.executeQuery("select * from durumlar where ortam = '" + ortam + "' order by odul desc");
            return rs;
        } catch (Exception e) {
            System.out.println("hareketeler hata." + e.getLocalizedMessage());
        }
        return null;
    }

    /* Ortamda yapılacak haretin ödülünü getirir. */
    public int odul(String ortam, int hareket) {
        try {
            ResultSet rs = hafiza.executeQuery("select * from durumlar where ortam = '" + ortam + "' and hareket = " + hareket + " order by odul desc");
            if (!rs.next()) { // Durum kayıtlı değilse, kayıt et.
                hareketEkle(ortam, hareket, 0);
                return 0;
            }
            return rs.getInt("odul");
        } catch (Exception e) {
            System.out.println("Hafıza sorunu yaşayan avcı var..");
        }
        return -1;
    }

    public int maxOdul(String ortam) {
        try {
            ResultSet rs = hafiza.executeQuery("select * from durumlar where ortam = '" + ortam + "' order by odul desc");
            if (!rs.next()) { // Durum kayıtlı değilse, kayıt et.
                //hareketEkle(ortam, hareket, 0);
                return 0;
            }
            return rs.getInt("odul");
        } catch (Exception e) {
            System.out.println("Hafıza sorunu yaşayan avcı var..");
        }
        return -1;
    }

    public int maxOdulluHareket(String ortam) {
        try {
            ResultSet rs = hareketler(ortam);//hafiza.executeQuery("select * from durumlar where ortam = '" + ortam + "' order by odul desc");
            if (!rs.next()) { // Durum kayıtlı değilse, kayıt et.
                //hareketEkle(ortam, hareket, 0);
                return 0;
            }
            return rs.getInt("hareket");
        } catch (Exception e) {
            System.out.println("Hafıza sorunu yaşayan avcı var..");
        }
        return -1;
    }

    /* Tüm verileri siler. */
    public void hafizaKaybi() {
        try {
            hafiza.executeQuery("delete from durumlar");
            System.out.println("Hafıza kaybedildi..");
        } catch (Exception e) {
            System.out.println("Hafıza kaybı sorun oluştu..");
        }
    }

    /* Avcı hareket eder. */
    public void moveRight() {
//        System.out.println("sağa git");
        x = (x < en) ? x + 1 : x;
    }

    public void moveLeft() {
//        System.out.println("sola git");
        x = (x > 0) ? x - 1 : x;
    }

    public void moveUp() {
//        System.out.println("yukari git");
        y = (y > 0) ? y - 1 : y;
    }

    public void moveDown() {
//        System.out.println("asagi git");
        y = (y < boy) ? y + 1 : y;
    }

    public void moveNot() {
    }
}
