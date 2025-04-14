# CSUBatch

The goal is to create a batch scheduling system called CSUbatch, which includes two threads: a scheduling thread (to enforce scheduling policies) and a dispatching thread (to execute submitted jobs). Synchronization between these threads must be handled using condition variables and mutexes. You can view the scheduling and dispatching threads as a producer-consumer model.

Three scheduling policies need to be implemented:

First Come, First Served (FCFS)

Shortest Job First (SJF)

Priority-Based Scheduling

You will also compare the performance of these policies under different workloads and create a user interface for invoking the system's services.

## Description

An in-depth paragraph about your project and overview of use.

## Getting Started

### Dependencies
java.time -> A powerful package for handling dates, times, and durations. It provides a modern and intuitive way to work with time-related data, replacing the older java.util.Date and java.util.Calendar classes.

java.util -> The java.util package in Java is a versatile collection of utility classes and interfaces that support various functionalities. We used that use collections, and concurrency. 


### Installing
change you directory to inside the csuBatch file that you cloned from github.

Open terminal on your computer

run `Javac .\MainClass.java`



### Executing program

* run `java MainClass`
* Below are the commands for the program.
```
run: Will submit a job
 -> run <job> <time> <pri>
list: display the job status.
 -> list
fcfs: change the scheduling policy to FCFS.
sjf: change the scheduling policy to SJF.
priority: change the scheduling policy to priority.
test: Will run the thread to view benchmark results of the jobs and threads
 -> test <benchmark> <policy> <num_of_jobs> <priority_levels>
quit: exit CSUbatch
```

## Help

Any advise for common problems or issues.
```
```

## Authors

Contributors names and contact info
    Joshua Weller
    Tiara Gibson
    David O'Keefe


