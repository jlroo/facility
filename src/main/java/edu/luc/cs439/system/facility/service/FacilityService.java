package edu.luc.cs439.system.facility.service;

import java.util.Collection;
import java.util.Optional;

import edu.luc.cs439.system.facility.Chaos.ChaosSource;
import edu.luc.cs439.system.facility.dal.FacilityDAO;
import edu.luc.cs439.system.facility.models.facility.Facility;
import edu.luc.cs439.system.facility.web.contracts.DefaultResponse;
import edu.luc.cs439.system.facility.web.contracts.FacilityResponse;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacilityService {

    static final Logger LOG = LoggerFactory.getLogger(FacilityService.class);

    private FacilityDAO _facilityDAO = new FacilityDAO();
    @Autowired
    private Facility facility;

    public void setFacility(Facility facility) {
        this.facility = facility;
    }
    public Facility getFacility() {return facility;}

    public String getAllFacilities() {
        FacilityResponse response;
        try{
            ChaosSource.ForService("Facility").ForMethod("getAllFacilities").run();
            Collection<Facility> all = _facilityDAO.ReadAllFacilities();
            response = FacilityResponse.Success(all);
        } catch(Exception e) {
            response = FacilityResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }

    public String getFacility(int facilityID) {
        FacilityResponse response;
        try{
            ChaosSource.ForService("Facility").ForMethod("getFacility").run();
            Collection<Facility>  facility = _facilityDAO.ReadAllFacilities();
            Optional<Facility> match = facility
                    .stream()
                    .filter(c -> c.getFacilityID() == facilityID)
                    .findFirst();
            if (match.isPresent()) {
                response = FacilityResponse.Success(new Facility[]{match.get()});
            } else {
                response = FacilityResponse.Error("Facility not found");
                LOG.error(response.Message);
            }
        } catch(Exception e){
            response = FacilityResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }


    public String addFacility(int facilityID,Facility newObj) {
        DefaultResponse response;
        try {
            ChaosSource.ForService("Facility").ForMethod("addFacility").run();
            if(newObj.getFacilityID() != facilityID) {
                response = DefaultResponse.Error("Error, ID did not match");
            } else {
                _facilityDAO.Insert(newObj);
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

    public String updateFacility(int facilityID,Facility newObj) {
        DefaultResponse response;
        try {
            ChaosSource.ForService("Facility").ForMethod("updateFacility").run();
            if(newObj.getFacilityID() != facilityID) {
                response = DefaultResponse.Error("Error, ID did not match");
                LOG.error(response.Message);
            } else {
                _facilityDAO.Update(facilityID,newObj);
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


    public String deleteFacility(int deleteID) {
        DefaultResponse response;
        try {
            ChaosSource.ForService("Facility").ForMethod("deleteFacility").run();
            _facilityDAO.Delete(deleteID);
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

    public String cleanDB() {
        DefaultResponse response;
        try {
            ChaosSource.ForService("Facility").ForMethod("DeleteDB").run();
            _facilityDAO.cleanTables();
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