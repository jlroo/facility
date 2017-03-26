package edu.luc.cs439.system.facility.models.facility;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Created by jlroo on 2/20/17.
 */

@Service
@Data
public class DetailsImpl implements Details {
    private int facilityID;
    private String phoneNumber;
    private String department;
    private boolean occupied;
    private LocalDate inspected;

    @Autowired
    private Details details;

    public Details getDetails() {
        return details;
    }
    public void setDetails(Details details) {this.details = details;}
}
