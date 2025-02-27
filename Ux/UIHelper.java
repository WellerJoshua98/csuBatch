package Ux;
public class UIHelper {
    
    public void commandFunction(String command){
        if(command.equals("exit")){
            System.out.println("Good-bye");
        } else if (command.equals("help")){
            help();
        } else if (command.contains("run")){
            run(command);
        } else if (command.equals("list")) {
            list();
        } else if (command.contains("test")) {
            test();
        } else {
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
    public void run(String job) {
        String[] words = job.split("\\s+");
        if(words.length > 4){
            errorHelperBatch();
        }
        String[] jobQueueInfo = new String[3];
        System.out.println("Job was submitted");
    }
    
    // TODO
    public void list() {
        System.out.println("display job status");
    }

    public void test(){
        System.out.println("run and display test");
    }

    public void errorHelperBatch(){
        System.out.println("The command you entered was not found, Please try again");
    }
    


}