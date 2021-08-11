package Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import Support.Process;

public class MultiHRRN extends Scheduler {

    int aantal;
    ArrayList<Integer> processors;
    
    public MultiHRRN(int aantal){this.aantal = aantal;
        processors = new ArrayList<Integer>();
        for (int i = 0;i<aantal;i++){
            processors.add(i,0);
        }
   }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input) {
        Queue<Process> queue = new LinkedList<>();
        PriorityQueue<Process> voltooid = new PriorityQueue<>();
        PriorityQueue<Process> wachtende = new PriorityQueue<>(10, Comparator.comparingDouble(Process::getResponsRatio));
        Process hulp;

        //adding all the processes to the queue
        for (Process process : input) {
            queue.add(new Process(process));
        }

        int Mintijdslot = processors.indexOf(Collections.min(processors));

        //repeating algorithm until queue is empty
        while(voltooid.size()!= input.size()){

            Mintijdslot = processors.indexOf(Collections.min(processors));

            while(queue.peek() != null && queue.peek().getAankomsttijd()<= processors.get(Mintijdslot)){
                wachtende.add(queue.poll());
            }

            for (Process p : wachtende){
                double rr=(p.getServicetijdNodig()+(processors.get(Mintijdslot) -p.getAankomsttijd())/(p.getServicetijdNodig()));
                p.setResponsRatio(rr);
            }

            if (!wachtende.isEmpty()) {

                hulp=wachtende.poll();
                hulp.setStarttijd(processors.get(Mintijdslot));
                processors.set(Mintijdslot,processors.get(Mintijdslot) + hulp.getServicetijd());
                hulp.setEindtijd(processors.get(Mintijdslot));
                hulp.calculate();
                wachttijd += hulp.getWachttijd();
                normomlooptijd += hulp.getNormomlooptijd();
                omlooptijd += hulp.getOmlooptijd();
                voltooid.add(hulp);

            }else {
                processors.set(Mintijdslot,processors.get(Mintijdslot)+1);
            }
        }

        wachttijd = wachttijd / input.size();
        normomlooptijd = normomlooptijd / input.size();
        omlooptijd = omlooptijd / input.size();
        wachttijd = wachttijd / aantal;
        
        omlooptijd = omlooptijd / aantal;

        System.out.println("HRRN: \tWachttijd: " + wachttijd + "\tGenorm. Omlooptijd: " + normomlooptijd + "\tOmlooptijd: " + omlooptijd);
        return voltooid;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q, int slice) {
        return schedule(q);
    }

}