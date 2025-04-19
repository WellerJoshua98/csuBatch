package SchedulingComponents;
import BatchJobsComponents.BatchJob;
import DispatchingComponents.DispatchingThread;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * The SchedulingThread class extends the Thread class and represents 
 * a thread for managing job scheduling in a batch system
 */
public class SchedulingThread extends Thread {

    private static void errorHelperBatch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    private BlockingQueue<BatchJob> jobQueue;
    private BlockingQueue<BatchJob> submittedJobsQueue = new LinkedBlockingQueue<>();
    private String schedulingPolicyName;
    private int jobCount = 0;
    private int totalExecutionTime = 0;
    private final List<BatchJob> jobBuffer = Collections.synchronizedList(new ArrayList<>());


    public SchedulingThread(BlockingQueue<BatchJob> jobQueue, String schedulingPolicyName) {
        this.jobQueue = jobQueue;
        this.schedulingPolicyName = schedulingPolicyName;
    }

    
    /**
     * Returns the total number of jobs.
     */
    public int getTotalJobs(){
        return jobCount;
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
     * Returns the submitted jobs queue
     * @return
     */
    public BlockingQueue<BatchJob> getSubmittedJobQueue(){
        return submittedJobsQueue;
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
     * Returns the total execution time of all jobs in the queue.
     * @return
     */
    public int getTotalExecutionTime() {
        return totalExecutionTime;
    }

    /**
     * Calculates the total execution time of jobs in the queue.
     */
    public int getTotalTime(){
        int total = 0;
        for(BatchJob job: jobQueue){
            total += job.getExecutionTime();
        }
        return total;
    }

    public void submitJob(BatchJob job) {
        try {
            jobQueue.put(job);
            submittedJobsQueue.put(job);
            jobCount++;
            totalExecutionTime += job.getExecutionTime();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void updatePolicy(String policy) {
        this.schedulingPolicyName = policy;
        reOrderQueue();
    }

    private void reOrderQueue(){
        List<BatchJob> jobList = new ArrayList<>();
        jobQueue.drainTo(jobList);

        BatchJob runningJob = DispatchingThread.getCurrentJob();
        List<BatchJob> waitingJobs = new ArrayList<>();
        
        for(BatchJob job: jobList){
            if(!job.equals(runningJob)){
                waitingJobs.add(job);
            }
        }

        if(schedulingPolicyName.equals("FCFS")){
            
        } else if(schedulingPolicyName.equals("SJF")){
            waitingJobs.sort(Comparator.comparingInt(BatchJob::getExecutionTime));
        } else if(schedulingPolicyName.equals("Priority")){
            waitingJobs.sort(Comparator.comparingInt(BatchJob::getPriority).reversed());
        }

        if(runningJob != null){
            jobQueue.add(runningJob);
        }
        jobQueue.addAll(waitingJobs);

        System.out.println("Scheduling policy is switched to " + schedulingPolicyName + " All the " +  waitingJobs.size()  + " waiting jobs have been rescheduled.");
    }
    
    private void scheduleJobs(){
        List<BatchJob> newJobs;
        synchronized (jobBuffer) {
            if(jobBuffer.isEmpty()) {
                return; // No new jobs to schedule
            }
            newJobs = new ArrayList<>(jobBuffer);
            jobBuffer.clear();
        }
        // Schedule jobs based on the scheduling policy
        
        if(schedulingPolicyName.equals("FCFS")) {

        } else if(schedulingPolicyName.equals("SJF")) {
            newJobs.sort(Comparator.comparingInt(BatchJob::getExecutionTime));
        } else if(schedulingPolicyName.equals("Priority")) {
            newJobs.sort(Comparator.comparingInt(BatchJob::getPriority).reversed());
        }
        
        for(BatchJob job : newJobs) {
            try {
                jobQueue.put(job);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    /**
     * Run the scheduling thread
     */
    @Override
    public void run() {
        
        while (!isInterrupted()) {
            scheduleJobs(); // Schedule jobs based on the scheduling policy
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
    }

    /**
     * Prints a summary of the job queue, including job names, 
     * priorities, and execution times.
     */

    public void printJobQueue() {
        System.out.println("Jobs in the queue:");
        for (BatchJob job : jobQueue) {
            System.out.println(job.getJobName()+  ", Priority: " + job.getPriority() + ", Execution time: " + job.getExecutionTime());
        }
    }
    
}