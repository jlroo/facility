package edu.luc.cs439.system.facility.service;

import edu.luc.cs439.system.facility.Chaos.ChaosSource;
import edu.luc.cs439.system.facility.models.maintenance.Maintenance;
import edu.luc.cs439.system.facility.dal.MaintenanceDAO;
import edu.luc.cs439.system.facility.web.contracts.DefaultResponse;
import edu.luc.cs439.system.facility.web.contracts.MaintenanceResponse;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by jlroo on 2/20/17.
 */

@Service
public class MaintenanceService {

    static final Logger LOG = LoggerFactory.getLogger(MaintenanceService.class);
    private MaintenanceDAO _maintenanceDAO = new MaintenanceDAO();
    @Autowired
    private Maintenance maintenance;

    public void setMaintenance(Maintenance maintenance) {
        this.maintenance = maintenance;
    }

    public Maintenance getMaintenance() {
        return maintenance;
    }


    public String getAllMaintenance() {
        MaintenanceResponse response;
        try {
            ChaosSource.ForService("Maintenance").ForMethod("GET").run();
            Collection<Maintenance> all = _maintenanceDAO.ReadAllMaintenance();
            response = MaintenanceResponse.Success(all);
        } catch (Exception e) {
            response = MaintenanceResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }

    public String getMaintenanceByID(int facilityID) {
        DefaultResponse response;
        try{
            ChaosSource.ForService("Maintenance").ForMethod("getMaintenanceByID").run();
            Collection<Maintenance>  facility = _maintenanceDAO.ReadAllMaintenance();
            Optional<Maintenance> match = facility
                    .stream()
                    .filter(c -> c.getFacilityID() == facilityID)
                    .findFirst();
            if (match.isPresent()) {
                response = MaintenanceResponse.Success(new Maintenance[]{match.get()});
            } else {
                response = MaintenanceResponse.Error("Facility not found");
                LOG.error(response.Message);
            }
        } catch(Exception e){
            response = MaintenanceResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }

    public String addMaintenance(int facilityID,Maintenance newObj) {
        DefaultResponse response;
        try {
            ChaosSource.ForService("Maintenance").ForMethod("addMaintenance").run();
            if(newObj.getFacilityID() != facilityID) {
                response = DefaultResponse.Error("Error, ID did not match");
            } else {
                _maintenanceDAO.Insert(newObj);
                response = DefaultResponse.Success();
            }
        } catch(PSQLException e) {
            if(e.getMessage().contains("duplicate")){
                response = DefaultResponse.Error("Facility with id: " + facilityID + " already exists!");
                LOG.error(response.Message);
            } else {
                response = DefaultResponse.Error(e);
                LOG.error(response.Message);
            }
        } catch(Exception e) {
            response = DefaultResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }

    public String updateMaintenance(int facilityID,Maintenance newObj) {
        DefaultResponse response;
        try {
            ChaosSource.ForService("Maintenance").ForMethod("updateMaintenance").run();
            if(newObj.getFacilityID() != facilityID) {
                response = DefaultResponse.Error("Error, ID did not match");
            } else {
                _maintenanceDAO.Update(facilityID,newObj);
                response = DefaultResponse.Success();
            }
        } catch(PSQLException e) {
            response = DefaultResponse.Error(e);
            LOG.error(response.Message);
        } catch(Exception e) {
            response = DefaultResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }

    public String deleteMaintenance(int deleteID) {
        DefaultResponse response;
        try {
            ChaosSource.ForService("Maintenance").ForMethod("deleteMaintenance").run();
            _maintenanceDAO.Delete(deleteID);
            response = DefaultResponse.Success();
        }
        catch(PSQLException e) {
            response = DefaultResponse.Error(e);
            LOG.error(response.Message);
        }
        catch(Exception e) {
            response = DefaultResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }
}