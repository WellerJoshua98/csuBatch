package BatchJobsComponents;

public class BatchJob {
    private String jobName;
    private int executionTime;
    private int priority;
    private String arrivalTime;
    private String status;

    public BatchJob(String jobName, int executionTime, int priority, String arrivalTime, String status) {
        this.jobName = jobName;
        this.executionTime = executionTime;
        this.priority = priority;
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

    @Override
    public String toString() {
        return String.format("Job Name: %s, Execution Time: %d, Priority: %d, Arrival Time: %s, Status: %s",
                jobName, executionTime, priority, arrivalTime, status);
    }

    public void execute(){
        System.out.println("Executing job: " + jobName);
        
        //simulate job execution time
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
