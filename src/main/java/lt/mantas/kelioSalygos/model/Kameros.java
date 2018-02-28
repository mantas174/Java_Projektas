package lt.mantas.kelioSalygos.model;

public class Kameros {

    private String kameros_id;
    private String img_src;
    private String name;
    private String data;
    private String latitude;
    private String longitude;

    public Kameros(String kameros_id, String img_src, String name, String data, String latitude, String longitude){
        this.kameros_id = kameros_id;
        this.img_src = img_src;
        this.name = name;
        this.data = data;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getKameros_id() {
        return kameros_id;
    }

    public void setKameros_id(String kameros_id) {
        this.kameros_id = kameros_id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }
}
