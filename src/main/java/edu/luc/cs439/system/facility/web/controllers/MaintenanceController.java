package edu.luc.cs439.system.facility.web.controllers;

import edu.luc.cs439.system.facility.models.maintenance.Maintenance;
import edu.luc.cs439.system.facility.service.MaintenanceService;
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
@RequestMapping(value="facility/maintenance/")
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService = new MaintenanceService();
    private String response;

    @RequestMapping(value = "all", produces = "application/json")
    public String getALL() {
        response = maintenanceService.getAllMaintenance();
        return response;
    }

    @RequestMapping(value = "{id}", produces = "application/json")
    public String getFacility(@PathVariable("id") int id) {
        response = maintenanceService.getMaintenanceByID(id);
        return response;
    }

    @RequestMapping(value = "delete/{id}", produces = "application/json")
    public String deleteByID(@PathVariable("id") int id) {
        response = maintenanceService.deleteMaintenance(id);
        return response;
    }

    @RequestMapping(value = "add", produces = "application/json")
    public String addMaintenance(@RequestParam("id") int id,
                                 @RequestParam("start") String startDate,
                                 @RequestParam("end") String endDate,
                                 @RequestParam("cost") int cost,
                                 @RequestParam(value = "log", required = false,
                                         defaultValue = "") String log) {

        Maintenance maintenance = maintenanceService.getMaintenance();
        maintenance.setFacilityID(id);
        String[] date0 = startDate.split("-");
        int year0 = Integer.parseInt(date0[0]);
        int month0 = Integer.parseInt(date0[1]);
        int day0 = Integer.parseInt(date0[2]);

        String[] date1 = endDate.split("-");
        int year1 = Integer.parseInt(date1[0]);
        int month1 = Integer.parseInt(date1[1]);
        int day1 = Integer.parseInt(date1[2]);

        maintenance.setMaintenanceStart(LocalDate.of(year0, month0, day0));
        maintenance.setMaintenanceEnd(LocalDate.of(year1, month1, day1));
        maintenance.setFacilityDowntime(maintenance.getMaintenanceStart(), maintenance.getMaintenanceEnd());
        maintenance.setMaintenanceCost(cost);
        maintenance.setMaintenanceLog(log);

        response = maintenanceService.addMaintenance(id, maintenance);
        return response;

    }
}