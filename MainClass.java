
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

        try (Scanner keyboard = new Scanner(System.in)) {
            String command = keyboard.nextLine();
            
            while(!command.equals("quit")){
                helper.commandFunction(command, fcfSchedulingThread);
                command = keyboard.nextLine();
            }
        }
    }
}