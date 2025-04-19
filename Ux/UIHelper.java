package Ux;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
            switchPolicy("SJF", schedulingThread);
        } else if(command.contains("fcfs")){
            switchPolicy("FCFS", schedulingThread);
        } else if(command.contains("priority")){
            switchPolicy("Priority", schedulingThread);
        } else if(command.equals("quit")){
            onQuit(schedulingThread, dispatchingThread);
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
        String[] jobDetails = words[1].split("\\s+");
        
        if(jobDetails.length == 3){
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = currentTime.format(formatter);
            String status = "";
            if(schedulingThread.getJobQueue().isEmpty()){
                status = "run";
            }

            BatchJob newJob = new BatchJob(status, 0, 0, formattedTime, status);
            try{
                newJob = new BatchJob(jobDetails[0], Integer.parseInt(jobDetails[1]), Integer.parseInt(jobDetails[2]), formattedTime, status);
                schedulingThread.submitJob(newJob);
                System.out.println("Job " + newJob.getJobName() + " was submitted");
                System.out.println("Total number of jobs in the queue: " + schedulingThread.getTotalJobs());
                System.out.println("Expected waiting time: " + schedulingThread.getTotalExecutionTime() + " seconds");
                System.out.println("Scheduling Policy " + schedulingThread.getSchedulingPolicy());
            
            }catch(Exception e){
                System.out.println("Invalid input. Please enter job name, execution time and priority separated by space.");
                System.out.println("Input for <pri> should be a number");
                System.out.println("Were you trying to use the command run?");
                System.out.println("Try run <job> <time> <pri>: submit a job named <job>");
            }
        }else{
            System.out.println("Invalid input. Please enter job name, execution time and priority separated by space.");
            System.out.println("Input for <time> should be a number");
            
            System.out.println("Were you trying to use the command run?");
            System.out.println("Try run <job> <time> <pri>: submit a job named <job>");
        }

        //schedulingThread.run(words[1], dispatchingThread);
    }

    /**
     * Retrieves and displays all jobs in the queue, along with their details 
     * (e.g., name, execution time, priority, arrival time, and progress).
     */
    public void list(SchedulingThread jobs) {
        System.out.println("Total number of jobs in the queue: " + jobs.getTotalJobs());
        System.out.println("Scheduling Policy: " + jobs.getSchedulingPolicy());
        System.out.println("Name CPU_Time Pri Arrival_time Progress");
        for(BatchJob job: jobs.getSubmittedJobQueue()){
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
    public void switchPolicy(String policy, SchedulingThread schedulingThread) {
        schedulingThread.updatePolicy(policy);
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

    public void onQuit(SchedulingThread schedulingThread, DispatchingThread dispatchingThread) {
        System.out.println("Exiting CSU BatchJob Scheduler...");
        System.out.println("Total number of jobs submitted: " + schedulingThread.getTotalJobs());
        System.out.printf("Average turnaround time: %.2f seconds\n", dispatchingThread.getAverageTurnaroundTime());
        System.out.printf("Average CPU time: %.2f seconds\n", dispatchingThread.getAverageCpuTime());
        System.out.printf("Average waiting time: %.2f seconds\n", dispatchingThread.getAverageWaitingTime());
        System.out.printf("Throughput: %.2f jobs/second\n", dispatchingThread.calculateThroughput());
    }

}