package Algorithms;

import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import Support.Process;

public class FirstComeFirstServed extends Scheduler {

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> queue) throws NullPointerException {

        Queue<Process> q = new LinkedList<>();
        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();
        int timeslot = 0;
        Process tmp;

        //adding all the processes to the queue
        for (Process p : queue) {
            q.add(new Process(p));
        }

        //repeating algorithm until queue is empty
        while (!q.isEmpty()) {
            tmp = q.poll();
            if (timeslot < tmp.getArrivaltime()) {

                timeslot = tmp.getArrivaltime() + tmp.getServicetime();
                tmp.setStarttime(tmp.getArrivaltime());

            } else {

                tmp.setStarttime(timeslot);
                timeslot += tmp.getServicetime();

            }

            tmp.setEndtime(timeslot);
            tmp.calculate();
            finishedProcesses.add(tmp);
            waittime += tmp.getWaittime();
            tatnorm += tmp.getTatnorm();
            tat += tmp.getTat();

        }

        //gemiddelde waarden berekenen
        waittime = waittime / queue.size();
        tatnorm = tatnorm / queue.size();
        tat = tat / queue.size();

        System.out.println("FCFS: \tWachttijd: " + waittime + "\tGenorm. Omlooptijd: " + tatnorm + "\tOmlooptijd: " + tat);
        return finishedProcesses;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q, int slice) {
        return schedule(q);
    }
}
