package SchedulingComponents;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import BatchJobsComponents.BatchJob;

public class SchedulingPolicy {
    /**
     * FCFS scheduling
     */
    public BlockingQueue<BatchJob> fcfs_scheduling(BlockingQueue<BatchJob> jobQueue) {
        //Create thread-safe queue that maintains the order of jobs
        BlockingQueue<BatchJob> fcfsQueue = new LinkedBlockingQueue<>();
        SchedulingThread schedulingThread = new SchedulingThread(fcfsQueue, "FCFS");
        schedulingThread.start();

        for (BatchJob job : jobQueue) {
            fcfsQueue.add(job);
        }
        return fcfsQueue;
    }

    /**
     * SJF scheduling implementation
     * @param jobQueue
     * @return
     */
    public BlockingQueue<BatchJob> sjf_scheduling(BlockingQueue<BatchJob> jobQueue) {
        BlockingQueue<BatchJob> sjfQueue = new LinkedBlockingQueue<>();
        SchedulingThread schedulingThread = new SchedulingThread(sjfQueue, "sjf");
        schedulingThread.start();

        //Get the shortest job from the queue
        while (!jobQueue.isEmpty()) {
            BatchJob shortestJob = null;
            for (BatchJob job : jobQueue) {
                //Set the first job as the shortest job sicne it shortest job is null
                if (shortestJob == null){
                    shortestJob = job;
                }
                if (job.getExecutionTime() < shortestJob.getExecutionTime()) {
                    shortestJob = job;
                }
            }
            
            sjfQueue.add(shortestJob);
        }
        

        //Add jobs to the queue based on their execution time
        for (BatchJob job : jobQueue) {
            
            for (BatchJob jobInQueue : sjfQueue) {
                if (job.getExecutionTime() < jobInQueue.getExecutionTime()) {
                    sjfQueue.add(job);
                }
            }
           
        }

        return sjfQueue;
    }

    public BlockingQueue<BatchJob> priority_scheduling(BlockingQueue<BatchJob> jobQueue) {
        BlockingQueue<BatchJob> priorityQueue = new LinkedBlockingQueue<>();
        SchedulingThread schedulingThread = new SchedulingThread(priorityQueue, "priority");
        schedulingThread.start();

        //Get the highest priority job from the queue
        while (!jobQueue.isEmpty()) {
            BatchJob highestPriorityJob = null;
            for (BatchJob job : jobQueue) {
                //Set the first job as the highest priority job since it highest priority job is null
                if (highestPriorityJob == null){
                    highestPriorityJob = job;
                }
                if (job.getExecutionTime() < highestPriorityJob.getExecutionTime()) {
                    highestPriorityJob = job;
                }
            }
            
            priorityQueue.add(highestPriorityJob);
        }
        

        //Add jobs to the queue based on their execution time
        for (BatchJob job : jobQueue) {
            
            for (BatchJob jobInQueue : priorityQueue) {
                if (job.getExecutionTime() < jobInQueue.getExecutionTime()) {
                    priorityQueue.add(job);
                }
            }
           
        }
        return priorityQueue;
    }
}
