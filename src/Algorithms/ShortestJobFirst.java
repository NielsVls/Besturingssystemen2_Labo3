package Algorithms;

import Support.Process;

import java.util.*;

public class ShortestJobFirst extends Scheduler {

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input) {
        Queue<Process> queue = new LinkedList<>();

        for (Process process : input) {
            queue.add(new Process(process));
        }


        //Verschillende que's aand de hand van waar het proces zich bevindt
        PriorityQueue<Process> voltooid = new PriorityQueue<>();
        PriorityQueue<Process> wachtende = new PriorityQueue<>(10, (a, b) -> a.getServicetime() - b.getServicetime());

        Process hulp;

        int tijdslot = 0;

        while(voltooid.size()!=input.size()){

            while(queue.peek() != null && queue.peek().getArrivaltime()<=tijdslot)
                wachtende.add(queue.poll());

            if (!wachtende.isEmpty()) {

                hulp=wachtende.poll();

                hulp.setStarttime(tijdslot);
                tijdslot += hulp.getServicetime();
                hulp.setEndtime(tijdslot);

                hulp.calculate();

                voltooid.add(hulp);

                waittime += hulp.getWaittime();
                tatnorm += hulp.getTatnorm();
                tat += hulp.getTat();
            }else {
                tijdslot++;
            }

        }

        waittime = waittime / input.size();
        tatnorm = tatnorm / input.size();
        tat = tat / input.size();

        System.out.println("SJF: \tWachttijd: " + waittime + "\tGenorm. Omlooptijd: " + tatnorm + "\tOmlooptijd: " + tat);
        return voltooid;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q, int slice) {
        return schedule(q);
    }

}
