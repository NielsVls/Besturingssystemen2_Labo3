package Algorithms;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import Support.Process;

public class HighestResponseRatioNext extends Scheduler {

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input) {
        Queue<Process> queue = new LinkedList<>();
        PriorityQueue<Process> voltooid = new PriorityQueue<>();
        PriorityQueue<Process> wachtende = new PriorityQueue<>(10, Comparator.comparingDouble(Process::getResponsRatio));
        int tijdslot = 0;
        Process hulp;

        //adding all the processes to the queue
        for (Process process : input) {
            queue.add(new Process(process));
        }

        //repeating algorithm until queue is empty
        while(voltooid.size()!= input.size()){

            while(queue.peek() != null && queue.peek().getAankomsttijd()<= tijdslot){
                wachtende.add(queue.poll());
            }

            for (Process p : wachtende){
                double rr=(p.getServicetijdNodig()+(tijdslot -p.getAankomsttijd())/(p.getServicetijdNodig()));
                p.setResponsRatio(rr);
            }

            if (!wachtende.isEmpty()) {

                hulp=wachtende.poll();
                hulp.setStarttijd(tijdslot);
                tijdslot += hulp.getServicetijd();
                hulp.setEindtijd(tijdslot);
                hulp.calculate();
                wachttijd += hulp.getWachttijd();
                normomlooptijd += hulp.getNormomlooptijd();
                omlooptijd += hulp.getOmlooptijd();
                voltooid.add(hulp);

            }else {
                tijdslot++;
            }
        }

        wachttijd = wachttijd / input.size();
        normomlooptijd = normomlooptijd / input.size();
        omlooptijd = omlooptijd / input.size();

        System.out.println("HRRN: \tWachttijd: " + wachttijd + "\tGenorm. Omlooptijd: " + normomlooptijd + "\tOmlooptijd: " + omlooptijd);
        return voltooid;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q, int slice) {
        return schedule(q);
    }

}