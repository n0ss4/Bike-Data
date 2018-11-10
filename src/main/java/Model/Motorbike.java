package Model;

public class Motorbike {
    private String make;
    private String model;
    private int year;

    public Motorbike(String model, String make, int year) {
        try {
            model = model.replace(make, "").trim();
        }catch (Exception e){
            //Who cares
        }
        this.make = make.trim();
        this.model = model.trim();
        this.year = year;
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
        String toReturn = make + "," + model + "," + year + "\n";
        return toReturn;
    }
}
