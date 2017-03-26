package edu.luc.cs439.system.client.retries;

import edu.luc.cs439.system.facility.web.contracts.DefaultResponse;

public interface ISingleMethodPolicy {

	void HadResponse(DefaultResponse response);

	void PerformWaitIfNeeded() throws Exception;

	void ThrowErrorFromResponseIfAppropriate() throws Exception;

	boolean ShouldRetry();

}
