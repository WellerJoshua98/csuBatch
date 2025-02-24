package BatchJobsComponents;

public class BatchJob {
    private String jobName;
    private int executionTime;
    private int priority;

    public BatchJob(String jobName, int executionTime, int priority) {
        this.jobName = jobName;
        this.executionTime = executionTime;
        this.priority = priority;
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
