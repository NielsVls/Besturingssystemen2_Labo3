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
        PriorityQueue<Process> wachtende = new PriorityQueue<>(10, (a, b) -> a.getServicetijd() - b.getServicetijd());

        Process hulp;

        int tijdslot = 0;

        while(voltooid.size()!=input.size()){

            while(queue.peek() != null && queue.peek().getAankomsttijd()<=tijdslot)
                wachtende.add(queue.poll());

            if (!wachtende.isEmpty()) {

                hulp=wachtende.poll();

                hulp.setStarttijd(tijdslot);
                tijdslot += hulp.getServicetijd();
                hulp.setEindtijd(tijdslot);

                hulp.calculate();

                voltooid.add(hulp);

                wachttijd += hulp.getWachttijd();
                normomlooptijd += hulp.getNormomlooptijd();
                omlooptijd += hulp.getOmlooptijd();
            }else {
                tijdslot++;
            }

        }

        wachttijd = wachttijd / input.size();
        normomlooptijd = normomlooptijd / input.size();
        omlooptijd = omlooptijd / input.size();

        System.out.println("SJF: \tWachttijd: " + wachttijd + "\tGenorm. Omlooptijd: " + normomlooptijd + "\tOmlooptijd: " + omlooptijd);
        return voltooid;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q, int slice) {
        return schedule(q);
    }

}
