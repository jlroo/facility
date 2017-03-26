package edu.luc.cs439.system.client;

import edu.luc.cs439.system.client.action.ClientCrudActionRun;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientMain {

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

    public static void main(String[] args) throws Exception {

        boolean flag = false;

        do {
            System.out.println("\n Select an option from the menu:");
            System.out.println("\n \t 1) ADD Facility Client Traffic");
            System.out.println("\n \t 2) GET Facility Client Traffic");
            System.out.println("\n \t 3) DELETE Client Traffic");
            System.out.println("\n \t 4) GET/ADD/DELETE Client Traffic");
            System.out.println("\n \t 5) Quit");

            Scanner scanner = new Scanner(System.in);
            int option = Integer.parseInt(scanner.next());

            switch(option) {
                case 1:

                    System.out.println("\n \t Number of Clients: ");
                    int counter  = Integer.parseInt(scanner.next());

                    System.out.println("\n \t Clients ID Runs: ");
                    int clients  = Integer.parseInt(scanner.next());

                    String parms = "room=101&media=false&capacity=10&name=spring&phone=345323452&dept=cs&occupied=true&date=2017-03-05";
                    generateTraffic("http://localhost:8080/facility/","ADD",parms,clients,counter);

                    break;
                case 2:

                    System.out.println("\n \t Number of Clients: ");
                    int counter2  = Integer.parseInt(scanner.next());

                    System.out.println("\n \t Clients ID Runs: ");
                    int clients2  = Integer.parseInt(scanner.next());
                    generateTraffic("http://localhost:8080/facility/","GET","",clients2,counter2);

                    break;
                case 3:

                    System.out.println("\n \t Number of Clients: ");
                    int counter3  = Integer.parseInt(scanner.next());

                    System.out.println("\n \t Clients ID Runs: ");
                    int clients3  = Integer.parseInt(scanner.next());

                    generateTraffic("http://localhost:8080/facility/","DELETE","",clients3,counter3);
                    break;
                case 4:
                     System.out.println("\n \t Number of Clients: ");
                     int counter4  = Integer.parseInt(scanner.next());

                     System.out.println("\n \t Clients ID Runs: ");
                     int clients4  = Integer.parseInt(scanner.next());
                    generateTraffic("http://localhost:8080/facility/","DELETE","",clients4,counter4);
                    break;

                case 5:
                    flag = true;
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }while (flag==false);
    }
}
