package SchedulingComponents;
import BatchJobsComponents.BatchJob;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class SchedulingThread extends Thread {

    private static void errorHelperBatch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    private BlockingQueue<BatchJob> jobQueue;
    private String schedulingPolicyName;


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
    
    /**
     * Run the scheduling thread
     */
    public void run(String userInput) {
        SchedulingPolicy schedulingPolicy = new SchedulingPolicy();
        Scanner sc = new Scanner(System.in);
        //Add jobs to the queue
        // String userInput = sc.nextLine();
        String[] jobDetails = userInput.split("\\s+");
        if(!userInput.equals("exit")) {
            if(jobDetails.length == 3) {
                BatchJob job = new BatchJob(jobDetails[0], Integer.parseInt(jobDetails[1]), Integer.parseInt(jobDetails[2]));
                try {
                    jobQueue.put(job);
                    System.out.println("Job " + job.getJobName() + " was submitted");
                    System.out.println("Total number of jobs in the queue: " + jobQueue.size());
                    System.out.println("Expected waiting time: ");
                    System.out.println("Scheduling Policy" + getSchedulingPolicy());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
            } else {
                System.out.println("Invalid input. Please enter job name, execution time and priority separated by space.");
            }
        }

        sc.close();

        //Schedule jobs based on the scheduling policy
        if(schedulingPolicyName.equals("FCFS")) {
            BlockingQueue<BatchJob> fcfsQueue = schedulingPolicy.fcfs_scheduling(jobQueue);
            this.setJobQueue(fcfsQueue);
        } else if(schedulingPolicyName.equals("SJF")) {
            BlockingQueue<BatchJob> sjfQueue = schedulingPolicy.sjf_scheduling(jobQueue);
            this.setJobQueue(sjfQueue);
        } else if(schedulingPolicyName.equals("Priority")) {
            BlockingQueue<BatchJob> priority = schedulingPolicy.priority_scheduling(jobQueue);
            this.setJobQueue(priority);
        }
    }
    
}