package Ux;

import SchedulingComponents.SchedulingPolicy;
import SchedulingComponents.SchedulingThread;
import DispatchingComponents.DispatchingThread;
import PerformanceEvaluationComponents.PerformanceEvaluation;




public class UIHelper {
    
    public void commandFunction(String command, SchedulingThread fcfSchedulingThread){
        if(command.equals("exit")){
            System.out.println("Good-bye");
        } else if (command.equals("help")){
            help();
        } else if (command.contains("run")){
            run(command, fcfSchedulingThread);
        } else if (command.equals("list")) {
            list();
        } else if (command.contains("test")) {
            test(command);
        } else if(command.contains("sjf")){
            switchPolicy("SJF");
        } else if(command.contains("fcfs")){
            switchPolicy("FCFS");
        } else if(command.contains("priority")){
            switchPolicy("Priority");
        }
         else {
            errorHelperBatch();
        }
    }
    
    public void help() {
        System.out.println("run <job> <time> <pri>: submit a job named <job>");
        System.out.println("\texecution time is <time>,");
        System.out.println("\t\tpriority is <pri>.");
        System.out.println("list: display the job status.");
        System.out.println("fcfs: change the scheduling policy to FCFS.");
        System.out.println("sjf: change the scheduling policy to SJF.");
        System.out.println("priority: change the scheduling policy to priority.");
        System.out.println("test <benchmark> <policy> <num_of_jobs> <priority_levels>");
        System.out.println("\t<min_CPU_time> <max_CPU_time>");
        System.out.println("quit: exit CSUbatch");
    }

    // TODO
    public void run(String job, SchedulingThread fcfSchedulingThread) {
        String[] words = job.split("run ");
        fcfSchedulingThread.run(words[1]);
    }
    
    // TODO
    public void list() {
        System.out.println("display job status");
    }

    public void test(String userInput){
        String[] testParameters = userInput.split(" ");
        if(testParameters.length == 7){
            PerformanceEvaluation performanceEvaluation = new PerformanceEvaluation();
            performanceEvaluation.setPolicy(testParameters[2]);
            performanceEvaluation.setNumOfJobs(Integer.parseInt(testParameters[3]));
            performanceEvaluation.setPriorityLevels(Integer.parseInt(testParameters[4]));
            performanceEvaluation.setMinCPUTime(Integer.parseInt(testParameters[5]));
            performanceEvaluation.setMaxCPUTime(Integer.parseInt(testParameters[6]));
            performanceEvaluation.evaluation();
        } else {
            System.out.println("Invalid input. Use help -test for proper input format");
        }
    }

    /*
     * Switch the scheduling policy
     * @param policy
     */
    public void switchPolicy(String policy){
        DispatchingThread dispatchingThread = new DispatchingThread();
        SchedulingPolicy schedulingPolicy = new SchedulingPolicy();
        // Switch the scheduling policy
        switch (policy) {
            case "FCFS":
                dispatchingThread.setJobQueue(schedulingPolicy.fcfs_scheduling(dispatchingThread.getJobQueue()));
                break;
        
            case "SJF":
                dispatchingThread.setJobQueue(schedulingPolicy.sjf_scheduling(dispatchingThread.getJobQueue()));
                break;
            case "Priority":
                dispatchingThread.setJobQueue(schedulingPolicy.priority_scheduling(dispatchingThread.getJobQueue()));
                break;    
            default:
                break;
        }
        System.out.println("Scheduling policy is switched to " + policy + "All the " +  dispatchingThread.getJobQueue().size()  + " waiting jobs have been rescheduled.");
    }

    public void errorHelperBatch(){
        System.out.println("The command you entered was not found, Please try again");
    }
    


}