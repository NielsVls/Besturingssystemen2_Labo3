package Algorithms;

import Support.Process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class MultiSRT extends Scheduler {

    int aantal;
    ArrayList<Integer> processors;

    public MultiSRT(int aantal){this.aantal = aantal;
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

        PriorityQueue<Process> voltooid = new PriorityQueue<>();
        PriorityQueue<Process> wachtende = new PriorityQueue<>(10,(a, b)->a.getServicetijd()-b.getServicetijd());
        PriorityQueue<Process> huidige = new PriorityQueue<>();
        Process hulp;

        int Mintijdslot = processors.indexOf(Collections.min(processors));
    
        while(voltooid.size()!= input.size()){

            Mintijdslot = processors.indexOf(Collections.min(processors));

            if(!huidige.isEmpty()){

                hulp=huidige.peek();
                hulp.decreaseServicetime();

                if (hulp.getServicetijd()==0){
                    hulp=huidige.poll();

                    assert hulp != null;
                    hulp.setEindtijd(processors.get(Mintijdslot));
                    hulp.calculate();

                    voltooid.add(hulp);

                    wachttijd += hulp.getWachttijd();
                    normomlooptijd += hulp.getNormomlooptijd();
                    omlooptijd += hulp.getOmlooptijd();
                }
            }

            while(queue.peek() != null && queue.peek().getAankomsttijd()<=processors.get(Mintijdslot))
                wachtende.add(queue.poll());

            if(huidige.isEmpty() && !wachtende.isEmpty()){

                hulp=wachtende.poll();

                hulp.setStarttijd(processors.get(Mintijdslot));

                huidige.add(hulp);

            } else if (!huidige.isEmpty() && !wachtende.isEmpty()){
                hulp=huidige.peek();


                if(hulp.getServicetijdNodig()>wachtende.peek().getServicetijdNodig()){
                    hulp=huidige.poll();
                    Process process=wachtende.peek();
                    assert process != null;
                    if(process.getStarttijd()==0)
                        process.setStarttijd(processors.get(Mintijdslot));

                    huidige.add(wachtende.poll());
                    wachtende.add(hulp);
                }
            }

            processors.set(Mintijdslot,processors.get(Mintijdslot)+1);

        }
        wachttijd = wachttijd / input.size();
        normomlooptijd = normomlooptijd / input.size();
        omlooptijd = omlooptijd / input.size();
        wachttijd = wachttijd / aantal;
        
        omlooptijd = omlooptijd / aantal;

        System.out.println("SRT: \tWachttijd: " + wachttijd + "   \tGenorm. Omlooptijd: " + normomlooptijd + "\tOmlooptijd: " + omlooptijd);
        return voltooid;
    }

    @Override
    public PriorityQueue<Process> schedule(Queue<Process> q, int slice) {
        return schedule(q);
    }

}
