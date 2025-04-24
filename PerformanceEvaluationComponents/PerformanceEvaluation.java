package PerformanceEvaluationComponents;

import BatchJobsComponents.BatchJob;
import DispatchingComponents.DispatchingThread;
import SchedulingComponents.SchedulingPolicy;
import SchedulingComponents.SchedulingThread;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PerformanceEvaluation {
    private String benchMark;
    private String policy;
    private int numOfJobs;
    private int priorityLevels;
    private int minCPUTime;
    private int maxCPUTime;
    private BlockingQueue<BatchJob> jobs = new LinkedBlockingQueue<>();

    public PerformanceEvaluation(String benchMark, String policy, int numOfJobs, int priorityLevels, int minCPUTime, int maxCPUTime) {
        this.benchMark = benchMark;
        this.policy = policy;
        this.numOfJobs = numOfJobs;
        this.priorityLevels = priorityLevels;
        this.minCPUTime = minCPUTime;
        this.maxCPUTime = maxCPUTime;
    }

    public PerformanceEvaluation(){}

    public String getBenchMark() {
        return benchMark;
    }

    public void setBenchMark(String benchMark) {
        this.benchMark = benchMark;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public int getNumOfJobs() {
        return numOfJobs;
    }

    public void setNumOfJobs(int numOfJobs) {
        this.numOfJobs = numOfJobs;
    }

    public int getPriorityLevels() {
        return priorityLevels;
    }

    public void setPriorityLevels(int priorityLevels) {
        this.priorityLevels = priorityLevels;
    }

    public int getMinCPUTime() {
        return minCPUTime;
    }

    public void setMinCPUTime(int minCPUTime) {
        this.minCPUTime = minCPUTime;
    }

    public int getMaxCPUTime() {
        return maxCPUTime;
    }

    public void setMaxCPUTime(int maxCPUTime) {
        this.maxCPUTime = maxCPUTime;
    }

    public void generateJobs() {
        Random random = new Random();

        for(int i = 1; i <= numOfJobs; i++){
            int executionTime = random.nextInt(maxCPUTime - minCPUTime + 1) + minCPUTime;
            int priority = random.nextInt(priorityLevels) + 1;
            BatchJob job = new BatchJob("Job" + i, executionTime, priority, "0", "");
            jobs.add(job);
        }
    }

    public void evaluation(){
        generateJobs();
        //SchedulingPolicy schedulingPolicy = new SchedulingPolicy();
        DispatchingThread dispatchingThread = new DispatchingThread(jobs);
        SchedulingThread schedulingThread = new SchedulingThread(jobs, policy);
        dispatchingThread.setIsPerformanceEvaluation(true);

        schedulingThread.start();
        dispatchingThread.start();
        System.out.println("Performance Evaluation is running...");
        try{
            dispatchingThread.join();
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
