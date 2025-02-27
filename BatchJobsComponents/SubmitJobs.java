package BatchJobsComponents;

public class SubmitJobs {
    public String[] submitAJob(String[] job){
        String[] ret = new String[3];
        int count = 1;
        String[] submission = new String[3];
        if(job.length != 4) {
            while(count < 4) {
                if(count > job.length - 1){
                    submission[count-1] = "0";
                } else {
                    submission[count-1] = job[count];
                }
            }
        }
        BatchJob batchJob = new BatchJob(submission[0], Integer.parseInt(submission[1]), Integer.parseInt(submission[2]));
        return ret;
    }
}
