package Algorithms;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import Support.Process;

public class HighestResponseRatioNext extends Scheduler {

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> queue) {
        Queue<Process> q = new LinkedList<>();
        PriorityQueue<Process> finishedProcesses = new PriorityQueue<>();
        PriorityQueue<Process> waitingProcesses = new PriorityQueue<>(10, Comparator.comparingDouble(Process::getResponseRatio));
        int timeslot = 0;
        Process temp;

        //adding all the processes to the queue
        for (Process p : queue) {
            q.add(new Process(p));
        }

        //repeating algorithm until queue is empty
        while(finishedProcesses.size()!= queue.size()){

            while(q.peek() != null && q.peek().getArrivaltime()<= timeslot){
                waitingProcesses.add(q.poll());
            }

            for (Process p : waitingProcesses){
                double rr=(p.getServicetimeneeded()+(timeslot -p.getArrivaltime())/(p.getServicetimeneeded()));
                p.setResponseRatio(rr);
            }

            if (!waitingProcesses.isEmpty()) {

                temp=waitingProcesses.poll();
                temp.setStarttime(timeslot);
                timeslot += temp.getServicetime();
                temp.setEndtime(timeslot);
                temp.calculate();
                waittime += temp.getWaittime();
                tatnorm += temp.getTatnorm();
                tat += temp.getTat();
                finishedProcesses.add(temp);

            }else {
                timeslot++;
            }
        }

        waittime = waittime / queue.size();
        tatnorm = tatnorm / queue.size();
        tat = tat / queue.size();

        System.out.println("HRRN: \tWachttijd: " + waittime + "\tGenorm. Omlooptijd: " + tatnorm + "\tOmlooptijd: " + tat);
        return finishedProcesses;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q, int slice) {
        return schedule(q);
    }

}