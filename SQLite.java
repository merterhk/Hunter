
import java.io.File;
import java.sql.*;
import java.util.Scanner;

public class SQLite {

    Connection conn = null;
    String db; // SQLite Veritaban� dosyas� ismi. Bu dosya program s�n�flar�n�n i�inde yer almal�d�r.

    public SQLite(String db) {
        this.db = db;
    }

    public boolean connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            String connStr = "jdbc:sqlite:/" + this.getClass().getResource(db).getFile();
            //System.out.println(connStr);
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(connStr);
            }
        } catch (Exception e) {
            System.out.println("SQLite connection error..");
            e.printStackTrace();
        }
        return false;
    }

    public boolean close() {
        try {
            if (conn != null) {
                conn.close();
            }
            return true;
        } catch (Exception ex) {
            System.out.println("SQLite DB close error..");
            ex.printStackTrace();
        }
        return false;
    }

    public synchronized ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            connect();
            rs = conn.createStatement().executeQuery(sql);
            return rs;
        } catch (Exception e) {
            System.out.println("executeQuery Error.");
            e.printStackTrace();
        }
        close();
        return rs;
    }

    public synchronized boolean executeUpdate(String sql) {
        ResultSet rs = null;
        try {
            connect();
            return conn.createStatement().executeUpdate(sql) > 0;//execute(sql);
        } catch (Exception e) {
            System.out.println("execute Error.");
            e.printStackTrace();
        }
        close();
        return false;
    }

    public static void main(String[] args) {
        SQLite vt = new SQLite("durumTablosu.mhkDB");

//        vt.executeUpdate("drop table if exists kelimeler;");
//        vt.executeUpdate("create table kelimeler (kelime);");

//        Scanner s = null;
//        try {
//            s = new Scanner(new File("sozluk.txt"), "utf-8");
//        } catch (Exception e) {
//        }

//        String st = "", tmp = "";
//        while (s.hasNext()) {
//            st = s.next();
//            if (!tmp.equals(st)) {
//                try {
//                    if (vt.executeUpdate("insert into kelimeler (kelime) values ('" + st + "')")) {
//                        System.out.println("eklendi : " + st);
//                    }
//                    tmp = st;
//                } catch (Exception e) {
//                }
//            }
//        }
// System.out.println("'" + st + "' kelimesi eklendi.");
        //System.out.println(st);

        //m.connect();
        try {
            //vt.executeUpdate("insert into durumlar (ortam) values ('merter1');");
            vt.executeUpdate("delete from durumlar");
            //m.execute("insert into people (name) values ('merter2');");
            //System.out.println("-->" + m.executeUpdate("insert into people (name) values ('merter3');"));
            long a = System.currentTimeMillis();
            ResultSet rs = vt.executeQuery("select * from durumlar;");
            //b.next();
            while (rs.next()) {
                System.out.println("name = " + rs.getString("ortam"));
                //System.out.println("job = " + rs.getString("occupation"));
            }
            long b = System.currentTimeMillis();
            System.out.println("s�re:" + (b - a) + "ms");
            //System.out.println("b=" + b.getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
