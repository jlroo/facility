package edu.luc.cs439.system.client.clients;

import edu.luc.cs439.system.client.action.ClientAction;
import edu.luc.cs439.system.client.action.HttpContentType;
import edu.luc.cs439.system.client.retries.IRetryPolicy;
import edu.luc.cs439.system.client.retries.ISingleMethodPolicy;
import edu.luc.cs439.system.facility.models.facility.Facility;
import edu.luc.cs439.system.facility.web.contracts.DefaultResponse;
import edu.luc.cs439.system.facility.web.contracts.FacilityResponse;

import java.net.URL;

public class FacilityClient {

    private final String _baseAddress;
    private final IRetryPolicy _retry;

    public FacilityClient(String baseAddress, IRetryPolicy retry) {
        _baseAddress = baseAddress;
        _retry = retry;
    }

    public Facility[] getAllFacilities() throws Exception {
        ISingleMethodPolicy method = _retry.NewMethod();
        FacilityResponse response;
        do {
            ClientAction action = new ClientAction();
            URL address = new URL(_baseAddress + "all");
            String json = action.Get(address, HttpContentType.JSON);
            response = FacilityResponse.FromJson(json);
            method.HadResponse(response);
            method.PerformWaitIfNeeded();
            method.ThrowErrorFromResponseIfAppropriate();
        } while (method.ShouldRetry());

        return response.facilityResponse;
    }

    public Facility[] getFacility(int id) throws Exception {
        ClientAction action = new ClientAction();
        URL address = new URL(_baseAddress + new Integer(id).toString());
        String json = action.Get(address, HttpContentType.JSON);
        FacilityResponse response = FacilityResponse.FromJson(json);
        ThrowFor(response);
        return response.facilityResponse;
    }

    public DefaultResponse addFacility(String parms) throws Exception {
        ClientAction action = new ClientAction();
        URL address = new URL(_baseAddress + parms);
        String json = action.Get(address, HttpContentType.JSON);
        DefaultResponse response = DefaultResponse.FromJson(json);
        ThrowFor(response);
        return response;
    }

    public DefaultResponse deleteFacility(String parms) throws Exception {
        ClientAction action = new ClientAction();
        URL address = new URL(_baseAddress + parms);
        String json = action.Get(address, HttpContentType.JSON);
        DefaultResponse response = DefaultResponse.FromJson(json);
        ThrowFor(response);
        return response;
    }

    private void ThrowFor(DefaultResponse response) {
        if(!response.Success) {
            if(response.StackTrace.length() == 0) {
                throw new RuntimeException(response.Message);
            } else {
                throw new RuntimeException(response.Message + "\r\n" + response.StackTrace);
            }
        }
    }
}
