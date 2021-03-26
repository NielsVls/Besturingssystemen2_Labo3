package Algorithms;

import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import Support.Process;

public class FirstComeFirstServed extends Scheduler {

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input) throws NullPointerException {

        Queue<Process> queue = new LinkedList<>();
        PriorityQueue<Process> voltooid = new PriorityQueue<>();
        int tijdslot = 0;
        Process hulp;

        //adding all the processes to the queue
        for (Process process : input) {
            queue.add(new Process(process));
        }

        //repeating algorithm until queue is empty
        while (!queue.isEmpty()) {
            hulp = queue.poll();
            if (tijdslot < hulp.getAankomsttijd()) {

                tijdslot = hulp.getAankomsttijd() + hulp.getServicetijd();
                hulp.setStarttijd(hulp.getAankomsttijd());

            } else {

                hulp.setStarttijd(tijdslot);
                tijdslot += hulp.getServicetijd();

            }

            hulp.setEindtijd(tijdslot);
            hulp.calculate();
            voltooid.add(hulp);
            wachttijd += hulp.getWachttijd();
            normomlooptijd += hulp.getNormomlooptijd();
            omlooptijd += hulp.getOmlooptijd();

        }

        //gemiddelde waarden berekenen
        wachttijd = wachttijd / input.size();
        normomlooptijd = normomlooptijd / input.size();
        omlooptijd = omlooptijd / input.size();

        System.out.println("FCFS: \tWachttijd: " + wachttijd + "\tGenorm. Omlooptijd: " + normomlooptijd + "\tOmlooptijd: " + omlooptijd);
        return voltooid;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q, int slice) {
        return schedule(q);
    }
}
