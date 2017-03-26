package edu.luc.cs439.system.facility.models.facility;

import java.time.LocalDate;

/**
 * Created by jlroo on 3/21/17.
 */

public interface Details {

    String getPhoneNumber();
    void setPhoneNumber(String phoneNumber);

    String getDepartment();
    void setDepartment(String department);

    boolean isOccupied();
    void setOccupied(boolean occupied);

    LocalDate getInspected();
    void setInspected(LocalDate inspected);

    int getFacilityID();
    void setFacilityID(int facilityID);
}
