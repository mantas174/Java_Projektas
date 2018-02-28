package lt.mantas.kelioSalygos.model;

import com.mysql.cj.api.mysqla.result.Resultset;
import lt.mantas.kelioSalygos.Main;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class DBAction {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/kelio_salygos";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    private Connection _conn = null;
    private String kablelis = ", ";
    ;

    private FileAction failas;


    public DBAction() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            _conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<String> garazinkKameruInfoIsDB(){
        ArrayList<String> rezultatas = new ArrayList<String>();
        Statement statement;
        ResultSet set;
        ResultSetMetaData duomenys;

        try {
            statement = _conn.createStatement();
            set = statement.executeQuery("SELECT *   FROM `kameros`");
            duomenys = set.getMetaData();

            while (set.next()){// 2 id, 3 img, 4 name,  5 data, 6 lat, 7 long
                if (!set.getString(6).equals("praleistas") || !set.getString(7).equals("praleistas")) {
//                    System.out.println(set.getString(2) + " " + set.getString(3) + " " + set.getString(4) + " " + set.getString(5) + " " + set.getString(6) + " " + set.getString(7));
                    rezultatas.add(set.getString(2) + Main.SKIRIKLIS + set.getString(3) + Main.SKIRIKLIS + set.getString(4) + Main.SKIRIKLIS + set.getString(5) + Main.SKIRIKLIS + set.getString(6) + Main.SKIRIKLIS + set.getString(7));
                }
//                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("\nKLAIDA ATLIEKANT SELECT \n" + getClass() + "\n");
            e.printStackTrace();
        }
        return rezultatas;
    }


    public ArrayList<String> paimkKameruIdIrData(ResultSet values) {
        ArrayList<String> sarasas = new ArrayList<String>();
        String eilute = "";
        ResultSetMetaData kameros_duomenys_db = null;

        try {
            kameros_duomenys_db = values.getMetaData();

            while (values.next()) {// result set VISADA prasideda nuo vieno ir eina iki tol kol mazesnis arba lygus .getColumnCount()
                for (int i = 1; i <= kameros_duomenys_db.getColumnCount(); i++) {
                    eilute += values.getString(i) + " ";
                }
                sarasas.add(eilute);
                eilute = "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sarasas;
    }

    public void dbAtnaujinimas(ArrayList<String> kameru_duomenys_atsisiustas) {
        Statement statement;
        ResultSet set = null;
        String[] duomenys_atsisiusti;
        String[] duomenys_db;

        int kameros_id_atsisiustas;
        String kameros_img_src_atsisiustas, kameros_data_atsisiustas, kameros_name_atsisiustas;
        int kameros_id_db;
        String kameros_data_db;

        System.out.println("\nPRASIDEDA DUOMENU ATNAUJINIMAS  \n" + getClass() + "\n");


        try {
            statement = _conn.createStatement();
            set = statement.executeQuery("SELECT `Kameros_id`, `Data` FROM `kameros` WHERE 1");
        } catch (SQLException e) {
            System.out.println("\nKLAIDA SU DUOMENU BAZES QUERRY DEL KAMEROS ID IR DATOS PAEMIMO  \n" + getClass() + "\n");
            e.printStackTrace();
        }

int klaidu_kiekis = 0;
        ArrayList<String> kameru_id_data_db = paimkKameruIdIrData(set);
        if (kameru_duomenys_atsisiustas.size() == kameru_id_data_db.size()) {
            for (int i = 0; i < kameru_duomenys_atsisiustas.size(); i++) {
                duomenys_atsisiusti = kameru_duomenys_atsisiustas.get(i).split(Main.SKIRIKLIS);
                duomenys_db = kameru_id_data_db.get(i).split(" ");
                //sudejimas i atskirus kintamuosius ir idejimas i duomenu baze
                // 0 imgsrc, 1 data, 2 id, 3 name   --- atsisiusti
                //0 id, 1 data,   3 laikas       ---db
                kameros_id_atsisiustas = Integer.parseInt(duomenys_atsisiusti[2]);
                kameros_id_db = Integer.parseInt(duomenys_db[0]);
                if (kameros_id_atsisiustas == kameros_id_db) {
                    kameros_data_db = duomenys_db[1] + " | " +  duomenys_db[3] ;
                    kameros_data_atsisiustas = duomenys_atsisiusti[1];
//                    System.out.println("" + kameros_data_atsisiustas  + "-" + kameros_data_db + "");
                    if(!kameros_data_db.equals(kameros_data_atsisiustas)){
                        kameros_img_src_atsisiustas = duomenys_atsisiusti[0];
                        kameros_name_atsisiustas = duomenys_atsisiusti[3];
                        try {
                            statement = _conn.createStatement();
                            String set_formavimas = "`Img_src`= '" + kameros_img_src_atsisiustas + "', `Name`= '" + kameros_name_atsisiustas + "', `Data`= '" + kameros_data_atsisiustas + "'";
                            statement.executeUpdate("UPDATE `kameros` SET "+ set_formavimas +" WHERE `Kameros_id` = '"+ kameros_id_atsisiustas +"' ");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }else{
                        klaidu_kiekis ++;
                    }
                }
            }
            if(klaidu_kiekis > 0) {
                System.out.println("\nKLAIDA DATA YRA TOKIA PATI  \n" + getClass());
                System.out.println(klaidu_kiekis);
            }
        } else {
            System.out.println("\nDUOMENU KIEKIS SU ATSISIUSTAIS IR DB ESANCIAIS NESUTAMPA  \n" + getClass() + "\n");
        }
    }



    public void irasykIDBKoordinatesIsFailo(ArrayList<String> kameru_duomenys_atsisiustas) {
        Statement statement;
        try {//
            statement = _conn.createStatement();

            int kameros_id_atsisiustas;
            String kameros_img_src_atsisiustas, kameros_data_atsisiustas, kameros_name_atsisiustas;
            int kameros_id_faile;
            String kameros_lattitude_faile, kameros_longitude_faile;
            String[] duomenys_atsisiusti;
            String[] duomenys_faile;

            //test
            System.out.println("\nPRASIDEDA KAMERU VISU DUOMENU SURASYMAS I DB \n" + getClass() + "\n");

            failas = new FileAction();
            ArrayList<String> kameru_koordinates_faile = failas.paimkKameruIdIrKoordinates();
            if (kameru_duomenys_atsisiustas.size() == kameru_koordinates_faile.size()) {
                for (int i = 0; i < kameru_duomenys_atsisiustas.size(); i++) {
                    duomenys_atsisiusti = kameru_duomenys_atsisiustas.get(i).split(Main.SKIRIKLIS);
                    duomenys_faile = kameru_koordinates_faile.get(i).split(":");

//                      Patikrinimas ar gerai isskaido
//                    int kiekis = 0;
//                    for(String e : duomenys_atsisiusti){
//                        System.out.print(e + " ");
//                        System.out.print(kiekis + " ");
//                        kiekis++;
//
//                    }

//                    System.out.println();
//                    kiekis = 0;
//                    for(String e : duomenys_faile){
//                        System.out.print(e + " ");
//                        System.out.print(kiekis + " ");
//                        kiekis++;
//                    }


                    //sudejimas i atskirus kintamuosius ir idejimas i duomenu baze
                    // 0 imgsrc, 1 data, 2 id, 3 name   --- atsisiusti
                    //0 id, 1 lat, 2 long  ----- faile
                    kameros_id_atsisiustas = Integer.parseInt(duomenys_atsisiusti[2]);
                    kameros_id_faile = Integer.parseInt(duomenys_faile[0]);
                    if (kameros_id_atsisiustas == kameros_id_faile) {
                        kameros_img_src_atsisiustas = duomenys_atsisiusti[0];
                        kameros_data_atsisiustas = duomenys_atsisiusti[1];
                        kameros_name_atsisiustas = duomenys_atsisiusti[3];
                        if (duomenys_faile.length < 2) {
                            kameros_lattitude_faile = "praleistas";
                            kameros_longitude_faile = "praleistas";
                        } else {
                            kameros_lattitude_faile = duomenys_faile[1];
                            kameros_longitude_faile = duomenys_faile[2];
                        }

                        String querry_value_formavimas = "'" + kameros_id_atsisiustas + "'" + kablelis + "'" + kameros_img_src_atsisiustas + "'" + kablelis + "'" + kameros_name_atsisiustas + "'" + kablelis +
                                "'" + kameros_data_atsisiustas + "'" + kablelis + "'" + kameros_lattitude_faile + "'" + kablelis + "'" + kameros_longitude_faile + "'";
//                        System.out.println("INSERT INTO `kameros_test`(`Kameros_id`, `Img_src`, `Name`, `Data`, `Latitude`, `Longitude`) VALUES (" + values + ")");               //testas
                        statement.executeUpdate("INSERT INTO `kameros`(`Kameros_id`, `Img_src`, `Name`, `Data`, `Latitude`, `Longitude`) VALUES (" + querry_value_formavimas + ")");
                    } else {
                        System.out.println("\natsisiustu kameru id nesutampa su faile esanciu kameros id  \n" + getClass() + "\n");
                    }
;
                }
            } else {
                System.out.println("\nDUOMENU KIEKIS SU ATSISIUSTAIS IR FAILE ESANCIAIS NESUTAMPA  \n" + getClass() + "\n");
            }

//            ALTER TABLE `kameros_test` CHANGE `Name` `Name` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL;
        } catch (SQLException e) {
            System.out.println("\nKLAIDA SU DUOMENU BAZE IRASINEJANT  \n" + getClass() + "\n");
            e.printStackTrace();
            System.out.println();
        }
    }
}
//    CREATE TABLE `kelio_salygos`.`kameros` ( `id` INT NOT NULL AUTO_INCREMENT , `Kameros_id` INT NOT NULL , `Img_src` VARCHAR CHARACTER SET utf8 COLLATE utf8_lithuanian_ci NULL DEFAULT NULL , `Name` VARCHAR CHARACTER SET utf8 COLLATE utf8_lithuanian_ci NULL DEFAULT NULL , `Data` VARCHAR CHARACTER SET utf8 COLLATE utf8_lithuanian_ci NULL DEFAULT NULL , `Latitude` VARCHAR CHARACTER SET utf8 COLLATE utf8_lithuanian_ci NULL DEFAULT NULL , `Longitude` VARCHAR CHARACTER SET utf8 COLLATE utf8_lithuanian_ci NULL DEFAULT NULL , PRIMARY KEY (`id`)) ENGINE = MyISAM;