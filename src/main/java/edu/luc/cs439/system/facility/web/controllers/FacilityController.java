package edu.luc.cs439.system.facility.web.controllers;
import edu.luc.cs439.system.facility.models.facility.Details;
import edu.luc.cs439.system.facility.service.FacilityService;
import edu.luc.cs439.system.facility.models.facility.Facility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

/**
 * Created by jlroo on 3/21/17.
 */

@RestController
@RequestMapping(value="/facility/")
public class FacilityController {

    @Autowired
    FacilityService facilityService = new FacilityService();
    private String response;

    @RequestMapping(value="all",produces = "application/json")
    public String getALL() {
        response = facilityService.getAllFacilities();
        return response;
    }

    @RequestMapping(value="{id}",produces = "application/json")
    public String getFacility(@PathVariable("id") int id) {
        response = facilityService.getFacility(id);
        return response;
    }

    @RequestMapping(value="delete/{id}",produces = "application/json")
    public String deleteByID(@PathVariable("id") int id) {
        response = facilityService.deleteFacility(id);
        return response;
    }

    @RequestMapping(value="delete",produces = "application/json")
    public String cleanDB() {
        response = facilityService.cleanDB();
        return response;
    }

    @RequestMapping(value="add",produces = "application/json")
    public String addFacility(@RequestParam("id") int id,
                              @RequestParam("room") int room,
                              @RequestParam("media") Boolean media,
                              @RequestParam("capacity") int capacity,
                              @RequestParam("name") String name,
                              @RequestParam("phone") String phone,
                              @RequestParam("dept") String dept,
                              @RequestParam("occupied") Boolean occupied,
                              @RequestParam("date") String date){

        Facility customer = facilityService.getFacility();

        customer.setFacilityID(id);
        customer.setRoomNumber(room);
        customer.setMedia(media);
        customer.setMaxCapacity(capacity);
        customer.setName(name);

        Details customerDetails = customer.getDetails();

        customerDetails.setFacilityID(customer.getFacilityID());
        customerDetails.setPhoneNumber(phone);
        customerDetails.setDepartment(dept);
        customerDetails.setOccupied(occupied);

        String[] localDate = date.split("-");
        int year = Integer.parseInt(localDate[0]);
        int month = Integer.parseInt(localDate[1]);
        int day = Integer.parseInt(localDate[2]);
        customerDetails.setInspected(LocalDate.of(year,month,day));
        customer.setDetails(customerDetails);

        response = facilityService.addFacility(customer.getFacilityID(),customer);

        return response;
    }
}