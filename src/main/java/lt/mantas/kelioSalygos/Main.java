package lt.mantas.kelioSalygos;


import lt.mantas.kelioSalygos.model.DBAction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static final String asd = "main";

    public static final String SKIRIKLIS = " /--/ ";;

    public static DBAction duomenu_baze;

    public static void main(String[] args) {
        duomenu_baze = new DBAction();
        SpringApplication.run(Main.class,args);

    }





    //testas
//        Main obj = new Main();
//        obj.run();

        /*try {
            URL url = null;
            url = new URL( "http://www.eismoinfo.lt/#");
            InputStream is = url.openStream();
            Scanner sc = new Scanner(is);
            while(sc.hasNextLine()){
                System.out.println(sc.nextLine());
                /*String [] eil = sc.nextLine().split("\\[\\{");
                for(int i = 0; i<eil.length; i++){
                    System.out.println(eil[i] +" ");
                }
                System.out.println();*/
            /*}
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    //SpringApplication.run(Main.class, args);
}