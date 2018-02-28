package lt.mantas.kelioSalygos.model;

import lt.mantas.kelioSalygos.Main;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class URLActions {


    public ArrayList<String> kameruSarasoAtsisiuntimas() {
        ArrayList<String> kameru_duomenu_sarasas = new ArrayList<String>();
        int kiekis = 0;
        Elements elements_data;
        Document doc = null;
        ByteBuffer byteBuffer;


        System.out.println("\nPRASIDEDA KAMERU DUOMENU ATSIUNTIMAS I SARASAS \n" + getClass() + "\n");

        try {                                                       //daryt ?number=273 tam kad visus surinktu
            doc = Jsoup.connect("http://www.eismoinfo.lt/camera-table?number=273&sortFrom=0").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        elements_data = doc.select("p");

//        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(myString)  //konvertuoja string i utf-8 tam jog db butu su lietuviskom raidem
        for (int i = 0; i < elements_data.size(); i = i + 6) {
//            String img_src = gaukImgSrc(doc, kiekis);
//            String data = elements_data.get(i).text();
//            String kameros_id = elements_data.get(i+1).text();
//            String name = elements_data.get(i+5).text();
            String eilute = gaukImgSrc(doc, kiekis) + Main.SKIRIKLIS + elements_data.get(i).text() + Main.SKIRIKLIS + elements_data.get(i + 1).text() + Main.SKIRIKLIS + elements_data.get(i + 5).text();
            kameru_duomenu_sarasas.add(eilute);
//            System.out.println(kameru_duomenu_sarasas.get(kiekis));
            kiekis++;
        }
//        System.out.println(kiekis);System.out.println();
        System.out.println("\nbaigtas \n" + getClass() + "\n");

        return kameru_duomenu_sarasas;
    }

    private String gaukImgSrc(Document document, int k) {
        String rezultatas = "";
        Elements elementas_img_src = null;

        elementas_img_src = document.select("div.camera-table-item > img"); //div.camera-table-item > img
        rezultatas = "http://www.eismoinfo.lt" + elementas_img_src.get(k).attr("src");

        return rezultatas;
    }


}
