package SchedulingComponents;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

import BatchJobsComponents.BatchJob;

public class SchedulingThread extends Thread {
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
    
    public void run() {
        SchedulingPolicy schedulingPolicy = new SchedulingPolicy();
        Scanner sc = new Scanner(System.in);
        //Add jobs to the queue
        String userInput = sc.nextLine();
        String[] jobDetails = userInput.split(" ");
        if(!userInput.equals("exit")) {
            if(jobDetails.length == 3) {
                BatchJob job = new BatchJob(jobDetails[0], Integer.parseInt(jobDetails[1]), Integer.parseInt(jobDetails[2]));
                try {
                    jobQueue.put(job);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
            } else {
                System.out.println("Invalid input. Please enter job name, execution time and priority separated by space.");
            }
        }

        sc.close();

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

    public static void main(String[] args) {
        
    }
    
}