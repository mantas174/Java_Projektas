package lt.mantas.kelioSalygos.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileAction {


    public ArrayList<String> paimkKameruIdIrKoordinates(){
        //Duomenu nuskaitymas i array list
        ArrayList<String> sarasas = new ArrayList<String>();
        String eilute;
        try {
            Scanner sc = new Scanner(new File("id_lat_long_kameru_long_lat_duomenys.txt"));

            System.out.println("\nPRASIDEDA KAMERU DUOMENU SURASYMAS I SARASA IS FAILO \n" + getClass() + "\n");


            while (sc.hasNextLine()) {
                //sc.nextFloat(), sc.nextFloat(), sc.nextFloat()
                eilute = sc.nextLine();
                sarasas.add(eilute);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println( "\nKLAIDA SU DUOMENU NUSKAITYMU IS FAILO  \n" +getClass()+"\n" );
            e.printStackTrace();
            System.out.println();
        }
        return sarasas;
    }

}
