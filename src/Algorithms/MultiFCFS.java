package Algorithms;

import java.util.Queue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import Support.Process;

public class MultiFCFS extends Scheduler {
    int aantal;
    ArrayList<Integer> processors;

    public MultiFCFS(int aantal){this.aantal = aantal;
         processors = new ArrayList<Integer>();
    }
    
    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input) throws NullPointerException {

        Queue<Process> queue = new LinkedList<>();
        PriorityQueue<Process> voltooid = new PriorityQueue<>();
        int counter = 0;
        Process hulp;
        
        //adding all the processes to the queue
        for (Process process : input) {
            queue.add(new Process(process));
        }

        //repeating algorithm until queue is empty
        while (!queue.isEmpty()) {
            hulp = queue.poll();

            if (counter <= aantal-1){ 
                processors.add(hulp.getAankomsttijd() + hulp.getServicetijd());
                counter++;
            }else{

            
            int Mintijdslot = processors.indexOf(Collections.min(processors));

            if (processors.get(Mintijdslot) < hulp.getAankomsttijd()) {

                processors.set(Mintijdslot,hulp.getAankomsttijd() + hulp.getServicetijd());
                hulp.setStarttijd(hulp.getAankomsttijd());

            } else {

                hulp.setStarttijd(processors.get(Mintijdslot));
                processors.set(Mintijdslot,processors.get(Mintijdslot)+hulp.getServicetijd());

            }

            hulp.setEindtijd(processors.get(Mintijdslot));
            hulp.calculate();
            voltooid.add(hulp);
            wachttijd += hulp.getWachttijd();
            normomlooptijd += hulp.getNormomlooptijd();
            omlooptijd += hulp.getOmlooptijd();
        
    }}
        //gemiddelde waarden berekenen
        wachttijd = wachttijd / input.size();
        normomlooptijd = normomlooptijd / input.size();
        omlooptijd = omlooptijd / input.size();
        wachttijd = wachttijd / aantal;
        normomlooptijd = normomlooptijd / aantal;
        omlooptijd = omlooptijd / aantal;

        System.out.println("FCFS: \tWachttijd: " + wachttijd + "\tGenorm. Omlooptijd: " + normomlooptijd + "\tOmlooptijd: " + omlooptijd);
        return voltooid;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> input,int slice) throws NullPointerException {
        return schedule(input);
    }

}
