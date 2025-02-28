package DispatchingComponents;

import java.util.concurrent.BlockingQueue;
import BatchJobsComponents.BatchJob;
import SchedulingComponents.SchedulingThread;

public class DispatchingThread {
    private SchedulingThread schedulingThread;
    private BlockingQueue<BatchJob> jobQueue;
    private int executionTime;

    public DispatchingThread(BlockingQueue<BatchJob> jobQueue, int executionTime) {
        this.jobQueue = jobQueue;
        this.executionTime = executionTime;
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
     * Returns the execution time
     * @return
     */
    public int getExecutionTime() {
        return executionTime;
    }

    /**
     * Sets the execution time
     * @param executionTime
     */
    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }    

    /**
     * Run the dispatching thread
     */
    public void run(){
        try {
            int jobCount = jobQueue.size();
            int jobExecutionTime = 0;
            //Retrieves and removes the head of this queue and executes the job
            while(!jobQueue.isEmpty()){
                BatchJob job = jobQueue.poll();
                job.execute();
                jobExecutionTime += job.getExecutionTime();
                if(Thread.interrupted()){
                    throw new InterruptedException();
                }
            }

            System.out.println("Total number of jobs executed: " + jobCount);
            System.out.println("Expected waiting time: " + jobExecutionTime + " seconds");
            System.out.println("Scheduling Policy: " + schedulingThread.getSchedulingPolicy());
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted. Exiting...");
        }
    }

    public static void main(String[] args) {
        
    }
}
