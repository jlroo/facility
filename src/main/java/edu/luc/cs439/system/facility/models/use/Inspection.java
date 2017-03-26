package edu.luc.cs439.system.facility.models.use;

import java.time.LocalDate;

/**
 * Created by jlroo on 3/21/17.
 */

public interface Inspection {

    String getInspectionCode();
    void setInspectionCode(String inspectionCode);

    LocalDate getInspectionDate();
    void setInspectionDate(LocalDate inspectionDate);

    Boolean getPassedInspection();
    void setPassedInspection(Boolean passedInspection);

    String getDescription();
    void setDescription(String description);
}
