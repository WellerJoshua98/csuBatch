package Ux;

import BatchJobsComponents.BatchJob;
import DispatchingComponents.DispatchingThread;
import PerformanceEvaluationComponents.PerformanceEvaluation;
import SchedulingComponents.SchedulingPolicy;
import SchedulingComponents.SchedulingThread;

/**
 * The UIHelper class serves as an interface between users and the batch scheduling 
 * system. Its primary role is to process user commands and trigger the 
 * corresponding operations.
 */
public class UIHelper {
    
    /**
     * Processes user input commands like run, list, test, help, and policy switching
     * commands (fcfs, sjf, priority)
     */
    public void commandFunction(String command, DispatchingThread dispatchingThread, SchedulingThread schedulingThread){
        if(command.equals("exit")){
            System.out.println("Enter quit to exit the program");
        } else if (command.equals("help")){
            help();
        } else if (command.contains("run")){
            run(command, schedulingThread, dispatchingThread);
        } else if (command.equals("list")) {
            list(schedulingThread);
        } else if (command.contains("test")) {
            test(command);
        } else if(command.contains("sjf")){
            switchPolicy("SJF");
        } else if(command.contains("fcfs")){
            switchPolicy("FCFS");
        } else if(command.contains("priority")){
            switchPolicy("Priority");
        } else {
            errorHelperBatch(command);
        }
    }
    
    /**
     * Lists detailed instructions for available commands, guiding users on how to 
     * interact with the system.
     */
    public void help() {
        System.out.println("run <job> <time> <pri>: submit a job named <job>");
        System.out.println("\texecution time is <time>,");
        System.out.println("\t\tpriority is <pri>.");
        System.out.println("list: display the job status.");
        System.out.println("fcfs: change the scheduling policy to FCFS.");
        System.out.println("sjf: change the scheduling policy to SJF.");
        System.out.println("priority: change the scheduling policy to priority.");
        System.out.println("test <benchmark> <policy> <num_of_jobs> <priority_levels>");
        System.out.println("\t<min_CPU_time> <max_CPU_time>");
        System.out.println("quit: exit CSUbatch");
    }

    /**
     * Extracts job details from the user command and delegates job submission to 
     * the SchedulingThread.
     */
    public void run(String job, SchedulingThread schedulingThread, DispatchingThread dispatchingThread) {
        String[] words = job.split("run ");
        schedulingThread.run(words[1], dispatchingThread);
    }

    /**
     * Retrieves and displays all jobs in the queue, along with their details 
     * (e.g., name, execution time, priority, arrival time, and progress).
     */
    public void list(SchedulingThread jobs) {
        System.out.println("Total numbe of jobs in the queue: " + jobs.getTotalJobs());
        System.out.println("Scheduling Policy: " + jobs.getName());
        System.out.println("Name CPU_Time Pri Arrival_time Progress");
        for(BatchJob job: jobs.getJobQueue()){
            System.out.println(job.toString());
        }
    }

    /**
     * Parses benchmarking parameters from the user input and runs performance 
     * evaluation tests using the PerformanceEvaluation class.
     */
    public void test(String userInput){
        String[] testParameters = userInput.split(" ");
        if(testParameters.length == 7){
            PerformanceEvaluation performanceEvaluation = new PerformanceEvaluation();
            performanceEvaluation.setPolicy(testParameters[2]);
            performanceEvaluation.setNumOfJobs(Integer.parseInt(testParameters[3]));
            performanceEvaluation.setPriorityLevels(Integer.parseInt(testParameters[4]));
            performanceEvaluation.setMinCPUTime(Integer.parseInt(testParameters[5]));
            performanceEvaluation.setMaxCPUTime(Integer.parseInt(testParameters[6]));
            performanceEvaluation.evaluation();
        } else {
            System.out.println("Invalid input. Use help -test for proper input format");
        }
    }

    /*
     * Switch the scheduling policy
     * @param policy
     */
    public void switchPolicy(String policy){
        DispatchingThread dispatchingThread = new DispatchingThread();
        SchedulingPolicy schedulingPolicy = new SchedulingPolicy();
        // Switch the scheduling policy
        switch (policy) {
            case "FCFS":
                dispatchingThread.setJobQueue(schedulingPolicy.fcfs_scheduling(dispatchingThread.getJobQueue()));
                break;
        
            case "SJF":
                dispatchingThread.setJobQueue(schedulingPolicy.sjf_scheduling(dispatchingThread.getJobQueue()));
                break;
            case "Priority":
                dispatchingThread.setJobQueue(schedulingPolicy.priority_scheduling(dispatchingThread.getJobQueue()));
                break;    
            default:
                break;
        }
        System.out.println("Scheduling policy is switched to " + policy + "All the " +  dispatchingThread.getJobQueue().size()  + " waiting jobs have been rescheduled.");
    }

    /**
     * Provides helpful suggestions if users enter an invalid command, 
     * assisting them in correcting their input.
     */
    public void errorHelperBatch(String command){
        if(command.startsWith("r")) {
            System.out.println("Were you trying to use the command run?");
            System.out.println("run <job> <time> <pri>: submit a job named <job>");
        } else if (command.startsWith("h")) {
            System.out.println("Were you trying to use the command help?");
        } else if (command.startsWith("l")){
            System.out.println("Were you trying to use the command list?");
        } else if (command.startsWith("t")){
            System.out.println("Were you trying to use the command test?");
        } else {
            System.out.println("The command you entered was not found, Please try again or type help to to see command options");
        }
    }
}