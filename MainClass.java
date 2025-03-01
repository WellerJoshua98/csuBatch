
import BatchJobsComponents.BatchJob;
import SchedulingComponents.SchedulingThread;
import Ux.UIHelper;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MainClass {
    
    public static void main(String[] args) {
        UIHelper helper = new UIHelper();
        BlockingQueue<BatchJob> fcfs = new LinkedBlockingQueue<>();
        SchedulingThread fcfSchedulingThread  = new SchedulingThread(fcfs, "fcfs");

        System.out.println("Welcome to CSU BatchJob Please Enter a Command");
        System.out.println("Enter help for more options");

        Scanner keyboardScanner = new Scanner(System.in); // No try-with-resources here

        System.out.println("Enter lines of text. Press Enter after each line.");
        System.out.println("Type 'quit' (case-sensitive) to exit.");

        while (true) {
            String line = "";
            try {
                line = keyboardScanner.nextLine();

                if (line.equals("quit")) {
                    break; // Exit the loop if the user enters "quit"
                }
                helper.commandFunction(line, fcfSchedulingThread);
                System.out.println("Please Enter a new command");
            } catch (IllegalStateException e){
                System.out.println("Scanner closed, closing program");
                break;
            }
        }

        System.out.println("Exiting out CSUBatch");
        keyboardScanner.close(); // Close the scanner when done
    }
}