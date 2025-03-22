package SchedulingComponents;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import BatchJobsComponents.BatchJob;

public class SchedulingPolicy {
    /**
     * FCFS scheduling implementation
     * @param jobQueue
     * @return fcfsQueue
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
     * @return sjfQueue
     */
    public BlockingQueue<BatchJob> sjf_scheduling(BlockingQueue<BatchJob> jobQueue) {
        List<BatchJob> jobList = new ArrayList<>();
        jobQueue.drainTo(jobList);
        Collections.sort(jobList, (job1, job2) -> job1.getExecutionTime() - job2.getExecutionTime());
        BlockingQueue<BatchJob> sjfQueue = new LinkedBlockingQueue<>(jobList);


        return sjfQueue;
    }

    /**
     * Priority scheduling implementation
     * @param jobQueue
     * @return sjfQueue
     */
    public BlockingQueue<BatchJob> priority_scheduling(BlockingQueue<BatchJob> jobQueue) {
        List<BatchJob> jobList = new ArrayList<>();
        jobQueue.drainTo(jobList);
        Collections.sort(jobList, (job1, job2) -> job1.getPriority() - job2.getPriority());
        BlockingQueue<BatchJob> priorityQueue = new LinkedBlockingQueue<>(jobList);

        return priorityQueue;
    }
}
