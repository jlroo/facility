package edu.luc.cs439.system.facility.models.facility;

import com.google.gson.Gson;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Data
public class FacilityImpl implements Facility {

    private int facilityID;
    private String name;
    private int roomNumber;
    private boolean media;
    private int maxCapacity;
    @Autowired
    private Details details;

    public Details getDetails() {
        return details;
    }
    public void setDetails(Details details) {
        this.details = details;
    }

    public static Facility FromJson(String json) {
        Gson gson = new Gson();
        Facility instance = gson.fromJson(json, Facility.class);
        return instance;
    }

    public static String ToJson(Collection<Facility> objects) {
        Facility[] allArray = (Facility[]) objects.toArray();
        String json = new Gson().toJson(allArray);
        return json;
    }

    public String ToJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }

}
