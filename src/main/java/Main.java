import Controller.MotorbikeController;
import Model.Motorbike;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class Main {
    public static void main(String args[]) throws Exception{
        int currentYear = LocalDate.now().getYear();
        //We're disregarding 2019 bikes in 2018 because nobody should be riding them yet.
        MotorbikeController motorbikeController = new MotorbikeController();
        //Arbitrary year to start from
        for(int year = 2010; year <= currentYear; year++){
            //Printing the year just for shits and gigs
            System.out.println(year);
            //Build the string for the year
            String url = "https://bikez.com/year/index.php?year=" + year;
            //Connect to webpage and get the doc
            Document doc = Jsoup.connect(url).get();
            //Get all the tables on the webpage
            Elements tables = doc.select("table");
            //The actual table of items, other ones are just ads
            Element modelTable = tables.get(2);
            //Get all the table rows
            Elements models = modelTable.select("tr");
            //Remove the first item, as it is just information.
            //It tells us that the order is Model, Brand, Picture.
            models.remove(0);

            //For each bike
            for(Element packedBike : models){
                //Split it into make, model, picture (We discard picture).
                Elements unpackedBike = packedBike.select("td");
                String make = null;
                String model = null;
                String details = null;
                for (Element e:unpackedBike){
                    if(make == null) make = e.text();
                    else if(model == null) model = e.text();
                    else if(details == null) details = e.select("a").attr("href");
                }
                if(make == null || model == null || make.length() < 2 || model.length() < 2){
                    //Don't create the motorbike because all the fields aren't there
                }else{
                    String urlDetails = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png";

                    if(!Objects.equals(details, "")){
                        urlDetails = details.replace("..","https://bikez.com");
                        urlDetails = getPicture(urlDetails);
                    }

                    //Create object, and add to controller.
                    Motorbike m = new Motorbike(make, model, year, urlDetails);
                    motorbikeController.addMotorbike(m);
                }
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv"));
        writer.write(motorbikeController.getCSV());
        writer.close();
    }

    public static String getPicture(String url) {
        try{
            Document doc = Jsoup.connect(url).get();
            Elements tables = doc.select("table");
            Element modelTable = tables.get(1);
            Elements models = modelTable.select("img");
            return models.get(0).select("img").attr("src");
        }catch (Exception e){
            System.out.println(e);
        }

        return "";
    }
}
