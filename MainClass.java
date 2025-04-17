
import BatchJobsComponents.BatchJob;
import DispatchingComponents.DispatchingThread;
import SchedulingComponents.SchedulingThread;
import Ux.UIHelper;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MainClass {
    
    public static void main(String[] args) {
        // Initialize necessary components
        UIHelper helper = new UIHelper(); 
        BlockingQueue<BatchJob> jobQueue = new LinkedBlockingQueue<>();
        DispatchingThread dispatchingThread = new DispatchingThread(jobQueue, 0, "FCFS"); // Default policy: FCFS
        SchedulingThread schedulingThread = new SchedulingThread(jobQueue, "FCFS");
        
        // Welcome Message
        System.out.println("Welcome to CSU BatchJob Scheduler!");
        System.out.println("Enter 'help' for available commands.");
        
        // Start the schedulingThread
        schedulingThread.start();

        // User Input Loop
        try (Scanner keyboardScanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Enter command: "); // Prompt user
                String command = keyboardScanner.nextLine();

                if (command.equalsIgnoreCase("quit")) {
                    break; // Exit the program on "quit"
                }
                
                // Pass command to UIHelper
                helper.commandFunction(command, dispatchingThread, schedulingThread);
            }
        }

        // Exit Message and Final Summary
        System.out.println("Exiting CSU BatchJob Scheduler...");
        System.out.println("Total number of jobs submitted: " + schedulingThread.getTotalJobs());
        System.out.printf("Average turnaround time: %.2f seconds\n", dispatchingThread.getAverageTurnaroundTime());
        System.out.printf("Average CPU time: %.2f seconds\n", dispatchingThread.getAverageCpuTime());
        System.out.printf("Average waiting time: %.2f seconds\n", dispatchingThread.getAverageWaitingTime());
        System.out.printf("Throughput: %.2f jobs/second\n", dispatchingThread.calculateThroughput());
    }
}