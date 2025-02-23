public class BatchJob {
    private String jobName;

    public BatchJob(String jobName){
        this.jobName = jobName;
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
