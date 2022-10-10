package Model;

public class Motorbike {
    private String make;
    private String model;
    private int year;
    private String picture;

    public Motorbike(String model, String make, int year, String picture) {
        try {
            model = model.replace(make, "").trim();
        }catch (Exception e){
            //Who cares
        }
        this.make = make.trim();
        this.model = model.trim();
        this.year = year;
        this.picture = picture;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getCSV(){
        return make + "," + model + "," + year + "," + picture + "\n";
    }
}
