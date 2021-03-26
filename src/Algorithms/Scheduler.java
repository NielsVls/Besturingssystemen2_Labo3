package Algorithms;

import java.util.PriorityQueue;
import java.util.Queue;
import Support.Process;


public abstract class Scheduler {
    double tat;
    double tatnorm;
    double waittime;

    public abstract PriorityQueue<Process> schedule(Queue<Process> q);
    public abstract PriorityQueue<Process> schedule(Queue<Process> q, int slice);
}
