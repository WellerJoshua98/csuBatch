package BatchJobsComponents;

public class BatchJob {
    private String jobName;
    private int executionTime;
    private int priority;
    private long submissionTime;
    private long completionTime;
    private long startTime;
    private String arrivalTime;
    private String status;

    public BatchJob(String jobName, int executionTime, int priority, String arrivalTime, String status) {
        this.jobName = jobName;
        this.executionTime = executionTime;
        this.priority = priority;
        this.submissionTime = System.currentTimeMillis();
        this.arrivalTime = arrivalTime;
        this.status = status;
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

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getStatus() {
        return arrivalTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void execute(){
        //System.out.println("Executing job: " + jobName);
        
        //simulate job execution time
        try {
            Thread.sleep(1000  * executionTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        setCompletionTime(System.currentTimeMillis());
    }
}
