package edu.luc.cs439.system.facility.service;

import edu.luc.cs439.system.facility.Chaos.ChaosSource;
import edu.luc.cs439.system.facility.dal.UseDAO;
import edu.luc.cs439.system.facility.models.use.FacilityUse;
import edu.luc.cs439.system.facility.web.contracts.DefaultResponse;
import edu.luc.cs439.system.facility.web.contracts.UseResponse;
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
public class UseService {

    static final Logger LOG = LoggerFactory.getLogger(UseService.class);

    private UseDAO _useDAO = new UseDAO();
    @Autowired
    private FacilityUse facility;

    public void setFacilityUse(FacilityUse facility) {
        this.facility = facility;
    }

    public FacilityUse getFacilityUse() {
        return facility;
    }

    public String getAllOrders() {
        UseResponse response;
        try {
            ChaosSource.ForService("FacilityUse").ForMethod("getAllOrders").run();
            Collection<FacilityUse> all = _useDAO.ReadAllOrders();
            response = UseResponse.Success(all);
        } catch (Exception e) {
            response = UseResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }

    public String getFacilityOrder(int facilityID) {
        DefaultResponse response;
        try {
            ChaosSource.ForService("FacilityUse").ForMethod("getOrder").run();
            Collection<FacilityUse> all = _useDAO.ReadAllOrders();
            Optional<FacilityUse> match = all
                    .stream()
                    .filter(c -> c.getFacilityID() == facilityID)
                    .findFirst();
            if (match.isPresent()) {
                response = UseResponse.Success(new FacilityUse[]{match.get()});
            } else {
                response = DefaultResponse.Error("Order for facility not found");
                LOG.error(response.Message);
            }
        } catch (Exception e) {
            response = UseResponse.Error(e);
            LOG.error(response.Message);
        }
        return response.ToJson();
    }

    public String addFacilityUse(int facilityID,FacilityUse newObj) {
        DefaultResponse response;
        try {
            ChaosSource.ForService("FacilityUse").ForMethod("addFacilityUse").run();
            if(newObj.getFacilityID() != facilityID) {
                response = DefaultResponse.Error("Error, ID did not match");
                LOG.error(response.Message);
            } else {
                _useDAO.Insert(newObj);
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

    public String updateFacilityUse(int facilityID,FacilityUse newObj) {
        DefaultResponse response;
        try {
            ChaosSource.ForService("FacilityUse").ForMethod("updateFacilityUse").run();
            if(newObj.getFacilityID() != facilityID) {
                response = DefaultResponse.Error("Error, ID did not match");
            } else {
                _useDAO.Update(facilityID,newObj);
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

    public String deleteFacilityUse(int deleteID) {
        DefaultResponse response;
        try {
            ChaosSource.ForService("FacilityUse").ForMethod("deleteFacilityUse").run();
            _useDAO.Delete(deleteID);
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