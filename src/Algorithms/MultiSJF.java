package Algorithms;

import Support.Process;

import java.util.*;

public class MultiSJF extends Scheduler {

    int aantal;
    ArrayList<Integer> processors;

    public MultiSJF(int aantal){this.aantal = aantal;
         processors = new ArrayList<Integer>();
         for (int i = 0;i<aantal;i++){
             processors.add(i,0);
         }
    }

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

        int Mintijdslot = processors.indexOf(Collections.min(processors));

        while(voltooid.size()!=input.size()){

            Mintijdslot = processors.indexOf(Collections.min(processors));

            while(queue.peek() != null && queue.peek().getAankomsttijd()<=processors.get(Mintijdslot)){
                wachtende.add(queue.poll());
            }
            if (!wachtende.isEmpty()) {

                hulp=wachtende.poll();

                hulp.setStarttijd(processors.get(Mintijdslot));
                processors.set(Mintijdslot,processors.get(Mintijdslot) + hulp.getServicetijd());
                hulp.setEindtijd(processors.get(Mintijdslot));

                hulp.calculate();

                voltooid.add(hulp);

                wachttijd += hulp.getWachttijd();
                normomlooptijd += hulp.getNormomlooptijd();
                omlooptijd += hulp.getOmlooptijd();
            }else {
                    processors.set(Mintijdslot,processors.get(Mintijdslot)+1);
                

            }

        }

        wachttijd = wachttijd / input.size();
        normomlooptijd = normomlooptijd / input.size();
        omlooptijd = omlooptijd / input.size();
        wachttijd = wachttijd / aantal;
        normomlooptijd = normomlooptijd / aantal;
        omlooptijd = omlooptijd / aantal;

        System.out.println("SJF: \tWachttijd: " + wachttijd + "\tGenorm. Omlooptijd: " + normomlooptijd + "\tOmlooptijd: " + omlooptijd);
        return voltooid;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q, int slice) {
        return schedule(q);
    }

}
