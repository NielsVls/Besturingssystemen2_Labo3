package Algorithms;

import java.util.PriorityQueue;
import java.util.Queue;
import Support.Process;

//abstract class we use to schedule the processes
public abstract class Scheduler {
    double tat;         //turnaround time= omlooptijd
    double tatnorm;     //genormaliseerde omlooptijd
    double waittime;    //wachttijd

    public abstract PriorityQueue<Process> schedule(Queue<Process> q);
    public abstract PriorityQueue<Process> schedule(Queue<Process> q, int slice);
}
