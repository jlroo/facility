package edu.luc.cs439.system.client.action;

import edu.luc.cs439.system.client.clients.FacilityClient;
import edu.luc.cs439.system.client.retries.ExponentialBackoffRetryPolicy;
import java.util.ArrayList;
import java.util.List;

public class ClientCrudActionRun implements Runnable {
	private final List<Exception> _errors;

	private int _low;
	private int _high;

	private String _clientRequest;
    private String _parameters;
    private String _baseURL;
    //private String _baseURL = "http://localhost:8080/facility/";

    public void SetClientRequest(String clientRequest){ _clientRequest = clientRequest;}
    public void SetParameters(String parameters){ _parameters = parameters;}
    public void SetBaseURL(String baseURL) {_baseURL = baseURL;}

    public ClientCrudActionRun(List<Exception> errors) {
		_low = 0;
		_high = 0;
		_errors = errors;
	}
	
	public ClientCrudActionRun() {
		_low = 0;
		_high = 0;
		_errors = new ArrayList<Exception>();
	}

	public void SetIdRange(int low, int high) {
		_low = low;
		_high = high;
	}

	//@Override
	public void run() {
		if(_low >= _high) {
			throw new RuntimeException("id range is not set");
		}
		try {
		    if(_clientRequest=="GET"){
                for(int i = _low; i < _high; i++) {
                    FacilityClient action = new FacilityClient(_baseURL, new ExponentialBackoffRetryPolicy());
                    action.getFacility(i);
                }
            }
            if (_clientRequest=="ADD"){
                for(int i = _low; i < _high; i++) {
                    FacilityClient action = new FacilityClient(_baseURL, new ExponentialBackoffRetryPolicy());
                    action.addFacility("add?id="+i+"&"+_parameters);
                }
            }
            if(_clientRequest=="DELETE"){
                for(int i = _low; i < _high; i++) {
                    FacilityClient action = new FacilityClient(_baseURL, new ExponentialBackoffRetryPolicy());
                    action.deleteFacility("delete/"+i);
                }
            }


		} catch(Exception e) {
			synchronized(_errors){
				_errors.add(e);
			}
		}
	}

}
