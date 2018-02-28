package lt.mantas.kelioSalygos.controller;

import lt.mantas.kelioSalygos.Main;
import lt.mantas.kelioSalygos.model.Kameros;
import lt.mantas.kelioSalygos.model.URLActions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.ui.Model;


import java.util.ArrayList;


@Controller //
public class IndexController {


    @RequestMapping("/") // Kokiu adresu bus
                                                                                                                      //    @ResponseBody // ką rodyti atsiliepiant nereik jei darai is atskiro index.html failo
    String index(@RequestParam(value="kelioPradzia", required = false, defaultValue = "") String kelioPradzia, @RequestParam(value="kelioPabaiga", required = false, defaultValue = "") String kelioPabaiga, Model model) {
        model.addAttribute("kelioPradzia", kelioPradzia);
        model.addAttribute("kelioPabaiga", kelioPabaiga);

        ArrayList<String> kameru_sarasas_atsisiustas;
        ArrayList<String> kameru_sarasas_db;
        ArrayList<Kameros> kameru_sarasas;

        URLActions urlA = new URLActions();
        kameru_sarasas_atsisiustas = urlA.kameruSarasoAtsisiuntimas();

//        Main.duomenu_baze.irasykIDBKoordinatesIsFailo(kameru_sarasas);
//        Main.duomenu_baze.dbAtnaujinimas(kameru_sarasas_atsisiustas);
        kameru_sarasas_db = Main.duomenu_baze.garazinkKameruInfoIsDB();
        kameru_sarasas = kameruSarasas(kameru_sarasas_db);


        //perkelt i metoda
        int kiek = 0;
        if(kameru_sarasas.size() > 0) {
            String [] kameros_id = new String[kameru_sarasas.size()];
            String [] img_src = new String[kameru_sarasas.size()];
            String [] name = new String[kameru_sarasas.size()];
            String [] data = new String[kameru_sarasas.size()];
//            String [] koordinates = new String[kameru_sarasas.size()];
            float [] lat = new float[kameru_sarasas.size()];
            float [] lng = new float[kameru_sarasas.size()];
             for(Kameros k : kameru_sarasas){
                kameros_id[kiek] = k.getKameros_id();
                img_src[kiek] = k.getImg_src();
                name[kiek] = k.getName();
                data[kiek] = k.getData();
                lat[kiek] = Float.parseFloat(k.getLatitude());
                lng[kiek] = Float.parseFloat(k.getLongitude());


                System.out.println(name[kiek]);
//            System.out.println(k.getKameros_id() + " " + k.getImg_src() + " " + k.getName() + " " + k.getData() + " " + k.getLatitude() + " " + k.getLongitude());
//                model.addAttribute("koordinates", "{lat: " +k.getLatitude() + ", lng: " + k.getLongitude() + "}");
                kiek++;
            }
            System.out.println(name);
            model.addAttribute("kameros_id", kameros_id);
            model.addAttribute("img_src", img_src);
            model.addAttribute("name", name);
            model.addAttribute("data", data);
            model.addAttribute("latitude", lat);
            model.addAttribute("longitude", lng);

        }






        /*if(true) {
            kameru_sarasas = kameruSarasas(kameru_sarasas_db);
            model.addAttribute("koordinates", kameru_sarasas.);
            model.addAttribute("koords", kameru_sarasas_atsisiustas.get(0));

        }*/

//        model.addAttribute("koords", kameru_sarasas_atsisiustas.get(0));
        return "index";
    }

    public ArrayList<Kameros> kameruSarasas(ArrayList<String> kameru_sarasas){
        String [] duomenys;
        ArrayList<Kameros> kameru_inf_sarasas = new ArrayList<Kameros>();
        Kameros kameros_inf;

        for(int i = 0; i < kameru_sarasas.size(); i++){// 1 id, 2 img, 3 name,  4 data, 5 lat, 6 long
            duomenys = kameru_sarasas.get(i).split(Main.SKIRIKLIS);
//            System.out.println(duomenys[0] + "       " + duomenys[1] + "       " + duomenys[2] + "       " + duomenys[3] + "       " + duomenys[4] + "       " + duomenys[5] + "       " );
            kameros_inf = new Kameros(duomenys[0], duomenys[1], duomenys[2], duomenys[3], duomenys[4], duomenys[5]);
            kameru_inf_sarasas.add(kameros_inf);
//            System.out.println(duomenys[2]);
        }
        return kameru_inf_sarasas;
    }



}