package DispatchingComponents;

import BatchJobsComponents.BatchJob;
import SchedulingComponents.SchedulingThread;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * The DispatchingThread class extends the Thread class and represents a 
 * thread responsible for managing and executing jobs in the batch system.
 */
public class DispatchingThread extends Thread {
    private SchedulingThread schedulingThread;
    private BlockingQueue<BatchJob> jobQueue;
    private int executionTime;
    private List<Long> cpuTimes = new ArrayList<>();
    private List<Long> turnaroundTimes = new ArrayList<>();
    private List<Long> waitingTimes = new ArrayList<>();
    private ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    private double averageCpuTime;
    private double averageTurnaroundTime;
    private double averageWaitingTime;
    private long startTime;
    private int completedJobs = 0;
    private double throughput;
    private String policy;
    private static volatile BatchJob currentJob = null;
    private boolean isPerformamaceEvaluation = false;

    public DispatchingThread(BlockingQueue<BatchJob> jobQueue, int executionTime) {
        this.jobQueue = jobQueue;
        this.executionTime = executionTime;

        schedulingThread = new SchedulingThread(jobQueue, policy);

        //Enables the thread CPU time measurement
        // if(!threadMXBean.isThreadCpuTimeEnabled()){
        //     threadMXBean.setThreadCpuTimeEnabled(true);
        // }

        
        if (threadMXBean.isCurrentThreadCpuTimeSupported()) {
            threadMXBean.setThreadCpuTimeEnabled(true); // Enable CPU time measurement
        } else {
            System.out.println("CPU time measurement is not supported.");
        }
        
    }

    public DispatchingThread(BlockingQueue<BatchJob> jobQueue, int executionTime, String policy) {
        this.jobQueue = jobQueue;
        this.executionTime = executionTime;
        this.policy = policy;

        //Enables the thread CPU time measurement
        // if(!threadMXBean.isThreadCpuTimeEnabled()){
        //     threadMXBean.setThreadCpuTimeEnabled(true);
        // }

        
        if (threadMXBean.isCurrentThreadCpuTimeSupported()) {
            threadMXBean.setThreadCpuTimeEnabled(true); // Enable CPU time measurement
        } else {
            System.out.println("CPU time measurement is not supported.");
        }
        
    }

    public DispatchingThread(BlockingQueue<BatchJob> jobQueue) {
        this.jobQueue = jobQueue;
        
        if (threadMXBean.isCurrentThreadCpuTimeSupported()) {
            threadMXBean.setThreadCpuTimeEnabled(true); // Enable CPU time measurement
        } else {
            System.out.println("CPU time measurement is not supported.");
        }
        
    }

    public DispatchingThread(){

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
     * Return the cpu times from the jobs
     * @return cpuTimes
     */
    public List<Long> getCpuTimes() {
        return cpuTimes;
    }

    /**
     * Sets the cpu times from the jobs
     * @param cpuTimes
     */
    public void setCpuTimes(List<Long> cpuTimes) {
        this.cpuTimes = cpuTimes;
    }

    /**
     * Returns the average cpu time
     * @return
     */
    public double getAverageCpuTime() {
        return averageCpuTime;
    }

    /**
     * Sets the average cpu time
     * @param averageCpuTime
     */
    public void setAverageCpuTime(double averageCpuTime) {
        this.averageCpuTime = averageCpuTime;
    }

    /**
     * Returns  the average turnaround time
     * @return averageTurnaroundTime
     */
    public double getAverageTurnaroundTime() {
        return averageTurnaroundTime;
    }


    /*
     * Sets the average turnaround time
     * @param averageTurnaroundTime
     */
    public void setAverageTurnaroundTime(double averageTurnaroundTime) {
        this.averageTurnaroundTime = averageTurnaroundTime;
    }

    /**
     * Returns the average waiting time
     * @return averageWaitingTime
     */
    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }

    /**
     * Sets the average waiting time
     * @param averageWaitingTime
     */
    public void setAverageWaitingTime(double averageWaitingTime) {
        this.averageWaitingTime = averageWaitingTime;
    }

