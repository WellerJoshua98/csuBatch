package Ux;

import BatchJobsComponents.BatchJob;
import SchedulingComponents.SchedulingThread;




public class UIHelper {
    
    public void commandFunction(String command, SchedulingThread fcfSchedulingThread){
        if(command.equals("exit")){
            System.out.println("Enter quit to exit the program");
        } else if (command.equals("help")){
            help();
        } else if (command.contains("run")){
            run(command, fcfSchedulingThread);
        } else if (command.equals("list")) {
            list(fcfSchedulingThread);
        } else if (command.contains("test")) {
            test();
        } else {
            errorHelperBatch(command);
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

    public void run(String job, SchedulingThread fcfSchedulingThread) {
        String[] words = job.split("run ");
        fcfSchedulingThread.run(words[1]);
    }
    
    public void list(SchedulingThread jobs) {
        System.out.println("Total numbe of jobs in the queue: ");
        System.out.println("Scheduling Policy: ");
        System.out.println("Name CPU_Time Pri Arrival_time Progress");
        for(BatchJob job: jobs.getJobQueue()){
            System.out.println(job.toString());
        }
    }

    public void test(){
        System.out.println("run and display test");
    }

    public void errorHelperBatch(String command){
        if(command.startsWith("r")) {
            System.out.println("Were trying to use the command run?");
            System.out.println("run <job> <time> <pri>: submit a job named <job>");
        } else if (command.startsWith("h")) {
            System.out.println("Were trying to use the command help?");
        } else if (command.startsWith("l")){
            System.out.println("Were trying to use the command list?");
        } else if (command.startsWith("t")){
            System.out.println("Were trying to use the command test?");
        } else {
            System.out.println("The command you entered was not found, Please try again or type help to to see command options");
        }
    }
    


}