package SchedulingComponents;
import BatchJobsComponents.BatchJob;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SchedulingThread extends Thread {

    private static void errorHelperBatch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    private BlockingQueue<BatchJob> jobQueue;
    private String schedulingPolicyName;
    private int jobCount = 0;


    public int getTotalJobs(){
        return jobCount;
    }


    public SchedulingThread(BlockingQueue<BatchJob> jobQueue, String schedulingPolicyName) {
        this.jobQueue = jobQueue;
        this.schedulingPolicyName = schedulingPolicyName;
    }

    /**
     * Returns the job queue
     * @return
     */
    public BlockingQueue<BatchJob> getJobQueue() {
        return jobQueue;
    }

    /**
     * Sets the job queue
     * @param jobQueue
     */
    public void setJobQueue(BlockingQueue<BatchJob> jobQueue) {
        this.jobQueue = jobQueue;
    }

    /**
     * Returns the scheduling policy
     * @return
     */
    public String getSchedulingPolicy() {
        return schedulingPolicyName;
    }

    /**
     * Sets the scheduling policy
     * @param schedulingPolicy
     */
    public void setSchedulingPolicy(String schedulingPolicyName) {
        this.schedulingPolicyName = schedulingPolicyName;
    }

    public int getTotalTime(){
        int total = 0;
        for(BatchJob job: jobQueue){
            total += job.getExecutionTime();
        }
        return total;
    }
    
    /**
     * Run the scheduling thread
     */
    public void run(String userInput) {
        System.out.println("Scheduling thread started");
        SchedulingPolicy schedulingPolicy = new SchedulingPolicy();
        //Add jobs to the queue
        String[] jobDetails = userInput.split("\\s+");
        if(!userInput.equals("exit")) {
            if(jobDetails.length == 3) {
                LocalTime currentTime = LocalTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String formattedTime = currentTime.format(formatter);
                String status = "";
                if(jobQueue.isEmpty()){
                    status = "run";
                }
                BatchJob job = new BatchJob(status, 0, 0, formattedTime, status);
                try {
                    job = new BatchJob(jobDetails[0], Integer.parseInt(jobDetails[1]), Integer.parseInt(jobDetails[2]), formattedTime, status);
                    try {
                        jobQueue.put(job);
                        System.out.println("Job " + job.getJobName() + " was submitted");
                        System.out.println("Total number of jobs in the queue: " + jobQueue.size());
                        System.out.println("Expected waiting time: " + getTotalTime());
                        System.out.println("Scheduling Policy " + getSchedulingPolicy());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter job name, execution time and priority separated by space.");
                    System.out.println("Input for <pri> should be a number");

                    System.out.println("Were you trying to use the command run?");
                    System.out.println("Try run <job> <time> <pri>: submit a job named <job>");
                }
                
                
            } else {
                System.out.println("Invalid input. Please enter job name, execution time and priority separated by space.");
                System.out.println("Input for <time> should be a number");
                
                System.out.println("Were you trying to use the command run?");
                System.out.println("Try run <job> <time> <pri>: submit a job named <job>");
            }
        }

        // Schedule jobs based on the scheduling policy
        if(schedulingPolicyName.equals("FCFS")) {
            BlockingQueue<BatchJob> fcfsQueue = schedulingPolicy.fcfs_scheduling(jobQueue);
            this.setJobQueue(fcfsQueue);
        } else if(schedulingPolicyName.equals("SJF")) {
            BlockingQueue<BatchJob> sjfQueue = schedulingPolicy.sjf_scheduling(jobQueue);
            this.setJobQueue(sjfQueue);
        } else if(schedulingPolicyName.equals("Priority")) {
            System.out.println("Priority scheduling");
            BlockingQueue<BatchJob> priority = schedulingPolicy.priority_scheduling(jobQueue);
            this.setJobQueue(priority);
        }
    }

    public void printJobQueue() {
        System.out.println("Jobs in the queue:");
        for (BatchJob job : jobQueue) {
            System.out.println(job.getJobName()+  ", Priority: " + job.getPriority() + ", Execution time: " + job.getExecutionTime());
        }
    }

    public static void main(String[] args) {
        
    };
    
}