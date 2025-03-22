package BatchJobsComponents;

public class BatchJob {
    private String jobName;
    private int executionTime;
    private int priority;
    private long submissionTime;
    private long completionTime;
    private long startTime;

    public BatchJob(String jobName, int executionTime, int priority) {
        this.jobName = jobName;
        this.executionTime = executionTime;
        this.priority = priority;
        this.submissionTime = System.currentTimeMillis();
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(long submissionTime) {
        this.submissionTime = submissionTime;
    }

    public long getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(long completionTime) {
        this.completionTime = completionTime;
    }

    public long getTurnaroundTime(){
        return completionTime - submissionTime;
    }

    /*
     * get the start time of the job
     * @return startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * set the start time of the job
     * @param startTime
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the wait time
     * @return waitTime
     */
    public long getWaitTime(){
        return startTime - submissionTime;
    }

    public void execute(){
        System.out.println("Executing job: " + jobName);
        
        //simulate job execution time
        try {
            Thread.sleep(1000  * executionTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        setCompletionTime(System.currentTimeMillis());
    }
}
