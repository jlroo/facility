package edu.luc.cs439.system.facility.models.facility;

/**
 * Created by jlroo on 3/21/17.
 */

public interface Facility{

    int getFacilityID();
    void setFacilityID(int facilityID);

    String getName();
    void setName(String name);

    int getRoomNumber();
    void setRoomNumber(int roomNumber);

    boolean isMedia();
    void setMedia(boolean media);

    int getMaxCapacity();
    void setMaxCapacity(int maxCapacity);

    Details getDetails();
    void setDetails(Details details);

}
