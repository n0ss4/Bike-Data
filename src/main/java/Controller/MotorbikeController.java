package Controller;

import Model.Motorbike;

import java.util.ArrayList;

public class MotorbikeController {
    ArrayList<Motorbike> motorbikes = new ArrayList<Motorbike>();

    public void addMotorbike(Motorbike toAdd){
        motorbikes.add(toAdd);
    }

    public void removeMotorbike(Motorbike toRemove){
        motorbikes.remove(toRemove);
    }

    public String getCSV(){
        StringBuilder finalStr = new StringBuilder();
        for(Motorbike m : motorbikes){
            finalStr.append(m.getCSV());
        }
        return finalStr.toString();
    }
}
