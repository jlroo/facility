package edu.luc.cs439.system.facility.models.use;
import com.google.gson.Gson;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by jlroo on 2/20/17.
 */

@Service
@Data
public class InspectionImpl implements Inspection {
    private String inspectionCode;
    private LocalDate inspectionDate;
    private Boolean passedInspection;
    private String description;

    public static Inspection FromJson(String json) {
        Gson gson = new Gson();
        Inspection instance = gson.fromJson(json, Inspection.class);
        return instance;
    }

    public static String ToJson(Collection<Inspection> objects) {
        Inspection[] allArray = (Inspection[]) objects.toArray();
        String json = new Gson().toJson(allArray);
        return json;
    }

    public String ToJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}
