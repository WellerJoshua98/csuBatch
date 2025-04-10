
import BatchJobsComponents.BatchJob;
import DispatchingComponents.DispatchingThread;
import Ux.UIHelper;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MainClass {
    
    public static void main(String[] args) {
        UIHelper helper = new UIHelper();
        BlockingQueue<BatchJob> jobs = new LinkedBlockingQueue<>();
        DispatchingThread dispatchingThread = new DispatchingThread(jobs, 0, "priority");

        System.out.println("Welcome to CSU BatchJob Please Enter a Command");
        System.out.println("Enter help for more options");
        dispatchingThread.run();

        Scanner keyboardScanner = new Scanner(System.in); // No try-with-resources here

        while (true) {
            String line = "";
            try {
                line = keyboardScanner.nextLine();

                if (line.equals("quit")) {
                    break; // Exit the loop if the user enters "quit"
                }
                helper.commandFunction(line, dispatchingThread.getSchedulingThread());
                System.out.println("Please Enter a new command");
            } catch (IllegalStateException e){
                System.out.println("Scanner closed, closing program");
                break;
            }
        }

        System.out.println("Exiting out CSUBatch");
        System.out.println("Total number of job submitted: " + dispatchingThread.getSchedulingThread().getTotalJobs());
        System.out.println("Average turnaround time:       ");
        System.out.println("Average CPU time:              ");
        System.out.println("Average waiting time:          ");
        System.out.println("Throughput:                    ");

        keyboardScanner.close(); // Close the scanner when done
    }
}