package edu.luc.cs439.system.client.action;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlroo on 3/27/17.
 */

public class ClientTraffic {

    public static String generateTraffic(String baseURL,
                                         String request,
                                         String parameters,
                                         int ClientThreadCount,
                                         int RunsClient) {

        List<Exception> errors = new ArrayList<Exception>();

        try{
            List<Thread> threads = new ArrayList<Thread>();
            try{
                try{

                    for(int i = 0; i < ClientThreadCount; i++) {
                        ClientCrudActionRun run = new ClientCrudActionRun(errors);
                        run.SetBaseURL(baseURL);
                        run.SetParameters(parameters);
                        run.SetClientRequest(request);
                        run.SetIdRange(i * RunsClient, i * RunsClient + RunsClient - 1);
                        Thread th = new Thread(run);
                        threads.add(th);
                    }
                } catch(Exception e) {
                    threads.clear();
                    throw new Exception(e.getMessage(), e);
                }
                for(Thread t : threads) {
                    t.start();
                }
            } finally {
                for(Thread t : threads) {
                    t.join();
                }
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
           e.printStackTrace(System.out);
        } finally {
            String response = "Number of request made: " + errors.size();
            return response;
        }
    }

}
