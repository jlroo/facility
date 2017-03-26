package edu.luc.cs439.system.facility.web.controllers;

import edu.luc.cs439.system.facility.models.use.FacilityUse;
import edu.luc.cs439.system.facility.models.use.Inspection;
import edu.luc.cs439.system.facility.service.UseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * Created by jlroo on 3/24/17.
 */

@RestController
@RequestMapping(value="facility/use/")
public class UseController {

    @Autowired
    UseService useService = new UseService();
    private String response;


    @RequestMapping(value="all",produces = "application/json")
    public String getALL() {
        response = useService.getAllOrders();
        return response;
    }

    @RequestMapping(value="{id}",produces = "application/json")
    public String getMaintenance(@PathVariable("id") int id) {
        response = useService.getFacilityOrder(id);
        return response;
    }

    @RequestMapping(value="delete/{id}",produces = "application/json")
    public String deleteByID(@PathVariable("id") int id) {
        response = useService.deleteFacilityUse(id);
        return response;
    }

    @RequestMapping(value="add",produces = "application/json")
    public String addFacilityUse(@RequestParam("id") int id,
                                    @RequestParam("customerID") int customer,
                                    @RequestParam("start") String startDate,
                                    @RequestParam("end") String endDate,
                                    @RequestParam("occupied") Boolean occupied,
                                    @RequestParam("inspectionDate") String inspectionDate,
                                    @RequestParam("code") String code,
                                    @RequestParam("passed") Boolean passed,
                                    @RequestParam("desc") String desc){

        String[] date0 = startDate.split("-");
        int year0 = Integer.parseInt(date0[0]);
        int month0 = Integer.parseInt(date0[1]);
        int day0 = Integer.parseInt(date0[2]);

        String[] date1 = endDate.split("-");
        int year1 = Integer.parseInt(date1[0]);
        int month1 = Integer.parseInt(date1[1]);
        int day1 = Integer.parseInt(date1[2]);

        String[] date3 = inspectionDate.split("-");
        int year3 = Integer.parseInt(date3[0]);
        int month3 = Integer.parseInt(date3[1]);
        int day3 = Integer.parseInt(date3[2]);

        FacilityUse customerOrder = useService.getFacilityUse();

        customerOrder.setFacilityID(id);
        customerOrder.setCustomerID(customer);
        customerOrder.setReservationStart(LocalDate.of(year0, month0, day0));
        customerOrder.setReservationEnd(LocalDate.of(year1, month1, day1));
        customerOrder.setOccupied(occupied);

        Inspection inspection = customerOrder.getInspection();

        inspection.setInspectionDate(LocalDate.of(year3, month3, day3));
        inspection.setInspectionCode(code);
        inspection.setPassedInspection(passed);
        inspection.setDescription(desc);

        customerOrder.setInspection(inspection);

        response = useService.addFacilityUse(id,customerOrder);
        return response;
    }
}