    /**
     * Returns the current job
     * @return currentJob
     */
    public static BatchJob getCurrentJob() {
        return currentJob;
    } 

    public void setIsPerformanceEvaluation(boolean isPerformamaceEvaluation){
        this.isPerformamaceEvaluation = isPerformamaceEvaluation;
    }

    /**
     * Returns the average turnaround times from the jobs
     * @return turnaroundTimes
     */
    public double calculateAverageCpuTime(){
        return cpuTimes.stream()
        .mapToLong(Long::longValue)
        .average()
        .orElse(0.0)/1_000_000_000.0;
    }

    /**
     * Returns the average turnaround times from the jobs
     * @return turnaroundTimes
     */
    public double calculateAverageTurnaroundTime(){
        return turnaroundTimes.stream()
        .mapToLong(Long::longValue)
        .average()
        .orElse(0.0)/1000.0;
    }

    /**
     * Returns the average waiting times from the jobs
     * @return waitingTimes
     */
    public double calculateAverageWaitingTime(){
        return waitingTimes.stream()
        .mapToLong(Long::longValue)
        .average()
        .orElse(0.0)/1000.0;
    }

    public double calculateThroughput(){
        long endTime = System.currentTimeMillis();
        double elapsedTime = (endTime - startTime)/ 1000.0;

        return completedJobs/elapsedTime;
    }

    /**
     * Run the dispatching thread
     */
    @Override
    public void run(){
        try {
            int jobCount = jobQueue.size();
            int jobExecutionTime = 0;
            this.startTime = System.currentTimeMillis();
            //Retrieves and removes the head of this queue and executes the job
            while(!interrupted()){
                currentJob = jobQueue.take();

                //Set the start time of the job
                long startTime = System.currentTimeMillis();
                currentJob.setStartTime(startTime);

                //Capture start cpu time 
                long startCpuTime = threadMXBean.getCurrentThreadCpuTime();

                //Calculate and store waiting time
                waitingTimes.add(currentJob.getWaitTime());

                currentJob.execute();

                completedJobs++;

                turnaroundTimes.add(currentJob.getTurnaroundTime());

                long endCpuTime = threadMXBean.getCurrentThreadCpuTime();
                
                long cpuTime = endCpuTime - startCpuTime;
                
                cpuTimes.add(cpuTime);

                jobExecutionTime += currentJob.getExecutionTime();

                currentJob = null; // Clear the current job reference
                if(Thread.interrupted()){
                    throw new InterruptedException();
                }

                //Calculate the average cpu time
                if(!cpuTimes.isEmpty()){
                    setAverageCpuTime(calculateAverageCpuTime());
                }

                //Calculate the average turnaround time
                if(!turnaroundTimes.isEmpty()){
                    setAverageTurnaroundTime(calculateAverageTurnaroundTime());
                }

                //Calculate the average waiting time
                if(!waitingTimes.isEmpty()){
                    setAverageWaitingTime(calculateAverageWaitingTime());
                }

                //Calculate throughput
                if(completedJobs > 0){
                    this.throughput = calculateThroughput();
                }

                if(isPerformamaceEvaluation){
                    if(jobQueue.isEmpty()){
                        Thread.currentThread().interrupt();
                    }
                }
            }

            //long endTime = System.nanoTime();
            //long elapsedTime = (endTime - startTime)/ 1000000000;
            if(isPerformamaceEvaluation){
                System.out.println("Total number of jobs submitted: " + jobCount);
                System.out.println("Average turnaround time: " + String.format("%.2f", getAverageTurnaroundTime()) + " seconds");
                System.out.println("Average CPU time: " + getAverageCpuTime() + " seconds");
                System.out.println("Average waiting time: " + String.format("%.2f", getAverageWaitingTime()) + " seconds");
                System.out.println("Throughput: " + String.format("%.3f", throughput) + " No ./second");
            }  
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
